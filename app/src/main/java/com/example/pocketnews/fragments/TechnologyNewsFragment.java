package com.example.pocketnews.fragments;

import static android.provider.ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pocketnews.R;
import com.example.pocketnews.Rahasia;
import com.example.pocketnews.adapter.NewsAdapter;
import com.example.pocketnews.models.Article;
import com.example.pocketnews.models.NewsResponse;
import com.example.pocketnews.network.ApiClient;
import com.example.pocketnews.network.ApiService;
import com.example.pocketnews.viewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TechnologyNewsFragment extends Fragment {

    private final String CATEGORY = "technology";
    private MainViewModel mainViewModel;
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;

    public TechnologyNewsFragment() {
        // Required empty public constructor
    }

    public static TechnologyNewsFragment newInstance(String param1, String param2) {
        TechnologyNewsFragment fragment = new TechnologyNewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_technology_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newsRecyclerView = view.findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this.requireContext()));

        newsAdapter = new NewsAdapter(new ArrayList<>());
        newsRecyclerView.setAdapter(newsAdapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.fetchNews(CATEGORY);

        mainViewModel.getArticlesLiveData().observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                newsAdapter.setArticleList(articles);
            }
        });
    }
}