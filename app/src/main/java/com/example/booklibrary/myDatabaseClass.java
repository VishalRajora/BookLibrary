package com.example.booklibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;


public class myDatabaseClass extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BookLibrary.DB";
    public static final String TABLE_NAME = "My_Book";
    public static final int DATABASE_VERSION = 1;
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "book_title";
    public static final String COLUMN_AUTHOR = "book_author";
    public static final String COLUMN_PAGES = "book_pages";
    private Context context;


    myDatabaseClass(@Nullable Context context) {
        super ( context , DATABASE_NAME , null , DATABASE_VERSION );
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = " CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_AUTHOR + " TEXT, " + COLUMN_PAGES + " INTEGER);";
        db.execSQL ( query );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db , int oldVersion , int newVersion) {

        db.execSQL ( "DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate ( db );

    }

    //for inserting the data into table
    void addBook(String title , String name , String pages) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase (); //this method in use to write the data to our table
        ContentValues values = new ContentValues (); //it passs the value to our database table
        values.put ( COLUMN_TITLE , title );
        values.put ( COLUMN_AUTHOR , name );
        values.put ( COLUMN_PAGES , pages );
        long result = sqLiteDatabase.insert ( TABLE_NAME , null , values );

        if (result == -1) {
            Snackbar.make ( MainActivity.coordinatorLayout , "Failed" , Snackbar.LENGTH_SHORT ).show ();
        } else {

            Snackbar.make ( MainActivity.coordinatorLayout , "Added" , Snackbar.LENGTH_SHORT ).show ();
        }
    }

    // for showing the data to the UI means fetch data
    public Cursor readAllData() {
        SQLiteDatabase database = this.getReadableDatabase ();
        String query = " SELECT * FROM " + TABLE_NAME;
        Cursor cursor = null;
        if (database != null) {
            cursor = database.rawQuery ( query , null );
        }
        return cursor;
    }

    //for update the data
    void upadteData(String row_id , String row_title , String row_author , String row_pages) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase ();
        ContentValues contentValues = new ContentValues ();
        contentValues.put ( COLUMN_TITLE , row_title );
        contentValues.put ( COLUMN_AUTHOR , row_author );
        contentValues.put ( COLUMN_PAGES , row_pages );

        long result = sqLiteDatabase.update ( TABLE_NAME , contentValues , "_id=?" , new String[]{row_id} );
        if (result == -1) {
            Snackbar.make ( MainActivity.coordinatorLayout , "Update Update" , Snackbar.LENGTH_SHORT ).show ();
        } else {
            Snackbar.make ( MainActivity.coordinatorLayout , "Update Book" , Snackbar.LENGTH_SHORT ).show ();
        }

    }

    //for delete the data
    void deleteData(String row_id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase ();
        long result = sqLiteDatabase.delete ( TABLE_NAME , "_id= ?" , new String[]{row_id} );

        if (result == -1) {
            Snackbar.make ( MainActivity.coordinatorLayout , "Failed Delete" , Snackbar.LENGTH_SHORT ).show ();
        } else {
            Snackbar.make ( MainActivity.coordinatorLayout , "Delete Book" , Snackbar.LENGTH_SHORT ).show ();
        }
    }
}
