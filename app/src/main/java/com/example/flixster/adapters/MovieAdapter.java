package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Movie> movies;

    private final int NOTPOP = 0, POP = 1;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case NOTPOP:
                View movieView1 = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
                viewHolder = new ViewHolder1(movieView1);
                break;
            case POP:
                View movieView2 = LayoutInflater.from(context).inflate(R.layout.item_pop_movie, parent, false);
                viewHolder = new ViewHolder2(movieView2);
                break;
            default:
                View movieView3 = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
                viewHolder = new ViewHolder1(movieView3);
                break;
        }
        return viewHolder;
    }

    // Involves populating that data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder");
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        //Bind the movie data into the VH
        switch (viewHolder.getItemViewType()) {
            case NOTPOP:
                ViewHolder1 vh1 = (ViewHolder1) viewHolder;
                vh1.bind(movie);
                break;
            case POP:
                ViewHolder2 vh2 = (ViewHolder2) viewHolder;
                vh2.bind(movie);
                break;
            default:
                ViewHolder1 vh3 = (ViewHolder1) viewHolder;
                vh3.bind(movie);
                break;
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        //voteAverage
        if (movies.get(position).getVoteAverage()<8) {
            return NOTPOP;
        } else if (8<=movies.get(position).getVoteAverage()) {
            return POP;
        }
        return -1;
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // then imageUrl = back drop image
                imageUrl = movie.getBackdropPath();
            }
            else {
                // else imageUrl = poster image
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context).load(imageUrl).into(ivPoster);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        ImageView ivPoster;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            String imageUrl;
            imageUrl = movie.getBackdropPath();

            Glide.with(context).load(imageUrl).into(ivPoster);
        }
    }
}
