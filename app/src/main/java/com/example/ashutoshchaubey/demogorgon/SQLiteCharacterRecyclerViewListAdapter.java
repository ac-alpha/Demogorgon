package com.example.ashutoshchaubey.demogorgon;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ashutoshchaubey on 07/01/18.
 */

public class SQLiteCharacterRecyclerViewListAdapter extends RecyclerView.Adapter<SQLiteCharacterRecyclerViewListAdapter.viewHolder>{

    private LayoutInflater inflater;
    ArrayList<Integer> characterID;
    ArrayList<String> characterName;
    ArrayList<Integer> imageID;
    ArrayList<String> description;
    ArrayList<Integer> numberOfRescues;
    String sqlQuery;
    Cursor cursor;
    SQLiteDatabase SQLITEDATABASE;
    Context context;
    DemogorgonDbHelper SQLITEHELPER;


    public SQLiteCharacterRecyclerViewListAdapter(
            Context context2,
            ArrayList<Integer> id,
            ArrayList<String> name,
            ArrayList<Integer> imageid,
            ArrayList<String> desc,
            ArrayList<Integer> number
    )
    {
        inflater= LayoutInflater.from(context2);
        this.context = context2;
        this.characterID = id;
        this.characterName = name;
        this.imageID = imageid;
        this.description = desc;
        this.numberOfRescues=number;

        SQLITEHELPER = new DemogorgonDbHelper(context);
        SQLITEDATABASE = SQLITEHELPER.getReadableDatabase();
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.character_list_item,parent,false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        holder.nameTextView.setText(characterName.get(position));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlQuery="SELECT * FROM "+ DemogorgonContract.CharactersEntry.TABLE_NAME+" WHERE "+ DemogorgonContract.CharactersEntry._ID+" = "+(position+1)+" ;";
                cursor = SQLITEDATABASE.rawQuery(sqlQuery,null);
                if(cursor!=null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {

                            CharactersFragment.currentCharacterID=cursor.getInt(0);
                            CharactersFragment.currentCharacterName=cursor.getString(1);
                            CharactersFragment.currentImageID=cursor.getInt(2);
                            CharactersFragment.currentCharacterDescription=cursor.getString(3);
                            CharactersFragment.currentCharacterRescuesTotal=cursor.getInt(4);

                        } while (cursor.moveToNext());
                    }
                }
                Intent intent = new Intent(context, CharacterDescriptionActivity.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return characterID.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        LinearLayout parent;
        public viewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.character_list_item_name);
            parent = (LinearLayout) itemView.findViewById(R.id.char_list_item_parent);
        }
    }

}


