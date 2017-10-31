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
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gm.archmvvm.mvvm.IViewModel;
import com.gm.lifecycle.delegate.IFragment;

import javax.inject.Inject;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * MVVM BaseFragment
 *
 * If you only use DataBinding, VM generic can pass {@link com.gm.archmvvm.mvvm.BaseViewModel}
 */

public abstract class BaseFragment<DB extends ViewDataBinding, VM extends IViewModel>
        extends Fragment implements IFragment {
    protected final String TAG = this.getClass().getName();
    /**
     * Is visible for lazy loading
     */
    protected boolean mVisible = false;
    /**
     * Whether the first load, for lazy loading
     */
    protected boolean mFirst = true;
    private View mRootView;

    /**
     * ViewDataBinding
     */
    protected DB mBinding;

    /**
     * MVVM ViewModel ViewModelProvider.Factory
     */
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
    /**
     * instance in subclass; Automatic destruction
     */
    protected VM mViewModel;


    public BaseFragment() {
        //You must ensure that when the Fragment is instantiated, setArguments()
        setArguments(new Bundle());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = initView(inflater, container, savedInstanceState);
        if (mViewModel != null) {
            getLifecycle().addObserver((LifecycleObserver) mViewModel);
        }
        //Visible, and is the first time to load
        if (mVisible && mFirst) {
            onFragmentVisibleChange(true);
        }
        return mRootView;
    }


    @Override
    public boolean injectable() {
        //Default dependency injection
        return true;
    }


    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mVisible = isVisibleToUser;
        if (mRootView == null) {
            return;
        }
        //Visible and invoked when loaded for the first time
        onFragmentVisibleChange(mVisible & mFirst);
    }

    /**
     * The method is called when the current Fragment visible state changes.
     * If the current Fragment is the first time to load, wait for onCreateView will callback after the method,
     * Other situations callback timing is consistent with {@link #setUserVisibleHint (boolean)}
     * In the callback method you can do some loading data manipulation, and even the operation of the control.
     *
     * @param isVisible true not visible -> visible
     *                  false visible -> not visible
     *                       
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mBinding = null;
        this.mRootView = null;
        this.mViewModelFactory = null;
        //Removed LifecycleObserver
        if (mViewModel != null) {
            getLifecycle().removeObserver((LifecycleObserver) mViewModel);
        }
        this.mViewModel = null;
    }
}
