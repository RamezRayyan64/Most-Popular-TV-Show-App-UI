package com.example.rayyantech.mostpopulartvshow.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.example.rayyantech.mostpopulartvshow.R;
import com.example.rayyantech.mostpopulartvshow.adapters.TVShowEpisodesAdapter;
import com.example.rayyantech.mostpopulartvshow.adapters.TVShowSliderImageAdapter;
import com.example.rayyantech.mostpopulartvshow.databinding.FragmentMostPopularTVShowBinding;
import com.example.rayyantech.mostpopulartvshow.databinding.LayoutTvShowEpisodeBinding;
import com.example.rayyantech.mostpopulartvshow.helper.ApiClient;
import com.example.rayyantech.mostpopulartvshow.models.MostPopularTVShowModel;
import com.example.rayyantech.mostpopulartvshow.models.TVShowDetailModel;
import com.example.rayyantech.mostpopulartvshow.models.TVShowsDetailModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowFragment extends Fragment {

    private FragmentMostPopularTVShowBinding fragmentMostPopularTVShowBinding;
    private List<String> tvShowSliderImageList;
    private TVShowSliderImageAdapter tvShowSliderImageAdapter;
    private String tvShowUrl;
    private TVShowEpisodesAdapter tvShowEpisodesAdapter;
    private View view;
    private MostPopularTVShowModel mostPopularTVShowModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mostPopularTVShowModel = getArguments().getParcelable("mostPopularTVShowModel");
        Log.i("MYTAG", "onCreate: " + mostPopularTVShowModel.getId());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMostPopularTVShowBinding = FragmentMostPopularTVShowBinding.inflate(getLayoutInflater());
        return fragmentMostPopularTVShowBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        setupSliderImage();
        fragmentMostPopularTVShowBinding.actTvshowDetailTextViewReadMore.setOnClickListener(v -> setupTextViewReadMore());
        fragmentMostPopularTVShowBinding.actTvshowDetailButtonWebsite.setOnClickListener(v -> goToWebsite());
        fragmentMostPopularTVShowBinding.actTvshowDetailButtonEpisodes.setOnClickListener(v -> goToEpisodes());
    }

    public void setupSliderImage() {
        tvShowSliderImageList = new ArrayList<>();
        tvShowSliderImageAdapter = new TVShowSliderImageAdapter(tvShowSliderImageList, getContext());
        fragmentMostPopularTVShowBinding.actTvshowDetailViewPagerTvShowSliderImages.setAdapter(tvShowSliderImageAdapter);
        getTVShowDetail();
        changeSliderImagesBySecond();
    }

    public void getTVShowDetail() {
        isLoadingData();
        ApiClient.getEpisodateApiClient().getTVShowsDetails(mostPopularTVShowModel.getId())
                .enqueue(new Callback<TVShowsDetailModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<TVShowsDetailModel> call, @NonNull Response<TVShowsDetailModel> response) {
                        if (!response.isSuccessful()) {
                            Log.i("MYTAG", "onResponse: " + response.code());
                            return;
                        }
                        if (response.body() != null) {
                            TVShowDetailModel tvShowDetailModel = response.body().getTvShowDetailModel();
                            tvShowSliderImageList.addAll(tvShowDetailModel.getPictures());
                            fragmentMostPopularTVShowBinding.actTvshowDetailCircleIndicatorTvShowSliderImage.setViewPager(
                                    fragmentMostPopularTVShowBinding.actTvshowDetailViewPagerTvShowSliderImages);
                            Glide.with(view.getContext()).load(tvShowDetailModel.getImagePath())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(fragmentMostPopularTVShowBinding.actTvshowDetailImageViewTvShowPoster);
                            fragmentMostPopularTVShowBinding.actTvshowDetailTextViewTvShowName.setText(tvShowDetailModel.getName());
                            fragmentMostPopularTVShowBinding.actTvshowDetailTextViewTvShowNetwork.setText(tvShowDetailModel.getNetwork() +
                                    " (" + tvShowDetailModel.getCountry() + ")");
                            fragmentMostPopularTVShowBinding.actTvshowDetailTextViewTvShowStartedDate.setText(
                                    "Started date: " + tvShowDetailModel.getStartDate());
                            fragmentMostPopularTVShowBinding.actTvshowDetailTextViewTvShowStatus.setText(tvShowDetailModel.getStatus());
                            fragmentMostPopularTVShowBinding.actTvshowDetailTextViewTvShowDescription.setText(tvShowDetailModel.getDescription());
                            fragmentMostPopularTVShowBinding.actTvshowDetailTextViewTvShowRate.setText(
                                    tvShowDetailModel.getRating().substring(0, 3)
                            );
                            for (int i = 0; i < tvShowDetailModel.getGenres().size(); i++) {
                                fragmentMostPopularTVShowBinding.actTvshowDetailTextViewTvShowGenre.append(
                                        (i == tvShowDetailModel.getGenres().size() - 1) ? tvShowDetailModel.getGenres().get(i) :
                                                tvShowDetailModel.getGenres().get(i) + ", "
                                );
                            }
                            fragmentMostPopularTVShowBinding.actTvshowDetailTextViewTvShowRuntime.setText(tvShowDetailModel.getRuntime() + "Min");
                            tvShowUrl = tvShowDetailModel.getUrl();
                            isLoadingData();
                            fragmentMostPopularTVShowBinding.actTvshowDetailLayoutTvShowDetail.setVisibility(View.VISIBLE);
                            tvShowEpisodesAdapter = new TVShowEpisodesAdapter(tvShowDetailModel.getTvShowEpisodesModelList(),
                                    getContext());
                            tvShowSliderImageAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TVShowsDetailModel> call, @NonNull Throwable t) {
                        Log.i("MYTAG", "onFailure: " + t.getMessage());
                    }
                });
    }

    public void changeSliderImagesBySecond() {
        Timer timer = new Timer();
        Handler handler = new Handler();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    int currentSliderImage = fragmentMostPopularTVShowBinding.actTvshowDetailViewPagerTvShowSliderImages.getCurrentItem();
                    fragmentMostPopularTVShowBinding.actTvshowDetailViewPagerTvShowSliderImages.setCurrentItem(++currentSliderImage);
                    if (currentSliderImage == tvShowSliderImageList.size()) {
                        fragmentMostPopularTVShowBinding.actTvshowDetailViewPagerTvShowSliderImages.setCurrentItem(0);
                    }
                });
            }
        }, 2000, 2000);
    }

    public void isLoadingData() {
        if (fragmentMostPopularTVShowBinding.getIsLoading() != null && fragmentMostPopularTVShowBinding.getIsLoading()) {
            fragmentMostPopularTVShowBinding.setIsLoading(false);
            fragmentMostPopularTVShowBinding.setIsViewDetailVisible(true);
        } else {
            fragmentMostPopularTVShowBinding.setIsLoading(true);
            fragmentMostPopularTVShowBinding.setIsViewDetailVisible(false);
        }
    }

    public void setupTextViewReadMore() {
        if (fragmentMostPopularTVShowBinding.actTvshowDetailTextViewReadMore.getText().toString().equals("Read More")) {
            fragmentMostPopularTVShowBinding.actTvshowDetailTextViewTvShowDescription.setMaxLines(Integer.MAX_VALUE);
            fragmentMostPopularTVShowBinding.actTvshowDetailTextViewTvShowDescription.setEllipsize(null);
            fragmentMostPopularTVShowBinding.actTvshowDetailTextViewReadMore.setText(getResources().getString(R.string.read_less));
        } else {
            fragmentMostPopularTVShowBinding.actTvshowDetailTextViewTvShowDescription.setMaxLines(4);
            fragmentMostPopularTVShowBinding.actTvshowDetailTextViewTvShowDescription.setEllipsize(TextUtils.TruncateAt.END);
            fragmentMostPopularTVShowBinding.actTvshowDetailTextViewReadMore.setText(getResources().getString(R.string.read_more));
        }
    }

    public void goToWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(tvShowUrl));
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    public void goToEpisodes() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(view.getContext());
        LayoutTvShowEpisodeBinding layoutTvShowEpisodeBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.layout_tv_show_episode,
                view.findViewById(R.id.layout_tv_show_episode),
                false
        );
        bottomSheetDialog.setContentView(layoutTvShowEpisodeBinding.getRoot());
        layoutTvShowEpisodeBinding.actMainLayoutToolbarTextViewTitle.setText("Episodes | " +
                fragmentMostPopularTVShowBinding.actTvshowDetailTextViewTvShowName.getText().toString());
        layoutTvShowEpisodeBinding.actMainLayoutToolbarImageViewClose.setOnClickListener(v -> bottomSheetDialog.dismiss());
        layoutTvShowEpisodeBinding.layoutTvShowEpisodeRecyclerView.setHasFixedSize(true);
        layoutTvShowEpisodeBinding.layoutTvShowEpisodeRecyclerView.setAdapter(tvShowEpisodesAdapter);
        FrameLayout frameLayout = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if (frameLayout != null) {
            BottomSheetBehavior<View> bottomSheetBehavior = new BottomSheetBehavior<>();
            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        bottomSheetDialog.show();
    }
}