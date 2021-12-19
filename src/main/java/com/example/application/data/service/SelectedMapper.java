package com.example.application.data.service;

import com.example.application.data.entity.Course;
import com.example.application.data.entity.Selected;

import java.util.List;

public interface SelectedMapper {
    // 查询全部用户
    List<Selected> getSelectedList();

    // 根据ID查询用户
    Selected getSelectedById(int id);

    // 根据时间查询用户
    Selected getSelectedByTime(String weekday, int time);

    // insert一个课程
    void addSelected(Selected selected);

    // 不用修改课程.
    int updateCredit(int credit);

    // 删除课程
    void deleteSelected(int id);
}
