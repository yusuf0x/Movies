package org.test.movies.Bookmarks;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.test.movies.Bookmarks.BookmarksContract.BookmarksEntry;
import org.test.movies.Movies.Movie;

import java.util.ArrayList;

public class BookmarkDbHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "favoritesDb.db";
    private static final int VERSION = 1;

    public BookmarkDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE "+ BookmarksEntry.TABLE_NAME + " ("+
                BookmarksEntry._ID + " INTEGER PRIMARY KEY, "+
                BookmarksEntry.MOVIE_TITLE + " TEXT NOT NULL, "+
                BookmarksEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, "+
                BookmarksEntry.MOVIE_RATING + " TEXT NOT NULL, "+
                BookmarksEntry.MOVIE_DATE + " TEXT NOT NULL, "+
                BookmarksEntry.MOVIE_OVERVIEW+ " TEXT NOT NULL, "+
                BookmarksEntry.MOVIE_IMAGEPATH + " TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+BookmarksEntry.TABLE_NAME);
        onCreate(db);
    }
    public  long insertData(Movie data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BookmarksContract.BookmarksEntry.MOVIE_TITLE,data.getTitle());
        contentValues.put(BookmarksContract.BookmarksEntry.MOVIE_RATING,data.getVoteAverage());
        contentValues.put(BookmarksContract.BookmarksEntry.MOVIE_DATE,data.getReleaseDate());
        contentValues.put(BookmarksContract.BookmarksEntry.COLUMN_MOVIE_ID,data.getId());
        contentValues.put(BookmarksContract.BookmarksEntry.MOVIE_IMAGEPATH,data.getPosterPath());
        contentValues.put(BookmarksContract.BookmarksEntry.MOVIE_OVERVIEW,data.getOverview());
        long id = db.insert(BookmarksContract.BookmarksEntry.TABLE_NAME , null , contentValues);
        db.close();
        return  id;
    }
    public ArrayList<Movie> getAllData() {
        ArrayList<Movie> allData = new ArrayList<>();
        String query = "SELECT * FROM "+ BookmarksContract.BookmarksEntry.TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( query ,null);
        if(cursor.moveToFirst())
            do{
                Movie data = new Movie();
                data.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(BookmarksEntry.MOVIE_TITLE)));
                data.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(BookmarksEntry.MOVIE_IMAGEPATH)));
                data.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(BookmarksEntry.MOVIE_OVERVIEW)));
                data.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(BookmarksEntry.MOVIE_RATING)));
                data.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(BookmarksEntry.MOVIE_DATE)));
//                data.setVideo(cursor.getString(cursor.getColumnIndexOrThrow(BookmarksEntry.)));
                data.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BookmarksEntry.COLUMN_MOVIE_ID)));
                allData.add(data);

            }while (cursor.moveToNext());
        db.close();
        return allData;
    }


}
