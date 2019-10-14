package com.example.healthapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import static android.content.ContentValues.TAG;


public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, DatabaseOptions.DB_NAME, null, DatabaseOptions.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table
        db.execSQL(DatabaseOptions.INSERT_USERS_);
        String[] listOfQueries = {};
        db.beginTransaction();

        try {
            for (String insertQuery : listOfQueries) {  // loop through your records
                db.execSQL(insertQuery);
            }

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseOptions.USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseOptions.DRUGS_TABLE);
        // Create tables again
        onCreate(db);
    }

    public User queryUser(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(DatabaseOptions.USERS_TABLE, new String[]{DatabaseOptions.ID,
                        DatabaseOptions.EMAIL, DatabaseOptions.PASSWORD}, DatabaseOptions.EMAIL + "=? and " + DatabaseOptions.PASSWORD + "=?",
                new String[]{email, password}, null, null, null, "1");
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            user = new User(cursor.getString(1), cursor.getString(2));
        }
        // return user
        return user;
    }

    public Cursor getAllDrugs(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DatabaseOptions.DRUGS_TABLE, new String[]{DatabaseOptions.ID_DRUG,
                        DatabaseOptions.NAME, DatabaseOptions.DESCRIPTION}, null,
                null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseOptions.EMAIL, user.getEmail());
        values.put(DatabaseOptions.PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(DatabaseOptions.USERS_TABLE, null, values);
        db.close(); // Closing database connection

    }

}
