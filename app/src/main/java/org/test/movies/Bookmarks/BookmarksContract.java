package org.test.movies.Bookmarks;

import android.net.Uri;
import android.provider.BaseColumns;

public class BookmarksContract {
    public static final String AUTHORITY = "com.example5.movies.Bookmarks";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);
    public static final String PATH_FAVORITES = "bookmarks";
    public static final class BookmarksEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();
        public static final String TABLE_NAME = "bookmarks" ;
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String MOVIE_TITLE = "title";
        public static final String MOVIE_RATING = "rating";
        public static final String MOVIE_DATE = "date";
        public static final String MOVIE_IMAGEPATH ="imagepath";
        public static final String MOVIE_OVERVIEW = "plot";
    }
}
