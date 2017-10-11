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

package com.gm.rtonumbermatcher.util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/20/17.
 */
public class ToastUtils {

    private static Toast sToast;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static boolean isJumpWhenMore;
    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Toast initiation
     *
     * @param isJumpWhenMore When the continuous pop up toast, is to pop up the new toast or only modify the text content
     *                       <p>{@code true}: Pop new toast<br>{@code false}: Only modify the text content</p>
     *                       <p>If it is{@code false}Can be used to show any time toast</p>
     */
    public static void init(boolean isJumpWhenMore) {
        ToastUtils.isJumpWhenMore = isJumpWhenMore;
    }

    /**
     * Safely show short toast
     *
     * @param text text
     */
    public static void showShortToastSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * Safely show short toast
     *
     * @param resId Resource Id
     */
    public static void showShortToastSafe(final @StringRes int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * Safely show short toast
     *
     * @param resId Resource Id
     * @param args  parameter
     */
    public static void showShortToastSafe(final @StringRes int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * Safely show short toast
     *
     * @param format format
     * @param args   parameter
     */
    public static void showShortToastSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(format, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * Safely show long toast
     *
     * @param text text
     */
    public static void showLongToastSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * Safely show long toast
     *
     * @param resId Resource Id
     */
    public static void showLongToastSafe(final @StringRes int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * Safely show long toast
     *
     * @param resId Resource Id
     * @param args  parameter
     */
    public static void showLongToastSafe(final @StringRes int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * Safely show long toast
     *
     * @param format format
     * @param args   parameter
     */
    public static void showLongToastSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * Show short toast
     *
     * @param text text
     */
    public static void showShortToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * Show short toast
     *
     * @param resId Resource Id
     */
    public static void showShortToast(@StringRes int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    /**
     * Show short toast
     *
     * @param resId Resource Id
     * @param args  parameter
     */
    public static void showShortToast(@StringRes int resId, Object... args) {
        showToast(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * Show short toast
     *
     * @param format format
     * @param args   parameter
     */
    public static void showShortToast(String format, Object... args) {
        showToast(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * Show long toast
     *
     * @param text text
     */
    public static void showLongToast(CharSequence text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    /**
     * Show long toast
     *
     * @param resId Resource Id
     */
    public static void showLongToast(@StringRes int resId) {
        showToast(resId, Toast.LENGTH_LONG);
    }

    /**
     * Show long toast
     *
     * @param resId Resource Id
     * @param args  parameter
     */
    public static void showLongToast(@StringRes int resId, Object... args) {
        showToast(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * Show long toast
     *
     * @param format format
     * @param args   parameter
     */
    public static void showLongToast(String format, Object... args) {
        showToast(format, Toast.LENGTH_LONG, args);
    }

    /**
     * Show toast
     *
     * @param resId    Resource Id
     * @param duration Show duration
     */
    private static void showToast(@StringRes int resId, int duration) {
        showToast(Utils.getContext().getResources().getText(resId).toString(), duration);
    }

    /**
     * Show toast
     *
     * @param resId    Resource Id
     * @param duration Show duration
     * @param args     parameter
     */
    private static void showToast(@StringRes int resId, int duration, Object... args) {
        showToast(String.format(Utils.getContext().getResources().getString(resId), args), duration);
    }

    /**
     * Show toast
     *
     * @param format   format
     * @param duration Show duration
     * @param args     parameter
     */
    private static void showToast(String format, int duration, Object... args) {
        showToast(String.format(format, args), duration);
    }

    /**
     * Show toast
     *
     * @param text     text
     * @param duration Show duration
     */
    @SuppressLint("ShowToast")
    private static void showToast(CharSequence text, int duration) {
        if (isJumpWhenMore) cancelToast();
        if (sToast == null) {
            sToast = Toast.makeText(Utils.getContext(), text, duration);
        } else {
            sToast.setText(text);
            sToast.setDuration(duration);
        }
        sToast.show();
    }

    /**
     * Cancel toast show
     */
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
