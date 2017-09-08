package com.ejar.baseframe.base.aty;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

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
        for (Activity activity : activityStruct) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStruct.clear();
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
