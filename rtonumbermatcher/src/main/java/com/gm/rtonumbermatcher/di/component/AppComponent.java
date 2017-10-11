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

package com.gm.rtonumbermatcher.di.component;

import com.gm.archmvvm.di.component.GmComponent;
import com.gm.archmvvm.di.scope.AppScope;
import com.gm.rtonumbermatcher.app.RtoApp;
import com.gm.rtonumbermatcher.di.module.AppModule;

import dagger.Component;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 */
@AppScope
@Component(dependencies = GmComponent.class,
        modules = AppModule.class)
public interface AppComponent {
    void inject(RtoApp mainApp);
}
