package com.example.application.data.service;

import com.example.application.data.entity.Course;
import com.example.application.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class CourseMapperTest {

    @Test
    public void test() {
        // ���sqlsession����
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        // ִ��SQL
        CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
        List<Course> courseList = courseMapper.getCourseList();

        for (Course course : courseList) {
            System.out.println(course);
        }

        // �ر�sqlsession
        sqlSession.close();
    }
}
