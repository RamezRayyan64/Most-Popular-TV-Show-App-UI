package com.example.rayyantech.mostpopulartvshow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rayyantech.mostpopulartvshow.R;

import java.util.List;

public class TVShowSliderImageAdapter extends PagerAdapter {

    private final List<String> tvShowImages;
    private final Context context;

    public TVShowSliderImageAdapter(List<String> tvShowImages, Context context) {
        this.tvShowImages = tvShowImages;
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_t_v_show_slider_image, container, false);
        AppCompatImageView appCompatImageView = view.findViewById(R.id.layout_tvshow_slider_image_image_view_tv_show_slider_images);
        Glide.with(context).load(tvShowImages.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).into(appCompatImageView);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return tvShowImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
