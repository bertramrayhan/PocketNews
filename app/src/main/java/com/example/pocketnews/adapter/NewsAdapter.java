package com.example.pocketnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pocketnews.DetailActivity;
import com.example.pocketnews.R;
import com.example.pocketnews.models.Article;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private List<Article> articleList;

    public NewsAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList.clear();
        this.articleList.addAll(articleList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        Article currentArticle = articleList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(currentArticle.getUrlToImage())
                .into(holder.newsImage);
        holder.judulBerita.setText(currentArticle.getTitle());
        holder.sumberBerita.setText(currentArticle.getSource().getName());
        holder.tanggalPublikasi.setText(currentArticle.getFormattedPublishedAt());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("ARTICLE", currentArticle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private ImageView newsImage;
        private TextView judulBerita, sumberBerita, tanggalPublikasi;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            newsImage = itemView.findViewById(R.id.newsImage);
            judulBerita = itemView.findViewById(R.id.judulBerita);
            sumberBerita = itemView.findViewById(R.id.sumberBerita);
            tanggalPublikasi = itemView.findViewById(R.id.tanggalPublikasi);
        }
    }
}
