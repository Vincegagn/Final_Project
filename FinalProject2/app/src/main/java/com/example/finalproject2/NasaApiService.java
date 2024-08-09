package com.example.finalproject2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NasaApiService {
    @GET("planetary/apod")
    Call<NasaResponse> getApod(@Query("api_key") String apiKey, @Query("date") String date);
}
