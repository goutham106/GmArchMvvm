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

package com.gm.rtonumbermatcher.ui.main;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.gm.archmvvm.di.scope.AppScope;
import com.gm.archmvvm.mvvm.BaseViewModel;

import javax.inject.Inject;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/3/17.
 */
@AppScope
public class MainViewModel extends BaseViewModel<MainModel> {

    private MutableLiveData<String> mLocation; //This data can be shared with Fragment

    @Inject
    MainViewModel(Application application, MainModel model) {
        super(application, model);
    }


    public MutableLiveData<String> getLocation() {
        if (mLocation == null) {
            mLocation = new MutableLiveData<>();
            mLocation.setValue("RTO App");
        }
        return mLocation;
    }

}
