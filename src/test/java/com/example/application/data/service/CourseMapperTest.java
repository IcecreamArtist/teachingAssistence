package com.example.application.data.service;

import com.example.application.data.entity.Course;
import com.example.application.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class CourseMapperTest {

    @Test
    public void test() {
        // 获得sqlsession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        // 执行SQL
        CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
        List<Course> courseList = courseMapper.getCourseList();

        for (Course course : courseList) {
            System.out.println(course);
        }

        // 关闭sqlsession
        sqlSession.close();
    }

    @Test
    public void getCourseById() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
        Course course = courseMapper.getCourseById(1);
        System.out.println(course);

        sqlSession.close();
    }

    @Test
    public void updateCourse() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
        courseMapper.updateCourse(new Course(2,"Linear Algebra","Weiheng Zhu","Wednesday",3,4,"Advanced Mathematics",0,null,3,1));

        sqlSession.commit();
        sqlSession.close();

    }
}
