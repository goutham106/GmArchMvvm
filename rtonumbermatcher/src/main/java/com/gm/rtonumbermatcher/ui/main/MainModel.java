package com.gm.rtonumbermatcher.ui.main;

import android.app.Application;

import com.gm.archmvvm.mvvm.BaseModel;

import javax.inject.Inject;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/3/17.
 */

public class MainModel extends BaseModel {

    @Inject
    public MainModel(Application application) {
        super(application);
    }
}
