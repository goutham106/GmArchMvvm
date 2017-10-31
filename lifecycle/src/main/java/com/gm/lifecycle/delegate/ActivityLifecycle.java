/*
 * Copyright (c) 2017 Gowtham Parimelazhagan.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gm.lifecycle.delegate;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.gm.lifecycle.ConfigLifecycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Activity life cycle callback.
 * In the Activity corresponding to the lifecycle of the super () method.
 */
@Singleton
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private AppManager mAppManager;
    private Application mApplication;
    private Map<String, Object> mExtras;
    private FragmentManager.FragmentLifecycleCallbacks mFragmentLifecycle;
    private List<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycles;

    @Inject
    public ActivityLifecycle(AppManager appManager, Application application, Map<String, Object> extras) {
        this.mAppManager = appManager;
        this.mApplication = application;
        this.mExtras = extras;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Timber.w(activity + " ---> onActivityCreated");

        //If the intent contains this field, and is true, do not join the list for unified management
        boolean isNotAdd = false;
        if (activity.getIntent() != null) {
            isNotAdd = activity.getIntent().getBooleanExtra(AppManager.IS_NOT_ADD_ACTIVITY_LIST, false);
        }
        if (!isNotAdd) {
            mAppManager.addActivity(activity);
        }
        //ActivityDelegate Configuration
        if (activity instanceof IActivity && activity.getIntent() != null) {
            ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
            if (activityDelegate == null) {
                activityDelegate = new ActivityDelegateImpl(activity);
                activity.getIntent().putExtra(ActivityDelegate.ACTIVITY_DELEGATE, activityDelegate);
            }
            activityDelegate.onCreate(savedInstanceState);
        }

        // For each Activity configuration Fragment monitoring,
        // Activity can be used {@link IActivity # useFragment ()} set whether to use the monitor
        registerFragmentCallbacks(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Timber.w(activity + " ---> onActivityStarted");
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.w(activity + " ---> onActivityResumed");
        mAppManager.setCurrentActivity(activity);

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.w(activity + " ---> onActivityPaused");
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.w(activity + " ---> onActivityStopped");
        if (mAppManager.getCurrentActivity() == activity) {
            mAppManager.setCurrentActivity(null);
        }

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Timber.w(activity + " ---> onActivitySaveInstanceState");
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.w(activity + " ---> onActivityDestroyed");
        mAppManager.removeActivity(activity);

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onDestroy();
            activity.getIntent().removeExtra(ActivityDelegate.ACTIVITY_DELEGATE);
        }
    }

    /**
     * To set the lifecycle of all Fragments settings for each Activity, the Activity can be accessed through {@link IActivity # useFragment ()}
     * Set whether to use the monitor, if the Activity returns false, then the activities of all the following Fragment will not be able to use {@link FragmentDelegate}
     *
     * @param activity: Activity
     */
    private void registerFragmentCallbacks(Activity activity) {
        boolean useFragment = !(activity instanceof IActivity) || ((IActivity) activity).useFragment();
        if (activity instanceof FragmentActivity && useFragment) {

            if (mFragmentLifecycle == null) {
                mFragmentLifecycle = new FragmentLifecycle();
            }
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycle, true);

            if (mFragmentLifecycles == null && mExtras.containsKey(ConfigLifecycle.class.getName())) {
                mFragmentLifecycles = new ArrayList<>();
                List<ConfigLifecycle> lifecycles = (List<ConfigLifecycle>) mExtras.get(ConfigLifecycle.class.getName());
                for (ConfigLifecycle lifecycle : lifecycles) {
                    lifecycle.injectFragmentLifecycle(mApplication, mFragmentLifecycles);
                }
                mExtras.remove(ConfigLifecycle.class.getName());
            }

            for (FragmentManager.FragmentLifecycleCallbacks fragmentLifecycle : mFragmentLifecycles) {
                ((FragmentActivity) activity).getSupportFragmentManager()
                        .registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
            }
        }
    }

    private ActivityDelegate fetchActivityDelegate(Activity activity) {
        ActivityDelegate activityDelegate = null;
        if (activity instanceof IActivity && activity.getIntent() != null) {
            activity.getIntent().setExtrasClassLoader(getClass().getClassLoader());
            activityDelegate = activity.getIntent().getParcelableExtra(ActivityDelegate.ACTIVITY_DELEGATE);
        }
        return activityDelegate;
    }

}
