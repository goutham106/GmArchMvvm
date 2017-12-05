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

package com.gm.archmvvm.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableField;

import com.gm.repository.http.Status;

import org.simple.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * <p>
 * MVVM BaseViewModel (ViewModel no longer holds View，But store and manage UI-related data)
 * ViewModel objects are scoped to the Lifecycle passed to the ViewModelProvider when getting the ViewModel.
 * The ViewModel stays in memory until the Lifecycle it’s scoped to goes away permanently
 * —in the case of an activity, when it finishes;
 * in the case of a fragment, when it’s detached.
 *
 * @see <a href="https://developer.android.com/topic/libraries/architecture/viewmodel.html">ViewModel</a>
 */

public class BaseViewModel<M extends IModel> extends AndroidViewModel
        implements IViewModel, LifecycleObserver {
    //Data request status
    public final ObservableField<Status> STATUS = new ObservableField<>();
    protected M mModel;

    public BaseViewModel(Application application) {
        super(application);
    }

    public BaseViewModel(Application application, M model) {
        super(application);
        this.mModel = model;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onStart() {
        if (useEventBus())
            EventBus.getDefault().register(this);//Register eventbus
    }

    //Whether to use EventBus
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (useEventBus())
            EventBus.getDefault().unregister(this);//Unregister eventbus

    }

}
