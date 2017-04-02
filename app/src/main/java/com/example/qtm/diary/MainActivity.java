package com.example.qtm.diary;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qtm.diary.db.DiaryDB;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

public class MainActivity extends AppCompatActivity {

    private MyAdapter adapter;
    private DiaryDB diaryDB;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.list);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        LitePal.getDatabase();
        setSupportActionBar(toolbar);

        diaryDB = new DiaryDB();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
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
