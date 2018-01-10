package com.example.ashutoshchaubey.demogorgon;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ashutoshchaubey.demogorgon.DemogorgonContract.CharactersEntry;

/**
 * Created by ashutoshchaubey on 29/12/17.
 */

public class VisionSaverDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    String enteredRescuerName;
    DemogorgonDbHelper mDbHelper;
    String sqlUpdateQuery;

    public VisionSaverDialog(Activity context) {
        super(context);
        this.c = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_vision_saver);
        Button chooseImageButton = (Button) findViewById(R.id.submit_button_dialog_saver);
        chooseImageButton.setOnClickListener(this);
        mDbHelper = new DemogorgonDbHelper(c);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button_dialog_saver:
                Log.v("VisionSaverDialog",getRescuerName());
                Log.v("VisionSaverDialog",VisionsFragment.currentVisionRescuer);
                if(getRescuerName().equals(VisionsFragment.currentVisionRescuer.trim())){
                    Log.v("VisionSaverDialog",getRescuerName());
                    Toast.makeText(getContext(),"Will has been saved by "+getRescuerName(),Toast.LENGTH_SHORT).show();
                    VisionsFragment.currentRescueState= DemogorgonContract.VisionsEntry.RESCUED;
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(DemogorgonContract.VisionsEntry.COLUMN_WHETHER_RESCUED, DemogorgonContract.VisionsEntry.RESCUED);
                    db.update(DemogorgonContract.VisionsEntry.TABLE_NAME,cv, DemogorgonContract.VisionsEntry._ID+" = '"+VisionsFragment.currentVisionID+"'",null);
                    sqlUpdateQuery="UPDATE "+ CharactersEntry.TABLE_NAME+" SET "+CharactersEntry.COLUMN_NUMBER_OF_RESCUES+
                            "="+CharactersEntry.COLUMN_NUMBER_OF_RESCUES+"+1 WHERE "+CharactersEntry.COLUMN_CHARACTER_NAME+"='"+
                            VisionsFragment.currentVisionRescuer+"';";
                    db.execSQL(sqlUpdateQuery);
                    db.close();
                    Button button = (Button) c.findViewById(R.id.vision_activity_rescued);
                    button.setEnabled(false);
                    button.setBackgroundColor(Color.parseColor("#EF9A9A"));
                    LinearLayout parent = (LinearLayout) c.findViewById(R.id.vision_activity_parent_viewgroup);
                    parent.setBackgroundColor(Color.parseColor("#69F0AE"));
                    TextView textOverImage = (TextView) c.findViewById(R.id.vision_activity_text_over_image);
                    textOverImage.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(),"Will can only be saved by the assigned person",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        dismiss();
    }

    public String getRescuerName() {
        EditText rescuerTextBox = (EditText) findViewById(R.id.entered_rescuer_name);
        enteredRescuerName = rescuerTextBox.getText().toString().trim();
        return enteredRescuerName;
    }

}
