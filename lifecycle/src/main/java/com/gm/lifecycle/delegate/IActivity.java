package com.gm.lifecycle.delegate;


import android.os.Bundle;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * Active interface
 */

public interface IActivity {
    /**
     * Description: UI initialization
     *
     * @param savedInstanceState Bundle
     * @return int
     */
    int initView(Bundle savedInstanceState);

    /**
     * Description: Data initialization
     *
     * @param savedInstanceState Bundle
     */
    void initData(Bundle savedInstanceState);

    /**
     * Description: Whether to use EventBus
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
}
