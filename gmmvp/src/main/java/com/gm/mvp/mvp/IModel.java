package com.gm.mvp.mvp;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/10/17.
 */
public interface IModel {

    /**
     * {@link BasePresenter # onDestroy ()} in the frame will call {@link IModel # onDestroy ()} by default
     */
    void onDestroy();
}
