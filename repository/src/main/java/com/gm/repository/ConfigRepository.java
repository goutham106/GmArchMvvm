package com.gm.repository;

import android.content.Context;

import com.gm.repository.di.module.RepositoryConfigModule;


/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * Repository Configure
 */
public interface ConfigRepository {
    /**
     * Use {@link RepositoryConfigModule.Builder} to configure some configuration parameters for the framework
     *
     * @param context: Context
     * @param builder: RepositoryConfigModule.Builder
     */
    void applyOptions(Context context, RepositoryConfigModule.Builder builder);

}
