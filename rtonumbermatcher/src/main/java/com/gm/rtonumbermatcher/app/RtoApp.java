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

import com.gm.archmvvm.base.BaseApplication;
import com.gm.rtonumbermatcher.di.component.AppComponent;
import com.gm.rtonumbermatcher.di.component.DaggerAppComponent;
import com.gm.rtonumbermatcher.util.ToastUtils;
import com.gm.rtonumbermatcher.util.Utils;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 */

public class RtoApp extends BaseApplication {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent
                .builder()
                .gmComponent(getGmComponent())
                .build();
        mAppComponent.inject(this);

        Utils.init(this);
        ToastUtils.init(true);
    }


    public AppComponent getAppComponent() {
        return this.mAppComponent;
    }


}
