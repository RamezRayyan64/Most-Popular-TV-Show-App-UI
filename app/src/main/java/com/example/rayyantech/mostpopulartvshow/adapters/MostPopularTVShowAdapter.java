package com.example.rayyantech.mostpopulartvshow.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.example.rayyantech.mostpopulartvshow.R;
import com.example.rayyantech.mostpopulartvshow.helper.MostPopularTVShowListener;
import com.example.rayyantech.mostpopulartvshow.models.MostPopularTVShowModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MostPopularTVShowAdapter extends RecyclerView.Adapter<MostPopularTVShowAdapter.ViewHolder> {

    public final List<MostPopularTVShowModel> mostPopularTVShowModelList;
    public final Context context;
    public MostPopularTVShowListener mostPopularTVShowListener;

    public MostPopularTVShowAdapter(List<MostPopularTVShowModel> mostPopularTVShowModelList, Context context,
                                    MostPopularTVShowListener mostPopularTVShowListener) {
        this.mostPopularTVShowModelList = mostPopularTVShowModelList;
        this.context = context;
        this.mostPopularTVShowListener = mostPopularTVShowListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_most_popular_tv_show, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MostPopularTVShowModel mostPopularTVShowModel = mostPopularTVShowModelList.get(position);
        Glide.with(context).load(mostPopularTVShowModel.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.roundedImageViewTVShowPoster);
        holder.roundedImageViewTVShowPoster.animate().alpha(1f).setDuration(500).start();
        holder.textViewTVShowName.setText(mostPopularTVShowModel.getName());
        holder.textViewTVShowNetwork.setText(mostPopularTVShowModel.getNetwork() + " (" +
                mostPopularTVShowModel.getCountry() + ")");
        holder.textViewTVShowStartedDate.setText("Started date: " + mostPopularTVShowModel.getStartedDate());
        holder.textViewTVShowStatus.setText(mostPopularTVShowModel.getStatus());
        holder.itemView.setOnClickListener(v -> {
            mostPopularTVShowListener.onClickedListener(mostPopularTVShowModel, position);
        });
    }

    @Override
    public int getItemCount() {
        return mostPopularTVShowModelList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView roundedImageViewTVShowPoster;
        AppCompatTextView textViewTVShowName, textViewTVShowNetwork, textViewTVShowStartedDate, textViewTVShowStatus;
        AppCompatImageView imageViewAddToWatchlist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageViewTVShowPoster = itemView.findViewById(R.id.layout_most_popular_tv_show_rounded_image_view_tv_show_poster);
            textViewTVShowName = itemView.findViewById(R.id.layout_most_popular_tv_show_text_view_tv_show_name);
            textViewTVShowNetwork = itemView.findViewById(R.id.layout_most_popular_tv_show_text_view_tv_show_network);
            textViewTVShowStartedDate = itemView.findViewById(R.id.layout_most_popular_tv_show_text_view_tv_show_started_date);
            textViewTVShowStatus = itemView.findViewById(R.id.layout_most_popular_tv_show_text_view_tv_show_status);
            imageViewAddToWatchlist = itemView.findViewById(R.id.layout_most_popular_tv_show_image_view_watchlist);
        }
    }
}
