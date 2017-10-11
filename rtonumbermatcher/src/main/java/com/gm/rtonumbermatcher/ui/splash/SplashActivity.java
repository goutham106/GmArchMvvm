package com.gm.rtonumbermatcher.ui.splash;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gm.archmvvm.base.GmActivity;
import com.gm.rtonumbermatcher.R;
import com.gm.rtonumbermatcher.databinding.ActivitySplashBinding;
import com.gm.rtonumbermatcher.ui.main.MainActivity;

public class SplashActivity extends GmActivity<ActivitySplashBinding, SplashViewModel> {
    @Override
    public int initView(Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SplashViewModel.class);
        return R.layout.activity_splash;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.setViewModel(mViewModel);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 3000);
    }

}

