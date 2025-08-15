package com.example.pocketnews.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pocketnews.Rahasia;
import com.example.pocketnews.models.Article;
import com.example.pocketnews.models.NewsResponse;
import com.example.pocketnews.network.ApiClient;
import com.example.pocketnews.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private final String COUNTRY = "us";

    private MutableLiveData<List<Article>> articlesLiveData = new MutableLiveData<>();

    public LiveData<List<Article>> getArticlesLiveData() {
        return articlesLiveData;
    }

    public void fetchNews(String category) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<NewsResponse> newsResponseCall = apiService.getNews(COUNTRY, category, Rahasia.API_KEY);

        newsResponseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_SUCCESS", "Response berhasil. Jumlah artikel: " + response.body().getArticles().size());
                    List<Article> articleList = response.body().getArticles();

                    articlesLiveData.postValue(articleList);
                } else {
                    try {
                        Log.e("API_ERROR", "Response gagal. Kode: " + response.code() + " | Pesan: " + response.message());
                        if (response.errorBody() != null) {
                            Log.e("API_ERROR_BODY", "Error Body: " + response.errorBody().string());
                        }
                    } catch (Exception e) {
                        Log.e("API_ERROR_PARSE", "Gagal parsing error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e("API_FAILURE", "Request gagal total.", t);
            }
        });
    }
}
