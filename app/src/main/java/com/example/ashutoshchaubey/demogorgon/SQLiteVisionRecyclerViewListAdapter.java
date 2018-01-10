package com.example.ashutoshchaubey.demogorgon;

/**
 * Created by ashutoshchaubey on 06/01/18.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;


/**
 * Created by ashutoshchaubey on 06/01/18.
 */

public class SQLiteVisionRecyclerViewListAdapter extends RecyclerView.Adapter<SQLiteVisionRecyclerViewListAdapter.viewHolder> {

    private LayoutInflater inflater;
    ArrayList<Integer> VisionID;
    ArrayList<String> RescuerName;
    ArrayList<String> ImageUri;
    ArrayList<Integer> whetherRescued ;
    String sqlQuery;
    Cursor cursor;
    SQLiteDatabase SQLITEDATABASE;
    Context context;
    DemogorgonDbHelper SQLITEHELPER;

    public SQLiteVisionRecyclerViewListAdapter(Context context,
                                         ArrayList<Integer> id,
                                         ArrayList<String> name,
                                         ArrayList<String> image,
                                         ArrayList<Integer> whetherrescued){

        inflater= LayoutInflater.from(context);
        this.context=context;
        this.VisionID = id;
        this.RescuerName = name;
        this.ImageUri = image;
        this.whetherRescued = whetherrescued ;
        SQLITEHELPER = new DemogorgonDbHelper(context);
        SQLITEDATABASE = SQLITEHELPER.getReadableDatabase();
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.vision_list_item,parent,false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        holder.nameTextView.setText(RescuerName.get(position));
        String returnedURI = ImageUri.get(position);
        if(returnedURI.charAt(0)=='c'){
            try {
                holder.visionImage.setImageURI(Uri.parse(returnedURI));
            }catch(OutOfMemoryError e){
                Toast.makeText(context,"Image size too large",Toast.LENGTH_SHORT).show();
            }
        }else {
            try {
                File imgFile = new File(returnedURI);

                if (imgFile.exists()) {

                    Bitmap original = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    holder.visionImage.setImageBitmap(original);
                }
            }catch (Exception e){
                Toast.makeText(context,"Image size too large",Toast.LENGTH_SHORT).show();
            }
        }
        holder.visionImage.setScaleType(ImageView.ScaleType.CENTER_CROP);


        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlQuery="SELECT * FROM "+ DemogorgonContract.VisionsEntry.TABLE_NAME+" WHERE "+ DemogorgonContract.VisionsEntry._ID+" = "+(position+1)+" ;";
                cursor = SQLITEDATABASE.rawQuery(sqlQuery,null);
                if(cursor!=null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {

                            VisionsFragment.currentVisionID=cursor.getInt(0);
                            VisionsFragment.currentVisionRescuer=cursor.getString(1);
                            VisionsFragment.currentImageURI=cursor.getString(2);
                            VisionsFragment.currentRescueState=cursor.getInt(3);

                        } while (cursor.moveToNext());
                    }
                }
                Log.v("VisionsFragment",VisionsFragment.currentVisionID+" "+VisionsFragment.currentVisionRescuer+" "+VisionsFragment.currentImageURI+" "+VisionsFragment.currentRescueState);
                Intent intent = new Intent(context, VisionActivity.class);
                context.startActivity(intent);
            }
        });
        Integer booleanRescued = whetherRescued.get(position);
        if (booleanRescued == DemogorgonContract.VisionsEntry.RESCUED) {
            holder.rescuedTextView.setText("Rescued");
            holder.rescuedTextView.setTextColor(Color.parseColor("#43A047"));
        } else {
            holder.rescuedTextView.setText("Save Me");
            holder.rescuedTextView.setTextColor(Color.parseColor("#D32F2F"));
        }


    }

    @Override
    public int getItemCount() {
        return VisionID.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView rescuedTextView;
        ImageView visionImage;
        LinearLayout parent;
        public viewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.text_view_rescuer_name);
            rescuedTextView = (TextView) itemView.findViewById(R.id.text_view_whether_rescued);
            visionImage = (ImageView) itemView.findViewById(R.id.image_view_vision);
            parent = (LinearLayout) itemView.findViewById(R.id.vision_list_item_parent);

        }
    }

}

