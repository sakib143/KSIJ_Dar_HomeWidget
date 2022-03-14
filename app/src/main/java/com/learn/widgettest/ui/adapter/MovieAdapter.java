package com.learn.widgettest.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.learn.widgettest.R;
import com.learn.widgettest.rest.model.MovieResponse;
import com.learn.widgettest.util.ImageUtility;
import java.util.List;

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String LOG_TAG = "==> MovieAdapter ";

    private List<MovieResponse.Datum> mMovies;

    public MovieAdapter(List<MovieResponse.Datum> movies) {
        mMovies = movies;
        Log.e("=>"," main array size is ???  " + movies.size());
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

        String key = movie.getDarimage();

        Glide.with(holder.mPoster.getContext()).load(key).into(holder.mPoster);

//        if(key != null) {
//            String url = ImageUtility.getImageUrl(key);
//            Picasso.with(holder.itemView.getContext())
//                    .load(url)
//                    .error(R.drawable.placeholder_poster_white)
//                    .placeholder(R.drawable.placeholder_poster_white)
//                    .into(holder.mPoster);
//        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mPoster;
        public TextView mTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            mPoster = (ImageView) itemView.findViewById(R.id.movie_poster);
            mTitle = (TextView) itemView.findViewById(R.id.movie_title);
        }
    }
}
