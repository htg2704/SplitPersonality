package com.android.splitpersonality.Profile_Data;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class profileDB extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "profile_data";
    private static final String COL1 = "ID";
    private static final String COL2 = "bluetooth";
    private static final String COL3 = "airplane";
    private static final String COL4 = "mode";
    private static final String COL5 = "hour";
    private static final String COL6 = "minute";
    public  profileDB(Context context){
        super(context, TABLE_NAME, null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT, " + COL3 +" TEXT, " + COL4 + " TEXT, " + COL5 + "TEXT, "+ COL6 + "TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }






    public boolean addData(String blue, String air, String x) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, blue);
        contentValues.put(COL3, air);
        contentValues.put(COL4, x);


        Log.d(TAG, "addData: Adding " + blue + air + x + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data is inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


}
