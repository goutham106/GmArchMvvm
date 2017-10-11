package com.gm.rtonumbermatcher.ui.main;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.gm.archmvvm.di.scope.AppScope;
import com.gm.archmvvm.mvvm.BaseViewModel;

import javax.inject.Inject;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/3/17.
 */
@AppScope
public class MainViewModel extends BaseViewModel<MainModel> {

    private MutableLiveData<String> mLocation; //This data can be shared with Fragment

    @Inject
    MainViewModel(Application application, MainModel model) {
        super(application, model);
    }


    public MutableLiveData<String> getLocation() {
        if (mLocation == null) {
            mLocation = new MutableLiveData<>();
            mLocation.setValue("RTO App");
        }
        return mLocation;
    }

}
