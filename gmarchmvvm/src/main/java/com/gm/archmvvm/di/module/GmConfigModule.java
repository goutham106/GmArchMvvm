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

package com.gm.archmvvm.di.module;

import com.gm.archmvvm.http.imageloader.BaseImageLoaderStrategy;
import com.gm.archmvvm.http.imageloader.glide.GlideImageLoaderStrategy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 * <p>
 * Configuration Gm frame components, can be free to expand
 */
@Module
public class GmConfigModule {
    private BaseImageLoaderStrategy mImageLoaderStrategy;


    private GmConfigModule(Builder builder) {
        this.mImageLoaderStrategy = builder.imageLoaderStrategy;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Singleton
    @Provides
    BaseImageLoaderStrategy provideImageLoaderStrategy() {
        //Use Glide to load the image by default
        return mImageLoaderStrategy == null ? new GlideImageLoaderStrategy() : mImageLoaderStrategy;
    }

    public static final class Builder {
        private BaseImageLoaderStrategy imageLoaderStrategy;

        private Builder() {
        }

        public Builder imageLoaderStrategy(BaseImageLoaderStrategy imageLoaderStrategy) {
            this.imageLoaderStrategy = imageLoaderStrategy;
            return this;
        }

        public GmConfigModule build() {
            return new GmConfigModule(this);
        }
    }
}
