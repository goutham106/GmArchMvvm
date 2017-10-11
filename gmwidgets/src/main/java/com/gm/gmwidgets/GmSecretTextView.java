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

package com.gm.gmwidgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/11/17.
 */

//        To fade in or fade out the text:
//        secretTextView.show();    // fade in
//        secretTextView.hide();    // fade out
//        secretTextView.toggle();  // fade in or out depending on current state
//
//        To change the duration of the fading (default is 2.5 seconds):
//        secretTextView.setDuration(3000);     // set fade duration to 3 seconds
//
//        To set visibility without fading in or out:
//        secretTextView.setIsVisible(true);
//        secretTextView.setIsVisible(false);

public class GmSecretTextView extends TextView {
    ValueAnimator animator;
    private String mTextString;
    private SpannableString mSpannableString;
    private double[] mAlphas;
    private MutableForegroundColorSpan[] mSpans;
    private boolean mIsVisible;
    private boolean mIsTextResetting = false;
    ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Float percent = (Float) valueAnimator.getAnimatedValue();
            resetSpannableString(mIsVisible ? percent : 2.0f - percent);
        }
    };
    private int mDuration = 2500;

    public GmSecretTextView(Context context) {
        super(context);
        init();
    }

    public GmSecretTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.mIsVisible = false;
        animator = ValueAnimator.ofFloat(0.0f, 2.0f);
        animator.addUpdateListener(listener);
        animator.setDuration(mDuration);
    }

    public void toggle() {
        if (mIsVisible) {
            hide();
        } else {
            show();
        }
    }

    public void show() {
        mIsVisible = true;
        animator.start();
    }

    public void hide() {
        mIsVisible = false;
        animator.start();
    }

    public boolean getIsVisible() {
        return mIsVisible;
    }

    public void setIsVisible(boolean isVisible) {
        mIsVisible = isVisible;
        resetSpannableString(isVisible == true ? 2.0f : 0.0f);
    }

    private void resetSpannableString(double percent) {
        mIsTextResetting = true;

        int color = getCurrentTextColor();
        for (int i = 0; i < this.mTextString.length(); i++) {
            MutableForegroundColorSpan span = mSpans[i];
            span.setColor(Color.argb(clamp(mAlphas[i] + percent), Color.red(color), Color.green(color), Color.blue(color)));
        }

        setText(mSpannableString);

        mIsTextResetting = false;
    }

    private void resetAlphas(int length) {
        mAlphas = new double[length];
        for (int i = 0; i < mAlphas.length; i++) {
            mAlphas[i] = Math.random() - 1;
        }
    }

    private void resetIfNeeded() {
        if (!mIsTextResetting) {
            mTextString = getText().toString();
            mSpannableString = new SpannableString(this.mTextString);
            mSpans = new MutableForegroundColorSpan[this.mTextString.length()];
            for (int i = 0; i < this.mTextString.length(); i++) {
                MutableForegroundColorSpan span = new MutableForegroundColorSpan();
                mSpannableString.setSpan(span, i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mSpans[i] = span;
            }
            resetAlphas(mTextString.length());
            resetSpannableString(mIsVisible ? 2.0f : 0);
        }
    }

    public void setText(String text) {
        super.setText(text);
        resetIfNeeded();
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        super.setText(text, type);
        resetIfNeeded();
    }

    private int clamp(double f) {
        return (int) (255 * Math.min(Math.max(f, 0), 1));
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
        animator.setDuration(duration);
    }
}
