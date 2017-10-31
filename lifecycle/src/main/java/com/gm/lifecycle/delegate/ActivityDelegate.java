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
import android.os.Parcelable;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Activity Lifecycle agent interface
 */

public interface ActivityDelegate extends Parcelable {
    String ACTIVITY_DELEGATE = "activity_delegate";

    /**
     * proxy {@link Activity#onCreate(Bundle)}
     *
     * @param savedInstanceState Data Recovery
     */
    void onCreate(Bundle savedInstanceState);

    /**
     * proxy {@link Activity#onStart()}
     */
    void onStart();

    /**
     * proxy {@link Activity#onResume()}
     */
    void onResume();

    /**
     * proxy {@link Activity#onPause()}
     */
    void onPause();

    /**
     * proxy {@link Activity#onStop()}
     */
    void onStop();

    /**
     * proxy {@link Activity#onSaveInstanceState(Bundle)}
     *
     * @param outState Data is saved
     */
    void onSaveInstanceState(Bundle outState);

    /**
     * proxy {@link Activity#onDestroy()}
     */
    void onDestroy();
}
