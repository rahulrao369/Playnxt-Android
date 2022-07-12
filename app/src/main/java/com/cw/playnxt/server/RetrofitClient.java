package com.cw.playnxt.server;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;


    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//Socket Time Exception increase
            builder.connectTimeout(60000, TimeUnit.SECONDS);
            builder.readTimeout(60000, TimeUnit.SECONDS);
            builder.writeTimeout(60000, TimeUnit.SECONDS);
//--------------------

//debbugging
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
//--------------------

//header
            builder.addNetworkInterceptor(new AddHeaderInterceptor());
//------------------------


            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(builder.build())
                    .build();
        }
        return retrofit;
    }
}

