package com.yjs.complexmenu;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;

/**
 * Created by yangjingsong on 17/2/20.
 */

public class AnimationUtil {
    boolean isAnimating = false;
    View target;

    public AnimationUtil(View view) {
        target = view;
    }

    public int getWidth() {
        return target.getLayoutParams().width;
    }

    public int getHeight() {
        return target.getLayoutParams().height;
    }

    public void setWidth(int width) {
        target.getLayoutParams().width = width;
        target.requestLayout();
    }

    public void setHeight(int height) {
        target.getLayoutParams().height = height;
        target.requestLayout();
    }


    public static void shrinkAnimate(final View view, Context context, final int height) {
        final AnimationUtil wrapper = new AnimationUtil(view);
        ObjectAnimator animator = ObjectAnimator.ofInt(wrapper, "height", height, 0);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                wrapper.setHeight(height);
                Log.d("measureHeight", view.getHeight() + "");

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(300).start();
    }

    public static ObjectAnimator stretchAnimate(View view, Context context, final int height) {
        final AnimationUtil wrapper = new AnimationUtil(view);
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(wrapper, "height", 0, height);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

                wrapper.setHeight(height);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.setDuration(300).start();
        return objectAnimator;
    }

    private static long lastClickTime = 0;
    private static final int SPACE_TIME = 500;
    public synchronized static boolean isDoubleClick(){
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        if(currentTime-lastClickTime>SPACE_TIME){
            isClick2 = false;
        }else {
            isClick2 = true;
        }
        lastClickTime = currentTime;
        return isClick2;
    }
}
