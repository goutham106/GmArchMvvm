package com.gm.mvp.http.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 * <p>
 * Glide configuration interface
 */

public interface GlideAppliesOptions {
    /**
     * Description: Configure Glide's custom parameters
     *
     * @param context
     * @param builder {@link GlideBuilder} This class is used to create Glide
     */
    void applyGlideOptions(Context context, GlideBuilder builder);
}