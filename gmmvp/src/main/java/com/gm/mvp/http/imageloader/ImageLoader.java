package com.gm.mvp.http.imageloader;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 * <p>
 * Policy mode, encapsulation class, hold BaseImageLoaderStrategy reference
 */

@Singleton
public final class ImageLoader {
    private BaseImageLoaderStrategy mStrategy;

    @Inject
    public ImageLoader(BaseImageLoaderStrategy strategy) {
        this.mStrategy = strategy;
    }


    public <T extends BaseImageConfig> void loadImage(Context context, T config) {
        mStrategy.loadImage(context, config);
    }

    public <T extends BaseImageConfig> void clear(Context context, T config) {
        mStrategy.clear(context, config);
    }

    public BaseImageLoaderStrategy getStrategy() {
        return mStrategy;
    }

    public void setStrategy(BaseImageLoaderStrategy strategy) {
        mStrategy = strategy;
    }
}
