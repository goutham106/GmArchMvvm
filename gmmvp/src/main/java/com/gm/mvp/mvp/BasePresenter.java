package com.gm.mvp.mvp;

import android.app.Activity;

import org.simple.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/10/17.
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresenter {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;

    protected M mModel;
    protected V mRootView;

    /**
     * If the current page requires both the Model and View layers, use this constructor (default)
     *
     * @param model
     * @param rootView
     */
    public BasePresenter(M model, V rootView) {
        this.mModel = model;
        this.mRootView = rootView;
        onStart();
    }

    /**
     * If the current page does not need to manipulate the data,
     * only the View layer is required, then use this constructor
     *
     * @param rootView
     */
    public BasePresenter(V rootView) {
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }


    @Override
    public void onStart() {
        if (useEventBus())//If you want to use Eventbus, return this method to true
            EventBus.getDefault().register(this);//Register Eventbus
    }

    /**
     * {@link Activity # onDestroy ()} in the frame will call {@link IPresenter # onDestroy ()} by default
     */
    @Override
    public void onDestroy() {
        if (useEventBus())//If you want to use Eventbus, return this method to true
            EventBus.getDefault().unregister(this);//Unregister Eventbus
        unDispose();//Unsubscribe
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mRootView = null;
        this.mCompositeDisposable = null;
    }

    /**
     * Whether to use {@link EventBus}, default to use (true),
     *
     * @return
     */
    public boolean useEventBus() {
        return true;
    }


    /**
     * Add {@link Disposable} to {@link CompositeDisposable} for unified management
     * Use the {@link #unDispose ()} in {@link Activity # onDestroy ()} to stop the RxJava task
     * that is being executed to avoid a memory leak
     *
     * @param disposable
     */
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//Put all Disposables in a centralized process
    }

    /**
     * Stops the RxJava task that is being executed in the collection
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//Ensure that all subscriptions are being executed at the end of the activity
        }
    }


}
