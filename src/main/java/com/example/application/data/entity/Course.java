package com.example.application.data.entity;

// 实体类
public class Course {
    private int id;
    private String name;
    private String teacher;
    private String weekday;
    private int credit;
    private int prerequisite1;
    private String prerequisite11;
    private int prerequisite2;
    private String prerequisite22;
    private int time;
    private int selected;

    public Course() {

    }

    public Course(int id, String name, String teacher, String weekday, int credit, int prerequisite1, String prerequisite11, int prerequisite2, String prerequisite22, int time, int selected) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.weekday = weekday;
        this.credit = credit;
        this.prerequisite1 = prerequisite1;
        this.prerequisite11 = prerequisite11;
        this.prerequisite2 = prerequisite2;
        this.prerequisite22 = prerequisite22;
        this.time = time;
        this.selected = selected;
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

    public int getPrerequisite1() {
        return prerequisite1;
    }

    public void setPrerequisite1(int prerequisite1) {
        this.prerequisite1 = prerequisite1;
    }

    public int getPrerequisite2() {return prerequisite2;}

    public void setPrerequisite2(int prerequisite2) {
        this.prerequisite2 = prerequisite2;
    }

    public String getPrerequisite11() {
        return prerequisite11;
    }

    public void setPrerequisite11(String prerequisite11) {
        this.prerequisite11 = prerequisite11;
    }

    public String getPrerequisite22() {
        return prerequisite22;
    }

    public void setPrerequisite22(String prerequisite22) {
        this.prerequisite22 = prerequisite22;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getSelected() {return selected;}

    public void setSelected(int selected) {this.selected = selected;}

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", weekday='" + weekday + '\'' +
                ", credit=" + credit +
                ", prerequisite1=" + prerequisite1 +
                ", time=" + time +
                ", prerequisite2=" + prerequisite2 +
                ", selected=" + selected +
                '}';
    }
}
