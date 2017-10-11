package com.gm.repository.di.component;

import com.gm.repository.IRepositoryManager;
import com.gm.repository.RepositoryInjector;
import com.gm.repository.di.module.ClientModule;
import com.gm.repository.di.module.RepositoryConfigModule;
import com.gm.repository.di.module.RepositoryModule;
import com.gm.repository.rxerrorhandler.core.RxErrorHandler;

import java.io.File;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * Dagger RepositoryComponent
 */
@Singleton
@Component(modules = {RepositoryModule.class, ClientModule.class, RepositoryConfigModule.class})
public interface RepositoryComponent {
    //Used to manage the network request layer and the database layer
    IRepositoryManager repositoryManager();

    //Rxjava error handling management class
    RxErrorHandler rxErrorHandler();

    //Provide OKHttpClient
    OkHttpClient okHttpClient();

    //Provide cache file
    File cacheFile();

    void inject(RepositoryInjector repositoryInjector);
}
