package com.gm.archmvvm.mvvm;


import android.app.Application;

import com.gm.repository.IRepositoryManager;
import com.gm.repository.utils.RepositoryUtils;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 */

public class BaseModel implements IModel {

    protected IRepositoryManager mRepositoryManager;


    public BaseModel(Application application) {
        this.mRepositoryManager = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .repositoryManager();
    }

    @Override
    public void onDestroy() {
        this.mRepositoryManager = null;
    }
}
