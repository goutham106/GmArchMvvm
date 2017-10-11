package com.gm.lifecycle.utils;

import android.app.Application;
import android.content.Context;

import com.gm.lifecycle.delegate.ILifecycle;
import com.gm.lifecycle.di.component.LifecycleComponent;


/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * LifecycleComponent tool class
 */

public enum LifecycleUtils {
    INSTANCE;

    public LifecycleComponent obtainLifecycleComponent(Context context) {
        return obtainLifecycleComponent((Application) context.getApplicationContext());
    }

    public LifecycleComponent obtainLifecycleComponent(Application application) {
        Preconditions.checkState(application instanceof ILifecycle,
                "%s does not implements ILifecycle", application.getClass().getName());
        return ((ILifecycle) application).getLifecycleComponent();
    }

}
