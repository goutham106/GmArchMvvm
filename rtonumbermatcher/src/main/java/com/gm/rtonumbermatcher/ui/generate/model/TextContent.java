package com.gm.rtonumbermatcher.ui.generate.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/28/17.
 */

public class TextContent extends BaseObservable {
    private String title;

    public TextContent(String title) {
        this.title = title;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
