package com.example.pocketnews.network;

import com.example.pocketnews.models.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("top-headlines")
    Call<NewsResponse> getNews(@Query("country") String country,
                               @Query("category") String category,
                               @Query("apikey") String apiKey);
}
