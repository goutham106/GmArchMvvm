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

import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.gm.archmvvm.http.imageloader.BaseImageConfig;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 */

public class ImageConfigImpl extends BaseImageConfig {
    /**
     * @see com.bumptech.glide.load.engine.DiskCacheStrategy#ALL : 0
     * @see com.bumptech.glide.load.engine.DiskCacheStrategy#NONE : 1
     * @see com.bumptech.glide.load.engine.DiskCacheStrategy#DATA : 2
     * @see com.bumptech.glide.load.engine.DiskCacheStrategy#RESOURCE : 3
     * @see com.bumptech.glide.load.engine.DiskCacheStrategy#AUTOMATIC : 4
     */
    private int cacheStrategy;//Cache strategy
    private int fallback;//Fallback Drawables are shown when the requested url/model is null
    private BitmapTransformation transformation;//Change the shape of the graphic
    private ImageView[] imageViews;
    private boolean isClearMemory;//Clear the memory cache
    private boolean isClearDiskCache;//Clear the local cache

    public ImageConfigImpl(Builder builder) {
        this.url = builder.url;
        this.imageView = builder.imageView;
        this.placeholder = builder.placeholder;
        this.errorPic = builder.errorPic;
        this.fallback = builder.fallback;
        this.cacheStrategy = builder.cacheStrategy;
        this.transformation = builder.transformation;
        this.imageViews = builder.imageViews;
        this.isClearMemory = builder.isClearMemory;
        this.isClearDiskCache = builder.isClearDiskCache;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getCacheStrategy() {
        return cacheStrategy;
    }

    public int getFallback() {
        return fallback;
    }

    public BitmapTransformation getTransformation() {
        return transformation;
    }

    public ImageView[] getImageViews() {
        return imageViews;
    }

    public boolean isClearMemory() {
        return isClearMemory;
    }

    public boolean isClearDiskCache() {
        return isClearDiskCache;
    }

    public static final class Builder {
        private String url;
        private ImageView imageView;
        private int placeholder;
        private int errorPic;
        private int fallback;//Fallback Drawables are shown when the requested url/model is null
        private int cacheStrategy;//Cache strategy
        private BitmapTransformation transformation;//Change the shape of the graphic
        private ImageView[] imageViews;
        private boolean isClearMemory;//Clear the memory cache
        private boolean isClearDiskCache;//Clear the local cache

        private Builder() {
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder placeholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder errorPic(int errorPic) {
            this.errorPic = errorPic;
            return this;
        }

        public Builder fallback(int fallback) {
            this.fallback = fallback;
            return this;
        }

        public Builder cacheStrategy(int cacheStrategy) {
            this.cacheStrategy = cacheStrategy;
            return this;
        }

        public Builder transformation(BitmapTransformation transformation) {
            this.transformation = transformation;
            return this;
        }

        public Builder imageViews(ImageView... imageViews) {
            this.imageViews = imageViews;
            return this;
        }

        public Builder isClearMemory(boolean isClearMemory) {
            this.isClearMemory = isClearMemory;
            return this;
        }

        public Builder isClearDiskCache(boolean isClearDiskCache) {
            this.isClearDiskCache = isClearDiskCache;
            return this;
        }

        public ImageConfigImpl build() {
            return new ImageConfigImpl(this);
        }


    }
}
