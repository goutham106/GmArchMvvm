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
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.view.View;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Used to manage all activities, and activities in the foreground
 * You can perform the corresponding method by directly holding the AppManager object
 * You can also use {@link #post (Message)},
 * remote control implementation of the corresponding method, usage and EventBus similar
 */
@Singleton
public final class AppManager {
    public static final String APPMANAGER_MESSAGE = "appmanager_message";
    /**
     * true Do not need to join the Activity container for unified management, and vice versa
     */
    public static final String IS_NOT_ADD_ACTIVITY_LIST = "is_not_add_activity_list";
    public static final int START_ACTIVITY = 5000;
    public static final int SHOW_SNACKBAR = 5001;
    public static final int KILL_ALL = 5002;
    public static final int APP_EXIT = 5003;
    protected final String TAG = this.getClass().getSimpleName();
    /**
     * Manage all activities
     */
    public List<Activity> mActivityList;
    private Application mApplication;
    /**
     * The current activity in the foreground
     */
    private Activity mCurrentActivity;
    /**
     * Provides an onReceive method for external extension AppManager
     */
    private HandleListener mHandleListener;

    @Inject
    public AppManager(Application application) {
        this.mApplication = application;
        EventBus.getDefault().register(this);
    }

    /**
     * Use this method to remotely control AppManager,Make {@link #onReceive (Message)} execute the corresponding method
     *
     * @param msg Message news
     */
    public static void post(Message msg) {
        EventBus.getDefault().post(msg, APPMANAGER_MESSAGE);
    }

    /**
     * Through the eventbus post event, the remote control performs the corresponding method
     */
    @Subscriber(tag = APPMANAGER_MESSAGE, mode = ThreadMode.MAIN)
    public void onReceive(Message message) {
        switch (message.what) {
            case START_ACTIVITY:
                if (message.obj == null) {
                    break;
                }
                dispatchStart(message);
                break;
            case SHOW_SNACKBAR:
                if (message.obj == null) {
                    break;
                }
                showSnackbar((String) message.obj, message.arg1 != 0);
                break;
            case KILL_ALL:
                killAll();
                break;
            case APP_EXIT:
                appExit();
                break;
            default:
                Timber.tag(TAG).w("The message.what not match");
                break;
        }
        if (mHandleListener != null) {
            mHandleListener.handleMessage(this, message);
        }
    }

    private void dispatchStart(Message message) {
        if (message.obj instanceof Intent) {
            startActivity((Intent) message.obj);
        } else if (message.obj instanceof Class) {
            startActivity((Class) message.obj);
        }
    }

    public HandleListener getHandleListener() {
        return mHandleListener;
    }

    /**
     * The method @{@link #onReceive} (remote remote control AppManager) provided to the external extension AppManager
     * Suggested in {@link com.gm.lifecycle.ConfigLifecycle # injectAppLifecycle (Context, List)}
     * Use {@link AppLifecycles # onCreate (Application)} in the App initialization, use this method passed custom {@link HandleListener}
     *
     * @param handleListener
     */
    public void setHandleListener(HandleListener handleListener) {
        this.mHandleListener = handleListener;
    }

    /**
     * Let the activity in the foreground use snackbar to display the text content
     *
     * @param message
     * @param isLong
     */
    public void showSnackbar(String message, boolean isLong) {
        if (getCurrentActivity() == null) {
            Timber.tag(TAG).w("mCurrentActivity == null when showSnackbar(String,boolean)");
            return;
        }
        View view = getCurrentActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, message, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
    }


    /**
     * Let the activity at the top of the stack open the specified activity
     *
     * @param intent
     */
    public void startActivity(Intent intent) {
        if (getTopActivity() == null) {
            Timber.tag(TAG).w("mCurrentActivity == null when startActivity(Intent)");
            //If there is no front of the activity on the use of new_task mode to start the activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mApplication.startActivity(intent);
            return;
        }
        getTopActivity().startActivity(intent);
    }

    /**
     * Let the activity at the top of the stack open the specified activity
     *
     * @param activityClass
     */
    public void startActivity(Class activityClass) {
        startActivity(new Intent(mApplication, activityClass));
    }

    /**
     * Release resources
     */
    public void release() {
        EventBus.getDefault().unregister(this);
        mActivityList.clear();
        mHandleListener = null;
        mActivityList = null;
        mCurrentActivity = null;
        mApplication = null;
    }

    /**
     * Get the activity in the foreground (to ensure that the activity is in the visible state, that is not called onStop), to obtain the activity duration
     * Is before the onStop, so if this activity calls the onStop method, no other activity back to the foreground (the user returns to the desktop or open other App will appear this situation)
     * It is possible to call {@link #getCurrentActivity()} to return null, so please note that the scene is not the same as {@link #getTopActivity()}
     * <p>
     * Example usage:
     * Use scenes that are more appropriate, only need to be performed in the visible state of the activity
     * If the background service to perform a task, you need to let the front of the activity, to make a response to the operation or other operations, such as pop-up Dialog,
     * then in the service can use {@link #getCurrentActivity()
     * If the return is null, that there is no foreground activity (the user returns to the desktop or open other App will appear this situation), then do nothing, not null, then pop up Dialog
     *
     *
     * @return
     */
    public Activity getCurrentActivity() {
        return mCurrentActivity != null ? mCurrentActivity : null;
    }

    /**
     * Will be in the foreground of the activity assigned to the currentActivity, pay attention to this method is in the implementation of the onResume method will be the top of the top of the activity assigned to the currentActivity
     * So at the top of the stack implementation of the onCreate method using {@link #getCurrentActivity ()} is not the current top of the stack activity, may be the last activity
     * If {@link #getCurrentActivity ()} is used when the first activity of the App executes the onCreate method, a return is null
     * To avoid this, please use {@link #getTopActivity ()}
     *
     * @param currentActivity
     */
    public void setCurrentActivity(Activity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }

    /**
     * Get the activity at the top of the stack, this method does not guarantee that the obtained activity is in a visible state, even if the App enters the background will return to the current top of the activity
     * So the basic situation will not appear null, more suitable for most of the use of scenes, such as startActivity, Glide load pictures
     *
     * @return
     */
    public Activity getTopActivity() {
        if (mActivityList == null) {
            Timber.tag(TAG).w("mActivityList == null when getTopActivity()");
            return null;
        }
        return mActivityList.size() > 0 ? mActivityList.get(mActivityList.size() - 1) : null;
    }


    /**
     * Returns a collection that stores all undeveloped activities
     *
     * @return
     */
    public List<Activity> getActivityList() {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        return mActivityList;
    }


    /**
     * Add the activity to the collection
     */
    public void addActivity(Activity activity) {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        synchronized (AppManager.class) {
            if (!mActivityList.contains(activity)) {
                mActivityList.add(activity);
            }
        }
    }

    /**
     * Delete the specified activity instance in the collection
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (mActivityList == null) {
            Timber.tag(TAG).w("mActivityList == null when removeActivity(Activity)");
            return;
        }
        synchronized (AppManager.class) {
            if (mActivityList.contains(activity)) {
                mActivityList.remove(activity);
            }
        }
    }

    /**
     * Removes the activity at the specified location in the collection
     *
     * @param location
     */
    public Activity removeActivity(int location) {
        if (mActivityList == null) {
            Timber.tag(TAG).w("mActivityList == null when removeActivity(int)");
            return null;
        }
        synchronized (AppManager.class) {
            if (location > 0 && location < mActivityList.size()) {
                return mActivityList.remove(location);
            }
        }
        return null;
    }

    /**
     * Closes all instances of the specified activity class
     *
     * @param activityClass
     */
    public void killActivity(Class<?> activityClass) {
        if (mActivityList == null) {
            Timber.tag(TAG).w("mActivityList == null when killActivity(Class)");
            return;
        }
        for (Activity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                activity.finish();
            }
        }
    }


    /**
     * Whether the specified activity instance is alive
     *
     * @param activity
     * @return
     */
    public boolean activityInstanceIsLive(Activity activity) {
        if (mActivityList == null) {
            Timber.tag(TAG).w("mActivityList == null when activityInstanceIsLive(Activity)");
            return false;
        }
        return mActivityList.contains(activity);
    }


    /**
     * Whether the specified activity class is alive (there may be multiple instances of the same activity class)
     *
     * @param activityClass
     * @return
     */
    public boolean activityClassIsLive(Class<?> activityClass) {
        if (mActivityList == null) {
            Timber.tag(TAG).w("mActivityList == null when activityClassIsLive(Class)");
            return false;
        }
        for (Activity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Get an instance of the specified activity class, and null if no (the same activity class has multiple instances, the first instance is returned)
     *
     * @param activityClass
     * @return
     */
    public Activity findActivity(Class<?> activityClass) {
        if (mActivityList == null) {
            Timber.tag(TAG).w("mActivityList == null when findActivity(Class)");
            return null;
        }
        for (Activity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                return activity;
            }
        }
        return null;
    }


    /**
     * Close all activity
     */
    public void killAll() {
        //        while (getActivityList().size() != 0) { //This method can only be compatible with LinkedList
        //            getActivityList().remove(0).finish();
        //        }

        Iterator<Activity> iterator = getActivityList().iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            iterator.remove();
            next.finish();
        }
    }

    /**
     * Closes all activities and excludes the specified activity
     *
     * @param excludeActivityClasses activity class
     */
    public void killAll(Class<?>... excludeActivityClasses) {
        List<Class<?>> excludeList = Arrays.asList(excludeActivityClasses);
        Iterator<Activity> iterator = getActivityList().iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();

            if (excludeList.contains(next.getClass())) {
                continue;
            }

            iterator.remove();
            next.finish();
        }
    }

    /**
     * Closes all activities and excludes the specified activity
     *
     * @param excludeActivityName full path of activity
     */
    public void killAll(String... excludeActivityName) {
        List<String> excludeList = Arrays.asList(excludeActivityName);
        Iterator<Activity> iterator = getActivityList().iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();

            if (excludeList.contains(next.getClass().getName())) {
                continue;
            }

            iterator.remove();
            next.finish();
        }
    }


    /**
     * Exit the application
     */
    public void appExit() {
        try {
            killAll();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface HandleListener {
        /**
         * Extended AppManager remote control function
         *
         * @param appManager AppManager
         * @param message    Message
         */
        void handleMessage(AppManager appManager, Message message);
    }
}
