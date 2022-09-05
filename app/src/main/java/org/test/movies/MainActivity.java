package org.test.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.test.movies.Fragments.BookmarksFragment;
import org.test.movies.Fragments.HomeFragment;
import org.test.movies.Fragments.SearchFragment;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemReselectedListener {

    private BottomNavigationView bottomNavigationView;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnItemReselectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

    }
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    BookmarksFragment bookmarksFragment = new BookmarksFragment();

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_container, homeFragment).commit();
                return;
            case R.id.navigation_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_container, searchFragment).commit();
                return;
            case R.id.navigation_bookmarks:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_container, bookmarksFragment).commit();
                return ;
        }
        return;
    }
}