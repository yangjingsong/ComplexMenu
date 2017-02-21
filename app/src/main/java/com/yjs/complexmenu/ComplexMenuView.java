package com.yjs.complexmenu;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjingsong on 17/2/17.
 */

public class ComplexMenuView extends LinearLayout {
    List<IComplexItemView> complexTitleViews;//选择按钮集合
    View lineBelowMenuView;//选择按钮下面的分割线
    FrameLayout mContentView;//盛放列表和阴影的布局
    LinearLayout mTitleBarLayout;//盛放选择按钮的布局
    View mBackView;//背景阴影布局

    List<View> mSortedViews;

    int currentOpenMenuIndex = -1;

    int sortItemHeight = 0;

    int divideLineHeight;

    int divideColor;

    int divideHeightVertical;

    int divideWidthVertical;

    int divideColorVertical;

    public ComplexMenuView(Context context) {
        this(context, null);
    }

    public ComplexMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComplexMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ComplexMenuView, defStyleAttr, 0);
        divideLineHeight = (int) typedArray.getDimension(
                R.styleable.ComplexMenuView_divide_height,
                context.getResources().getDimension(R.dimen.line_height));

        divideColor = typedArray.getColor(R.styleable.ComplexMenuView_divide_color,
                Color.GRAY);

        divideColorVertical = typedArray.getColor(R.styleable.ComplexMenuView_divide_color_vertical,Color.GRAY);
        divideHeightVertical = (int) typedArray.getDimension(R.styleable.ComplexMenuView_divide_height_vertical,
                context.getResources().getDimension(R.dimen.line_height_vertical));

        divideWidthVertical = (int) typedArray.getDimension(R.styleable.ComplexMenuView_divide_width_vertical,
                context.getResources().getDimension(R.dimen.line_width_vertical));
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {

        setOrientation(VERTICAL);
        complexTitleViews = new ArrayList<>();
        mSortedViews = new ArrayList<>();
        lineBelowMenuView = new View(context);
        lineBelowMenuView.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        divideLineHeight));
        lineBelowMenuView.setBackgroundColor(divideColor);
        initTitleBarLayout(context);
        initContentView();
        initBackView();
    }

    private void initTitleBarLayout(Context context) {
        mTitleBarLayout = new LinearLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTitleBarLayout.setLayoutParams(params);
        mTitleBarLayout.setOrientation(HORIZONTAL);
        mTitleBarLayout.setGravity(Gravity.CENTER);
    }

    private void initBackView() {
        mBackView = new View(getContext());
        mBackView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mBackView.setBackgroundColor(Color.BLACK);
        mBackView.setAlpha(0.5f);
        mBackView.setVisibility(GONE);
        mBackView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMenus();
            }
        });


    }

    public void hideMenus() {
        currentOpenMenuIndex = -1;
        ObjectAnimator.ofFloat(mBackView, "alpha", 0.5f, 0).setDuration(300).start();
        mBackView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBackView.setVisibility(GONE);
            }
        }, 300);

//        final View targetView = mContentView.getChildAt(mContentView.getChildCount()-1);
//        AnimationUtil.shrinkAnimate(targetView,getContext(),targetView.getHeight());
//        targetView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mContentView.removeView(targetView);
//                targetView.getLayoutParams().height = sortItemHeight;
//                targetView.requestLayout();
//            }
//        },300);
        mContentView.removeViewAt(mContentView.getChildCount() - 1);

        for (IComplexItemView view : complexTitleViews) {
            view.setChecked(false);
        }
    }

    private void initContentView() {
        mContentView = new FrameLayout(getContext());
        mContentView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public ComplexMenuView addTitleView(IComplexItemView titleView, View sortView) {
        complexTitleViews.add(titleView);
        mSortedViews.add(sortView);
        return this;
    }

    public void build() {
        if (complexTitleViews.size() == 0) {
            new RuntimeException("请添加title选项");
            return;
        }

        for (int j = 0; j < complexTitleViews.size(); j++) {
            View complexTitleView = (View) complexTitleViews.get(j);
            LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            layoutParams.gravity = Gravity.CENTER;
            complexTitleView.setLayoutParams(layoutParams);
            if (complexTitleView.getParent() != null) {
                ViewGroup viewGroup = (ViewGroup) complexTitleView.getParent();
                viewGroup.removeView(complexTitleView);
            }

            final View sortView = mSortedViews.get(j);


            sortView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (sortView.getParent() != null) {
                ViewGroup viewGroup = (ViewGroup) sortView.getParent();
                viewGroup.removeView(sortView);
            }


            sortView.measure(0, 0);

            if (sortView instanceof ViewGroup) {
                int height = 0;
                for (int i = 0; i < ((ViewGroup) sortView).getChildCount(); i++) {
                    ((ViewGroup) sortView).getChildAt(i).measure(0, 0);
                    height += ((ViewGroup) sortView).getChildAt(i).getMeasuredHeight();
                }
                Log.d("heighttt",height+"");
            }


            Log.d("height", sortView.getMeasuredHeight() + "");
            final int finalJ = j;
            complexTitleView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AnimationUtil.isDoubleClick()) {
                        return;
                    }
                    if (currentOpenMenuIndex == finalJ) {
                        hideMenus();
                        currentOpenMenuIndex = -1;
                    } else {
                        currentOpenMenuIndex = finalJ;
                        if (mBackView.getVisibility() == VISIBLE) {
                            mContentView.removeViewAt(mContentView.getChildCount() - 1);
                            mContentView.addView(sortView);

                        } else {
                            mBackView.setVisibility(VISIBLE);
                            ObjectAnimator.ofFloat(mBackView, "alpha", 0, 0.5f).setDuration(500).start();
                            mContentView.addView(sortView);
                            Log.d("height", sortView.getHeight() + "");

                        }

                        AnimationUtil.stretchAnimate(sortView, getContext(), sortView.getMeasuredHeight())
                        ;
                        sortItemHeight = sortView.getMeasuredHeight();
                    }

                    for (int k = 0; k < complexTitleViews.size(); k++) {
                        if (k != finalJ) {
                            complexTitleViews.get(k).setChecked(false);
                        } else {
                            complexTitleViews.get(k).setChecked(true);
                        }
                    }


                }
            });

            mTitleBarLayout.addView(complexTitleView);
            View line = new View(getContext());
            line.setLayoutParams(new LayoutParams(divideWidthVertical, divideHeightVertical));
            line.setBackgroundColor(divideColorVertical);

            if (j < complexTitleViews.size() - 1) {
                mTitleBarLayout.addView(line);
            }


        }
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            removeView(child);
            mContentView.addView(child);
        }
        mContentView.addView(mBackView);
        addView(mTitleBarLayout);
        addView(lineBelowMenuView);
        addView(mContentView);


    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new LayoutParams(LayoutParams.MATCH_PARENT
                    ,
                    LayoutParams.WRAP_CONTENT);
        }
        int lpHeight = p.height;
        int lpWidth = p.width;
        int childHeightSpec;
        int childWidthSpec;
        if (lpHeight > 0) {   //如果Height是一个定值，那么我们测量的时候就使用这个定值
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {   //否则，我们将mode设置为不指定，size设置为0
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        if (lpWidth > 0) {
            childWidthSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childWidthSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }
}
