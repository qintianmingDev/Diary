package com.example.qtm.diary.db;

import org.litepal.crud.DataSupport;

/**
 * Created by QTM on 2017/4/1.
 */

public class DiaryDB extends DataSupport {
    private int Id;
    private String Time;
    private String Content;
    private String Path;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }


    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }
}
