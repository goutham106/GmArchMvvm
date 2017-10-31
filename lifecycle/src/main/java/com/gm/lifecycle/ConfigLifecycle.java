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

package com.gm.lifecycle;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.gm.lifecycle.delegate.AppLifecycles;

import java.util.List;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/4/17.
 */

public interface ConfigLifecycle {
    /**
     * Use {@link AppLifecycles} to inject some operations in the application's lifecycle
     *
     * @param context:    Context
     * @param lifecycles: List<AppLifecycles>
     */
    void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles);

    /**
     * Use {@link Application.ActivityLifecycleCallbacks} to inject some actions in the Activity lifecycle
     *
     * @param context:    Context
     * @param lifecycles: List<Application.ActivityLifecycleCallbacks>
     */
    void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles);


    /**
     * Use {@link FragmentManager.FragmentLifecycleCallbacks} to inject some actions in the Fragment lifecycle
     *
     * @param context:    Context
     * @param lifecycles: List<FragmentManager.FragmentLifecycleCallbacks>
     */
    void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles);
}
