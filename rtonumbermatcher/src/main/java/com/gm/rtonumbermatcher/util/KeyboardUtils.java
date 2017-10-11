package com.gm.rtonumbermatcher.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 *
 * Keyboard related tools
 */

public final class KeyboardUtils {

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /*
      Avoid input method masking
      <p>Set the activity in manifest.xml</p>
      <p>android:windowSoftInputMode="adjustPan"</p>
     */

    /**
     * Dynamic display soft keyboard
     *
     * @param activity activity
     */
    public static void showSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null)
            view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null)
            return;
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * Dynamic display soft keyboard
     *
     * @param view view
     */
    public static void showSoftInput(Context context, final View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null)
            return;
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * Dynamic hiding the soft keyboard
     *
     * @param activity activity
     */
    public static void hideSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null)
            view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null)
            return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Dynamic hiding the soft keyboard
     *
     * @param view view
     */
    public static void hideSoftInput(Context context, final View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null)
            return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Switch the keyboard display status
     */
    public static void toggleSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null)
            return;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Click on the screen blank area to hide the soft keyboard
     * <p>According to the coordinates of EditText and the user click the coordinates of the contrast，To determine whether to hide the keyboard</p>
     * <p>Need to override dispatchTouchEvent</p>
     * <p>Refer to the following comment code</p>
     */
    public static void clickBlankArea2HideSoftInput() {
        Log.d("tips", "U should copy the following code.");
        /*
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // According to the coordinates of EditText and the user click the coordinates of the contrast，To determine whether to hide the keyboard
        private boolean isShouldHideKeyboard(View v, MotionEvent event) {
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                return !(event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom);
            }
            return false;
        }
        */
    }
}
