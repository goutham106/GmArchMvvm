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

package com.gm.rtonumbermatcher.util;

import android.view.View;

import com.gm.gmwidgets.GmFadeTextView;

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
//            v.post(ENABLE_AGAIN);
            v.postDelayed(ENABLE_AGAIN, 10000);
            doClick(v);
        }
    }
}
