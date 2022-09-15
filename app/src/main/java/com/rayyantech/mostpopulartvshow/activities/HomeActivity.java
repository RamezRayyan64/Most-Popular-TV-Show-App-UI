package com.rayyantech.mostpopulartvshow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rayyantech.mostpopulartvshow.adapters.FragmentAdapter;
import com.rayyantech.mostpopulartvshow.adapters.MostPopularTVShowAdapter;
import com.rayyantech.mostpopulartvshow.databinding.ActivityHomeBinding;
import com.rayyantech.mostpopulartvshow.fragment.HomeFragment;
import com.rayyantech.mostpopulartvshow.fragment.SearchFragment;
import com.rayyantech.mostpopulartvshow.fragment.WatchlistFragment;
import com.rayyantech.mostpopulartvshow.helper.ApiClient;
import com.rayyantech.mostpopulartvshow.models.MostPopularTVShowModel;
import com.rayyantech.mostpopulartvshow.models.MostPopularTVShowsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding activityHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());
    }
}