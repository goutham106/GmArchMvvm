package com.gm.mvp.integration.lifecycle;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.gm.mvp.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.RxLifecycle;

import io.reactivex.subjects.Subject;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 *
 * {@link Activity} / {@ink Fragment} to implement this interface, you can use {@link RxLifecycle}
 * No need to inherit {@link RxLifecycle} to provide the Activity / Fragment, highly scalable
 *
 * @see RxLifecycleUtils Please refer to this category for detailed usage
 */
public interface Lifecycleable<E> {
    @NonNull
    Subject<E> provideLifecycleSubject();
}
