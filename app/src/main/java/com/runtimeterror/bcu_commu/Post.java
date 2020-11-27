package com.runtimeterror.bcu_commu;

public class Post {
    String title;
    String content;
    String writer;
    String time;

    public Post(String title, String content, String writer, String time) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.time = time;
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

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
