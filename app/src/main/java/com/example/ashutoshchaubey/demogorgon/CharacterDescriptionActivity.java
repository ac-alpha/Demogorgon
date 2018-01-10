package com.example.ashutoshchaubey.demogorgon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class CharacterDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_description);
        Log.v("VisionDescriptionActi",CharactersFragment.currentImageID+"    "+CharactersFragment.currentCharacterName);
        TextView nameTextView = (TextView) findViewById(R.id.character_activity_name);
        nameTextView.setText(CharactersFragment.currentCharacterName);

        ImageView characterImageView = (ImageView) findViewById(R.id.character_activity_image);
        characterImageView.setImageResource(CharactersFragment.currentImageID);

        TextView numberTextView = (TextView)findViewById(R.id.character_activity_number_of_rescues);
        numberTextView.setText(""+CharactersFragment.currentCharacterRescuesTotal);

        TextView descTextView = (TextView)findViewById(R.id.character_activity_description);
        descTextView.setText(CharactersFragment.currentCharacterDescription);

    }

    @Override
    protected void onStop() {
        CharactersFragment.currentCharacterDescription="";
        CharactersFragment.currentCharacterID=0;
        CharactersFragment.currentCharacterName="";
        CharactersFragment.currentCharacterRescuesTotal=0;
        CharactersFragment.currentImageID=0;
        super.onStop();
    }
}
