package com.example.ashutoshchaubey.demogorgon;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class HomeActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=2000;
    int buttonInfoAlreadyShown;
    DemogorgonDbHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ContentValues cv;
    int dummyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SQLITEHELPER = new DemogorgonDbHelper(this);
        SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();
        cursor = SQLITEDATABASE.query(DemogorgonContract.ButtonsInfo.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    dummyID = cursor.getInt(0);
                    buttonInfoAlreadyShown = cursor.getInt(1);

                } while (cursor.moveToNext());
            }
        }
        Log.v("HomeActivity", "Msg = "+buttonInfoAlreadyShown);
        if(buttonInfoAlreadyShown==0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent(HomeActivity.this, ButtonsInfoActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
            cv=new ContentValues();
            cv.put(DemogorgonContract.ButtonsInfo.COLUMN_SHOWN_OR_NOT, 1);
            SQLITEDATABASE.update(DemogorgonContract.ButtonsInfo.TABLE_NAME, cv, DemogorgonContract.ButtonsInfo._ID + " = 1", null);
            SQLITEDATABASE.close();

        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }
}
