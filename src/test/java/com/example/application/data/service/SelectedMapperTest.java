package com.example.application.data.service;

import com.example.application.data.entity.Selected;
import com.example.application.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class SelectedMapperTest {

    @Test
    public void test() {
        // 获得sqlsession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        // 执行SQL
        SelectedMapper selectedMapper = sqlSession.getMapper(SelectedMapper.class);
        List<Selected> selectedList = selectedMapper.getSelectedList();

        for (Selected selected : selectedList) {
            System.out.println(selected);
        }

        // 关闭sqlsession
        sqlSession.close();
    }

    @Test
    public void getSelectedById() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        SelectedMapper selectedMapper = sqlSession.getMapper(SelectedMapper.class);
        Selected selected = selectedMapper.getSelectedById(1);
        System.out.println(selected);
        if (selected == null) System.out.println("Not found!");
        if (selected != null && selected.getWeekday() == null)  System.out.println("test!");

        sqlSession.close();
    }

    @Test
    public void getSelectedByTime() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        SelectedMapper selectedMapper = sqlSession.getMapper(SelectedMapper.class);
        Selected selected = selectedMapper.getSelectedByTime("Monday",2);
        System.out.println(selected);

        sqlSession.close();
    }

    @Test
    public void addSelected() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        SelectedMapper selectedMapper = sqlSession.getMapper(SelectedMapper.class);
        selectedMapper.addSelected(new Selected(100,"name","teacher","monday",3,3));

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void updateCredit() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        SelectedMapper selectedMapper = sqlSession.getMapper(SelectedMapper.class);
        selectedMapper.updateCredit(3);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void deleteSelected() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        SelectedMapper selectedMapper = sqlSession.getMapper(SelectedMapper.class);
        selectedMapper.deleteSelected(100);

        sqlSession.commit();
        sqlSession.close();
    }
}
