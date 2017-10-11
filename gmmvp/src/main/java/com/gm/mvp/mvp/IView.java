/**
  * Copyright 2017 JessYan
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package com.gm.mvp.mvp;

import android.app.Activity;
import android.content.Intent;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/10/17.
 */
public interface IView {

    /**
     * Show load
     */
    void showLoading();

    /**
     * Hide load
     */
    void hideLoading();

    /**
     * Display information
     */
    void showMessage(String message);

    /**
     * Jump {@link Activity}
     */
    void launchActivity(Intent intent);
    /**
     * Kill yourself
     */
    void killMyself();
}
