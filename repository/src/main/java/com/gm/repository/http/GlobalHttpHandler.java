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

package com.gm.repository.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import com.gm.repository.di.module.RepositoryConfigModule;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Http Request / response processing
 * use {@link RepositoryConfigModule.Builder#globalHttpHandler(GlobalHttpHandler)} Method configuration
 */
public interface GlobalHttpHandler {

    /**
     *This can be the first step to get the client http request every time the results can be resolved into json, do some operations,
     * Re-request token if token is detected expired and re-execute the request
     * <p>
     * If you find token if expired, you can first ask for the latest token, and then take the new token into the request to re-request
     * Note that this callback has been called before the progress, so here must be their own to establish a network request, such as the use of okhttp use the new request to request
     * create a new request and modify it found using the new token
     * {@code Request newRequest = chain.request (). newBuilder (). header ("token", newToken) .build ();}
     * <p>
     * retry the request
     * {@code response.body (). close ();}
     * If you use okhttp will be the new request, the request is successful, the return will return the return
     * If you do not need to return the new results, the response parameters directly back out
     *
     * @param httpResult String
     * @param chain Interceptor.Chain
     * @param response the original Response
     * @return processed after Response
     */
    Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);

    /**
     * If you need to ask the server before doing some operations, then return to the operation of a request.
     * If you increase the header, do not do the operation directly return request parameters
     * {@code return chain.request (). newBuilder (). header ("token", tokenId) .build ();}
     *
     * @param chain Interceptor.
     * @param request original request
     * @return processed after the Request
     */
    Request onHttpRequestBefore(Interceptor.Chain chain, Request request);


    GlobalHttpHandler EMPTY = new GlobalHttpHandler() {
        @Override
        public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
            return response;
        }

        @Override
        public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
            return request;
        }
    };

}
