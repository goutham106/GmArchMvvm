package com.gm.mvp.utils;

import android.app.Application;
import android.content.Context;

import com.gm.mvp.base.IGm;
import com.gm.mvp.di.component.GmComponent;
import com.gm.repository.utils.Preconditions;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * <p>
 * https://stackoverflow.com/questions/70689/what-is-an-efficient-way-to-implement-a-singleton-pattern-in-java
 * Get GmComponent to get everything in the frame
 * {@link GmComponent}
 */

public enum GmUtils {
    INSTANCE;

    public GmComponent obtainGmComponent(Context context) {
        Preconditions.checkState(context.getApplicationContext() instanceof IGm, "Application does not implements IGm");
        return ((IGm) context.getApplicationContext()).getGmComponent();
    }

    public GmComponent obtainGmComponent(Application application) {
        Preconditions.checkState(application instanceof IGm, "Application does not implements IGm");
        return ((IGm) application).getGmComponent();
    }
}
