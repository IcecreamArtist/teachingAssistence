package com.example.application.data.service;

import com.example.application.data.entity.History;

import java.util.List;

public interface HistoryMapper {
    // 查询全部用户
    List<History> getHistoryList();

    // 根据ID查询用户
    History getHistoryById(int id);
}
