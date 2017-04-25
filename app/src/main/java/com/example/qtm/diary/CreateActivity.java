package com.example.qtm.diary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.qtm.diary.db.DiaryDB;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener{
    private String value;
    public File file;

    private Button createImg;
    private Button createVideo;
    private EditText editText;
    private ImageView addImage;
    private VideoView addVideo;
    private DiaryDB diaryDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar)findViewById(R.id.createtoolbar);
        setSupportActionBar(toolbar);
        value = getIntent().getStringExtra("flag");
        createImg = (Button)findViewById(R.id.createImage);
        createVideo = (Button)findViewById(R.id.createVideo);
        editText = (EditText)findViewById(R.id.edit_text);
        addImage = (ImageView)findViewById(R.id.add_img);
        addVideo = (VideoView)findViewById(R.id.add_video);
        createImg.setOnClickListener(this);
        createVideo.setOnClickListener(this);
        diaryDB = new DiaryDB();
       // initView();
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.createtoolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                addDB();
                finish();
                break;
            default:
        }
        return true;
    }

    public void initView(){
        //判断传入的flag
        if (value.equals("1")){
            addImage.setVisibility(View.VISIBLE);
            addVideo.setVisibility(View.GONE);
        }
        if (value.equals("2")){
            addImage.setVisibility(View.GONE);
            addVideo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //调用系统相机，将拍摄的照片显示在imageview上
            case R.id.createImage:
                Intent intentImg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //获取绝对路径
                file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+ "/" + getCurrentTime()+ "jpg");
                intentImg.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                intentImg.putExtra("flag",1);
                startActivityForResult(intentImg,1);
                break;
            //调用系统摄像机，将拍摄的视频显示在videoview上
            case R.id.createVideo:
                Intent intentVideo = new Intent();
                intentVideo.putExtra("flag",2);
                startActivity(intentVideo);
                break;
            default:
        }
    }


    public void addDB(){
        diaryDB.setContent(editText.getText().toString());
        diaryDB.setTime(getCurrentTime());
        //diaryDB.setPath(file.getPath());
        diaryDB.save();
    }

    private String getCurrentTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        String curTime = format.format(date);
        return curTime;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            try{
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                addImage.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
