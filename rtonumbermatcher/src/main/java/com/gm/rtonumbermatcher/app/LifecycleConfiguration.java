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

package com.gm.rtonumbermatcher.app;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.gm.lifecycle.ConfigLifecycle;
import com.gm.lifecycle.delegate.AppLifecycles;
import com.gm.lifecycle.utils.LifecycleUtils;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/5/17.
 */

public class LifecycleConfiguration implements ConfigLifecycle {
    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        // AppDelegate.Lifecycle Of all methods will be in the base class Application corresponding to the life cycle is called, so in the corresponding method can be extended some of the logic you need
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        //Used to Llisten to the Activity callback, you can add some custom logic
        //lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                // In the configuration changes when the Fragment saved in the Activity configuration changes due to the reconstruction is to re-use has been created Fragment.
                //Activity rebuild, restore Fragment; onCreate () and onDestroy () will not be called
                // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
                // If you create a Fragment in the XML using the <Fragment /> tag, be sure to include the android: id or the android: tag attribute in the tag, otherwise setRetainInstance (true) is invalid
                // Binding a small amount of Fragment in Activity recommended to do so, if you need to bind more Fragment does not recommend setting this parameter, such as ViewPager need to show more Fragment
                f.setRetainInstance(true);
            }

            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                //This should be the detection of Fragment instead of FragmentLifecycleCallbacks.
                ((RefWatcher) (LifecycleUtils.INSTANCE.obtainLifecycleComponent(f.getContext()))
                        .extras()
                        .get(RefWatcher.class.getName()))
                        .watch(f);
            }
        });
    }
}
