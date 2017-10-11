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

import android.app.Application;
import android.content.Context;

import com.gm.repository.IRepository;
import com.gm.repository.di.component.RepositoryComponent;


/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * RepositoryComponent Tools
 */
public enum RepositoryUtils {
    INSTANCE;

    public RepositoryComponent obtainRepositoryComponent(Context context) {
        return obtainRepositoryComponent((Application) context.getApplicationContext());
    }

    public RepositoryComponent obtainRepositoryComponent(Application application) {
        Preconditions.checkState(application instanceof IRepository,
                "%s does not implements IRepository", application.getClass().getName());
        return ((IRepository) application).getRepositoryComponent();
    }

}
