package com.gm.repository;

import com.gm.repository.di.component.RepositoryComponent;
import com.gm.repository.di.module.RepositoryModule;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * Application inherits the interface, you can have RepositoryComponent provided by the method.
 */
public interface IRepository {
    /**
     * Description: Get global RepositoryComponent
     *
     * @return RepositoryComponent
     */
    RepositoryComponent getRepositoryComponent();

    /**
     * Description: Get the global RepositoryModule
     *
     * @return RepositoryModule
     */
    RepositoryModule getRepositoryModule();

    }
