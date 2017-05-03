package com.example.qtm.diary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.qtm.diary.db.DiaryDB;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener{
    public File imgFile;
    public File videoFile;
    public static final int TAKE_PHOTO = 1;
    public static final int TAKE_VIDEO = 2;
    private Uri imageUri;
    private Uri videoUri;
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
        createImg = (Button)findViewById(R.id.createImage);
        createVideo = (Button)findViewById(R.id.createVideo);
        editText = (EditText)findViewById(R.id.edit_text);
        addImage = (ImageView)findViewById(R.id.add_img);
        addVideo = (VideoView)findViewById(R.id.add_video);
        createImg.setOnClickListener(this);
        createVideo.setOnClickListener(this);
        diaryDB = new DiaryDB();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //调用系统相机，将拍摄的照片显示在imageview上
            case R.id.createImage:
                //创建file对象，存储拍摄的照片
                imgFile = new File(getExternalCacheDir(),getCurrentTime()+ "jpg");
                try{
                    if (imgFile.exists()){
                        imgFile.delete();
                    }
                    imgFile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                //系统低于7.0就用fromfile将File对象转换成uri对象
                if (Build.VERSION.SDK_INT >= 24){
                    imageUri = FileProvider.getUriForFile(CreateActivity.this,"com.example.qtm.diary.fileprovider",imgFile);
                }else {
                    imageUri = Uri.fromFile(imgFile);
                }
                Intent intentImg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentImg.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intentImg,TAKE_PHOTO);
                break;
            //调用系统摄像机，将拍摄的视频显示在videoview上
            case R.id.createVideo:
                //创建file对象，存储拍摄的照片
                videoFile = new File(getExternalCacheDir(),getCurrentTime()+ "mp4");
                try{
                    if (videoFile.exists()){
                        videoFile.delete();
                    }
                    videoFile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                //系统低于7.0就用fromfile将File对象转换成uri对象
                if (Build.VERSION.SDK_INT >= 24){
                    videoUri = FileProvider.getUriForFile(CreateActivity.this,"com.example.qtm.diary.fileprovider",videoFile);
                }else {
                    videoUri = Uri.fromFile(videoFile);
                }
                Intent intentVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intentVideo.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
                startActivityForResult(intentVideo,TAKE_VIDEO);
                break;
            default:
        }
    }


    public void addDB(){
        diaryDB.setContent(editText.getText().toString());
        diaryDB.setTime(getCurrentTime());
        diaryDB.setPath(imgFile+"");
        diaryDB.setVideo(videoFile+"");
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
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try{
                        //显示拍摄的照片
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        addImage.setImageBitmap(bitmap);
                        addImage.setVisibility(View.VISIBLE);
                        addVideo.setVisibility(View.GONE);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                break;
            case TAKE_VIDEO:
                if (resultCode == RESULT_OK) {
                    //显示拍摄的视频
                    addVideo.setVideoURI(videoUri);
                    addVideo.start();
                    addImage.setVisibility(View.GONE);
                    addVideo.setVisibility(View.VISIBLE);

                }
            default:
                break;
        }
    }
}
