package com.example.qtm.diary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.qtm.diary.db.DiaryDB;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener{
    private Button save;
    private Button cancel;
    private EditText editText;
    private ImageView addImage;
    private VideoView addVideo;
    private DiaryDB diaryDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        save = (Button)findViewById(R.id.save);
        cancel = (Button)findViewById(R.id.cancel);
        editText = (EditText)findViewById(R.id.edit_text);
        addImage = (ImageView)findViewById(R.id.add_img);
        addVideo = (VideoView)findViewById(R.id.add_video);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        diaryDB = new DiaryDB();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                addDB();
                finish();
                break;
            case R.id.cancel:
                finish();
                break;
            default:
        }
    }

    public void addDB(){
        diaryDB.setContent(editText.getText().toString());
        diaryDB.setTime(getCurrentTime());
        diaryDB.save();
    }

    private String getCurrentTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        String curTime = format.format(date);
        return curTime;
    }
}
