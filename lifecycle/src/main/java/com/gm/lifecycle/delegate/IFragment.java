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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Fragment public interface
 */

public interface IFragment {
    /**
     * UI initialization
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * Data initialization
     *
     * @param savedInstanceState Bundle
     */
    void initData(Bundle savedInstanceState);

    /**
     * Activity and Fragment Communication Interface
     * This method is to make external calls to make Fragment to do some operations, such as external Fragment Fragment object to do some of the implementation of the method,
     * It is recommended that when there are multiple methods that require external calls, {@link android.os.Message}, through what field, to distinguish between different methods,
     * In this method can switch do different operations, so that you can use a unified entrance to do different things
     * <p>
     * New posture: You can use the Activity ViewModel shared data to include the Fragment, with LiveData easy to use burst.
     *
     * @param data Object
     * @see <a href="https://developer.android.com/topic/libraries/architecture/viewmodel.html#sharing_data_between_fragments">Sharing Data Between Fragments</a>
     */
    void setData(Object data);

    /**
     * Fragment Whether to rely on the injection, if not, rewrite the method, return false
     *
     * @return true: Dependency injection; false: No dependency injection
     */
    boolean injectable();


    /**
     * Whether to use EventBus.
     * Default use (true)
     *
     * @return boolean
     */
    boolean useEventBus();
}

