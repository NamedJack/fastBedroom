package com.ejar.baseframe.utils.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ejar.baseframe.R;

/**
 * Created by json on 2017/9/29.
 */

public class ToastUtils {
    private static volatile Toast toast;
    public static Context c;


    private ToastUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    public static void register(Context context) {
        c = context;
    }

    public static void t(String msg) {
        if (toast == null) {
            synchronized (ToastUtils.class) {
                if (toast == null) {
                    toast = Toast.makeText(c, msg, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        } else {
            toast.setText(msg);
            toast.show();
        }
    }

    public static void t(int msgRes) {
        cT(c.getString(msgRes));
    }


    //  #######################################自定义布局的Toast#############################

    private static String oldMsg;
    private static long firstShowTime;
    private static long lastShowTime;
    private static TextView tv;

    public static void cT(String s) {

        if (toast == null) {

            toast = new Toast(c);

            View v = View.inflate(c, R.layout.toast_layout, null);
            tv = (TextView) v.findViewById(R.id.tv_toast);
            tv.setText(s);
//            tv.setTextColor(Color.WHITE);

            toast.setView(v);

            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            firstShowTime = System.currentTimeMillis();
            oldMsg = s;
        } else {
            lastShowTime = System.currentTimeMillis();
            if (oldMsg.equals(s) && lastShowTime - firstShowTime < Toast.LENGTH_SHORT) {
                toast.show();
            } else {
                tv.setText(s);
//                tv.setTextColor(Color.WHITE);

                toast.show();
                oldMsg = s;
            }
            firstShowTime = lastShowTime;
        }


    }

    public static void cT(int res) {
        cT(c.getString(res));
    }
}
