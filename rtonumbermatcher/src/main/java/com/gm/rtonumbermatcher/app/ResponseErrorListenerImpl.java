package com.gm.rtonumbermatcher.app;

import android.content.Context;
import android.net.ParseException;

import com.gm.lifecycle.utils.UiUtils;
import com.gm.repository.rxerrorhandler.handler.listener.ResponseErrorListener;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;
import timber.log.Timber;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 */

public class ResponseErrorListenerImpl implements ResponseErrorListener {
    @Override
    public void handleResponseError(Context context, Throwable t) {
        //Used to provide a monitor for handling all errors
        //rxjava need to use ErrorHandleSubscriber (the default implementation of Subscriber's onError method), this monitor to take effect
        Timber.tag("Catch-Error").w(t.getMessage());
        //Here is not only print errors, but also according to different errors to make different logical processing
        String msg = "Unknown";
        if (t instanceof UnknownHostException) {
            msg = "The network is not available";
        } else if (t instanceof SocketTimeoutException) {
            msg = "Network timeout";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(httpException);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException || t instanceof JsonIOException) {
            msg = "Data parsing error";
        }
        UiUtils.snackbarText(msg);
    }


    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "The server has encountered an error";
        } else if (httpException.code() == 404) {
            msg = "The request address does not exist";
        } else if (httpException.code() == 403) {
            msg = "The request was rejected by the server";
        } else if (httpException.code() == 307) {
            msg = "The request is redirected to another page";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
