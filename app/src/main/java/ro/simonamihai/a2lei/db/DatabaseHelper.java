package ro.simonamihai.a2lei.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "expense";

    // Table columns
    public static final String ID = "id";
    public static final String DATE = "created_at";
    public static final String NAME = "name";
    public static final String PRICE = "price";

    // Database Information
    static final String DB_NAME = "expense.db";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID
            + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " + DATE + " INTEGER NOT NULL, " + PRICE + " REAL NOT NULL, " + NAME + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}