package com.innoviussoftwaresolution.tjss.AddDeleteCheckInHistory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.innoviussoftwaresolution.tjss.BuildConfig;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 3/29/2018.
 */

public class APIClient {


    //public static final String BASE_URL = "http://innoviussoftware.com/tjss/tjss_api/public/";


    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(CheckInHistoryConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        /*if (BuildConfig.DEBUG) {
            Log.d("Error",HttpLoggingInterceptor.Level.BODY.toString());
        }*/


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        return retrofit;
    }

}
