package com.rayyantech.mostpopulartvshow.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rayyantech.mostpopulartvshow.R;
import com.rayyantech.mostpopulartvshow.adapters.MostPopularTVShowAdapter;
import com.rayyantech.mostpopulartvshow.databinding.ActivityHomeBinding;
import com.rayyantech.mostpopulartvshow.databinding.FragmentHomeBinding;
import com.rayyantech.mostpopulartvshow.helper.ApiClient;
import com.rayyantech.mostpopulartvshow.helper.MostPopularTVShowListener;
import com.rayyantech.mostpopulartvshow.models.MostPopularTVShowModel;
import com.rayyantech.mostpopulartvshow.models.MostPopularTVShowsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements MostPopularTVShowListener {

    private FragmentHomeBinding fragmentHomeBinding;
    private List<MostPopularTVShowModel> mostPopularTVShowModelList;
    private MostPopularTVShowAdapter mostPopularTVShowAdapter;
    private int currentPage = 1, totalPages = 1;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        this.view = view;
        fragmentHomeBinding.actMainLayoutToolbarImageButtonSearch.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_searchFragment);
        });
        fragmentHomeBinding.actMainLayoutToolbarImageButtonWatchlist.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_watchlistFragment);
        });
    }

    public void setupRecyclerView() {
        mostPopularTVShowModelList = new ArrayList<>();
        mostPopularTVShowAdapter = new MostPopularTVShowAdapter(mostPopularTVShowModelList, getContext(), this);
        fragmentHomeBinding.actMainRecyclerViewMostPopularTvShow.setAdapter(mostPopularTVShowAdapter);
        fragmentHomeBinding.actMainRecyclerViewMostPopularTvShow.setHasFixedSize(true);
        fragmentHomeBinding.actMainRecyclerViewMostPopularTvShow.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!fragmentHomeBinding.actMainRecyclerViewMostPopularTvShow.canScrollVertically(1)) {
                    if (currentPage <= totalPages) {
                        currentPage++;
                        getMostPopularTVShowsFromApi();
                    }
                }
            }
        });
        getMostPopularTVShowsFromApi();
    }

    private void getMostPopularTVShowsFromApi() {
        setupLoadingMoreTVShows();
        ApiClient.getEpisodateApiClient().getMostPopularTVShows(currentPage)
                .enqueue(new Callback<MostPopularTVShowsModel>() {
                    @Override
                    public void onResponse(@NonNull Call<MostPopularTVShowsModel> call, @NonNull Response<MostPopularTVShowsModel> response) {
                        setupLoadingMoreTVShows();
                        if (!response.isSuccessful()) {
                            Log.i("MYTAG", "onResponse: " + response.code());
                            return;
                        }
                        totalPages = response.body().getPages();
                        int oldCountTVShowListed = mostPopularTVShowModelList.size();
                        mostPopularTVShowModelList.addAll(response.body().getMostPopularTVShowModel());
                        mostPopularTVShowAdapter.notifyItemRangeInserted(oldCountTVShowListed, mostPopularTVShowModelList.size());
                    }

                    @Override
                    public void onFailure(@NonNull Call<MostPopularTVShowsModel> call, @NonNull Throwable t) {
                        Log.i("MYTAG", "onFailure: " + t.getMessage());
                    }
                });
    }

    public void setupLoadingMoreTVShows() {
        if (currentPage == 1) {
            if (fragmentHomeBinding.getIsLoading() != null && fragmentHomeBinding.getIsLoading()) {
                fragmentHomeBinding.setIsLoading(false);
            } else {
                fragmentHomeBinding.setIsLoading(true);
            }
        } else {
            if (fragmentHomeBinding.getIsLoadingMore() != null && fragmentHomeBinding.getIsLoadingMore()) {
                fragmentHomeBinding.setIsLoadingMore(false);
            } else {
                fragmentHomeBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onClickedListener(MostPopularTVShowModel mostPopularTVShowModel, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("mostPopularTVShowModel", mostPopularTVShowModel);
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_mostPopularTVShowFragment, bundle);
    }
}