package com.java.linzexi.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class NewsEntity {
    private Boolean read;

    @PrimaryKey
    @NonNull
    private String _id;

    private String type;

    private String time;

    private Long tflag;

    private String source;

    private String title;

    private String content;

    public NewsEntity(final Boolean read, final String _id, final String type, final String time, final Long tflag, final String source, final String title, final String content) {
        this.read = read;
        this._id = _id;
        this.type = type;
        this.time = time;
        this.tflag = tflag;
        this.source = source;
        this.title = title;
        this.content = content;
    }

    @Ignore
    public NewsEntity(final NewsEntity newsEntity) {
        this.read = newsEntity.read;
        this._id = newsEntity._id;
        this.type = newsEntity.type;
        this.time = newsEntity.time;
        this.tflag = newsEntity.tflag;
        this.source = newsEntity.source;
        this.title = newsEntity.title;
        this.content = newsEntity.content;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getTflag() {
        return tflag;
    }

    public void setTflag(Long tflag) {
        this.tflag = tflag;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
