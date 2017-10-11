package com.gm.mvp.integration.lifecycle;

import android.app.Activity;

import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 *
 * Let {@link Activity} implement this interface to use {@link RxLifecycle}
 *
 */
public interface ActivityLifecycleable extends Lifecycleable<ActivityEvent> {
}
