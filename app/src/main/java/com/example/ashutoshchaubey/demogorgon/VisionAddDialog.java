package com.example.ashutoshchaubey.demogorgon;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;
import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by ashutoshchaubey on 27/12/17.
 */

public class VisionAddDialog extends Dialog implements android.view.View.OnClickListener, AdapterView.OnItemSelectedListener{

    Spinner spinner;
    public Activity c;
    public Dialog d;
    public static final int GALLERY_INTENT_CALLED = 1;
    public static final int GALLERY_KITKAT_INTENT_CALLED=2;
    public String rescuerName;
    public static Uri outputFileUri;
    public static int YOUR_SELECT_PICTURE_REQUEST_CODE=1;

    public VisionAddDialog(Activity context) {
        super(context);
        this.c=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_vision);
        Button chooseImageButton = (Button) findViewById(R.id.choose_image_button);
        chooseImageButton.setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.spinner_char_name);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(c,R.array.char_names_spinner, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_image_button:
                if(MainActivity.currentRescuer.equals("") || MainActivity.currentRescuer.equals("--Select--")){
                    Toast.makeText(c,"Select one character please!",Toast.LENGTH_SHORT).show();
                } else {
                    IntentChooserDialog dialogBox = new IntentChooserDialog(c);
                    dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialogBox.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    lp.gravity = Gravity.BOTTOM;
                    lp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    dialogBox.show();
                    dialogBox.getWindow().setAttributes(lp);
                }

                break;
            default:
                break;
        }
        dismiss();
    }

//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView selectedTextView = (TextView) view;
        MainActivity.currentRescuer = selectedTextView.getText().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
