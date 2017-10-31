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

import android.app.Application;
import android.content.Context;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Application lifecycle
 */

public interface AppLifecycles {

    /**
     * proxy {@link Application#attachBaseContext(Context)}
     *
     * @param context Context
     */
    void attachBaseContext(Context context);

    /**
     * proxy {@link Application#onCreate()}
     *
     * @param application Application
     */
    void onCreate(Application application);

    /**
     * proxy {@link Application#onTerminate()}
     *
     * @param application Application
     */
    void onTerminate(Application application);
}
