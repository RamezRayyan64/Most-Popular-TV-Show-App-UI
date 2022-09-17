package com.example.rayyantech.mostpopulartvshow.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rayyantech.mostpopulartvshow.R;
import com.example.rayyantech.mostpopulartvshow.adapters.WatchlistTVShowAdapter;
import com.example.rayyantech.mostpopulartvshow.database.MostPopularTVShowDatabase;
import com.example.rayyantech.mostpopulartvshow.databinding.FragmentWatchlistBinding;
import com.example.rayyantech.mostpopulartvshow.helper.WatchlistListener;
import com.example.rayyantech.mostpopulartvshow.models.MostPopularTVShowModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchlistFragment extends Fragment implements WatchlistListener {

    private FragmentWatchlistBinding fragmentWatchlistBinding;
    private List<MostPopularTVShowModel> watchlistTVShowList;
    private WatchlistTVShowAdapter watchlistTVShowAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentWatchlistBinding = FragmentWatchlistBinding.inflate(getLayoutInflater());
        return fragmentWatchlistBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        fragmentWatchlistBinding.actWatchlistImageViewBack.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_watchlistFragment_to_HomeFragment);
        });
    }

    public void setupRecyclerView() {
        watchlistTVShowList = new ArrayList<>();
        getAllTVShowFromDatabase();
    }

    private void getAllTVShowFromDatabase() {
        fragmentWatchlistBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(MostPopularTVShowDatabase.getMostPopularTVShowDatabase(getContext())
                .mostPopularTVShowDao().getWatchListMostPopularTVShow().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(mostPopularTVShowModels -> {
                    fragmentWatchlistBinding.setIsLoading(false);
                    if (watchlistTVShowList.size() > 0) {
                        watchlistTVShowList.clear();
                    }
                    watchlistTVShowList.addAll(mostPopularTVShowModels);
                    watchlistTVShowAdapter = new WatchlistTVShowAdapter(watchlistTVShowList, getContext(), this);
                    fragmentWatchlistBinding.actWatchlistRecyclerView.setHasFixedSize(true);
                    fragmentWatchlistBinding.actWatchlistRecyclerView.setAdapter(watchlistTVShowAdapter);
                    fragmentWatchlistBinding.actWatchlistRecyclerView.setVisibility(View.VISIBLE);
                    compositeDisposable.dispose();
                }));
    }

    @Override
    public void onClickedTVShow(MostPopularTVShowModel mostPopularTVShowModel, int position) {

    }

    @Override
    public void onDeleteTVShow(MostPopularTVShowModel mostPopularTVShowModel, int position) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(MostPopularTVShowDatabase.getMostPopularTVShowDatabase(getContext())
                .mostPopularTVShowDao().deleteFromWatchList(mostPopularTVShowModel)
                .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    watchlistTVShowList.remove(position);
                    watchlistTVShowAdapter.notifyItemRemoved(position);
                    compositeDisposable.dispose();
                    Toast.makeText(getContext(), "Deleted from watchlist", Toast.LENGTH_SHORT).show();
                }));
    }
}