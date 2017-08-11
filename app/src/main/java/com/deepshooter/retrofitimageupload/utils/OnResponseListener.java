package com.deepshooter.retrofitimageupload.utils;

/**
 * Created by Avinash on 29-07-2017.
 */

public interface OnResponseListener<T> {

    void OnResponse(T response, WebServices.ApiType URL, boolean isSucces);

}
