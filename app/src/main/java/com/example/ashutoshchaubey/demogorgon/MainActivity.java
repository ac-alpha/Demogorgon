package com.example.ashutoshchaubey.demogorgon;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DemogorgonDbHelper mDbHelper;
    public static String currentRescuer;
    public static boolean permissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("MainActivity","Permission is granted");
                permissionGranted=true;
            } else {

                Log.v("MainActivity","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                permissionGranted = false;
            }
        }else { //permission is automatically granted on sdk<23 upon installation
            Log.v("MainActivity","Permission is granted");
            permissionGranted = true;
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VisionAddDialog dialogBox = new VisionAddDialog(MainActivity.this);
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialogBox.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialogBox.show();
                dialogBox.getWindow().setAttributes(lp);

            }
        });
        mDbHelper = new DemogorgonDbHelper(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.visions_viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        DemogorgonFragmentPagerAdapter adapter = new DemogorgonFragmentPagerAdapter(getSupportFragmentManager(),MainActivity.this);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (null == imageReturnedIntent) return;
        Uri originalUri = null;
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    if(IntentChooserDialog.file.exists()){
                        Toast.makeText(this,"The image was saved at "+IntentChooserDialog.file.getAbsolutePath(),Toast.LENGTH_LONG).show();;
                    }

                    insertVision(currentRescuer, IntentChooserDialog.file.getAbsolutePath() );
                    currentRescuer="";
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    originalUri = imageReturnedIntent.getData();
                    final int takeFlags = imageReturnedIntent.getFlags()
                            & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    // Check for the freshest data.
                    getContentResolver().takePersistableUriPermission(originalUri, takeFlags);
                    insertVision(currentRescuer, originalUri.toString() );
                    currentRescuer="";
                }
                break;
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete_all_visions) {
            ConfirmDeletionDialog dialogBox = new ConfirmDeletionDialog(MainActivity.this);
            dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialogBox.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialogBox.show();
            dialogBox.getWindow().setAttributes(lp);
        } else if (id == R.id.about_the_app){
            startActivity(new Intent(this,AboutTheAppActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void insertVision(String name, String imageURI) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DemogorgonContract.VisionsEntry.COLUMN_RESCUER_NAME, name);
        values.put(DemogorgonContract.VisionsEntry.COLUMN_VISION_IMAGE_URI, imageURI);
        db.insert(DemogorgonContract.VisionsEntry.TABLE_NAME, null, values);

    }



}
