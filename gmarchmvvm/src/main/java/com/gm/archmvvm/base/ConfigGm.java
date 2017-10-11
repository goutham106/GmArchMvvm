package com.gm.archmvvm.base;

import android.content.Context;

import com.gm.archmvvm.di.module.GmConfigModule;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * Frame configuration interface
 */

public interface ConfigGm {
    /**
     *Use {@link GmConfigModule.Builder} to configure some configuration parameters for the framework
     *
     * @param context: Context
     * @param builder: GmConfigModule.Builder
     */
    void applyOptions(Context context, GmConfigModule.Builder builder);
}
