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

package com.gm.archmvvm.base;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gm.archmvvm.mvvm.IViewModel;
import com.gm.lifecycle.delegate.IActivity;

import javax.inject.Inject;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * MVVM GmActivity
 */

public abstract class GmActivity<DB extends ViewDataBinding, VM extends IViewModel>
        extends AppCompatActivity implements IActivity {
    protected final String TAG = this.getClass().getName();

    //DataBinding
    protected DB mBinding;

    //MVVM ViewModel
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
    protected VM mViewModel;//instance in subclass; Automatic destruction

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the DataBinding
        mBinding = DataBindingUtil.setContentView(this, initView(savedInstanceState));
        initData(savedInstanceState);
        if (mViewModel != null)
            getLifecycle().addObserver((LifecycleObserver) mViewModel);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }


    @Override
    public boolean useFragment() {
        return true;
    }

    @SuppressWarnings("all")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /**
         * New posture: Save data via ViewModel.
         *  @see <a href="https://developer.android.com/topic/libraries/architecture/viewmodel.html#viewmodel_vs_savedinstancestate">ViewModel vs SavedInstanceState</a>
         */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mBinding = null;
        this.mViewModelFactory = null;
        if (getLifecycle() != null && mViewModel != null)//Remove LifecycleObserver
            getLifecycle().removeObserver((LifecycleObserver) mViewModel);
        this.mViewModel = null;
    }
}