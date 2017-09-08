package com.ejar.baseframe.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ejar.baseframe.R;

/**
 * Created by json on 2017/8/14.
 */

public class HomeBottomTab extends LinearLayout {
    private String sName;
    private Drawable sIcon;
    private ImageView sIconImgView;
    private TextView sNameTv;

    public HomeBottomTab(Context context) {
        this(context, null);
    }

    public HomeBottomTab(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBottomTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.tabbar_mains, this, true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabBar_Attr);
        sName = typedArray.getString(R.styleable.TabBar_Attr_name);
        sIcon = typedArray.getDrawable(R.styleable.TabBar_Attr_icon);
        typedArray.recycle();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        sIconImgView = (ImageView) findViewById(R.id.icon_tabbar);
        sNameTv = (TextView) findViewById(R.id.name_tabbar);
        if (TextUtils.isEmpty(sName)) ;
        else setName(sName);
        if (sIcon != null)
            setIcon(sIcon);
    }

    public void setName(String name) {
        sNameTv.setText(name);

    }

    public void setIcon(Drawable icon) {
        sIconImgView.setImageDrawable(icon);
    }
}
