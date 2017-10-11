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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/3/17.
 */

public class GmFadeTextView extends AppCompatTextView {

    public static final int DEFAULT_TIME_OUT = 15000;
    public static final int MILLISECONDS = 1,
            SECONDS = 2,
            MINUTES = 3;

    private Animation fadeInAnimation, fadeOutAnimation;
    private Handler handler;
    private CharSequence[] texts;
    private CharSequence sTexts;
    private boolean isSingleText;
    private boolean isShown;
    private int position;
    private int timeout = DEFAULT_TIME_OUT;
    private boolean stopped;

    public GmFadeTextView(Context context) {
        super(context);
        init();
    }

    public GmFadeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        if (!isInEditMode()) {
            handleAttrs(context, attrs, 0, 0);
        }
    }

    public GmFadeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        if (!isInEditMode()) {
            handleAttrs(context, attrs, defStyleAttr, 0);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GmFadeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init();
        if (!isInEditMode()) {
            handleAttrs(context, attrs, defStyleAttr, defStyleRes);
        }
    }

    /**
     * Resumes the animation
     * Should only be used if you notice {@link #onAttachedToWindow()} ()} is not being executed as expected
     */
    public void resume() {
        isShown = true;
        startAnimation();
    }

    /**
     * Pauses the animation
     * Should only be used if you notice {@link #onDetachedFromWindow()} is not being executed as expected
     */
    public void pause() {
        isShown = false;
        stopAnimation();
    }

    /**
     * Stops the animation
     * Unlike the pause function, the stop method will permanently stop the animation until the view is restarted
     */
    public void stop() {
        isShown = false;
        stopped = true;
        stopAnimation();
    }

    /**
     * Restarts the animation
     * Only use this to restart the animation after stopping it using {@link #stop}
     */
    public void restart() {
        isShown = true;
        stopped = false;
        startAnimation();
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pause();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        resume();
    }

    /**
     * Initialize the view and the animations
     */
    private void init() {
        fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        fadeOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout);
        handler = new Handler();
        isShown = true;
    }

    /**
     * Handle the attributes
     * set the texts
     * set the timeout
     *
     * @param attrs provided attributes
     */
    private void handleAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GmFadeTextView, defStyleAttr, defStyleRes);
