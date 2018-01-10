package com.example.ashutoshchaubey.demogorgon;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ashutoshchaubey on 03/01/18.
 */

public class SQLiteCharactersListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Integer> characterID;
    ArrayList<String> characterName;
    ArrayList<Integer> imageID;
    ArrayList<String> description;
    ArrayList<Integer> numberOfRescues;


    public SQLiteCharactersListAdapter(
            Context context2,
            ArrayList<Integer> id,
            ArrayList<String> name,
            ArrayList<Integer> imageid,
            ArrayList<String> desc,
            ArrayList<Integer> number
    )
    {

        this.context = context2;
        this.characterID = id;
        this.characterName = name;
        this.imageID = imageid;
        this.description = desc;
        this.numberOfRescues=number;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return characterID.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.character_list_item, null);

            holder = new Holder();

            holder.textviewname = (TextView) child.findViewById(R.id.character_list_item_name);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }

        holder.textviewname.setText(characterName.get(position));


        return child;
    }

    public class Holder {
        TextView textviewname;
    }

}
