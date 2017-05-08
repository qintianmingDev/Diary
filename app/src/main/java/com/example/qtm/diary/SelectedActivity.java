package com.example.qtm.diary;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.qtm.diary.db.DiaryDB;

import org.litepal.crud.DataSupport;

/*
* 详情页的展示内容
* */
public class SelectedActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private Button delete;
    private Button back;
    private ImageView sel_img;
    private VideoView sel_video;
    private EditText sel_text;
    private DiaryDB diaryDB;

/*
* 初始化变量
* */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);
         toolbar = (Toolbar)findViewById(R.id.selectedtoolbar);
        setSupportActionBar(toolbar);
        delete = (Button)findViewById(R.id.delete);
        back = (Button) findViewById(R.id.back);
        sel_img = (ImageView)findViewById(R.id.selected_img);
        sel_video = (VideoView)findViewById(R.id.selected_video);
        sel_text = (EditText) findViewById(R.id.selected_text);
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
        initView();
    }
    private void initView(){
            sel_text.setText(getIntent().getStringExtra("content"));
        /*
        * 判断图片和视频是否为空，若空则将ImageView和VideoView隐藏
        * */
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(SelectedActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("确认要删除吗?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteData();
                        finish();
                    }
                });
                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
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
/*toolbar的引入*/
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.selectedtoolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sel_save:
                save();
                //用Intent将更新后的ontent传回给MainActivity，否则MainActivity中的content不更新
                Intent intent = new Intent(SelectedActivity.this,MainActivity.class);
                intent.putExtra("content",sel_text.getText().toString());
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    private void save(){
        diaryDB = new DiaryDB();
        ContentValues values = new ContentValues();
        values.put("content",sel_text.getText().toString());
//        ContentValues values = new ContentValues();
//        sel_text.setText(sel_text.getText().toString());
//        values.put("content",sel_text.getText().toString());
        diaryDB.updateAll(DiaryDB.class,values,"id = "+ getIntent().getIntExtra("id",0));
    }
}
