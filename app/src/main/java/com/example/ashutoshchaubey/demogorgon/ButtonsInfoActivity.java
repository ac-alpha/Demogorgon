package com.example.ashutoshchaubey.demogorgon;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ButtonsInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons_info);
        RelativeLayout parent = (RelativeLayout) findViewById(R.id.buttons_info_parent);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(ButtonsInfoActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });

    }
}
