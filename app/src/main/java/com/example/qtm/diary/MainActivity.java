package com.example.qtm.diary;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.qtm.diary.db.DiaryDB;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

public class MainActivity extends AppCompatActivity {

    private MyAdapter adapter;
    private DiaryDB diaryDB;
    private ListView listView;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        value = getIntent().getStringExtra("flag");
        listView = (ListView)findViewById(R.id.list);
        Toolbar toolbar = (Toolbar)findViewById(R.id.maintoolbar);
        LitePal.getDatabase();
        setSupportActionBar(toolbar);

        diaryDB = new DiaryDB();
    }
//    public void initView(){
//        if (value.equals("1")){
//
//        }
//    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.maintoolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //点击新建跳转到新建页面
            case R.id.createNew:
                Intent intent = new Intent(this,CreateActivity.class);
                startActivity(intent);
                break;
            default:

        }
        return true;
    }


    public void queryDB(){
        Cursor cursor = DataSupport.findBySQL("select * from DiaryDB");
        adapter = new MyAdapter(this,cursor);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        queryDB();
    }
}
