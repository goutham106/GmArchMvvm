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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gm.lifecycle.delegate.IFragment;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * MVVM BaseFragment
 */

public abstract class BaseFragment extends Fragment
        implements IFragment {
    protected final String TAG = this.getClass().getName();
    protected boolean mVisible = false;//Is it visible?
    protected boolean mFirst = true;//Whether the first load
    private View mRootView;

    public BaseFragment() {
        setArguments(new Bundle());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = initView(inflater, container, savedInstanceState);
        if (mVisible && mFirst)//Visible, and is the first time to load
            onFragmentVisibleChange(true);
        return mRootView;
    }

    public boolean useEventBus() {
        return true;
    }

    @Override
    public boolean injectable() {
        return true;//Default dependency injection
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mVisible = isVisibleToUser;
        if (mRootView == null)
            return;
        //Visible and invoked when loaded for the first time
        onFragmentVisibleChange(mVisible & mFirst);
    }

    /**
     * This method is called when the current Fragment visible state changes.
     * If the current Fragment is the first time to load, wait for onCreateView will callback after the method,
     * In other cases the timing of the callback {@link #setUserVisibleHint(boolean)}Consistent
     * In the callback method you can do some loading data operations, and even the operation of the control.
     *
     * @param isVisible true  Not visible -> visible
     *                  false Visible -> not visible
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mRootView = null;
    }
}

