package com.newtownroom.userapp.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static final String BASE_URL = "https://app.newtownroom.com/rest/";
    private static final String PAY_U_BASE_URL = "https://www.payumoney.com/sandbox/payment/payment/";
    private static Retrofit retrofit = null;
    private static Retrofit payURetrofit = null;


    public static Retrofit getRetrofitInstance() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getPayURetrofitInstance() {
        if (payURetrofit==null) {
            payURetrofit = new Retrofit.Builder()
                    .baseUrl(PAY_U_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return payURetrofit;
    }
}
