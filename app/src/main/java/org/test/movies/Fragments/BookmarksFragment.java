package org.test.movies.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.test.movies.Bookmarks.BookmarkDbHelper;
import org.test.movies.Movies.MovieAdapter;
import org.test.movies.R;

public class BookmarksFragment extends Fragment {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private MovieAdapter movieAdapter;
    private BookmarkDbHelper bookmarkDbHelper;
    public BookmarksFragment() {
    }

    public static BookmarksFragment newInstance(String param1, String param2) {
        BookmarksFragment fragment = new BookmarksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bookmarkDbHelper = new BookmarkDbHelper(getContext());
        View view = inflater.inflate(R.layout.fragment_bookmarks_layout, container, false);
        recyclerView = view.findViewById(R.id.bookmarkMoviesRecyclerView);
        gridLayoutManager = new GridLayoutManager(view.getContext(),2);
        movieAdapter = new MovieAdapter(getContext(),bookmarkDbHelper.getAllData());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieAdapter);
        return view;
    }
}