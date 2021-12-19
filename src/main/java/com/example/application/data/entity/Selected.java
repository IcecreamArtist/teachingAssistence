package com.example.application.data.entity;

// 实体类
public class Selected {
    private int id;
    private String name;
    private String teacher;
    private String weekday;
    private int credit;
    private int time;

    public Selected() {

    }

    public Selected(int id, String name, String teacher, String weekday, int credit, int time) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.weekday = weekday;
        this.credit = credit;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", weekday='" + weekday + '\'' +
                ", credit=" + credit +
                ", time=" + time +
                '}';
    }
}
