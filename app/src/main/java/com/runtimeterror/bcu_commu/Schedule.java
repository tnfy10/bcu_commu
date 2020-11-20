package com.runtimeterror.bcu_commu;

public class Schedule {
    String name;
    String date;
    String memo;

    public Schedule(String name, String date, String memo) {
        this.name = name;
        this.date = date;
        this.memo = memo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
