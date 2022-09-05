package org.test.movies.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.test.movies.Movies.Movie;
import org.test.movies.Movies.MovieAdapter;
import org.test.movies.Utils.MySingleton;
import org.test.movies.R;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private MovieAdapter movieAdapter;
    private SearchView searchView;
    private ArrayList<Movie> allMovies = new ArrayList<>();

    public SearchFragment() {
    }
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_search_layout, container, false);
        recyclerView = view.findViewById(R.id.searchMoviesRecyclerView);
        gridLayoutManager = new GridLayoutManager(view.getContext(),2);
        movieAdapter = new MovieAdapter(getContext());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieAdapter);
        searchView = view.findViewById(R.id.movieSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadNews(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        recyclerView.setAdapter(new MovieAdapter(getContext(),allMovies));
        return view;
    }
    private void loadNews(String search) {
        String url = "https://api.themoviedb.org/3/search/movie?api_key=API_KEY&query="+search;
//        Toast.makeText(getContext(),url,Toast.LENGTH_LONG).show();

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setTitle("Fetch Movies");
                progressDialog.setMessage("Retrieving movies...");
                progressDialog.show();
                try {
                    JSONArray articleResponse = response.getJSONArray("results");
                    allMovies.clear();
                    for (int i=0; i < articleResponse.length();i++) {
                        JSONObject movieJsonObject = articleResponse.getJSONObject(i);

                        Movie movie  = new Movie(
                                movieJsonObject.getString("poster_path"),
                                movieJsonObject.getBoolean("adult"),
                                movieJsonObject.getString("overview"),
                                movieJsonObject.getString("release_date"),
                                null,
                                movieJsonObject.getInt("id"),
                                movieJsonObject.getString("original_title"),
                                movieJsonObject.getString("original_language"),
                                movieJsonObject.getString("title"),
                                movieJsonObject.getString("backdrop_path"),
                                movieJsonObject.getDouble("popularity"),
                                movieJsonObject.getInt("vote_count"),
                                movieJsonObject.getBoolean("video"),
                                movieJsonObject.getDouble("vote_average")
                        );
                        allMovies.add(movie);
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObject);
        recyclerView.setAdapter(new MovieAdapter(getContext(),allMovies));

    }
}