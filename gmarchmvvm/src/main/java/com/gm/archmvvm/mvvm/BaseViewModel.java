package com.gm.archmvvm.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableField;

import com.gm.repository.http.Status;

import org.simple.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 *
 * MVVM BaseViewModel (ViewModel no longer holds View，But store and manage UI-related data)
 * ViewModel objects are scoped to the Lifecycle passed to the ViewModelProvider when getting the ViewModel.
 * The ViewModel stays in memory until the Lifecycle it’s scoped to goes away permanently
 * —in the case of an activity, when it finishes;
 * in the case of a fragment, when it’s detached.
 *
 * @see <a href="https://developer.android.com/topic/libraries/architecture/viewmodel.html">ViewModel</a>
 */

public class BaseViewModel<M extends IModel> extends AndroidViewModel
        implements IViewModel, LifecycleObserver {
    protected CompositeDisposable mCompositeDisposable;

    protected M mModel;

    //Data request status
    public final ObservableField<Status> mStatus = new ObservableField<>();

    public BaseViewModel(Application application) {
        super(application);
    }

    public BaseViewModel(Application application, M model) {
        super(application);
        this.mModel = model;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onStart() {
        if (useEventBus())
            EventBus.getDefault().register(this);//Register eventbus
    }

    //Whether to use EventBus
    protected boolean useEventBus() {
        return true;
    }

    //RxJava add a subscription
    protected void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//Put all disposable into, focus on processing
    }

    //RxJava automatically releases the subscription
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void unDispose() {
        if (mCompositeDisposable != null) {
            //Guaranteed LifecycleOwner at the end of the cancellation of all ongoing subscriptions
            mCompositeDisposable.clear();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (useEventBus())
            EventBus.getDefault().unregister(this);//Unregister eventbus

    }

    //Used for package refresh operation
    public void retry() {
        //If the subclass's business has refresh logic, you can override this method
    }

}
