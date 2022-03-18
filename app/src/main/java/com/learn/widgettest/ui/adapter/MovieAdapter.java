package com.learn.widgettest.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.learn.widgettest.R;
import com.learn.widgettest.rest.model.MovieResponse;
import java.util.List;

public class MovieAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieResponse.Datum> mMovies;

    final int VIEW_TYPE_VIDEOS = 1;
    final int VIEW_TYPE_IMAGE_AND_TEXT = 2;

    public MovieAdapter(List<MovieResponse.Datum> movies) {
        mMovies = movies;
    }

    @Override
    public int getItemViewType(int position) {
        if(mMovies.get(position).getMsgtype().equalsIgnoreCase("v")) {
            return VIEW_TYPE_VIDEOS;
        } else {
            return VIEW_TYPE_IMAGE_AND_TEXT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_IMAGE_AND_TEXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
                return new VideoTypeViewHolder(view);
            case VIEW_TYPE_VIDEOS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_video, parent, false);
                return new ImageTypeViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(mMovies.get(position).getMsgtype().equalsIgnoreCase("v")) {
            Context contex = ((ImageTypeViewHolder) holder).mPoster.getContext();
            ((ImageTypeViewHolder) holder).mTitle.setText(mMovies.get(position).getDartext());
            ((ImageTypeViewHolder) holder).date.setText(mMovies.get(position).getMsgdate());
            ((ImageTypeViewHolder) holder).mPoster.setBackground(ContextCompat.getDrawable(contex, R.drawable.video_thumbnail));
            ((ImageTypeViewHolder) holder).mPoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(mMovies.get(position).getDarimage()));
                    contex.startActivity(i);
                }
            });
        } else {
            ((VideoTypeViewHolder) holder).mTitle.setText(mMovies.get(position).getDartext());
            ((VideoTypeViewHolder) holder).date.setText(mMovies.get(position).getMsgdate());
            Context contex = ((VideoTypeViewHolder) holder).mPoster.getContext();

            Glide
                    .with(contex)
                    .load(mMovies.get(position).getDarimage())
                    .apply(new RequestOptions())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            ((VideoTypeViewHolder) holder).mPoster.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(((VideoTypeViewHolder) holder).mPoster);
        }
    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {
        public ImageView mPoster;
        public TextView mTitle,date;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);
            mPoster = itemView.findViewById(R.id.movie_poster);
            mTitle = itemView.findViewById(R.id.movie_title);
            date = itemView.findViewById(R.id.date);
        }
    }

    public static class VideoTypeViewHolder extends RecyclerView.ViewHolder {
        public ImageView mPoster;
        public TextView mTitle,date;

        public VideoTypeViewHolder(View itemView) {
            super(itemView);
            mPoster = itemView.findViewById(R.id.movie_poster);
            mTitle = itemView.findViewById(R.id.movie_title);
            date = itemView.findViewById(R.id.date);
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
