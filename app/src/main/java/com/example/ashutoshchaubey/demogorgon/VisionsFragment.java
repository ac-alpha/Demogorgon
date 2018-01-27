package com.example.ashutoshchaubey.demogorgon;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class VisionsFragment extends Fragment {

    DemogorgonDbHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    public static SQLiteVisionRecyclerViewListAdapter ListAdapter ;

    ArrayList<Integer> VISION_ID_ArrayList = new ArrayList<Integer>();
    ArrayList<String> RESCUER_NAME_ArrayList = new ArrayList<String>();
    ArrayList<String> IMAGE_URI_ArrayList = new ArrayList<String>();
    ArrayList<Integer> WHETHER_RESCUED_ArrayList = new ArrayList<Integer>();
    public static RecyclerView recyclerView;
    public static String currentVisionRescuer;
    public static String currentImageURI;
    public static int currentRescueState;
    public static int currentVisionID;

    public VisionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_visions, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.vision_list_view);

        SQLITEHELPER = new DemogorgonDbHelper(getActivity());

        return rootView;

    }

    @Override
    public void onResume() {

        recyclerView.setAdapter(null);

        ShowSQLiteDBdata() ;

        super.onResume();
    }

    private void ShowSQLiteDBdata() {

        SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();

        cursor = SQLITEDATABASE.query(DemogorgonContract.VisionsEntry.TABLE_NAME, null, null, null, null, null, null);
        VISION_ID_ArrayList.clear();
        RESCUER_NAME_ArrayList.clear();
        IMAGE_URI_ArrayList.clear();
        WHETHER_RESCUED_ArrayList.clear();
        Log.v("VisionsFragment", "Number of Rows = " + cursor.getCount());
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    VISION_ID_ArrayList.add(cursor.getInt(0));

                    RESCUER_NAME_ArrayList.add(cursor.getString(1));

                    IMAGE_URI_ArrayList.add(cursor.getString(2));

                    WHETHER_RESCUED_ArrayList.add(cursor.getInt(3));

                } while (cursor.moveToNext());
            }
        }
        ListAdapter = new SQLiteVisionRecyclerViewListAdapter(getActivity(),
                VISION_ID_ArrayList,
                RESCUER_NAME_ArrayList,
                IMAGE_URI_ArrayList,
                WHETHER_RESCUED_ArrayList

        );

        ListAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(ListAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

}


