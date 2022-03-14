package com.learn.widgettest.widget;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.learn.widgettest.R;
import com.learn.widgettest.app.App;
import com.learn.widgettest.rest.model.MovieResponse;
import com.learn.widgettest.rest.service.MovieService;
import com.learn.widgettest.ui.activity.MovieActivity;
import com.learn.widgettest.util.ImageUtility;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.Call;


public class StackWidgetRemoteViewsService extends RemoteViewsService {
    private static final String LOG_TAG = " Service ==>";

    private List<MovieResponse.Datum> mMovies;


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {

            @Override
            public void onCreate() {
                Log.v(LOG_TAG, "onCreate()");
            }

            @Override
            public void onDataSetChanged() {
                Log.v(LOG_TAG, "onDataSetChanged()");
                try {
                    MovieService movieService = App.getRestClient().getMovieService();
                    Map<String, String> map = new HashMap<>();
                    map.put("Mode", "getdarwallpaper");
                    Call<MovieResponse> call = movieService.getMovies(map);
                    try {
                        MovieResponse response = call.execute().body();
                        List<MovieResponse.Datum> movies = response.getData();
                        mMovies = movies;

                        Log.v(LOG_TAG, "Array size is ???  = " + mMovies.size());
                        Toast.makeText(getApplicationContext(), "List size is " + mMovies.size(), Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        Log.e(LOG_TAG, "ERROR: ", e);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDestroy() { }

            @Override
            public int getCount() {
                return mMovies == null ? 0 : mMovies.size();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                Log.v(LOG_TAG, "getViewAt(): position = " + position);

                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_stack_item);

                String posterUrl = ImageUtility.getImageUrl(mMovies.get(position).getDarimage());

                Log.e("posterUrl " ,"==>  " + posterUrl);

                if(posterUrl == null || posterUrl.equalsIgnoreCase("null")) {
                    views.setViewVisibility(R.id.widget_movie_poster, View.GONE);
                } else {
                    views.setViewVisibility(R.id.widget_movie_poster, View.VISIBLE);
                    try {
                        Bitmap bitmap = Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(mMovies.get(position).getDarimage())
                                .submit(512, 512)
                                .get();
                        views.setImageViewBitmap(R.id.widget_movie_poster, bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                        views.setViewVisibility(R.id.widget_movie_poster, View.GONE);
                    }
                }


                views.setTextViewText(R.id.title, mMovies.get(position).getDartext());

                final Intent fillInIntent = new Intent();
                fillInIntent.putExtra(MovieActivity.EXTRA_URL, posterUrl);
                views.setOnClickFillInIntent(R.id.widget_stack_item, fillInIntent);

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_stack_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
//                if (position < mMovies.size()) {
//                    return mMovies.get(position).getId();
//                }
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
