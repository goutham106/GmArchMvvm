package com.gm.rtonumbermatcher.ui.binding;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/28/17.
 */

public class BindingAdapters {

    //Custom DataBinding controls the display and hiding of View
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
