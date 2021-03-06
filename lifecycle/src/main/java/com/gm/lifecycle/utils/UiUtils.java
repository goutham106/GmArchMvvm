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

package com.gm.lifecycle.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.gm.lifecycle.delegate.AppManager;

import java.security.MessageDigest;

import static com.gm.lifecycle.delegate.AppManager.APP_EXIT;
import static com.gm.lifecycle.delegate.AppManager.KILL_ALL;
import static com.gm.lifecycle.delegate.AppManager.SHOW_SNACKBAR;
import static com.gm.lifecycle.delegate.AppManager.START_ACTIVITY;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * UiUtils
 */
@SuppressWarnings("all")
public class UiUtils {
    static public Toast mToast;


    private UiUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * Set hint size
     *
     * @param size
     * @param v
     * @param res
     */
    public static void setViewHintSize(Context context, int size, TextView v, int res) {
        SpannableString ss = new SpannableString(getResources(context).getString(
                res));
        // Create a new property object, set the size of the text
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
        // Attach attributes to text
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set hint, must be converted, otherwise the property will disappear
        v.setHint(new SpannedString(ss));
    }

    /**
     * dip to pix
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Access to resources
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * Get the character array
     */
    public static String[] getStringArray(Context context, int id) {
        return getResources(context).getStringArray(id);
    }

    /**
     * pix to dip
     */
    public static int pix2dip(Context context, int pix) {
        final float densityDpi = getResources(context).getDisplayMetrics().density;
        return (int) (pix / densityDpi + 0.5f);
    }


    /**
     * Get the dimensions from the dimensions
     *
     * @param context
     * @param id
     * @return
     */

    public static int getDimens(Context context, int id) {
        return (int) getResources(context).getDimension(id);
    }

    /**
     * Get the dimensions from the dimensions
     *
     * @param context
     * @param dimenName
     * @return
     */

    public static float getDimens(Context context, String dimenName) {
        return getResources(context).getDimension(getResources(context).getIdentifier(dimenName, "dimen", context.getPackageName()));
    }

    /**
     * Get the character from the String
     *
     * @return
     */

    public static String getString(Context context, int stringID) {
        return getResources(context).getString(stringID);
    }

    /**
     * Get the character from the String
     *
     * @return
     */

    public static String getString(Context context, String strName) {
        return getString(context, getResources(context).getIdentifier(strName, "string", context.getPackageName()));
    }

    /**
     * findview
     *
     * @param view
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Context context, View view, String viewName) {
        int id = getResources(context).getIdentifier(viewName, "id", context.getPackageName());
        T v = (T) view.findViewById(id);
        return v;
    }

    /**
     * findview
     *
     * @param activity
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Context context, Activity activity, String viewName) {
        int id = getResources(context).getIdentifier(viewName, "id", context.getPackageName());
        T v = (T) activity.findViewById(id);
        return v;
    }

    /**
     * According to the name of the placement id
     *
     * @param layoutName
     * @return
     */
    public static int findLayout(Context context, String layoutName) {
        int id = getResources(context).getIdentifier(layoutName, "layout", context.getPackageName());
        return id;
    }

    /**
     * Fill the view
     *
     * @param detailScreen
     * @return
     */
    public static View inflate(Context context, int detailScreen) {
        return View.inflate(context, detailScreen, null);
    }

    /**
     * Single toast
     *
     * @param string
     */

    public static void makeText(Context context, String string) {
        if (mToast == null) {
            mToast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        }
        mToast.setText(string);
        mToast.show();
    }

    /**
     * Use {@link #snackbarText(String)} to display text messages
     *
     * @param text String
     */
    public static void snackbarText(String text) {
        Message message = new Message();
        message.what = SHOW_SNACKBAR;
        message.obj = text;
        message.arg1 = 0;
        AppManager.post(message);
    }

    /**
     * Use {@link #snackbarTextWithLong(String)} to display text messages for a long time
     *
     * @param text String
     */
    public static void snackbarTextWithLong(String text) {
        Message message = new Message();
        message.what = SHOW_SNACKBAR;
        message.obj = text;
        message.arg1 = 1;
        AppManager.post(message);
    }


    /**
     * Get drawable by resource id
     *
     * @param rID
     * @return
     */
    @TargetApi(value = 21)
    public static Drawable getDrawablebyResource(Context context, int rID) {
        return getResources(context).getDrawable(rID,null);
    }


    /**
     * Jump interface 1, via {@link AppManager # startActivity (Class)}
     *
     * @param activityClass Class
     */
    public static void startActivity(Class activityClass) {
        Message message = new Message();
        message.what = START_ACTIVITY;
        message.obj = activityClass;
        AppManager.post(message);
    }

    /**
     * Jump interface 2, via {@link AppManager # startActivity (Class)}
     *
     * @param content Intent
     */
    public static void startActivity(Intent content) {
        Message message = new Message();
        message.what = START_ACTIVITY;
        message.obj = content;
        AppManager.post(message);
    }

    /**
     * Jump interface 3
     *
     * @param activity
     * @param homeActivityClass
     */
    public static void startActivity(Activity activity, Class homeActivityClass) {
        Intent intent = new Intent(activity.getApplicationContext(), homeActivityClass);
        activity.startActivity(intent);
    }

    /**
     * Jump interface
     *
     * @param activity Activity
     * @param intent Intent
     */
    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    /**
     * Get the width of the screen
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getResources(context).getDisplayMetrics().widthPixels;
    }

    /**
     * Get the height of the screen
     *
     * @return
     */
    public static int getScreenHeidth(Context context) {
        return getResources(context).getDisplayMetrics().heightPixels;
    }


    /**
     * Get the color
     */
    @TargetApi(value = 23)
    public static int getColor(Context context, int rid) {
        return getResources(context).getColor(rid,null);
    }

    /**
     * Get the color
     */
    public static int getColor(Context context, String colorName) {
        return getColor(context, getResources(context).getIdentifier(colorName, "color", context.getPackageName()));
    }

    /**
     * Remove the child
     *
     * @param view
     */
    public static void removeChild(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(view);
        }
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }


    /**
     * MD5
     *
     * @param string
     * @return
     */
    public static String encodeToMD5(String string) {
        byte[] hash = new byte[0];
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


    /**
     * Full screen, and submerged status bar
     *
     * @param activity
     */
    public static void statuInScreen(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


    /**
     * Configuration recycleview
     *
     * @param recyclerView
     * @param layoutManager
     */
    public static void configRecycleView(final RecyclerView recyclerView
            , RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //If you can determine the height of each item is fixed, set this option can improve performance
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * Remote control {@link AppManager # killAll ()}
     */
    public static void killAll() {
        Message message = new Message();
        message.what = KILL_ALL;
        AppManager.post(message);
    }

    /**
     * Remote control {@link AppManager # appExit ()}
     */
    public static void exitApp() {
        Message message = new Message();
        message.what = APP_EXIT;
        AppManager.post(message);
    }

}
