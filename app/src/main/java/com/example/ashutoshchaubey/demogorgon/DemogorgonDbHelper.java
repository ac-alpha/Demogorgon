package com.example.ashutoshchaubey.demogorgon;

import com.example.ashutoshchaubey.demogorgon.DemogorgonContract.VisionsEntry;
import com.example.ashutoshchaubey.demogorgon.DemogorgonContract.CharactersEntry;
import com.example.ashutoshchaubey.demogorgon.DemogorgonContract.ButtonsInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by ashutoshchaubey on 27/12/17.
 */

public class DemogorgonDbHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ThirtyDatabase.db";

    public DemogorgonDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String CREATE_TABLE_VISIONS = "CREATE TABLE " + VisionsEntry.TABLE_NAME + "("+
            VisionsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            VisionsEntry.COLUMN_RESCUER_NAME + " TEXT, " +
            VisionsEntry.COLUMN_VISION_IMAGE_URI + " TEXT, "+
            VisionsEntry.COLUMN_WHETHER_RESCUED+" INTEGER DEFAULT 0 );";

    public static final String CREATE_TABLE_BUTTONS_INFO = "CREATE TABLE "+ ButtonsInfo.TABLE_NAME+" ( "+
            ButtonsInfo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ButtonsInfo.COLUMN_SHOWN_OR_NOT + " INTEGER DEFAULT 0 );";

    private static final String CREATE_TABLE_CHARACTERS = "CREATE TABLE " + CharactersEntry.TABLE_NAME + "("+
            CharactersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CharactersEntry.COLUMN_CHARACTER_NAME + " TEXT, " +
            CharactersEntry.COLUMN_CHARACTER_IMAGE_ID + " INTEGER, "+
            CharactersEntry.COLUMN_DESCRIPTION+" TEXT, "+
            CharactersEntry.COLUMN_NUMBER_OF_RESCUES+" INTEGER DEFAULT 0 );";

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating table
        db.execSQL(CREATE_TABLE_VISIONS);
        db.execSQL(CREATE_TABLE_CHARACTERS);
        db.execSQL(CREATE_TABLE_BUTTONS_INFO);
        insertData(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + VisionsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CharactersEntry.TABLE_NAME);
        // create new table
        onCreate(db);
    }

    public void insertData(SQLiteDatabase db){
        ArrayList<Character> characters = new ArrayList<Character>();
        characters.add(new Character("Mike Wheeler", R.drawable.mikewheelers, "The Leader of the group. " +
                "A preteen boy who tasks himself and his friends to find their missing friend Will with the help of the " +
                "mysterious young girl known as \"Eleven\".",0));
        characters.add(new Character("Eleven", R.drawable.eleven, "A mysterious young girl with psychokinetic " +
                "powers found by Mike and his friends the day after Will's disappearance.",0));
        characters.add(new Character("Dustin Henderson", R.drawable.dustinhenderson,"Mike's classmate and close " +
                "friend. An enthusiastic chubby boy with a lisp who aids him in the search for Will, acting as the voice of reason.",0));
        characters.add(new Character("Lucas Sinclair",R.drawable.lucas,"Mike's childhood friend, next-door neighbor, " +
                "and classmate, who is apprehensive about the prospect of collaborating with Eleven in the search for Will.",0));
        characters.add(new Character("Max Mayfield",R.drawable.maxmayfield,"A transfer student to Hawkins Middle School. " +
                "She later becomes the group's Sixth Ranger.",0));
        characters.add(new Character("Joyce Byers",R.drawable.joycebyers,"A divorced supermarket cashier and Will and " +
                "Jonathan's single mother.",0));
        characters.add(new Character("Jim Hopper", R.drawable.hopper ,"The town of Hawkins' Police Chief and main investigator" +
                " of Will's disappearance. A divorced former law enforcer in an undisclosed big city, whose daughter died of cancer. " +
                "He struggles with alcoholism and substance abuse to cope with his losses.", 0));
        characters.add(new Character("Jonathan Byers",R.drawable.jonathanbyers,"Joyce's older son and Will's brother. " +
                "An adolescent loner who balances between his duties at school and " +
                "contributing at home by working due to their father's abandonment. A classmate of Nancy and Steve.",0));
        characters.add(new Character("Nancy Wheeler",R.drawable.nancywheeler, "Mike's older sister. A straight-arrow " +
                "highschooler who is starting to experiment with girlfriendhood.",0));
        characters.add(new Character("Steve Harrington",R.drawable.steveharrington,"A popular jock at Hawkins High with a crush " +
                "on Nancy and some skewed priorities.",0));
        characters.add(new Character("Billy Hargrove",R.drawable.billyhargrove, "Hawkins High School's new transfer student and " +
                "Max's stepbrother.",0));
        ContentValues cv=new ContentValues();
        for (int i=0;i<characters.size();i++){
            cv.put(CharactersEntry.COLUMN_CHARACTER_NAME,characters.get(i).getName());
            cv.put(CharactersEntry.COLUMN_CHARACTER_IMAGE_ID,characters.get(i).getImageID());
            cv.put(CharactersEntry.COLUMN_DESCRIPTION,characters.get(i).getDescription());
            cv.put(CharactersEntry.COLUMN_NUMBER_OF_RESCUES,characters.get(i).getNumberOfRescues());
            db.insert(CharactersEntry.TABLE_NAME,null,cv);
            cv.clear();
        }
        cv.put(ButtonsInfo.COLUMN_SHOWN_OR_NOT,0);
        db.insert(ButtonsInfo.TABLE_NAME,null,cv);
    }

}