//        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.fading_text_view);
        this.texts = a.getTextArray(R.styleable.GmFadeTextView_texts);
        this.sTexts = a.getText(R.styleable.GmFadeTextView_texts);
        this.timeout = Math.abs(a.getInteger(R.styleable.GmFadeTextView_timeout, 14500)) + getResources().getInteger(android.R.integer.config_longAnimTime);
        if (!TextUtils.isEmpty(sTexts)) {
            isSingleText = true;
        }
        a.recycle();
    }

    /**
     * Get a list of the texts
     *
     * @return the texts array
     */
    public CharSequence[] getTexts() {
        return texts;
    }

    /**
     * Sets the texts to be shuffled using a string array
     *
     * @param texts The string array to use for the texts
     */
    public void setTexts(@NonNull String texts) {
        isSingleText = true;
        this.sTexts = texts;
        stopAnimation();
        position = 0;
        startAnimation();
    }

    public CharSequence getsTexts() {
        return sTexts;
    }

    /**
     * Sets the texts to be shuffled using a string array resource
     *
     * @param texts The string array resource to use for the texts
     */
    public void setTexts(@ArrayRes int texts) {
        isSingleText = false;
        if (getResources().getStringArray(texts).length < 1)
            throw new IllegalArgumentException("There must be at least one text");
        else {
            this.texts = getResources().getStringArray(texts);
            stopAnimation();
            position = 0;
            startAnimation();
        }
    }

    /**
     * Sets the texts to be shuffled using a string array
     *
     * @param texts The string array to use for the texts
     */
    public void setTexts(@NonNull String[] texts) {
        isSingleText = false;
        if (texts.length < 1)
            throw new IllegalArgumentException("There must be at least one text");
        else {
            this.texts = texts;
            stopAnimation();
            position = 0;
            startAnimation();
        }
    }

    /**
     * This method should only be used to forcefully apply timeout changes
     * It will dismiss the currently queued animation change and start a new animation
     */
    public void forceRefresh() {
        stopAnimation();
        startAnimation();
    }

    /**
     * Sets the length of time to wait between text changes in milliseconds
     *
     * @param timeout The length of time to wait between text change in milliseconds
     * @deprecated use {@link #setTimeout(long, java.util.concurrent.TimeUnit)} instead.
     */
    @Deprecated
    public void setTimeout(int timeout) {
        if (timeout < 1)
            throw new IllegalArgumentException("Timeout must be longer than 0");
        else
            this.timeout = timeout;
    }

    /**
     * Sets the length of time to wait between text changes in specific time units
     *
     * @param timeout  The length of time to wait between text change
     * @param timeUnit The time unit to use for the timeout parameter
     *                 Must be of {@link TimeUnit} type.    Either {@link #MILLISECONDS} or
     *                 {@link #SECONDS} or
     *                 {@link #MINUTES}
     * @deprecated use {@link #setTimeout(long, java.util.concurrent.TimeUnit)} instead.
     */
    @Deprecated
    public void setTimeout(double timeout, @TimeUnit int timeUnit) {
        if (timeout <= 0)
            throw new IllegalArgumentException("Timeout must be longer than 0");
        else {
            int multiplier;
            switch (timeUnit) {
                case MILLISECONDS:
                    multiplier = 1;
                    break;
                case SECONDS:
                    multiplier = 1000;
                    break;
                case MINUTES:
                    multiplier = 60000;
                    break;
                default:
                    multiplier = 1;
                    break;
            }
            this.timeout = (int) (timeout * multiplier);
        }
    }

    @SuppressLint("reference not found")
    /**
     * Sets the length of time to wait between text changes in specific time units
     *
     * @param timeout  The length of time to wait between text change
     * @param timeUnit The time unit to use for the timeout parameter
     *                 Must be of {@link java.util.concurrent.TimeUnit} type.
     *                 Must be one of
     *                 {@link java.util.concurrent.TimeUnit.NANOSECONDS} or
     *                 {@link java.util.concurrent.TimeUnit.MICROSECONDS} or
     *                 {@link java.util.concurrent.TimeUnit.MILLISECONDS} or
     *                 {@link java.util.concurrent.TimeUnit.SECONDS} or
     *                 {@link java.util.concurrent.TimeUnit.MINUTES} or
     *                 {@link java.util.concurrent.TimeUnit.HOURS} or
     *                 {@link java.util.concurrent.TimeUnit.DAYS} or
     */
    public void setTimeout(long timeout, java.util.concurrent.TimeUnit timeUnit) {
        if (timeout <= 0)
            throw new IllegalArgumentException("Timeout must be longer than 0");
        else {
            this.timeout = (int) java.util.concurrent.TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
        }
    }

    /**
     * Start the specified animation now if should
     *
     * @param animation the animation to start now
     */
    @Override
    public void startAnimation(Animation animation) {
        if (isShown && !stopped)
            super.startAnimation(animation);
    }

    /**
     * Start the animation
     */
    protected void startAnimation() {
        if (isSingleText) {
            setText(sTexts);
        } else {
            setText(texts[position]);
        }
        startAnimation(fadeInAnimation);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation(fadeOutAnimation);
                if (getAnimation() != null)
                    getAnimation().setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (!isSingleText) {
                                if (isShown) {
                                    position = position == texts.length - 1 ? 0 : position + 1;
                                    startAnimation();
                                }
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
            }
        }, timeout);
    }

    /**
     * Stop the currently active animation
     */
    private void stopAnimation() {
        handler.removeCallbacksAndMessages(null);
        if (getAnimation() != null) getAnimation().cancel();
    }


    @IntDef({MILLISECONDS, SECONDS, MINUTES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeUnit {
    }
}
