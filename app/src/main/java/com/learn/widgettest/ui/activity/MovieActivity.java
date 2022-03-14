package com.learn.widgettest.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.learn.widgettest.R;


public class MovieActivity extends AppCompatActivity {
    private static final String LOG_TAG = "==> MovieActivity ";

    public static final String EXTRA_URL = "extra_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ImageView imageView = (ImageView) findViewById(R.id.movie_poster);
        String url = getIntent().getStringExtra(EXTRA_URL);
        Log.v(LOG_TAG, "onCreate(): url = " + url);

        if (url != null) {
            Glide.with(this).load(url).into(imageView);
        }
    }
}
