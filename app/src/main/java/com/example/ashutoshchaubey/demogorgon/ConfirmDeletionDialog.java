package com.example.ashutoshchaubey.demogorgon;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Toast;

/**
 * Created by ashutoshchaubey on 07/01/18.
 */

public class ConfirmDeletionDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    DemogorgonDbHelper SQLITEHELPER;
    SQLiteDatabase DATABASE;
    String sqlQuery;

    public ConfirmDeletionDialog(Activity context) {
        super(context);
        this.c=context;
        SQLITEHELPER = new DemogorgonDbHelper(c);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_visions_delete);
        Button confirmButton = findViewById(R.id.button_confirm_deletion);
        confirmButton.setOnClickListener(this);
        DATABASE = SQLITEHELPER.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_confirm_deletion:
                DATABASE.delete(DemogorgonContract.VisionsEntry.TABLE_NAME, null, null);
                DATABASE.execSQL("DROP TABLE IF EXISTS "+DemogorgonContract.VisionsEntry.TABLE_NAME);
                DATABASE.execSQL(DemogorgonDbHelper.CREATE_TABLE_VISIONS);
                DATABASE.close();
                VisionsFragment.ListAdapter.notifyDataSetChanged();
                c.startActivity(new Intent(c,MainActivity.class));
                break;
            case R.id.button_cancel_deletion:
                break;
            default:
                break;
        }
        dismiss();
    }
}
