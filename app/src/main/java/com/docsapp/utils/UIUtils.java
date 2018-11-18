package com.docsapp.utils;

/*
 * Created by Kushaal Singla on 23-Nov-17.
 */


import com.docsapp.config.MyApplication;

public class UIUtils extends com.ks.myutils.utils.UIUtils {

    public static void makeToast(String s) {
        makeToast(MyApplication.getContext(), s);
    }

    public static void makeToastLong(String s) {
        makeToastLong(MyApplication.getContext(), s);
    }
}
