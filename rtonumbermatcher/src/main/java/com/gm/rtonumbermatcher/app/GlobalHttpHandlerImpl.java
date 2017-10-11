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

package com.gm.rtonumbermatcher.app;

import com.gm.repository.http.GlobalHttpHandler;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 */

public class GlobalHttpHandlerImpl implements GlobalHttpHandler {
    // Here you can provide a global processing Http request and response to the results of the processing class,
    // Here can be a step ahead of the client to get the results of the server to return, you can do some operations, such as token timeout, re-get
    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
        /* Here you can step by step to get the results of each http request, you can resolve to json, do some operations, such as the detection token is expired
           Re-request token and re-execute the request */

        /* If the token is found expired, you can request the latest token, and then get the new token in the request to re-request
           Note that this callback has been called before the progress, so here must be their own to establish a network request, such as the use of okhttp use the new request to request
           create a new request and modify it accordingly using the new token
           Request newRequest = chain.request().newBuilder().header("token", newToken)
                                             .build();
            retry the request

           response.body().close();
           If you use okhttp will be the new request, the request is successful, the return will return the return
           If you do not need to return a new result, the response parameter is returned directly */

        return response;
    }

    // Here you can ask the server before you can get the request, do some operations such as the request to add a unified token or header and parameter encryption and other operations
    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
        /* If you need to ask the server before doing some operations, then return to the operation of a request such as increase the header,
           Do not do the operation directly return request parameters */
        return chain.request().newBuilder()
                .addHeader("Connection", "close")
                .build();
        // return request;
    }
}
