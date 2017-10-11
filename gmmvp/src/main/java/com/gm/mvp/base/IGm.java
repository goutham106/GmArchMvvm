package com.gm.mvp.base;


import com.gm.mvp.di.component.GmComponent;
import com.gm.mvp.di.module.GmModule;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Application inherits the interface, you can have GmComponent provided by the method.
 */

public interface IGm {
    /**
     * Description: Get global GmComponent
     *
     * @return GmComponent
     */
    GmComponent getGmComponent();


    /**
     * Description: Get global GmModule
     *
     * @return GmModule
     */
    GmModule getGmModule();
}
