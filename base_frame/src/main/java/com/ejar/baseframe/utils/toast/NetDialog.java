package com.ejar.baseframe.utils.toast;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by json on 2017/8/23.
 */

public class NetDialog {
    public static Dialog createDialog(Context context, String msg){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.show();
        return  dialog;
    }
    public static void closeDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
