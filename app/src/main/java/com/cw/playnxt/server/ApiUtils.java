package com.cw.playnxt.server;



public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = Allurls.MainURL;

    public static JsonPlaceHolderApi getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(JsonPlaceHolderApi.class);
    }
}