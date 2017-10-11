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

package com.gm.archmvvm.base;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import com.gm.archmvvm.di.component.DaggerGmComponent;
import com.gm.archmvvm.di.component.GmComponent;
import com.gm.archmvvm.di.module.GmConfigModule;
import com.gm.archmvvm.di.module.GmModule;
import com.gm.archmvvm.http.imageloader.glide.ImageConfigImpl;
import com.gm.archmvvm.utils.GmUtils;
import com.gm.archmvvm.utils.ManifestGmParser;
import com.gm.lifecycle.utils.Preconditions;

import java.util.List;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * GmInjector, need to initialize the Application, injection GmComponent
 */

public class GmInjector implements IGm {
    private Application mApplication;
    private GmComponent mGmComponent;
    private GmModule mGmModule;
    private List<ConfigGm> mConfigGms;
    private ComponentCallbacks2 mComponentCallback;


    public GmInjector(Context context) {
        mConfigGms = new ManifestGmParser(context).parse();
    }


    public void onCreate(Application application) {
        this.mApplication = application;

        if (mGmModule == null)
            mGmModule = new GmModule(mApplication);
        mGmComponent = DaggerGmComponent.builder()
                // .lifecycleModule(((ILifecycle) mApplication).getLifecycleModule())
                // .repositoryModule(((IRepository) mApplication).getRepositoryModule())
                .gmConfigModule(getGmConfigModule(mApplication, mConfigGms))
                .gmModule(new GmModule(mApplication))
                .build();
        mGmComponent.inject(this);

        mComponentCallback = new AppComponentCallbacks(mApplication);
        mApplication.registerComponentCallbacks(mComponentCallback);
    }


    public void onTerminate(Application application) {
        if (mComponentCallback != null)
            mApplication.unregisterComponentCallbacks(mComponentCallback);
        this.mComponentCallback = null;
    }

    /**
     * Encapsulate the global configuration information of the app into the module (using Dagger to place where the information needs to be configured)
     *      * Requires declaration of {@link ConfigGm} in AndroidManifest, similar to Glide's configuration
     *
     * @return ArmsConfigModule
     *      
     */
    private GmConfigModule getGmConfigModule(Context context, List<ConfigGm> configGms) {
        GmConfigModule.Builder builder = GmConfigModule.builder();
        // Register the Gm custom configuration
        for (ConfigGm module : configGms)
            module.applyOptions(context, builder);
        return builder.build();
    }

    @Override
    public GmComponent getGmComponent() {
        Preconditions.checkNotNull(mGmComponent,
                "%s cannot be null,first call %s#onCreate(Application) in %s#onCreate()",
                GmComponent.class.getName(), getClass().getName(), mApplication.getClass().getName());
        return this.mGmComponent;
    }

    @Override
    public GmModule getGmModule() {
        Preconditions.checkNotNull(mGmModule,
                "%s cannot be null,first call %s#onCreate(Application) in %s#onCreate()",
                GmModule.class.getName(), getClass().getName(), mApplication.getClass().getName());
        return this.mGmModule;
    }


    private static class AppComponentCallbacks implements ComponentCallbacks2 {
        private Application mApplication;

        public AppComponentCallbacks(Application application) {
            this.mApplication = application;
        }

        @Override
        public void onTrimMemory(int level) {

        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {

        }

        @Override
        public void onLowMemory() {
            //When the memory is low, clean the picture request frame memory cache
            GmUtils.INSTANCE.obtainGmComponent(mApplication)
                    .imageLoader()
                    .clear(mApplication, ImageConfigImpl.builder().isClearMemory(true).build());
        }
    }
}
