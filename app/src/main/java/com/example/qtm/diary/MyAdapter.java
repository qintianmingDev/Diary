package com.example.qtm.diary;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by QTM on 2017/4/2.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private Cursor cursor;
    private LinearLayout layout;

    public MyAdapter(Context context,Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }
    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return cursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        layout = (LinearLayout)inflater.inflate(R.layout.unit,null);
        TextView contentText = (TextView) layout.findViewById(R.id.list_text);
        TextView timeText = (TextView)layout.findViewById(R.id.list_time);
        ImageView img = (ImageView)layout.findViewById(R.id.list_image);
        ImageView video = (ImageView)layout.findViewById(R.id.list_video);
        cursor.moveToPosition(position);
        String content = cursor.getString(cursor.getColumnIndex("content"));
        String time = cursor.getString(cursor.getColumnIndex("time"));
        contentText.setText(content);
        timeText.setText(time);

        return layout;
    }
}
