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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gm.repository.utils.ZipHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import timber.log.Timber;

import static timber.log.Timber.w;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Http Request / response interceptor
 */
@Singleton
public class RequestInterceptor implements Interceptor {
    private final Level printLevel;
    private GlobalHttpHandler mHandler;

    public enum Level {
        /**
         * Do not print log
         */
        NONE,

        /**
         * Only print request information
         */
        REQUEST,

        /**
         * Print only the response message
         */
        RESPONSE,

        /**
         * All data is printed
         */
        ALL
    }

    @Inject
    public RequestInterceptor(@Nullable GlobalHttpHandler handler, @Nullable Level level) {
        this.mHandler = handler;
        if (level == null) {
            printLevel = Level.ALL;
        } else {
            printLevel = level;
        }
    }

    /**
     * Response Interception
     *
     * @param chain Chain
     * @return Response
     * @throws IOException IOException
     */
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        boolean logRequest = printLevel == Level.ALL || (printLevel != Level.NONE && printLevel == Level.REQUEST);

        if (logRequest) {
            boolean hasRequestBody = request.body() != null;
            //Print request information
            Timber.w("HTTP REQUEST >>>%n 「 %s 」%nParams : 「 %s 」%nConnection : 「 %s 」%nHeaders : %n「 %s 」",
                    getTag(request),
                    hasRequestBody ? parseParams(request.newBuilder().build().body()) : "Null",
                    chain.connection(),
                    request.headers());
        }

        boolean logResponse = printLevel == Level.ALL || (printLevel != Level.NONE && printLevel == Level.RESPONSE);

        long t1 = logResponse ? System.nanoTime() : 0;
        Response originalResponse;
        try {
            originalResponse = chain.proceed(request);
        } catch (Exception e) {
            w("Http Error: " + e);
            throw e;
        }
        long t2 = logResponse ? System.nanoTime() : 0;

        if (logResponse) {
            String bodySize = originalResponse.body().contentLength() != -1 ? originalResponse.body().contentLength() + "-byte" : "unknown-length";
            //Print response time and response header
            Timber.w("HTTP RESPONSE in [ %d-ms ] , [ %s ] >>>%n%s",
                    TimeUnit.NANOSECONDS.toMillis(t2 - t1), bodySize, originalResponse.headers());
        }

        //Print the response results
        String bodyString = printResult(request, originalResponse.newBuilder().build(), logResponse);

        if (mHandler != null)//Here can be a step ahead of the client to get the results of the server to return, you can do some operations, such as token timeout, re-get
            return mHandler.onHttpResultResponse(bodyString, chain, originalResponse);

        return originalResponse;
    }

    public static String parseParams(RequestBody body) throws UnsupportedEncodingException {
        if (isParseable(body.contentType())) {
            try {
                Buffer requestbuffer = new Buffer();
                body.writeTo(requestbuffer);
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
                return URLDecoder.decode(requestbuffer.readString(charset), convertCharset(charset));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "This params isn't parsed";
    }

    public static boolean isParseable(MediaType mediaType) {
        if (mediaType == null)
            return false;
        return mediaType.toString().toLowerCase().contains("text")
                || isJson(mediaType) || isForm(mediaType)
                || isHtml(mediaType) || isXml(mediaType);
    }

    public static boolean isJson(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("json");
    }

    public static boolean isXml(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("xml");
    }

    public static boolean isHtml(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("html");
    }

    public static boolean isForm(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("x-www-form-urlencoded");
    }

    public static String convertCharset(Charset charset) {
        String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1)
            return s;
        return s.substring(i + 1, s.length() - 1);
    }

    /**
     * Print the response results
     *
     * @param request Request
     * @param response Response
     * @param logResponse Whether to print
     * @return String
     * @throws IOException IOException
     */
    @Nullable
    private String printResult(Request request, Response response, boolean logResponse) throws IOException {
        //Read the results returned by the server
        ResponseBody responseBody = response.body();
        String bodyString = null;
        if (isParseable(responseBody.contentType())) {
            try {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                //Get the compression type of content
                String encoding = response
                        .headers()
                        .get("Content-Encoding");

                Buffer clone = buffer.clone();


                //Analysis response content
                bodyString = parseContent(responseBody, encoding, clone);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (logResponse) {
                Timber.w("HTTP RESPONSE >>>%n「 %s 」%nResponse Content:%n%s",
                        getTag(request),
                        isJson(responseBody.contentType()) ?
                                CharacterHandler.jsonFormat(bodyString) : isXml(responseBody.contentType()) ?
                                CharacterHandler.xmlFormat(bodyString) : bodyString);
            }

        } else {
            if (logResponse) {
                Timber.w("HTTP RESPONSE >>>%n「 %s 」%n%s",
                        getTag(request),
                        "This result isn't parsed");
            }
        }
        return bodyString;
    }

    private String getTag(Request request) {
        return String.format(" [%s] 「 %s 」", request.method(), request.url().toString());
    }

    /**
     * Resolve the contents of the server response
     *
     * @param responseBody ResponseBody
     * @param encoding coding
     * @param clone Buffer
     * @return String
     */
    private String parseContent(ResponseBody responseBody, String encoding, Buffer clone) {
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {//content is compressed using gzip
            return ZipHelper.decompressForGzip(clone.readByteArray(), convertCharset(charset));//Decompression
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {//content using zlib compression
            return ZipHelper.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset));//Decompression
        } else {//content is not compressed
            return clone.readString(charset);
        }
    }
}
