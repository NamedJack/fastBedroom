//package com.ejar.baseframe.utils.toast;
//
//import android.content.Context;
//import android.support.v7.app.AlertDialog;
//
///**
// * Created by json on 2017/8/16.
// */
//
//public class ChooseDialog {
//
//    public static ChooseDialog  chooseDialog;
//    private Context context;
//
//    private ChooseDialog(Context context) {
//        this.context = context;
//    }
//
//    public ChooseDialog getInstance(Context context){
//        if(chooseDialog == null){
//            synchronized (ChooseDialog.class){
//                if(chooseDialog == null){
//                    chooseDialog =  new ChooseDialog(context);
//                }
//            }
//        }
//        return chooseDialog;
//    }
//
//
//    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//
//}
