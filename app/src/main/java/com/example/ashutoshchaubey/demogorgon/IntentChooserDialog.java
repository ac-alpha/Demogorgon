package com.example.ashutoshchaubey.demogorgon;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by ashutoshchaubey on 08/01/18.
 */

public class IntentChooserDialog extends Dialog implements android.view.View.OnClickListener {

    private static final int GALLERY_RESULT = 1;
    private static final int CHOOSE_CAMERA_RESULT = 0;
    public Activity c;

    public static File file;

    public IntentChooserDialog(Activity context) {
        super(context);
        this.c = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_intent_chooser);
        ImageView capture = (ImageView) findViewById(R.id.capture_image);
        capture.setOnClickListener(this);
        ImageView galleryPick = (ImageView) findViewById(R.id.choose_image_from_gallery);
        galleryPick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.capture_image:
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(c, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(c);
                }
                builder.setTitle("Add from camera?")
                        .setMessage("Make sure that the image is not of high quality otherwise app will crash.\nIf the app crashes, restart the app and delete all visions")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "IMG_" + timeStamp + ".jpg");
                                Uri tempuri = Uri.fromFile(file);
                                i.putExtra(MediaStore.EXTRA_OUTPUT, tempuri);
                                i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,0);
                                c.startActivityForResult(i, CHOOSE_CAMERA_RESULT);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
            case R.id.choose_image_from_gallery:
                AlertDialog.Builder builder1;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder1 = new AlertDialog.Builder(c, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder1 = new AlertDialog.Builder(c);
                }
                builder1.setTitle("Add from Gallery?")
                        .setMessage("Make sure that the image is not of high quality otherwise app will crash.\nIf the app crashes, restart the app and delete all visions")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intent.setType("image/jpeg");
                                c.startActivityForResult(intent, GALLERY_RESULT);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                break;
            default:
                break;
        }
        dismiss();
    }
}