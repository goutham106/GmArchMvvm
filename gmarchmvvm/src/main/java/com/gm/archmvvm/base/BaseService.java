package com.gm.archmvvm.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.simple.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * MVVMGm BaseService
 */

public abstract class BaseService extends Service {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unDisposeAll();
        this.mCompositeDisposable = null;
    }


    /**
     * Description: Add RxJava subscription
     *
     * @param disposable Disposable
     */
    protected void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null)
            mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(disposable);
    }

    /**
     * When the Service is destroyed, all subscriptions are automatically released and can be executed manually
     */
    protected void unDisposeAll() {
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
    }


    /**
     * Service initialization
     */
    protected abstract void init();
}
