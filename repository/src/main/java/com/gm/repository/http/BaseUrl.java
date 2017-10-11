package com.gm.repository.http;

import okhttp3.HttpUrl;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * Retrofit Url interface
 */

public interface BaseUrl {
    /**
     * For BaseUrl in the App can not be determined when the start, you need to request the server interface to dynamically obtain the application scenario
     * Before calling the Retrofit interface, use Okhttp or other means to request the correct BaseUrl and return through this method
     *
     * @return HttpUrl
     */
    HttpUrl url();
}
