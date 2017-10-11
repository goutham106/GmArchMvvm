package com.gm.archmvvm.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.gm.archmvvm.base.ConfigGm;

import java.util.ArrayList;
import java.util.List;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 */
@SuppressWarnings("all")
public final class ManifestGmParser {
    private static final String MODULE_VALUE = "ConfigArms";

    private final Context context;

    public ManifestGmParser(Context context) {
        this.context = context;
    }

    public List<ConfigGm> parse() {
        List<ConfigGm> configGms = new ArrayList<>();
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                for (String key : appInfo.metaData.keySet()) {
                    if (MODULE_VALUE.equals(appInfo.metaData.get(key))) {
                        Log.d("ManifestGmParser ---> ",
                                String.format("Find ConfigArms in [%s]", key));
                        configGms.add(parseModule(key));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse ConfigArms", e);
        }

        return configGms;
    }

    private static ConfigGm parseModule(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to find ConfigArms implementation", e);
        }

        Object gms;
        try {
            gms = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate ConfigArms implementation for " + clazz, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to instantiate ConfigArms implementation for " + clazz, e);
        }

        if (!(gms instanceof ConfigGm)) {
            throw new RuntimeException("Expected instanceof ConfigArms, but found: " + gms);
        }
        return (ConfigGm) gms;
    }
}