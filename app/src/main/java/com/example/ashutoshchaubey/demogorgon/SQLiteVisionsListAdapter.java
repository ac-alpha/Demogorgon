package com.example.ashutoshchaubey.demogorgon;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ashutoshchaubey on 28/12/17.
 */

public class SQLiteVisionsListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Integer> VisionID;
    ArrayList<String> RescuerName;
    ArrayList<String> ImageUri;
    ArrayList<Integer> whetherRescued ;


    public SQLiteVisionsListAdapter(
            Context context2,
            ArrayList<Integer> id,
            ArrayList<String> name,
            ArrayList<String> image,
            ArrayList<Integer> whetherrescued
    )
    {

        this.context = context2;
        this.VisionID = id;
        this.RescuerName = name;
        this.ImageUri = image;
        this.whetherRescued = whetherrescued ;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return VisionID.size();
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
            child = layoutInflater.inflate(R.layout.vision_list_item, null);

            holder = new Holder();

            holder.textviewname = (TextView) child.findViewById(R.id.text_view_rescuer_name);
            holder.imageviewvision = (ImageView) child.findViewById(R.id.image_view_vision);
            holder.textviewrescued = (TextView) child.findViewById(R.id.text_view_whether_rescued);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }

        holder.textviewname.setText(RescuerName.get(position));
        holder.imageviewvision.setImageURI(Uri.parse(ImageUri.get(position)));
        Integer booleanRescued = whetherRescued.get(position);
        if (booleanRescued == DemogorgonContract.VisionsEntry.RESCUED) {
            holder.textviewrescued.setText("Rescued");
            holder.textviewrescued.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            holder.textviewrescued.setText("Save Me");
            holder.textviewrescued.setTextColor(Color.parseColor("#D84315"));
        }

        return child;
    }

    public class Holder {
        TextView textviewname;
        ImageView imageviewvision;
        TextView textviewrescued;
    }

}
