package com.learn.widgettest.widget;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.job.JobInfo;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.learn.widgettest.R;
import com.learn.widgettest.app.App;
import com.learn.widgettest.rest.model.MovieResponse;
import com.learn.widgettest.rest.service.MovieService;
import com.learn.widgettest.ui.activity.MovieActivity;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit.Call;


public class StackWidgetProvider extends AppWidgetProvider {
    private static final String LOG_TAG = StackWidgetProvider.class.getSimpleName();

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Log.v(LOG_TAG, "onUpdate(): appWidgetId = " + appWidgetId);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_stack);

            Intent intent = new Intent(context, StackWidgetRemoteViewsService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.widget_stack, intent);

//            Intent clickIntentTemplate =  new Intent(context, MovieActivity.class);
//            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
//                    .addNextIntentWithParentStack(clickIntentTemplate)
//                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//            views.setPendingIntentTemplate(R.id.widget_stack, clickPendingIntentTemplate);

            views.setEmptyView(R.id.widget_stack, R.id.widget_empty);

            appWidgetManager.updateAppWidget(appWidgetId, views);


            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                public void run() {

                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_stack);
                    handler.postDelayed(this, 1000 *  60 ); //now is every 2 minutes.
                }
            }, 1000 *  60 ); //Every 120000 ms (2 minutes)

        }
    }
}
