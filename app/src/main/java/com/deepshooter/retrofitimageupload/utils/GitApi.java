package com.deepshooter.retrofitimageupload.utils;


import com.deepshooter.retrofitimageupload.model.OutPutBean;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Avinash on 29-07-2017.
 */

public interface GitApi {



    @Multipart
    @POST("uploadImage")
    Call<OutPutBean> uploadImage(@Part MultipartBody.Part image, @Part MultipartBody.Part uid);

}
