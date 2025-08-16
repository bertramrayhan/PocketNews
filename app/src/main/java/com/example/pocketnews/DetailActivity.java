package com.example.pocketnews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.pocketnews.models.Article;
import com.google.android.material.imageview.ShapeableImageView;

public class DetailActivity extends AppCompatActivity {

    private ShapeableImageView newsImage;
    private TextView judulBerita, sumberBerita, tanggalPublikasi, newsDescription, readMoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d("DetailActivity", "detail activity terbuat");

        Intent intent = getIntent();
        Article article = (Article) intent.getSerializableExtra("ARTICLE");

        newsImage = findViewById(R.id.newsImage);
        judulBerita = findViewById(R.id.judulBerita);
        sumberBerita = findViewById(R.id.sumberBerita);
        tanggalPublikasi = findViewById(R.id.tanggalPublikasi);
        newsDescription = findViewById(R.id.newsDescription);

        Glide.with(DetailActivity.this)
                .load(article.getUrlToImage())
                .into(newsImage);
        judulBerita.setText(article.getTitle());
        sumberBerita.setText(article.getSource().getName());
        tanggalPublikasi.setText(article.getFormattedPublishedAt());
        String content = article.getContent();
        int charIndex = content.lastIndexOf("[+");
        if (charIndex != -1) {
            content = content.substring(0, charIndex).trim() + "...";
        }
        newsDescription.setText(content);

        readMoreBtn = findViewById(R.id.readMoreBtn);
        readMoreBtn.setOnClickListener(v -> {
            String url = article.getUrl();
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

            builder.setToolbarColor(ContextCompat.getColor(this, R.color.primaryBackground));

            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, Uri.parse(url));
        });
    }
}