package com.example.qtm.diary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.qtm.diary.db.DiaryDB;

import org.litepal.crud.DataSupport;

/*
* 详情页的展示内容
* */
public class SelectedActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);
        Button delete = (Button)findViewById(R.id.delete);
        Button back = (Button) findViewById(R.id.back);
        ImageView sel_img = (ImageView)findViewById(R.id.selected_img);
        VideoView  sel_video = (VideoView)findViewById(R.id.selected_video);
        TextView sel_text = (TextView)findViewById(R.id.selected_text);
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
        sel_text.setText(getIntent().getStringExtra("content"));
        if (getIntent().getStringExtra("path").equals("null")){
            sel_img.setVisibility(View.GONE);
        }else {
            sel_img.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("path"));
            sel_img.setImageBitmap(bitmap);
        }
        if (getIntent().getStringExtra("video").equals("null")){
            sel_video.setVisibility(View.GONE);
        }else {
            sel_video.setVisibility(View.VISIBLE);
            sel_video.setVideoURI(Uri.parse(getIntent().getStringExtra("video")));
            sel_video.start();
        }
    }
/*
* 删除和返回的点击事件
* */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete:
                deleteData();
                finish();
                break;
            case R.id.back:
                finish();
                break;
            default:
        }
    }
  /*
  * 删除数据的方法
  * */
    public void deleteData(){
        DataSupport.deleteAll(DiaryDB.class,"id = " + getIntent().getIntExtra("id",0));
    }
}
