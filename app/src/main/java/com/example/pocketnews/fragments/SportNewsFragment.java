package com.example.pocketnews.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pocketnews.R;
import com.example.pocketnews.adapter.NewsAdapter;
import com.example.pocketnews.models.Article;
import com.example.pocketnews.viewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class SportNewsFragment extends Fragment {

    private final String CATEGORY = "sport";
    private MainViewModel mainViewModel;
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;

    public SportNewsFragment() {
        // Required empty public constructor
    }

    public static SportNewsFragment newInstance(String param1, String param2) {
        SportNewsFragment fragment = new SportNewsFragment();
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
        return inflater.inflate(R.layout.fragment_sport_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newsRecyclerView = view.findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this.requireContext()));

        mainViewModel = new ViewModelProvider(this.requireActivity()).get(MainViewModel.class);

        newsAdapter = new NewsAdapter(new ArrayList<>());
        newsRecyclerView.setAdapter(newsAdapter);

        mainViewModel.getArticlesLiveData(CATEGORY).observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                newsAdapter.setArticleList(articles);
            }
        });
    }
}