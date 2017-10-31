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

package com.gm.archmvvm.http.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.gm.archmvvm.http.OkHttpUrlLoader;
import com.gm.archmvvm.http.imageloader.BaseImageLoaderStrategy;
import com.gm.archmvvm.utils.GmUtils;
import com.gm.repository.utils.DataHelper;
import com.gm.repository.utils.RepositoryUtils;

import java.io.File;
import java.io.InputStream;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 * <p>
 * {@link AppGlideModule} The default implementation class
 * Used to configure the cache folder, switch the image request frame and other operations
 * @link http://bumptech.github.io/glide/doc/configuration.html
 */

@GlideModule(glideName = "GlideGm")
public class GlideConfiguration extends AppGlideModule {
    /**
     * The maximum value for the image cache file is 100Mb
     */
    public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;//The picture cache file has a maximum value of 100Mb

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        super.applyOptions(context, builder);
        builder.setDiskCache(() -> {
            // Careful: the external cache directory doesn't enforce permissions
            return DiskLruCacheWrapper.get(DataHelper.makeDirs(new File(RepositoryUtils.INSTANCE.obtainRepositoryComponent(context).cacheFile(), "Glide")), IMAGE_DISK_CACHE_MAX_SIZE);
        });

        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        //Will transfer the opportunity to transfer Glide to GlideImageLoaderStrategy, as you think the framework provides GlideImageLoaderStrategy
        //And can not meet their own needs, would like to customize BaseImageLoaderStrategy, then you'd better achieve GlideAppliesOptions
        //Because only to become GlideAppliesOptions implementation class, where to call applyGlideOptions (), so you have the right to configure Glide

        BaseImageLoaderStrategy imageLoaderStrategy = GmUtils.INSTANCE.obtainGmComponent(context).imageLoader().getStrategy();
        if (imageLoaderStrategy instanceof GlideAppliesOptions) {
            ((GlideAppliesOptions) imageLoaderStrategy).applyGlideOptions(context, builder);
        }
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
        //Glide defaults to using HttpURLConnection to make a network request, where you switch to okhttp request
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(RepositoryUtils.INSTANCE.obtainRepositoryComponent(context).okHttpClient()));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
