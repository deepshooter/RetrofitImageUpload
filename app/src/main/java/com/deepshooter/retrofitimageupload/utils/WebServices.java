package com.deepshooter.retrofitimageupload.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.IntentService;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Avinash on 29-07-2017.
 */

public class WebServices<T> {

    public final static String PublicDevService = "base_url";


    private static OkHttpClient.Builder builder;
    Call<T> call = null;
    ApiType apiTypeVariable;
    Context context;
    OnResponseListener<T> mOnResponseListener;
    android.app.ProgressDialog pdLoading;
    private T t;
    public WebServices(OnResponseListener<T> onResponseListener) {
        if (onResponseListener instanceof Activity) {
            this.context = (Context) onResponseListener;
        } else if (onResponseListener instanceof IntentService) {
            this.context = (Context) onResponseListener;
        } else if (onResponseListener instanceof android.app.DialogFragment) {
            android.app.DialogFragment dialogFragment = (android.app.DialogFragment) onResponseListener;
            this.context = dialogFragment.getActivity();
        } else if (onResponseListener instanceof android.support.v4.app.Fragment) {
            android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) onResponseListener;
            this.context = fragment.getActivity();
        } else {
            Fragment fragment = (Fragment) onResponseListener;
            this.context = fragment.getActivity();
        }
        this.mOnResponseListener = onResponseListener;

        builder = getHttpClient();
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public OkHttpClient.Builder getHttpClient() {
        if (builder == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.addInterceptor(loggingInterceptor);
            client.writeTimeout(60000, TimeUnit.MILLISECONDS);
            client.readTimeout(60000, TimeUnit.MILLISECONDS);
            client.connectTimeout(60000, TimeUnit.MILLISECONDS);
            return client;
        }
        return builder;
    }

    public void uploadFile(String api, ApiType apiTypes, String filePath, String userid) {
        apiTypeVariable = apiTypes;

        try {
            pdLoading = ProgressDialog.getInstance(context);
            pdLoading.show();
        } catch (Exception e) {
            // e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
        GitApi gi = retrofit.create(GitApi.class);
        File file = new File(filePath);


        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", "Name.jpg", reqFile);
        MultipartBody.Part uid = MultipartBody.Part.createFormData("id", userid);
        call = (Call<T>) gi.uploadImage( body, uid);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                t = (T) response.body();
                if (pdLoading.isShowing())
                    pdLoading.cancel();
                mOnResponseListener.OnResponse(t, apiTypeVariable, true);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.d(apiTypeVariable.toString(), t.getMessage() + ".");
                if (pdLoading.isShowing())
                    pdLoading.cancel();
                mOnResponseListener.OnResponse(null, apiTypeVariable, false);
            }

        });
    }


    public enum ApiType {postUploadImage}

}
