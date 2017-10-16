package com.ejar.fastbedroom.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.baseframe.utils.toast.ToastUtils;
import com.ejar.fastbedroom.application.APP;
import com.jph.takephoto.uitl.TUriParse;

import java.util.Stack;

/**
 * Created by json on 2017/8/14.
 */

public class AppManager {

    private static Stack<Activity> activityStruct = new Stack<>();

    public static void addActivity(Activity activity) {
        activityStruct.push(activity);
    }

    public static void removeActivity(Activity activity) {
        if (activity != null) {
            activityStruct.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }

    }

    public static void removeAllAty() {
        TU.cT("请登录！");
        for (Activity activity : activityStruct) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStruct.clear();
        SpUtils.put(APP.getContext(),"token","");
    }
    public static void backToHome() {
        for (Activity activity : activityStruct) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStruct.clear();
//        SpUtils.put(APP.getContext(),"token","");
    }




    /**
     * 退出应用程序
     */
    public static void AppExit(Context context) {
        try {
            removeAllAty();
            ActivityManager manager = (ActivityManager)
                    context.getSystemService(Context.ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
