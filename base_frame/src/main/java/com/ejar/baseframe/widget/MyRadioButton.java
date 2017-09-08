package com.ejar.baseframe.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.ejar.baseframe.R;


/**
 * Created by json on 2017/8/24.
 */

@SuppressLint("AppCompatCustomView")
public class MyRadioButton extends RadioButton {

//    private int mDrawableSize;// xml文件中设置的大小

    public MyRadioButton(Context context) {
        this(context, null);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyRadioButton);
        Drawable drawableTop = a.getDrawable(R.styleable.MyRadioButton_rbDrawableTop);
//        mDrawableSize = a.getDimensionPixelSize(R.styleable.MyRadioButton_rbDrawableTopSize);
        setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null, null);
    }

    /**
     * RadioButton上、下、左、右设置图标
     */
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {


        if (top != null) {
            top.setBounds(0, 0, 70, 60);
        }

        setCompoundDrawables(left, top, right, bottom);
    }

}
