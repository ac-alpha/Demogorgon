package com.example.ashutoshchaubey.demogorgon;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CharactersFragment extends Fragment {


    DemogorgonDbHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    SQLiteCharacterRecyclerViewListAdapter ListAdapter ;

    ArrayList<Integer> CHARACTER_ID_ArrayList = new ArrayList<Integer>();
    ArrayList<String> CHARACTER_NAME_ArrayList = new ArrayList<String>();
    ArrayList<Integer> IMAGE_ID_ArrayList = new ArrayList<Integer>();
    ArrayList<String> DESCRIPTION_Arraylist = new ArrayList<String>();
    ArrayList<Integer> NUMBER_OF_RESCUES_ArrayList = new ArrayList<Integer>();
    RecyclerView RECYCLERVIEW;

    public static int currentCharacterID;
    public static String currentCharacterName;
    public static int currentImageID;
    public static String currentCharacterDescription;
    public static int currentCharacterRescuesTotal;

    public CharactersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_characters, container, false);
        RECYCLERVIEW = (RecyclerView) rootView.findViewById(R.id.characters_recycler_view);
        SQLITEHELPER = new DemogorgonDbHelper(getActivity());

        return rootView;

    }

    @Override
    public void onResume() {

        RECYCLERVIEW.setAdapter(null);

        ShowSQLiteDBdata() ;

        super.onResume();
    }

    private void ShowSQLiteDBdata() {

        SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();

        cursor = SQLITEDATABASE.query(DemogorgonContract.CharactersEntry.TABLE_NAME, null, null, null, null, null, null);
        CHARACTER_ID_ArrayList.clear();
        CHARACTER_NAME_ArrayList.clear();
        IMAGE_ID_ArrayList.clear();
        DESCRIPTION_Arraylist.clear();
        NUMBER_OF_RESCUES_ArrayList.clear();
        Log.v("CharactersFragment","Number of Rows = "+cursor.getCount());
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    CHARACTER_ID_ArrayList.add(cursor.getInt(0));

                    CHARACTER_NAME_ArrayList.add(cursor.getString(1));

                    IMAGE_ID_ArrayList.add(cursor.getInt(2));

                    DESCRIPTION_Arraylist.add(cursor.getString(3));

                    NUMBER_OF_RESCUES_ArrayList.add(cursor.getInt(4));

                } while (cursor.moveToNext());
            }
        }
        ListAdapter = new SQLiteCharacterRecyclerViewListAdapter(getActivity(),
                CHARACTER_ID_ArrayList,
                CHARACTER_NAME_ArrayList,
                IMAGE_ID_ArrayList,
                DESCRIPTION_Arraylist,
                NUMBER_OF_RESCUES_ArrayList

        );

        RECYCLERVIEW.setAdapter(ListAdapter);
        ListAdapter.notifyDataSetChanged();

        RECYCLERVIEW.setLayoutManager(new LinearLayoutManager(getContext()));
        cursor.close();
    }

}
