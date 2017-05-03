package com.gaos.dropdialogex;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
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

public class DropDialogView extends FrameLayout {

    private RelativeLayout rlRemindDialog;
    private long durationMillis = 500;
    private IEvent event;
    private static final String TAG = "DropDialogView";
    private FrameLayout flRoot;

    public DropDialogView(Context context) {
        super(context);
        inflate(getContext(), R.layout.layout_fl_dialog, this);
        initView();
    }

    private void initView() {
        flRoot = (FrameLayout) findViewById(R.id.fl_root_dialog);
        rlRemindDialog = (RelativeLayout) findViewById(R.id.rl_remind_dialog);
        Button btnConfirmDialog = (Button) rlRemindDialog.findViewById(R.id.btn_confirm_dialog);
        btnConfirmDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
    }

    private void dismissDialog() {
        executeAnim(0, 1).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                excuteFadeOut();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                flRoot.setVisibility(View.GONE);
                flRoot.setBackgroundColor(Color.GRAY);
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

    private void excuteFadeOut() {
        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 0);
        valueAnimator.setDuration(durationMillis);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                Log.i(TAG, "onAnimationUpdate: " + fraction);
                Integer evaluate = (Integer) argbEvaluator.evaluate(fraction, Color.GRAY, Color.TRANSPARENT);
                flRoot.setBackgroundColor(evaluate);
            }
        });
    }

    public void showDialog(@NonNull IEvent event) {
        this.event = event;
        executeAnim(-1, 0);
    }

    private TranslateAnimation executeAnim(int fromY, int toY) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, fromY, Animation.RELATIVE_TO_PARENT, toY);
        translateAnimation.setDuration(durationMillis);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new BounceInterpolator());
        rlRemindDialog.clearAnimation();
        rlRemindDialog.setAnimation(translateAnimation);
        translateAnimation.start();
        return translateAnimation;
    }

    public interface IEvent {
        void onDismissCompleted();
    }
}
