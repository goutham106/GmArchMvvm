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

import android.content.Context;

import com.gm.archmvvm.base.ConfigGm;
import com.gm.archmvvm.di.module.GmConfigModule;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/5/17.
 */

public class GmConfiguration implements ConfigGm {

    @Override
    public void applyOptions(Context context, GmConfigModule.Builder builder) {
        //Customize your own image loading logic
        //builder.imageLoaderStrategy(new CustomLoaderStrategy());
    }


}
