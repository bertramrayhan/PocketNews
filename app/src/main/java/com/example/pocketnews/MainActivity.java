package com.example.pocketnews;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.pocketnews.viewModels.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private MainViewModel mainViewModel;
    private ProgressBar loadingBar;
    private View navHostContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);

            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) bottomNavigationView.getLayoutParams();
            params.bottomMargin = systemBars.bottom;
            bottomNavigationView.setLayoutParams(params);

            return WindowInsetsCompat.CONSUMED;
        });

        loadingBar = findViewById(R.id.loadingBar);
        navHostContainer = findViewById(R.id.navHostFragment);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading){
                    loadingBar.setVisibility(View.VISIBLE);
                    navHostContainer.setVisibility(View.INVISIBLE);
                }else{
                    loadingBar.setVisibility(View.GONE);
                    navHostContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}