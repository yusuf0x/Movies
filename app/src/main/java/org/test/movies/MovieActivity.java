package org.test.movies;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;


import org.test.movies.Bookmarks.BookmarkDbHelper;
import org.test.movies.Movies.Movie;
import org.test.movies.Reviews.Review;
import org.test.movies.Reviews.ReviewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.test.movies.Utils.MySingleton;
import org.test.movies.Videos.Video;
import org.test.movies.Videos.VideoAdapter;

import java.util.ArrayList;



public class MovieActivity extends AppCompatActivity  {
    TextView title ;
    ImageView image ;
    TextView overview;
    TextView userRating;
    TextView date;
    ArrayList<Video> videos;
    ArrayList<Review> reviews;
    Context context;
    public final static String BASE_URL = "https://api.themoviedb.org/";
    VideoAdapter videoAdapter ;
    RecyclerView videoRecyclerView ;
    LinearLayoutManager videolayoutManager;
    ReviewAdapter reviewAdapter;
    RecyclerView reviewRecyclerView;
    LinearLayoutManager reviewlinearLayoutManager;
    ViewPager viewPager;
    Button addToBookmark;
    String movieName  ;
    String posterImage ;
    String plot ;
    String ratings ;
    String releaseDate ;
    String movieId ;
    String video_boolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        context = getBaseContext();
        title =  findViewById(R.id.movie_name);
        image =  findViewById(R.id.movie_image);
        overview = findViewById(R.id.movie_plot);
        userRating =  findViewById(R.id.movie_rating);
        date =  findViewById(R.id.movie_date);
        addToBookmark =  findViewById(R.id.add_to_bookmark_button);
        // Intent
        Intent receiveIntent = getIntent();
        movieName = receiveIntent.getStringExtra("OriginalTitle");
        posterImage = receiveIntent.getStringExtra("PosterPath");
        plot = receiveIntent.getStringExtra("Overview");
        ratings = receiveIntent.getStringExtra("ratings");
        releaseDate = receiveIntent.getStringExtra("releaseDate");
        movieId = receiveIntent.getStringExtra("movie_id");
        video_boolean = receiveIntent.getStringExtra("video_boolean");
        // VideoRecyclerView
        videoRecyclerView =  findViewById(R.id.trailers_recyclerview);
        videoRecyclerView.setHasFixedSize(true);
        videolayoutManager = new LinearLayoutManager(this);
        videoRecyclerView.setLayoutManager(videolayoutManager);
        videos = new ArrayList<>();
        // ReviewRecyclerView
        reviewRecyclerView =  findViewById(R.id.reviews_recyclerview);
        reviewRecyclerView.setHasFixedSize(true);
        reviewlinearLayoutManager = new LinearLayoutManager(this);
        reviewRecyclerView.setLayoutManager(reviewlinearLayoutManager);
        reviews = new ArrayList<>();
        //
        getEveryVideo(movieId);
        getEveryReview(movieId);
        //
        videoAdapter = new VideoAdapter(this,videos);
        videoRecyclerView.setAdapter(videoAdapter);
        reviewAdapter = new ReviewAdapter(this,reviews);
        reviewRecyclerView.setAdapter(reviewAdapter);

        //
        title.setText(movieName);
        Picasso.get().load("http://image.tmdb.org/t/p/w185/"+posterImage)
                .resize(400,400)
                .placeholder(R.color.colorBlack)
                .into(image);
        overview.setText("About this movie :\n "+plot);
        userRating.setText("Rating: "+ratings+"/10");
        date.setText("Date:"+releaseDate);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Movie Detail");
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2c3539")));

        addToBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookmarkDbHelper bookmarkDbHelper = new BookmarkDbHelper(getApplicationContext());
                Log.d("TEST",Integer.parseInt(movieId)+"|"+movieId);
                Movie movie = new Movie(posterImage,false,plot,releaseDate,null,Integer.parseInt(movieId),movieName,null,movieName,null,0.0,0,Boolean.parseBoolean(video_boolean),Double.parseDouble(ratings));
                long id = bookmarkDbHelper.insertData(movie);
                bookmarkDbHelper.close();
                Toast.makeText(getBaseContext(),"Added to Favorites",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getEveryVideo(String word){
        //3/movie/{id}/videos?api_key=
        String url = BASE_URL+"3/movie/"+word+"/videos?api_key=API_KEY&language=en-US&page=1";
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray articleResponse = response.getJSONArray("results");
                    videos.clear();
                    for (int i=0; i < articleResponse.length();i++) {
                        JSONObject movieJsonObject = articleResponse.getJSONObject(i);

                        Video video  = new Video(
                                movieJsonObject.getString("id"),
                                movieJsonObject.getString("iso_639_1"),
                                movieJsonObject.getString("iso_3166_1"),
                                movieJsonObject.getString("key"),
                                movieJsonObject.getString("name"),
                                movieJsonObject.getString("site"),
                                movieJsonObject.getInt("size"),
                                movieJsonObject.getString("type")
                        );
                        videos.add(video);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObject);
        videoRecyclerView.setAdapter(new VideoAdapter(this,videos));
    }

    private void getEveryReview(String word){
        String url = BASE_URL+"3/movie/"+word+"/reviews?api_key=API_KEY&language=en-US&page=1";
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray articleResponse = response.getJSONArray("results");
                    reviews.clear();
                    for (int i=0; i < articleResponse.length();i++) {
                        JSONObject movieJsonObject = articleResponse.getJSONObject(i);

                        Review review  = new Review(
                                movieJsonObject.getString("id"),
                                movieJsonObject.getString("author"),
                                movieJsonObject.getString("content"),
                                movieJsonObject.getString("url")
                        );
                        reviews.add(review);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObject);
        reviewRecyclerView.setAdapter(new ReviewAdapter(this,reviews));
    }

}
