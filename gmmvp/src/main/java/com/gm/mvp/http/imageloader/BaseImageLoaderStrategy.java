package com.gm.mvp.http.imageloader;

import android.content.Context;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 *
 * Policy mode, ImageLoader abstract policy class
 */

public interface BaseImageLoaderStrategy<T extends BaseImageConfig> {
    void loadImage(Context context, T config);

    void clear(Context context, T config);
}
