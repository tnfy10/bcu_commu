package com.runtimeterror.bcu_commu;

public class LatestBoard {
    String title;
    String latestPost1;
    String latestPost2;
    String latestPost3;

    public LatestBoard(String title, String latestPost1, String latestPost2, String latestPost3) {
        this.title = title;
        this.latestPost1 = latestPost1;
        this.latestPost2 = latestPost2;
        this.latestPost3 = latestPost3;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLatestPost1() {
        return latestPost1;
    }

    public void setLatestPost1(String latestPost1) {
        this.latestPost1 = latestPost1;
    }

    public String getLatestPost2() {
        return latestPost2;
    }

    public void setLatestPost2(String latestPost2) {
        this.latestPost2 = latestPost2;
    }

    public String getLatestPost3() {
        return latestPost3;
    }

    public void setLatestPost3(String latestPost3) {
        this.latestPost3 = latestPost3;
    }
}
