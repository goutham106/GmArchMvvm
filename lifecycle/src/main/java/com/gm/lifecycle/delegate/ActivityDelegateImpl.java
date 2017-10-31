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

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Activity lifecycle agent interface implementation class
 */

public class ActivityDelegateImpl implements ActivityDelegate, HasSupportFragmentInjector {
    public static final Parcelable.Creator<ActivityDelegateImpl> CREATOR = new Parcelable.Creator<ActivityDelegateImpl>() {
        @Override
        public ActivityDelegateImpl createFromParcel(Parcel source) {
            return new ActivityDelegateImpl(source);
        }

        @Override
        public ActivityDelegateImpl[] newArray(int size) {
            return new ActivityDelegateImpl[size];
        }
    };
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;
    private Activity mActivity;
    private IActivity iActivity;

    public ActivityDelegateImpl(Activity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }

    protected ActivityDelegateImpl(Parcel in) {
        this.mActivity = in.readParcelable(Activity.class.getClassLoader());
        this.iActivity = in.readParcelable(IActivity.class.getClassLoader());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //If you want to use eventbus please return this method to true
        if (iActivity.useEventBus()) {
            //Register to the event main line
            EventBus.getDefault().register(mActivity);
        }
        //Dagger.Android depends on injection
        if (iActivity.injectable()) {
            AndroidInjection.inject(mActivity);
        }
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
    public void onDestroy() {
        //If you want to use eventbus please return this method to true
        if (iActivity.useEventBus())
            EventBus.getDefault().unregister(mActivity);
        this.iActivity = null;
        this.mActivity = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return this.mFragmentInjector;
    }
}

