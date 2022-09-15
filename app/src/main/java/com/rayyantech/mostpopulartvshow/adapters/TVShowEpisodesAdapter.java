package com.rayyantech.mostpopulartvshow.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rayyantech.mostpopulartvshow.R;
import com.rayyantech.mostpopulartvshow.models.TVShowEpisodesModel;

import java.util.List;

public class TVShowEpisodesAdapter extends RecyclerView.Adapter<TVShowEpisodesAdapter.ViewHolder> {

    List<TVShowEpisodesModel> tvShowEpisodesModelList;
    Context context;

    public TVShowEpisodesAdapter(List<TVShowEpisodesModel> tvShowEpisodesModelList, Context context) {
        this.tvShowEpisodesModelList = tvShowEpisodesModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_tv_show_episode_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TVShowEpisodesModel tvShowEpisodesModel = tvShowEpisodesModelList.get(position);
        holder.textViewTVShowSeasonEpisode.setText((tvShowEpisodesModel.getSeason() < 10) ?
                "S0" + tvShowEpisodesModel.getSeason() : "S" + tvShowEpisodesModel.getSeason());
        holder.textViewTVShowSeasonEpisode.append((tvShowEpisodesModel.getEpisode() < 10) ? "E0" + tvShowEpisodesModel.getEpisode() :
                "E" + tvShowEpisodesModel.getEpisode());
        holder.textViewTVShowEpisodeName.setText(tvShowEpisodesModel.getName());
        holder.textViewTVShowEpisodeAirDate.setText(tvShowEpisodesModel.getAir_date());
    }

    @Override
    public int getItemCount() {
        return tvShowEpisodesModelList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textViewTVShowSeasonEpisode, textViewTVShowEpisodeName, textViewTVShowEpisodeAirDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTVShowSeasonEpisode = itemView.findViewById(R.id.layout_tv_show_episode_item_text_view_tv_show_season_episode);
            textViewTVShowEpisodeName = itemView.findViewById(R.id.layout_tv_show_episode_item_text_view_tv_show_episode_name);
            textViewTVShowEpisodeAirDate = itemView.findViewById(R.id.layout_tv_show_episode_item_text_view_tv_show_episode_air_date);
        }
    }
}
