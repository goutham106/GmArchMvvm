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

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gm.archmvvm.base.BaseFragment;
import com.gm.rtonumbermatcher.R;
import com.gm.rtonumbermatcher.databinding.FragmentRtoBinding;
import com.gm.rtonumbermatcher.ui.generate.adapter.RtoAdapter;
import com.gm.rtonumbermatcher.ui.main.MainViewModel;
import com.gm.rtonumbermatcher.util.CustomAnimation;
import com.gm.rtonumbermatcher.util.DebouncingOnClickListener;
import com.gm.rtonumbermatcher.util.KeyboardUtils;
import com.gm.rtonumbermatcher.util.ToastUtils;
import com.gm.rtonumbermatcher.util.VegaLayoutManager;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import javax.inject.Inject;

public class RtoFragment extends BaseFragment<FragmentRtoBinding, RtoViewModel> {

    @Inject
    MainViewModel mainViewModel;
    private RtoAdapter mAdapter;

    public static RtoFragment newInstance() {
        Bundle args = new Bundle();
        RtoFragment fragment = new RtoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(RtoViewModel.class);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rto, container, false);
        mBinding.setViewModel(mViewModel);//Set ViewModel
        return mBinding.getRoot();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.txtResponse.setHasFixedSize(true);
        mBinding.txtResponse.setLayoutManager(new VegaLayoutManager());
        mAdapter = new RtoAdapter(R.layout.item_text, null);
//        mAdapter.openLoadAnimation(new CustomAnimation());
//        mAdapter.isFirstOnly(false);
        mBinding.txtResponse.setAdapter(mAdapter);
        emptyWindow();
    }

    @Override
    public void setData(Object data) {
        /**
         * Can be called in the Activity interface, to achieve Activity and Fragment communication. Advise data to pass {@link android.os.Message};
         * This can be based on message.what to receive messages.
         * But:
         * New posture: You can use the Activity ViewModel shared data to include the Fragment, with LiveData easy to use burst.
         * @see <a href="https://developer.android.com/topic/libraries/architecture/viewmodel.html#sharing_data_between_fragments"> Sharing Data Between Fragments </a>
         */
    }

    @Subscriber(tag = "onFabClick", mode = ThreadMode.POST)
    private void onClickFab(View view) {
        view.setOnClickListener((DebouncingOnClickListener) v -> {
            generateList();
            KeyboardUtils.hideSoftInput(getActivity());
        });
    }


    public void generateList() {
        mBinding.txtCount.setVisibility(View.GONE);

        String start = mBinding.edtStartNo.getText().toString().trim();
        String end = mBinding.edtEndNo.getText().toString().trim();
        String search = mBinding.edtAdditionNo.getText().toString().trim();


        if (TextUtils.isEmpty(start)) {
            ToastUtils.showLongToast("Please fill start value");
            return;
        }
        if (TextUtils.isEmpty(end)) {
            ToastUtils.showLongToast("Please fill end value");
            return;
        }

        int mStart = Integer.valueOf(start);
        int mEnd = Integer.valueOf(end);
        int mSearch;


        if (mStart > mEnd) {
            ToastUtils.showLongToast("End value is greater than start value");
            return;
        }

        if (TextUtils.isEmpty(search)) {
            mSearch = -1;
//            ToastUtils.showLongToast("Please fill Search value");
//            return;
        } else {
            mSearch = Integer.valueOf(search);
        }

        if (mViewModel != null) {
            if (mBinding.spnCheck.getSelectedItemPosition() == 0) {
                mViewModel.getSearchData(mStart, mEnd, mSearch)
                        .observe(RtoFragment.this, textContents -> {
                            assert textContents != null;
                            mAdapter.replaceData(textContents);
                            emptyWindow();
                            mBinding.txtCount.setTexts(String.format(getString(R.string.count), textContents.size()));
                        });
            } else {
                mViewModel.getSearchDataBySort(mStart, mEnd, mSearch, mBinding.spnCheck.getSelectedItemPosition() + 1)
                        .observe(RtoFragment.this, textContents -> {
                            assert textContents != null;
                            mAdapter.replaceData(textContents);
                            emptyWindow();
                            mBinding.txtCount.setTexts(String.format(getString(R.string.count), textContents.size()));
                        });
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }

    private void emptyWindow() {
        if (mAdapter.getData().size() == 0) {
            mBinding.txtCount.setVisibility(View.GONE);
            mAdapter.setEmptyView(R.layout.empty_layout, (ViewGroup) mBinding.txtResponse.getParent());
        } else {
            mBinding.txtCount.setVisibility(View.VISIBLE);
        }

    }

//    @Override
//    public boolean onBackClick() {
//    FragmentUtils.popToFragment(getFragmentManager(), RtoFragment.class, true);
//        return false;
//    }
}
