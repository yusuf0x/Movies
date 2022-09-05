package org.test.movies.Movies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.test.movies.MovieActivity;
import  org.test.movies.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.myViewHolder> {
    Context context;
    private  ArrayList<Movie> movies;

    public MovieAdapter() {
    }
    public MovieAdapter(Context context) {
        this.context = context;
    }

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.each_movie_layout,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
    try {
        final String imageUrl = "http://image.tmdb.org/t/p/w185/" + movies.get(holder.getAdapterPosition()).getPosterPath();
        holder.movieTextView.setText(movies.get(holder.getAdapterPosition()).getTitle());
        Picasso.get().load(imageUrl)
                .fit()
                .placeholder(R.color.colorBlack)
                .into(holder.movieImage);
        holder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieActivity.class);
                intent.putExtra("PosterPath",movies.get(holder.getAdapterPosition()).getPosterPath());
                intent.putExtra("OriginalTitle",movies.get(holder.getAdapterPosition()).getOriginalTitle());
                intent.putExtra("Overview",movies.get(holder.getAdapterPosition()).getOverview());
                intent.putExtra("ratings",movies.get(holder.getAdapterPosition()).getVoteAverage().toString());
                intent.putExtra("releaseDate",movies.get(holder.getAdapterPosition()).getReleaseDate());
                intent.putExtra("Video",movies.get(holder.getAdapterPosition()).getVideo());
                intent.putExtra("movie_id",movies.get(holder.getAdapterPosition()).getId().toString());
                context.startActivity(intent);
            }
        });
     } catch (Exception e){
            e.printStackTrace();
            holder.movieTextView.setText("title");
            holder.movieImage.setImageResource(R.drawable.place_holder);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public ImageView movieImage;
        public TextView movieTextView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movieImageView);
            movieTextView = itemView.findViewById(R.id.movieTextView);
        }
    }

}
