package ro.simonamihai.a2lei.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import ro.simonamihai.a2lei.model.Expense;

public class DatabaseManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DatabaseManager(Context c) {
        context = c;
        this.open();
    }

    public DatabaseManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Expense expense) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAME, expense.getName());
        contentValue.put(DatabaseHelper.DATE, expense.getCreatedAt().getTime());
        contentValue.put(DatabaseHelper.PRICE, expense.getPrice());
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseHelper.ID, DatabaseHelper.NAME, DatabaseHelper.DATE, DatabaseHelper.PRICE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, DatabaseHelper.DATE + " DESC");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(Expense expense) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, expense.getName());
        contentValues.put(DatabaseHelper.DATE, expense.getCreatedAt().getTime());
        contentValues.put(DatabaseHelper.PRICE, expense.getPrice());
        return database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.ID + " = " + expense.getId(), null);
    }

    public Expense findById(int uid){
        String[] columns = new String[]{DatabaseHelper.ID, DatabaseHelper.NAME, DatabaseHelper.DATE, DatabaseHelper.PRICE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, "id="+uid, null, null, null, null);
        cursor.moveToNext();
        int id = cursor.getInt(
                cursor.getColumnIndexOrThrow(DatabaseHelper.ID));
        String name = cursor.getString(
                cursor.getColumnIndexOrThrow(DatabaseHelper.NAME));
        Date createdAt = new Date(cursor.getLong(
                cursor.getColumnIndexOrThrow(DatabaseHelper.DATE)));
        double price = cursor.getDouble(
                cursor.getColumnIndexOrThrow(DatabaseHelper.PRICE));
        return new Expense(id, createdAt, name, price);
    }

    public void delete(Expense expense) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ID + "=" + expense.getId(), null);
    }

    public void deleteAll(){
        database.delete(DatabaseHelper.TABLE_NAME, "1", null);
    }

    public ArrayList<Expense> findAll() {
        ArrayList<Expense> values = new ArrayList<>();
        Cursor cursor = this.fetch();
        if (cursor.getCount()>0) {
            do {
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.ID));
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.NAME));
                Date createdAt = new Date(cursor.getLong(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.DATE)));
                double price = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.PRICE));
                values.add(new Expense(id, createdAt, name, price));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return values;
    }
}