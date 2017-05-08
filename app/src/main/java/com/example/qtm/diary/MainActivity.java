package com.example.qtm.diary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.qtm.diary.Adapater.MyAdapter;
import com.example.qtm.diary.db.DiaryDB;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

public class MainActivity extends AppCompatActivity {

    private MyAdapter adapter;
    private ListView listView;
    private Cursor cursor;
    private DiaryDB diaryDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.list);
        Toolbar toolbar = (Toolbar)findViewById(R.id.maintoolbar);
        LitePal.getDatabase();
        diaryDB = new DiaryDB();
        setSupportActionBar(toolbar);
        cursor = DataSupport.findBySQL("select * from DiaryDB");
        changeView();
    }

     /*点击每一个item的事件*/
    public void changeView(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                Intent intent = new Intent(MainActivity.this,SelectedActivity.class);
                //获取SelectedActivity中传过来的content
                diaryDB.setContent(getIntent().getStringExtra("content"));
                intent.putExtra("id",cursor.getInt(cursor.getColumnIndex("id")));
                intent.putExtra("content",cursor.getString(cursor.getColumnIndex("content")));
                intent.putExtra("time",cursor.getString(cursor.getColumnIndex("time")));
                intent.putExtra("path",cursor.getString(cursor.getColumnIndex("path")));
                intent.putExtra("video",cursor.getString(cursor.getColumnIndex("video")));
                startActivity(intent);
            }
        });
    }

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
