package com.cw.playnxt.server;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class AddHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
      /*  builder.addHeader("Content-Type", "application/json");
        builder.addHeader("Accept", "application/json");*/
        return chain.proceed(builder.build());
    }
}