package com.pt.khanh.movie.data.source.local.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.pt.khanh.movie.utils.StringUtils;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "movie";
    public static final String COLLUM_ID = "id";
    public static final String COLLUM_TITLE = "title";
    public static final String COLLUM_VOTE_AVERAGE = "vote_average";
    public static final String COLLUM_RELEASE_DATE = "release_date";
    public static final String COLLUM_POSTER_PATH = "poster_path";
    public static final String COLLUM_BACKDROP_PATH = "backdrop_path";
    public static final String COLLUM_OVER_VIEW = "overview";

    private static DBHelper sInstance;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MovieDB.db";

    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String FLOAT_TYPE = " FLOAT";
    private static final String COMMA = ",";
    private static final String OPEN_BRACKET = " (";
    private static final String CLOSE_BRACKET = " )";

    private static final String SQL_CREATE_MOVIES_ENTRIES = StringUtils.getQuery(
            CREATE_TABLE, TABLE_NAME, OPEN_BRACKET,
            COLLUM_ID, INTEGER_TYPE, COMMA,
            COLLUM_TITLE, TEXT_TYPE, COMMA,
            COLLUM_VOTE_AVERAGE, FLOAT_TYPE, COMMA,
            COLLUM_POSTER_PATH, TEXT_TYPE, COMMA,
            COLLUM_BACKDROP_PATH, TEXT_TYPE, COMMA,
            COLLUM_OVER_VIEW, TEXT_TYPE, COMMA,
            COLLUM_RELEASE_DATE, TEXT_TYPE, CLOSE_BRACKET);

    private static final String SQL_DELETE_MOVIES_ENTRIES =
            StringUtils.getQuery(DROP_TABLE, TABLE_NAME);

    public static DBHelper getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context);
        }
        return sInstance;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_MOVIES_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
