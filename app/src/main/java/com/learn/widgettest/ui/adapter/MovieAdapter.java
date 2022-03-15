package com.learn.widgettest.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.learn.widgettest.R;
import com.learn.widgettest.rest.model.MovieResponse;
import com.learn.widgettest.ui.activity.MovieActivity;
import com.learn.widgettest.util.ImageUtility;
import java.util.List;

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String LOG_TAG = "==> MovieAdapter ";

    private List<MovieResponse.Datum> mMovies;

    public MovieAdapter(List<MovieResponse.Datum> movies) {
        mMovies = movies;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        MovieResponse.Datum movie = mMovies.get(position);

        holder.mTitle.setText(movie.getDartext());
        holder.date.setText(movie.getMsgdate());

        String key = movie.getDarimage();

        Glide
                .with(holder.mPoster.getContext())
                .load(key)
                .apply(new RequestOptions())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        holder.mPoster.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mPoster;
        public TextView mTitle,date;

        public ViewHolder(View itemView) {
            super(itemView);
            mPoster = itemView.findViewById(R.id.movie_poster);
            mTitle = itemView.findViewById(R.id.movie_title);
            date = itemView.findViewById(R.id.date);
        }
    }
}
