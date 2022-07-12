package com.cw.playnxt.server;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit
{
    private static final String BASE_URL=Allurls.MainURL;
    private static MyRetrofit myClient;
    private Retrofit retrofit;

    private MyRetrofit()
    {
        retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }
    public static synchronized MyRetrofit getInstance(){
        if (myClient==null){
            myClient=new MyRetrofit();
        }
        return myClient;
    }
    public JsonPlaceHolderApi getMyApi(){
        return retrofit.create(JsonPlaceHolderApi.class);
    }
}
