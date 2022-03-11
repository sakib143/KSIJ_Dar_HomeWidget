package com.learn.widgettest.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.learn.widgettest.R;
import com.squareup.picasso.Picasso;


public class MovieActivity extends AppCompatActivity {
    private static final String LOG_TAG = MovieActivity.class.getSimpleName();

    public static final String EXTRA_URL = "extra_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ImageView imageView = (ImageView) findViewById(R.id.movie_poster);
        String url = getIntent().getStringExtra(EXTRA_URL);
        Log.v(LOG_TAG, "onCreate(): url = " + url);

        if (url != null) {
            Picasso.with(this)
                    .load(url)
                    .into(imageView);
        }
    }
}
