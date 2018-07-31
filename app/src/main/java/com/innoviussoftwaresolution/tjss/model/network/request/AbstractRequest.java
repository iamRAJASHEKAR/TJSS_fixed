package com.innoviussoftwaresolution.tjss.model.network.request;

import android.support.annotation.NonNull;
import android.util.Log;

import com.bugsnag.android.Bugsnag;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Sony Raj on 04-08-17.
 */

public abstract class AbstractRequest<ResponseType, NetworkInterface> implements Callback<ResponseType> {

    NetworkInterface mNetworkInterface;
    private RequestCallback<ResponseType> mRequestCallback;

    AbstractRequest(Class<NetworkInterface> networkInterface, RequestCallback<ResponseType>
            callback) {
        mRequestCallback = callback;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        @SuppressWarnings("HardCodedStringLiteral")
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .build())
                //.baseUrl("http://innoviussoftware.com/tjss/tjss_api/public/")
                .baseUrl("https://tjssapi.cyboticx.com/")
                .build();

        mNetworkInterface = retrofit.create(networkInterface);
    }

    @Override
    public void onResponse(@NonNull Call<ResponseType> call, @NonNull Response<ResponseType> response) {
        if (mRequestCallback != null)
            mRequestCallback.onSuccess(response.body());
        Log.e("Response", new GsonBuilder().setPrettyPrinting().create().toJson(response));


    }

    @Override
    public void onFailure(@NonNull Call<ResponseType> call, @NonNull Throwable t) {
        if (mRequestCallback != null)
            mRequestCallback.onFailure(t.getMessage());
        Bugsnag.notify(new RuntimeException(t));
    }


}
