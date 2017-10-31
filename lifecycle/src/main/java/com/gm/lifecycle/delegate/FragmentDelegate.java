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
import android.os.Parcelable;
import android.view.View;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Fragment Lifecycle Proxy Interface
 */

public interface FragmentDelegate extends Parcelable {

    String FRAGMENT_DELEGATE = "fragment_delegate";

    /**
     * proxy {@link android.support.v4.app.Fragment#onAttach(Context)}
     *
     * @param context Context
     */
    void onAttach(Context context);

    /**
     * proxy {@link android.support.v4.app.Fragment#onCreate(Bundle)}
     *
     * @param savedInstanceState Data Recovery
     */
    void onCreate(Bundle savedInstanceState);

    /**
     * proxy {@link android.support.v4.app.Fragment#onViewCreated(View, Bundle)}
     *
     * @param view               View
     * @param savedInstanceState Data Recovery
     */
    void onCreateView(View view, Bundle savedInstanceState);

    /**
     * proxy {@link android.support.v4.app.Fragment#onActivityCreated(Bundle)}
     *
     * @param savedInstanceState Data Recovery
     */
    void onActivityCreate(Bundle savedInstanceState);

    /**
     * proxy {@link android.support.v4.app.Fragment#onStart()}
     */
    void onStart();

    /**
     * proxy {@link android.support.v4.app.Fragment#onResume()}
     */
    void onResume();

    /**
     * proxy {@link android.support.v4.app.Fragment#onPause()}
     */
    void onPause();

    /**
     * proxy {@link android.support.v4.app.Fragment#onStop()}
     */
    void onStop();

    /**
     * proxy {@link android.support.v4.app.Fragment#onSaveInstanceState(Bundle)}
     *
     * @param outState Data is saved
     */
    void onSaveInstanceState(Bundle outState);

    /**
     * proxy {@link android.support.v4.app.Fragment#onDestroyView()}
     */
    void onDestroyView();

    /**
     * proxy {@link android.support.v4.app.Fragment#onDestroy()}
     */
    void onDestroy();

    /**
     * proxy {@link android.support.v4.app.Fragment#onDetach()}
     */
    void onDetach();

    /**
     * Whether the Fragment is added to the Activity
     *
     * Return true if the fragment is currently added to its activity.
     */
    boolean isAdded();
}

