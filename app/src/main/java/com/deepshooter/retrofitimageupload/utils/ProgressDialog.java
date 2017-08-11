package com.deepshooter.retrofitimageupload.utils;

import android.content.Context;

/**
 * Created by Avinash on 29-07-2017.
 */

public class ProgressDialog {

    private static android.app.ProgressDialog pdLoading = null;

    public static android.app.ProgressDialog getInstance(Context context) {
        if (pdLoading == null || pdLoading.getContext() != context) {
            pdLoading = new android.app.ProgressDialog(context);
            pdLoading.setMessage("Please Wait");
            pdLoading.setCancelable(false);
            pdLoading.setCanceledOnTouchOutside(false);
            return pdLoading;
        }

        return pdLoading;
    }
}
