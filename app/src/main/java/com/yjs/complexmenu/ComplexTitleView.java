package com.yjs.complexmenu;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by yangjingsong on 17/2/17.
 */

public class ComplexTitleView extends TextView implements IComplexItemView{
    public ComplexTitleView(Context context) {
        this(context, null);
    }

    public ComplexTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComplexTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setChecked(false);
    }

    @Override
    public void setChecked(boolean isChecked) {
        if (isChecked){
            setTextColor(Color.BLACK);
        }else {
            setTextColor(Color.BLUE);
        }

    }
}
