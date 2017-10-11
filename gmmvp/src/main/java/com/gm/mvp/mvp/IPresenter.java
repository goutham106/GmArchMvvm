package com.gm.mvp.mvp;

import android.app.Activity;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/10/17.
 */
public interface IPresenter {

    /**
     * Do some initialization
     */
    void onStart();

    /**
     * {@link Activity # onDestroy ()} in the frame will call {@link IPresenter # onDestroy ()} by default
     */
    void onDestroy();
}
