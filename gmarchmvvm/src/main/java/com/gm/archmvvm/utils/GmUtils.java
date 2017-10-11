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

package com.gm.archmvvm.utils;

import android.app.Application;
import android.content.Context;

import com.gm.archmvvm.base.IGm;
import com.gm.archmvvm.di.component.GmComponent;
import com.gm.repository.utils.Preconditions;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * <p>
 * https://stackoverflow.com/questions/70689/what-is-an-efficient-way-to-implement-a-singleton-pattern-in-java
 * Get GmComponent to get everything in the frame
 * {@link GmComponent}
 */

public enum GmUtils {
    INSTANCE;

    public GmComponent obtainGmComponent(Context context) {
        return obtainGmComponent((Application) context.getApplicationContext());

    }

    public GmComponent obtainGmComponent(Application application) {
        Preconditions.checkState(application instanceof IGm, "Application does not implements IGm");
        return ((IGm) application).getGmComponent();
    }
}
