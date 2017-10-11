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
 *
 * MVVM GmFragment
 */

public abstract class GmFragment<DB extends ViewDataBinding, VM extends IViewModel>
        extends Fragment implements IFragment {
    protected final String TAG = this.getClass().getName();
    protected boolean mVisible = false;//Is it visible?
    protected boolean mFirst = true;//Whether the first load
    private View mRootView;

    //DataBinding
    protected DB mBinding;

    //MVVM ViewModel
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
    protected VM mViewModel;//instance in subclass; Automatic destruction

    public GmFragment() {
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = initView(inflater, container, savedInstanceState);
        if (mViewModel != null)
            getLifecycle().addObserver((LifecycleObserver) mViewModel);
        if (mVisible && mFirst)//Visible, and is the first time to load
            onFragmentVisibleChange(true);
        return mRootView;
    }

    @Override
    public boolean injectable() {
        return true;//Default dependency injection
    }

    public boolean useEventBus() {
        return true;
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
     * In other cases, the timing of the callback is the same as {@link #setUserVisibleHint (boolean)}
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
        this.mBinding = null;
        this.mRootView = null;
        this.mViewModelFactory = null;
        if (getLifecycle() != null && mViewModel != null)//Remove LifecycleObserver
            getLifecycle().removeObserver((LifecycleObserver) mViewModel);
        this.mViewModel = null;
    }
}
