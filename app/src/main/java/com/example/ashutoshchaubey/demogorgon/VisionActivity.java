package com.example.ashutoshchaubey.demogorgon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class VisionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);
        Log.v("VisionActivity",VisionsFragment.currentImageURI);
        TextView visionActivityRescuerTextView = (TextView)findViewById(R.id.vision_activity_rescuer_name);
        visionActivityRescuerTextView.setText(VisionsFragment.currentVisionRescuer);

        ImageView visionActivityImageView = (ImageView) findViewById(R.id.vision_activity_image);
        if(VisionsFragment.currentImageURI.charAt(0)=='c'){
            try {
                visionActivityImageView.setImageURI(Uri.parse(VisionsFragment.currentImageURI));
            }catch(OutOfMemoryError e){
                Toast.makeText(this,"Image size too large",Toast.LENGTH_SHORT).show();
            }

        }else if(VisionsFragment.currentImageURI.charAt(0)=='/') {
            try {
                File imgFile = new File(VisionsFragment.currentImageURI);

                if (imgFile.exists()) {

                    Bitmap original = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    visionActivityImageView.setImageBitmap(original);


                }
            }catch (Exception e){
                Toast.makeText(this,"Image size too large",Toast.LENGTH_SHORT).show();
            }
        }

        Button visionActivityButton = (Button) findViewById(R.id.vision_activity_rescued);
        if (VisionsFragment.currentRescueState==DemogorgonContract.VisionsEntry.RESCUED){
            visionActivityButton.setEnabled(false);
        }

        visionActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VisionSaverDialog dialogBox = new VisionSaverDialog(VisionActivity.this);
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialogBox.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialogBox.show();
                dialogBox.getWindow().setAttributes(lp);
            }
        });

    }

    @Override
    protected void onResume() {
        if(VisionsFragment.currentRescueState== DemogorgonContract.VisionsEntry.RESCUED) {
            Button button = (Button) findViewById(R.id.vision_activity_rescued);
            button.setEnabled(false);
            button.setBackgroundColor(Color.parseColor("#EF9A9A"));
            LinearLayout parent = (LinearLayout) findViewById(R.id.vision_activity_parent_viewgroup);
            parent.setBackgroundColor(Color.parseColor("#69F0AE"));
            TextView textOverImage = (TextView) findViewById(R.id.vision_activity_text_over_image);
            textOverImage.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        VisionsFragment.currentRescueState=0;
        VisionsFragment.currentVisionID=0;
        VisionsFragment.currentImageURI="";
        VisionsFragment.currentVisionRescuer="";
        super.onStop();
    }
}
