package com.rayyantech.mostpopulartvshow.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rayyantech.mostpopulartvshow.R;
import com.rayyantech.mostpopulartvshow.adapters.MostPopularTVShowAdapter;
import com.rayyantech.mostpopulartvshow.databinding.FragmentSearchBinding;
import com.rayyantech.mostpopulartvshow.helper.ApiClient;
import com.rayyantech.mostpopulartvshow.helper.MostPopularTVShowListener;
import com.rayyantech.mostpopulartvshow.models.MostPopularTVShowModel;
import com.rayyantech.mostpopulartvshow.models.MostPopularTVShowsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements MostPopularTVShowListener {

    private FragmentSearchBinding fragmentSearchBinding;
    private List<MostPopularTVShowModel> mostPopularTVShowModelList;
    private MostPopularTVShowAdapter mostPopularTVShowAdapter;
    private int currentPage = 1, totalPages;
    private String searchedTVShow;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSearchBinding = FragmentSearchBinding.inflate(getLayoutInflater());
        return fragmentSearchBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        setupEditTextSearch();
        setupRecyclerView();
        fragmentSearchBinding.actSearchLayoutToolbarImageViewBack.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_homeFragment);
        });
        fragmentSearchBinding.actSearchLayoutToolbarImageViewSearch.setOnClickListener(v -> {

        });
    }

    public void setupEditTextSearch() {
        fragmentSearchBinding.actSearchLayoutToolbarEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mostPopularTVShowModelList.clear();
                if (!s.toString().trim().equals("")) {
                    currentPage = 1;
                    totalPages = 1;
                    getSearchedTVShow(s.toString(), currentPage);
                } else {
                    mostPopularTVShowModelList.clear();
                    mostPopularTVShowAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setupRecyclerView() {
        mostPopularTVShowModelList = new ArrayList<>();
        mostPopularTVShowAdapter = new MostPopularTVShowAdapter(mostPopularTVShowModelList, getContext(), this);
        fragmentSearchBinding.actSearchLayoutToolbarRecyclerView.setHasFixedSize(true);
        fragmentSearchBinding.actSearchLayoutToolbarRecyclerView.setAdapter(mostPopularTVShowAdapter);
        fragmentSearchBinding.actSearchLayoutToolbarRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!fragmentSearchBinding.actSearchLayoutToolbarRecyclerView.canScrollVertically(1)) {
                    if (currentPage < totalPages) {
                        currentPage++;
                        getSearchedTVShow(searchedTVShow, currentPage);
                    }
                }
            }
        });
    }

    private void getSearchedTVShow(String tvShowName, int currentPage) {
        isLoading();
        ApiClient.getEpisodateApiClient().getSearchedTVShow(tvShowName, currentPage).enqueue(new Callback<MostPopularTVShowsModel>() {
            @Override
            public void onResponse(Call<MostPopularTVShowsModel> call, Response<MostPopularTVShowsModel> response) {
                isLoading();
                if (!response.isSuccessful()) {
                    Log.i("MYTAG", "onResponse: " + response.code());
                    return;
                }
                int oldItemCountLoaded = mostPopularTVShowModelList.size();
                totalPages = response.body().getPages();
                mostPopularTVShowModelList.addAll(response.body().getMostPopularTVShowModel());
                mostPopularTVShowAdapter.notifyItemRangeInserted(oldItemCountLoaded, mostPopularTVShowModelList.size());
            }

            @Override
            public void onFailure(Call<MostPopularTVShowsModel> call, Throwable t) {
                Log.i("MYTAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void isLoading() {
        if (currentPage == 1) {
            if (fragmentSearchBinding.getIsLoading() != null && fragmentSearchBinding.getIsLoading()) {
                fragmentSearchBinding.setIsLoading(false);
            } else {
                fragmentSearchBinding.setIsLoading(true);
            }
        } else {
            if (fragmentSearchBinding.getIsLoadingMore() != null && fragmentSearchBinding.getIsLoadingMore()) {
                fragmentSearchBinding.setIsLoadingMore(false);
            } else {
                fragmentSearchBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onClickedListener(MostPopularTVShowModel mostPopularTVShowModel, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("mostPopularTVShowModel", mostPopularTVShowModel);
        Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_mostPopularTVShowFragment, bundle);
    }
}