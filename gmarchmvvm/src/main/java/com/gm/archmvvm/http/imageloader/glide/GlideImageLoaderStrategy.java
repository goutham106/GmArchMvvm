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
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.gm.archmvvm.http.imageloader.BaseImageLoaderStrategy;
import com.gm.repository.utils.Preconditions;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<ImageConfigImpl>, GlideAppliesOptions {

    @Override
    public void loadImage(Context context, ImageConfigImpl config) {
        Preconditions.checkNotNull(context, "Context is required");
        Preconditions.checkNotNull(config, "ImageConfigImpl is required");
        Preconditions.checkNotNull(config.getImageView(), "ImageView is required");

        GlideRequests requests = GlideGm.with(context);

        GlideRequest<Drawable> glideRequest = requests.load(config.getUrl())
                .transition(DrawableTransitionOptions.withCrossFade())//Animation
                .fitCenter();//Adapt to the center

        switch (config.getCacheStrategy()) {//Cache strategy
            case 0:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case 1:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case 3:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case 4:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            default:
                break;
        }

        if (config.getTransformation() != null)//Customize to change the picture shape
            glideRequest.transform(config.getTransformation());

        if (config.getPlaceholder() != 0)//Set the placeholder
            glideRequest.placeholder(config.getPlaceholder());

        if (config.getErrorPic() != 0)//Set the Error image
            glideRequest.error(config.getErrorPic());

        if (config.getFallback() != 0)//Set url to null as image
            glideRequest.fallback(config.getFallback());

        glideRequest.into(config.getImageView());
    }

    @Override
    public void clear(final Context context, ImageConfigImpl config) {
        Preconditions.checkNotNull(context, "Context is required");
        Preconditions.checkNotNull(config, "ImageConfigImpl is required");
        //Although it’s good practice to clear loads you no longer need, you’re not required to do so.
        // In fact, Glide will automatically clear the load and recycle any resources used by the load
        // when the Activity or Fragment you pass in to Glide.with() is destroyed.
        if (config.getImageViews() != null && config.getImageViews().length > 0) {
            //Cancel the ongoing load task, and release the resource
            for (ImageView imageView : config.getImageViews())
                GlideGm.get(context).getRequestManagerRetriever().get(context).clear(imageView);
        }

        if (config.isClearDiskCache()) {
            //Clear the local cache
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe(integer -> GlideGm.get(context).clearDiskCache());
        }

        if (config.isClearMemory()) {
            //Clear the memory cache
            Observable.just(0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> GlideGm.get(context).clearMemory());
        }
    }

    @Override
    public void applyGlideOptions(Context context, GlideBuilder builder) {
        Timber.w("applyGlideOptions");
    }
}
