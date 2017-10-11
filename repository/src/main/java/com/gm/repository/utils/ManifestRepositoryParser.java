package com.gm.repository.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.gm.repository.ConfigRepository;

import java.util.ArrayList;
import java.util.List;


/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * AndroidManifest.xml ManifestRepositoryParser
 */
@SuppressWarnings("all")
public final class ManifestRepositoryParser {
    private static final String MODULE_VALUE = "ConfigRepository";

    private final Context context;

    public ManifestRepositoryParser(Context context) {
        this.context = context;
    }

    public List<ConfigRepository> parse() {
        List<ConfigRepository> configRepositories = new ArrayList<>();
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                for (String key : appInfo.metaData.keySet()) {
                    if (MODULE_VALUE.equals(appInfo.metaData.get(key))) {
                        Log.d("ManifestRepositoryParser ---> ",
                                String.format("Find ConfigRepository in [%s]", key));
                        configRepositories.add(parseModule(key));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse ConfigRepository", e);
        }

        return configRepositories;
    }

    private static ConfigRepository parseModule(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to find ConfigRepository implementation", e);
        }

        Object repository;
        try {
            repository = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate ConfigRepository implementation for " + clazz, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to instantiate ConfigRepository implementation for " + clazz, e);
        }

        if (!(repository instanceof ConfigRepository)) {
            throw new RuntimeException("Expected instanceof ConfigRepository, but found: " + repository);
        }
        return (ConfigRepository) repository;
    }
}