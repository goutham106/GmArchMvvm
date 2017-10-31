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

package com.gm.lifecycle.utils;

import android.app.Application;
import android.content.Context;

import com.gm.lifecycle.delegate.ILifecycle;
import com.gm.lifecycle.di.component.LifecycleComponent;


/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * LifecycleComponent tool class
 */

public enum LifecycleUtils {
    /**
     * Enumeration of singleton mode implementation
     */
    INSTANCE;

    /**
     * Get {@link LifecycleComponent}, using Dagger's external exposure method
     *
     * @param context Context
     * @return LifecycleComponent
     */
    public LifecycleComponent obtainLifecycleComponent(Context context) {
        return obtainLifecycleComponent((Application) context.getApplicationContext());
    }

    /**
     * Get {@link LifecycleComponent}, using Dagger's external exposure method
     *
     * @param application Application
     * @return LifecycleComponent
     */
    public LifecycleComponent obtainLifecycleComponent(Application application) {
        Preconditions.checkState(application instanceof ILifecycle,
                "%s does not implements ILifecycle", application.getClass().getName());
        return ((ILifecycle) application).getLifecycleComponent();
    }

}
