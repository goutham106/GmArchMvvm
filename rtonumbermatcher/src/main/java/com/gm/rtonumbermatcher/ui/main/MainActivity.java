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

package com.gm.rtonumbermatcher.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import com.gm.archmvvm.base.BaseActivity;
import com.gm.rtonumbermatcher.R;
import com.gm.rtonumbermatcher.databinding.ActivityMainBinding;
import com.gm.rtonumbermatcher.ui.generate.RtoFragment;
import com.gm.rtonumbermatcher.util.FragmentUtils;
import com.gm.rtonumbermatcher.util.ToastUtils;

import org.simple.eventbus.EventBus;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements NavigationView.OnNavigationItemSelectedListener {


    private Fragment rootFragment;

    @Override
    public int initView(Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.setViewModel(mViewModel);
        mBinding.navView.setNavigationItemSelectedListener(this);
        initToolbar();
        openFragment();
        mBinding.appContainer.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(view, "onFabClick");
            }
        });
    }

    private void openFragment() {
//        ArrayList<Fragment> fragments = new ArrayList<>();
//        fragments.add(RtoFragment.newInstance());
        rootFragment = FragmentUtils.addFragment(getSupportFragmentManager(), RtoFragment.newInstance(), R.id.main_container, false);
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.main_container, RtoFragment.newInstance());
//        transaction.commit();
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.appContainer.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mBinding.drawerLayout, mBinding.appContainer.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!FragmentUtils.dispatchBackPress(getSupportFragmentManager())) {
            super.onBackPressed();
        }
//        else {
//            super.onBackPressed();
//        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            ToastUtils.showLongToast("Hi this is Goutham :)");
        }

        mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
