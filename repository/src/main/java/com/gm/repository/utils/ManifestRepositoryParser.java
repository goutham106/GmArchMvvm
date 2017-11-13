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
 * <p>
 * AndroidManifest.xml ManifestRepositoryParser
 */
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
                        Log.d("Repository ---> ",
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