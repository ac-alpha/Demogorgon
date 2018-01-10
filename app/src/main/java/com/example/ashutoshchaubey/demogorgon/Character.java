package com.example.ashutoshchaubey.demogorgon;

/**
 * Created by ashutoshchaubey on 03/01/18.
 */

public class Character {

    private String mName;
    private int mImageID;
    private String mDescription;
    private int mNumberOfRescues;

    public Character(String name, int id, String desc, int number){
        mName=name;
        mImageID=id;
        mDescription=desc;
        mNumberOfRescues=number;
    }

    public int getNumberOfRescues() {
        return mNumberOfRescues;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getImageID() {
        return mImageID;
    }
}
