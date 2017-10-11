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

package com.gm.rtonumbermatcher.ui.generate.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;

import com.gm.archmvvm.mvvm.binding.BaseBindAdapter;
import com.gm.archmvvm.mvvm.binding.BaseBindHolder;
import com.gm.rtonumbermatcher.BR;
import com.gm.rtonumbermatcher.ui.generate.model.TextContent;

import java.util.List;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/28/17.
 */

public class RtoAdapter extends BaseBindAdapter<TextContent> {

    public RtoAdapter(int layoutResId, @Nullable List<TextContent> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseBindHolder helper, TextContent item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.content, item);
        binding.executePendingBindings();
    }
}
