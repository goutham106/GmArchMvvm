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

package com.gm.repository;

import android.app.Application;
import android.content.Context;

import com.gm.repository.di.component.DaggerRepositoryComponent;
import com.gm.repository.di.component.RepositoryComponent;
import com.gm.repository.di.module.ClientModule;
import com.gm.repository.di.module.RepositoryConfigModule;
import com.gm.repository.di.module.RepositoryModule;
import com.gm.repository.utils.ManifestRepositoryParser;
import com.gm.repository.utils.Preconditions;

import java.util.List;


/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * RepositoryInjector, need to initialize the Application, into the RepositoryComponent
 */
public class RepositoryInjector implements IRepository {
    private Application mApplication;
    private List<ConfigRepository> mConfigRepositories;
    private RepositoryComponent mRepositoryComponent;
    private RepositoryModule mRepositoryModule;

    public RepositoryInjector(Context context) {
        mConfigRepositories = new ManifestRepositoryParser(context).parse();
    }

    public void onCreate(Application application) {
        this.mApplication = application;
        if (mRepositoryModule == null) {
            mRepositoryModule = new RepositoryModule(mApplication);
        }
        mRepositoryComponent = DaggerRepositoryComponent.builder()
                .repositoryModule(mRepositoryModule)
                .clientModule(new ClientModule(mApplication))
                .repositoryConfigModule(getRepositoryConfigModule(mApplication, mConfigRepositories))
                .build();
        mRepositoryComponent.inject(this);
    }

    public void onTerminate(Application application) {
        this.mRepositoryModule = null;
        this.mRepositoryComponent = null;
        this.mConfigRepositories = null;
        this.mApplication = null;
    }

    private RepositoryConfigModule getRepositoryConfigModule(Context context, List<ConfigRepository> configRepositories) {
        RepositoryConfigModule.Builder builder = RepositoryConfigModule.builder();
        for (ConfigRepository repository : configRepositories) {
            repository.applyOptions(context, builder);
        }
        return builder.application(mApplication).build();
    }

    @Override
    public RepositoryComponent getRepositoryComponent() {
        Preconditions.checkNotNull(mRepositoryComponent,
                "%s cannot be null,first call %s#onCreate(Application) in %s#onCreate()",
                RepositoryComponent.class.getName(), getClass().getName(), mApplication.getClass().getName());
        return this.mRepositoryComponent;
    }

    @Override
    public RepositoryModule getRepositoryModule() {
        Preconditions.checkNotNull(mRepositoryComponent,
                "%s cannot be null,first call %s#onCreate(Application) in %s#onCreate()",
                RepositoryModule.class.getName(), getClass().getName(), mApplication.getClass().getName());
        return this.mRepositoryModule;
    }
}
