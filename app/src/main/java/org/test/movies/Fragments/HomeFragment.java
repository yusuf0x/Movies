package org.test.movies.Fragments;

import android.os.Bundle;
import android.app.ProgressDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.util.List;


public class HomeFragment extends Fragment {

    private ArrayList<Movie> allMovies = new ArrayList<>();
    private RecyclerView recyclerView;
    private Spinner movieSpinner;
    private GridLayoutManager gridLayoutManager;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private  String movieType = "popular";
    private  int page = 1;

    public HomeFragment() {
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.allMoviesRecyclerView);

        gridLayoutManager = new GridLayoutManager(view.getContext(),2);
        movieAdapter = new MovieAdapter(getContext());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieAdapter);
        loadNews(movieType);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(movieType);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        // Spinner
        List age = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            age.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, age);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        Spinner pageSpinner = view.findViewById(R.id.page);
        pageSpinner.setAdapter(spinnerArrayAdapter);
        pageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                page = i+1;
                loadNews(movieType);
//                Toast.makeText(getContext(),page+"",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        movieSpinner = view.findViewById(R.id.movieSpinner);
        movieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: movieType = "popular";loadNews(movieType);break;
                    case 1: movieType = "top_rated";loadNews(movieType);break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        recyclerView.setAdapter(new MovieAdapter(getContext(),allMovies));
        return view;
    }

    private void loadNews(String movieType) {
        String api = "?api_key=API_KEY&language=en-US&page="+page;
        String url = "https://api.themoviedb.org/3/movie/"+movieType+api;
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