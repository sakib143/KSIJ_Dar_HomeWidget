package com.learn.widgettest.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.learn.widgettest.R;


public class MovieActivity extends AppCompatActivity {
    private static final String LOG_TAG = "==> MovieActivity ";

    public static final String EXTRA_URL = "extra_url";
    public static final String EXTRA_TEXT = "extra_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ImageView imageView = findViewById(R.id.movie_poster);
        TextView title = findViewById(R.id.title);

        title.setText(getIntent().getStringExtra(EXTRA_TEXT));

        String url = getIntent().getStringExtra(EXTRA_URL);
        Log.e(LOG_TAG, "Image: url = " + url);


        try {
            Bitmap bitmap = Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(url)
                    .submit(512, 512)
                    .get();
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setVisibility(View.GONE);
        }
    }
}
