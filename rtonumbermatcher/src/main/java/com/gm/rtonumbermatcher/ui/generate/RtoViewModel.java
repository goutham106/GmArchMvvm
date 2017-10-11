/*
 * Copyright (c) 2017 Gowtham Parimelazhagan.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gm.rtonumbermatcher.ui.generate;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.gm.archmvvm.di.scope.AppScope;
import com.gm.archmvvm.mvvm.BaseViewModel;
import com.gm.repository.http.Status;
import com.gm.repository.rxerrorhandler.handler.ErrorHandleSubscriber;
import com.gm.repository.utils.RepositoryUtils;
import com.gm.rtonumbermatcher.ui.generate.model.TextContent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 */

@AppScope
public class RtoViewModel extends BaseViewModel<RtoModel> {

    private MutableLiveData<List<TextContent>> mContents;
    private List<String> mData;

    @Inject
    RtoViewModel(Application application, RtoModel model) {
        super(application, model);
    }


    LiveData<List<TextContent>> getSearchData(int start, int end, int search) {
        if (mContents == null)
            mContents = new MutableLiveData<>();

        if (mData == null)
            mData = new ArrayList<>();


        mModel.getDatas(start, end, search)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<List<TextContent>>(
                                   RepositoryUtils.INSTANCE.obtainRepositoryComponent(getApplication()).rxErrorHandler()) {
                               @Override
                               public void onSubscribe(Disposable d) {
                                   super.onSubscribe(d);
                                   addDispose(d);
                               }

                               @Override
                               public void onNext(List<TextContent> textContents) {
                                   mStatus.set(Status.SUCCESS);
//                                   mContents.setValue(textContents);
                                   mContents.postValue(textContents);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   super.onError(e);
                                   mStatus.set(Status.ERROR);
                               }
                           }
                );

        return mContents;
    }

    LiveData<List<TextContent>> getSearchDataBySort(int start, int end, int search, int sort) {
        if (mContents == null)
            mContents = new MutableLiveData<>();

        if (mData == null)
            mData = new ArrayList<>();


        mModel.getSortedData(start, end, search, sort)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<List<TextContent>>(
                                   RepositoryUtils.INSTANCE.obtainRepositoryComponent(getApplication()).rxErrorHandler()) {
                               @Override
                               public void onSubscribe(Disposable d) {
                                   super.onSubscribe(d);
                                   addDispose(d);
                               }

                               @Override
                               public void onNext(List<TextContent> textContents) {
//                                   mContents.setValue(textContents);
                                   mContents.postValue(textContents);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   super.onError(e);
                               }
                           }
                );


        return mContents;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        this.mContents = null;
    }
}
