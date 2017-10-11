package com.gm.mvp.di.module;



import com.gm.mvp.http.imageloader.BaseImageLoaderStrategy;
import com.gm.mvp.http.imageloader.glide.GlideImageLoaderStrategy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 *
 * Configuration Gm frame components, can be free to expand
 */
@Module
public class GmConfigModule {
    private BaseImageLoaderStrategy mImageLoaderStrategy;


    @Singleton
    @Provides
    BaseImageLoaderStrategy provideImageLoaderStrategy() {
        //Use Glide to load the image by default
        return mImageLoaderStrategy == null ? new GlideImageLoaderStrategy() : mImageLoaderStrategy;
    }


    private GmConfigModule(Builder builder) {
        this.mImageLoaderStrategy = builder.imageLoaderStrategy;
    }

    public static Builder builder() {
        return new Builder();
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
