package com.ejar.baseframe.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @Author cairui
 * @CreateTime 2017/7/11 0011 09:50
 */
public class CustomDialog extends Dialog {
    public CustomDialog(@NonNull Context context) {
        this(context, 0);
    }

    public CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public static class Builder {
        private int     mGravity;
        private View    mView;
        private int     mWindowAnimations;
        private Context mContext;
        private int     mThemeResId;
        private boolean mCancelable;
        private boolean mDimEnable;
        private boolean mFullScreen;
        private int     x;
        private int     y;


        public Builder(Context context) {
            this.mContext = context;
            this.mThemeResId = 0;
            this.mGravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
            this.mCancelable = true;
            this.mDimEnable = true;
            this.mFullScreen = false;
        }

        public Builder View(@NonNull View view) {
            this.mView = view;
            return this;
        }

        public Builder fullScreen(boolean fullscreen) {
            this.mFullScreen = fullscreen;
            return this;
        }

        public Builder gravity(int gravity) {
            this.mGravity = gravity;
            return this;
        }

        public Builder withAnimation(int animRes) {
            this.mWindowAnimations = animRes;
            return this;
        }

        public Builder style(@StyleRes int themeResId) {
            this.mThemeResId = themeResId;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public Builder dimEnable(boolean dimEnable) {
            this.mDimEnable = dimEnable;
            return this;
        }

        public Builder xy(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public CustomDialog build() {
            CustomDialog dialog = new CustomDialog(mContext, mThemeResId);
            if (mView != null) {
                dialog.setContentView(mView);
            }
            dialog.setCancelable(mCancelable);
            Window window = dialog.getWindow();

            if (mWindowAnimations != 0) {
                window.setWindowAnimations(mWindowAnimations);
            }
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = mGravity;
            if (x != 0 || y != 0) {
                attributes.gravity = Gravity.TOP | Gravity.START;
                attributes.x = x;
                attributes.y = y;
            }
            if (mFullScreen) {
                window.getDecorView().setPadding(0, 0, 0, 0);
                attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
                //                attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            if (!mDimEnable) {
                window.setDimAmount(0f);//背景不变暗
            }
            window.setAttributes(attributes);
            return dialog;
        }

    }


    public void showAsDropDown(View anchor, int xOffset, int yOffset) {
        if (anchor == null) {
            return;
        }
        final int anchorHeight = anchor.getHeight();
        final int anchorWidth = anchor.getWidth();
        final int[] drawingLocation = new int[2];
        anchor.getLocationInWindow(drawingLocation);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.TOP | Gravity.START;
        attributes.x = drawingLocation[0] + xOffset;
        attributes.y = drawingLocation[1] + anchorHeight + yOffset;
        getWindow().setAttributes(attributes);
        show();
    }

}
