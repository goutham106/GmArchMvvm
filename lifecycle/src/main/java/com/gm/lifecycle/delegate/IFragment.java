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
     * Description: UI initialization
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * Description: Data initialization
     *
     * @param savedInstanceState Bundle
     */
    void initData(Bundle savedInstanceState);

    /**
     * This method is to make external calls to make some of the operation of the fragment, for example, the external activities of the fragment object to perform some of the methods,
     * It is recommended that there are a number of ways to make external calls, the unified message, through what field, to distinguish between different methods, setData
     * Method can switch to do different operations, so that you can use a unified entrance to do different things
     * <p>
     * <p>
     * New posture: You can use the Activity ViewModel to share data to include the Fragment, with LiveData easy to use burst.
     *
     * @param data Object
     * @see <a href="https://developer.android.com/topic/libraries/architecture/viewmodel.html#sharing_data_between_fragments">Sharing Data Between Fragments</a>
     */
    void setData(Object data);

    /**
     * Description: Whether Fragment is dependent on injection
     *
     * @return true: Dependency injection; false: No dependency injection
     */
    boolean injectable();


    /**
     * Description: Whether to use EventBus.
     * Default use (true)
     *
     * @return boolean
     */
    boolean useEventBus();
}

