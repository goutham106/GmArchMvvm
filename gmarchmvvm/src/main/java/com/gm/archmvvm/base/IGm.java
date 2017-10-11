package com.gm.archmvvm.base;


import com.gm.archmvvm.di.component.GmComponent;
import com.gm.archmvvm.di.module.GmModule;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
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
