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
