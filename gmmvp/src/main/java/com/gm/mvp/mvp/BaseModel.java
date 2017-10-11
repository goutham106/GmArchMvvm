package com.gm.mvp.mvp;


import com.gm.repository.IRepositoryManager;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/10/17.
 */
public class BaseModel implements IModel {
    protected IRepositoryManager mRepositoryManager;//Used to manage the network request layer, and data cache layer

    public BaseModel(IRepositoryManager repositoryManager) {
        this.mRepositoryManager = repositoryManager;
    }

    /**
     * In the frame {@link BasePresenter # onDestroy ()}  will be call by default {@link IModel # onDestroy ()}
     */
    @Override
    public void onDestroy() {
        mRepositoryManager = null;
    }
}
