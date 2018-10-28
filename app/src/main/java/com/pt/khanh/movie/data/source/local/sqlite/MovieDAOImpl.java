package com.pt.khanh.movie.data.source.local.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MovieDAOImpl implements MovieDAO {
    private static final String STRING_QUERY = " = ? ";
    private static MovieDAOImpl sInstance;
    private DBHelper mDBHelper;

    public MovieDAOImpl(DBHelper dbHelper) {
        mDBHelper = dbHelper;
    }

    public static MovieDAOImpl getInstance(DBHelper dbHelper) {
        if (sInstance == null) {
            sInstance = new MovieDAOImpl(dbHelper);
        }
        return sInstance;
    }

    @Override
    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DBHelper.TABLE_NAME,
                genProjection(),
                null,
                null,
                null,
                null,
                null
        );
        movies = genMovies(cursor);
        db.close();
        return movies;
    }

    @Override
    public Movie getMovie(long id) {
        Movie movie = null;
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NAME,
                genProjection(),
                StringUtils.getQuery(DBHelper.COLLUM_ID, STRING_QUERY),
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            movie = genMovie(cursor);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return movie;
    }

    @Override
    public boolean insertMovie(Movie movie) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLLUM_ID, movie.getId());
        values.put(DBHelper.COLLUM_TITLE, movie.getTitle());
        values.put(DBHelper.COLLUM_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(DBHelper.COLLUM_POSTER_PATH, movie.getPosterPath());
        values.put(DBHelper.COLLUM_BACKDROP_PATH, movie.getBackdropPath());
        values.put(DBHelper.COLLUM_OVER_VIEW, movie.getOverview());
        values.put(DBHelper.COLLUM_RELEASE_DATE, movie.getReleaseDate());
        db.insert(DBHelper.TABLE_NAME, null, values);
        db.close();
        return true;
    }

    @Override
    public boolean deleteMovie(Movie movie) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME,
                StringUtils.getQuery(DBHelper.COLLUM_ID, STRING_QUERY),
                new String[]{String.valueOf(movie.getId())});
        db.close();
        return true;
    }

    @Override
    public boolean isFavourite(Movie movie) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NAME,
                null,
                StringUtils.getQuery(DBHelper.COLLUM_ID, STRING_QUERY),
                new String[]{String.valueOf(movie.getId())},
                null,
                null,
                null);
        try {
            return cursor.moveToFirst();
        } finally {
            db.close();
        }
    }

    private List<Movie> genMovies(Cursor cursor) {
        List<Movie> movies = new ArrayList<>();
        if (cursor.moveToFirst()) {
            movies = new ArrayList<>();
            do {
                movies.add(genMovie(cursor));
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return movies;
    }

    private Movie genMovie(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLLUM_ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.COLLUM_TITLE)));
        movie.setVoteAverage(cursor.getFloat(cursor.getColumnIndex(DBHelper.COLLUM_VOTE_AVERAGE)));
        movie.setPosterPath(cursor.getString(cursor.getColumnIndex(DBHelper.COLLUM_POSTER_PATH)));
        movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(DBHelper.COLLUM_BACKDROP_PATH)));
        movie.setOverview(cursor.getString(cursor.getColumnIndex(DBHelper.COLLUM_OVER_VIEW)));
        movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(DBHelper.COLLUM_RELEASE_DATE)));
        return movie;
    }

    private String[] genProjection() {
        return new String[]{
                DBHelper.COLLUM_ID,
                DBHelper.COLLUM_TITLE,
                DBHelper.COLLUM_VOTE_AVERAGE,
                DBHelper.COLLUM_POSTER_PATH,
                DBHelper.COLLUM_BACKDROP_PATH,
                DBHelper.COLLUM_OVER_VIEW,
                DBHelper.COLLUM_RELEASE_DATE,
        };
    }
}
