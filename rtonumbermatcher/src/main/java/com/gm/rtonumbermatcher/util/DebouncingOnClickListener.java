package com.gm.rtonumbermatcher.util;

import android.view.View;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/10/17.
 */

public interface DebouncingOnClickListener extends View.OnClickListener {

    Enabled enabled = new Enabled(true);

    Runnable ENABLE_AGAIN = () -> enabled.set(true);

    void doClick(View v);

    @Override
    default void onClick(View v) {
        if (enabled.get()) {
            enabled.set(false);
            v.post(ENABLE_AGAIN);
            doClick(v);
        }
    }
}
