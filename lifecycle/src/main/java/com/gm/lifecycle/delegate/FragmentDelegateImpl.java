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

package com.gm.lifecycle.delegate;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import org.simple.eventbus.EventBus;

import dagger.android.support.AndroidSupportInjection;


/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Fragment Lifecycle Proxy Interface Implementation Class
 */
public class FragmentDelegateImpl implements FragmentDelegate {

    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private IFragment iFragment;

    public FragmentDelegateImpl(FragmentManager fragmentManager, Fragment fragment) {
        this.mFragmentManager = fragmentManager;
        this.mFragment = fragment;
        this.iFragment = (IFragment) fragment;
    }

    @Override
    public void onAttach(Context context) {
        //If you want to use eventbus please return this method to true
        if (iFragment.useEventBus()) {
            //Register to the event main line
            EventBus.getDefault().register(mFragment);
        }
        if (iFragment.injectable()) {
            //Dagger.Android Dependent injection
            AndroidSupportInjection.inject(mFragment);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onCreateView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
        iFragment.initData(savedInstanceState);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDetach() {
        //If you want to use eventbus please return this method to true
        if (iFragment.useEventBus()) {
            //Register to the event main line
            EventBus.getDefault().unregister(mFragment);
        }
        this.mFragmentManager = null;
        this.mFragment = null;
        this.iFragment = null;
    }

    /**
     * Return true if the fragment is currently added to its activity.
     */
    @Override
    public boolean isAdded() {
        return mFragment != null && mFragment.isAdded();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    protected FragmentDelegateImpl(Parcel in) {
        this.mFragmentManager = in.readParcelable(FragmentManager.class.getClassLoader());
        this.mFragment = in.readParcelable(Fragment.class.getClassLoader());
        this.iFragment = in.readParcelable(IFragment.class.getClassLoader());
    }

    public static final Creator<FragmentDelegateImpl> CREATOR = new Creator<FragmentDelegateImpl>() {
        @Override
        public FragmentDelegateImpl createFromParcel(Parcel source) {
            return new FragmentDelegateImpl(source);
        }

        @Override
        public FragmentDelegateImpl[] newArray(int size) {
            return new FragmentDelegateImpl[size];
        }
    };
}
