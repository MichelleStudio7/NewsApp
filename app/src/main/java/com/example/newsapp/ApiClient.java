package com.example.newsapp;

import androidx.core.util.Pools;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private  static  final String BASE_URL="https://newsapi.org/v2/";
//    private  static  final String BASE_URL="https://newsapi.org/v2/top-headlines?country=in&apiKey=0410489401ba45569b93e7a47f4f9237";

    private static Retrofit retrofit;
    private  static ApiClient apiClient;

    private ApiClient(){
        retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public  static synchronized ApiClient getInstance(){
        if(apiClient==null){

            apiClient=new ApiClient();

        }
        return  apiClient;
    }
    public ApiInterface getApi(){
        return retrofit.create(ApiInterface.class);
    }


}
