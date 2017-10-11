package com.gm.repository.di.module;

import android.app.Application;

import com.gm.repository.IRepositoryManager;
import com.gm.repository.RepositoryManager;

import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * Dagger RepositoryModule
 */
@Module
public class RepositoryModule {
    private Application mApplication;

    public RepositoryModule(Application application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    IRepositoryManager provideRepositoryManager(Lazy<Retrofit> retrofit, DBModule.RoomConfiguration roomConfiguration) {
        return new RepositoryManager(mApplication, retrofit, roomConfiguration);
    }
}
