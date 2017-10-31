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

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Active interface
 */

public interface IActivity {
    /**
     * UI initialization
     *
     * @param savedInstanceState Bundle
     * @return int
     */
    int initView(Bundle savedInstanceState);

    /**
     * Data initialization
     *
     * @param savedInstanceState Bundle
     */
    void initData(Bundle savedInstanceState);

    /**
     * Whether to use EventBus
     * {@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * Default use (true)
     *
     * @return boolean
     */
    boolean useEventBus();

    /**
     * Whether the Activity will use Fragment, the framework will be based on this property to determine whether to register {@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * Default (true)
     *
     * @return boolean
     */
    boolean useFragment();

    /**
     * Activity is dependent on the injection, if not, then rewrite this method, return false
     *
     * @return true: dependency injection; false: no dependency injection
     */
    boolean injectable();
}
