package com.example.application.data.service;

import com.example.application.data.entity.Course;

import java.util.List;

public interface CourseMapper {
    // 查询全部用户
    List<Course> getCourseList();

    // 根据ID查询用户
    Course getCourseById(int id);

    // 能更新数据：有无被选择的状态
    int updateCourse(Course course);

}
