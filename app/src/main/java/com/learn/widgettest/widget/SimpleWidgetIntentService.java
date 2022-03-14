package com.learn.widgettest.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.learn.widgettest.R;
import com.learn.widgettest.app.App;
import com.learn.widgettest.rest.model.MovieResponse;
import com.learn.widgettest.rest.service.MovieService;
import com.learn.widgettest.ui.activity.MainActivity;
import com.learn.widgettest.util.ImageUtility;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import retrofit.Call;


public class SimpleWidgetIntentService extends IntentService {
    private static final String LOG_TAG = SimpleWidgetIntentService.class.getSimpleName();

    public SimpleWidgetIntentService() {
        super("SimpleWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Toast.makeText(getApplicationContext(), "onHandleIntent is calling in SimpleWidgetIntentService!!! ", Toast.LENGTH_SHORT).show();

        Log.v(LOG_TAG, "Start intent service.");

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                SimpleWidgetProvider.class));

        MovieService movieService = App.getRestClient().getMovieService();

        Map<String, String> map = new HashMap<>();
        map.put("Mode", "getdarwallpaper");
        Call<MovieResponse> call = movieService.getMovies(map);

        try {
            MovieResponse response = call.execute().body();
            if (response != null) {
                List<MovieResponse.Datum> movies = response.getData();

                Random random = new Random();
                int base = random.nextInt(movies.size());
                Log.v(LOG_TAG, "onHandleIntent(): base = " + base);

                int widgetCount = 0;
                for (int appWidgetId : appWidgetIds) {
                    Log.v(LOG_TAG, "onHandleIntent(): Updating appWidgetId = " + appWidgetId);

                    RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_poster);

                    Intent launchIntent = new Intent(this, MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
                    views.setOnClickPendingIntent(R.id.widget_movie_poster, pendingIntent);

                    int pick = (base + widgetCount++) % movies.size();
                    String posterPath = movies.get(pick).getDarimage();
//                    Bitmap poster = Picasso.with(this)
//                            .load(ImageUtility.getImageUrl(posterPath))
//                            .get();
//                    views.setImageViewBitmap(R.id.widget_movie_poster, poster);

                    if(movies.get(pick).getDarimage() != null) {
                        views.setImageViewUri(R.id.widget_movie_poster, Uri.parse(movies.get(pick).getDarimage()));
                    }


                    appWidgetManager.updateAppWidget(appWidgetId, views);
                }
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "ERROR: ", e);
        }
    }
}
