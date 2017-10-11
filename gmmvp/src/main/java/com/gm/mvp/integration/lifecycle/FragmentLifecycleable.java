package com.gm.mvp.integration.lifecycle;

import android.support.v4.app.Fragment;

import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 * <p>
 * {@link Fragment} to implement this interface, you can use {@link RxLifecycle} *
 */
public interface FragmentLifecycleable extends Lifecycleable<FragmentEvent> {
}
