package com.gaos.dropdialogex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Author:　Created by benjamin
 * DATE :  2017/5/3 16:32
 * versionCode:　1.0.0
 */

public class AnotherCustomDialog extends FrameLayout {

    private long durationMillis = 500;
    private IEvent event;
    private FrameLayout flRoot;
    private RelativeLayout dialog;

    public AnotherCustomDialog(Context context) {
        super(context);
        inflate(getContext(), R.layout.layout_fl_dialog, this);
        initView();
    }

    private void initView() {
        flRoot = (FrameLayout) findViewById(R.id.fl_root_dialog);
        dialog = (RelativeLayout) findViewById(R.id.rl_remind_dialog);
        Button btnConfirmDialog = (Button) dialog.findViewById(R.id.btn_confirm_dialog);
        btnConfirmDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
    }

    private void dismissDialog() {
        executeAnim(0, 1, dialog).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                excuteFadeOut(flRoot);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (event != null) {
                    event.onDismissCompleted();
                    event = null;
                    System.gc();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void excuteFadeOut(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setFillAfter(false);
        alphaAnimation.setDuration(durationMillis);
        view.startAnimation(alphaAnimation);
    }

    public void showDialog(@NonNull IEvent event) {
        this.event = event;
        executeAnim(-1, 0, dialog);
    }

    private TranslateAnimation executeAnim(int fromY, int toY, View view) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, fromY, Animation.RELATIVE_TO_PARENT, toY);
        translateAnimation.setDuration(durationMillis);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new BounceInterpolator());
        view.clearAnimation();
        view.setAnimation(translateAnimation);
        translateAnimation.start();
        return translateAnimation;
    }


    public interface IEvent {
        void onDismissCompleted();
    }
}
