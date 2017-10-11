package com.gm.archmvvm.mvvm.binding;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.gm.archmvvm.R;
import com.gm.base.adapter.BaseViewHolder;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 */

public class BaseBindHolder extends BaseViewHolder {

    public BaseBindHolder(View view) {
        super(view);
    }

    public ViewDataBinding getBinding() {
        return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
    }
}