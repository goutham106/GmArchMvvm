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
import android.support.multidex.MultiDex;

import com.gm.lifecycle.delegate.AppLifecycles;
import com.gm.lifecycle.utils.LifecycleUtils;
import com.gm.repository.utils.RepositoryUtils;
import com.gm.rtonumbermatcher.BuildConfig;
import com.gm.rtonumbermatcher.util.CrashUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 */

public class AppLifecyclesImpl implements AppLifecycles {

    @Override
    public void attachBaseContext(Context base) {
         MultiDex.install(base);  //Here is the first implementation than onCreate,Commonly used in MultiDex initialization, plug-in framework for initialization
    }

    @Override
    public void onCreate(Application application) {
        if (BuildConfig.LOG_DEBUG) {
            //Timber is initialized
            //Timber is a log frame container, the external use of a unified Api, the internal can be dynamically switched to any log frame (print strategy) for log printing
            //And support the addition of multiple log framework (print strategy), so that an external call Api, the internal can do at the same time using multiple strategies
            //Such as adding three strategies, a print log, a log to save the local, a log upload server
            Timber.plant(new Timber.DebugTree());
            // If you want to switch the frame to Logger to print the log, use the following code, if you want to switch to another log frame, expand as follows
            //                    Logger.addLogAdapter(new AndroidLogAdapter());
            //                    Timber.plant(new Timber.DebugTree() {
            //                        @Override
            //                        protected void log(int priority, String tag, String message, Throwable t) {
            //                            Logger.log(priority, tag, message, t);
            //                        }
            //                    });
        }
        //leakCanary memory leak check
        RepositoryUtils.INSTANCE.obtainRepositoryComponent(application)
                .extras()
                .put(RefWatcher.class.getName(), BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);

        //Set the global Crash monitor
        CrashUtils.init(application, RepositoryUtils.INSTANCE.obtainRepositoryComponent(application).cacheFile());

        //Extended AppManager remote control function
        LifecycleUtils.INSTANCE.obtainLifecycleComponent(application).appManager()
                .setHandleListener((appManager, message) -> {
                    Timber.d("handleMessage: " + message.what);
                    //AppManager.post(message);
                    //handle message
                });
    }

    @Override
    public void onTerminate(Application application) {

    }
}

