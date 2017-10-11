package com.gm.lifecycle.delegate;


import com.gm.lifecycle.di.component.LifecycleComponent;
import com.gm.lifecycle.di.module.LifecycleModule;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * Application inherits the interface and can have the methods provided by LifecycleComponent.
 *
 */
public interface ILifecycle {
    /**
     * Description:Gets the global LifecycleComponent
     *
     * @return LifecycleComponent
     */
    LifecycleComponent getLifecycleComponent();


    /**
     * Description: Get global Lifecycle Module
     *
     * @return LifecycleModule
     */
    LifecycleModule getLifecycleModule();
}
