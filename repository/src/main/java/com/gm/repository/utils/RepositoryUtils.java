package com.gm.repository.utils;

import android.app.Application;
import android.content.Context;

import com.gm.repository.IRepository;
import com.gm.repository.di.component.RepositoryComponent;


/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * RepositoryComponent Tools
 */
public enum RepositoryUtils {
    INSTANCE;

    public RepositoryComponent obtainRepositoryComponent(Context context) {
        return obtainRepositoryComponent((Application) context.getApplicationContext());
    }

    public RepositoryComponent obtainRepositoryComponent(Application application) {
        Preconditions.checkState(application instanceof IRepository,
                "%s does not implements IRepository", application.getClass().getName());
        return ((IRepository) application).getRepositoryComponent();
    }

}
