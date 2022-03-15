package com.learn.widgettest.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.learn.widgettest.R;
import com.learn.widgettest.app.App;
import com.learn.widgettest.rest.model.MovieResponse;
import com.learn.widgettest.rest.service.MovieService;
import com.learn.widgettest.ui.adapter.MovieAdapter;
import com.learn.widgettest.ui.misc.EndlessRecyclerOnScrollListener;
import com.learn.widgettest.ui.misc.SpacesItemDecoration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;


public class MainActivityFragment extends Fragment {
    private static final String LOG_TAG = "==> Main Fragment ";

    private RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;
    private List<MovieResponse.Datum> mMovies = new ArrayList<>();
    private EndlessRecyclerOnScrollListener mOnScrollListener;
    private Call<MovieResponse> mCall;

    public MainActivityFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.movie_grid);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        fetchMovies();
    }

    private void fetchMovies() {
        MovieService movieService = App.getRestClient().getMovieService();

        Map<String, String> map = new HashMap<>();
        map.put("Mode", "getdarwallpaper");
        mCall = movieService.getMovies(map);

        mCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(retrofit.Response<MovieResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    MovieResponse movieResponse = response.body();
                    List<MovieResponse.Datum> movies = movieResponse.getData();

                    Log.e("==>","onResponse in fragment ??? " + movies.size());
                    mMovies.clear();
                    mMovies.addAll(movies);
                    setupRecyclerView(mRecyclerView);

                    int insertSize = movies.size();
                    int insertPos = mMovies.size() - insertSize;
                    Log.v(LOG_TAG, "onResponse(): insertPos = " + insertPos
                            + ", insertSize = " + insertSize);
                    mMovieAdapter.notifyItemRangeInserted(insertPos, insertSize);
                } else {
                    int statusCode = response.code();
                    Log.e(LOG_TAG, "onResponse(): Error code = " + statusCode);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mMovieAdapter = new MovieAdapter(mMovies);
        SlideInBottomAnimationAdapter animationAdapter =
                new SlideInBottomAnimationAdapter(mMovieAdapter);
        animationAdapter.setDuration(500);
        recyclerView.setAdapter(animationAdapter);
        recyclerView.setHasFixedSize(true);
    }

}
