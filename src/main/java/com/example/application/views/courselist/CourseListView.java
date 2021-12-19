package com.example.application.views.courselist;

import com.example.application.data.entity.Course;
import com.example.application.data.service.CourseMapper;
import com.example.application.utils.MybatisUtils;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

@PageTitle("Course List")
@Route(value = "course-list", layout = MainLayout.class)
public class CourseListView extends Div implements AfterNavigationObserver {

    Grid<Course> grid = new Grid<>();

    public CourseListView() {
        addClassName("card-list-view");
        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(this::createCard);
        add(grid);
    }

    private HorizontalLayout createCard(Course course) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span name = new Span(course.getName());
        name.addClassName("name");
        Span teacher = new Span(course.getTeacher());
        header.add(name,teacher);

        HorizontalLayout actions = new HorizontalLayout();
        actions.addClassName("actions");
        actions.setSpacing(false);
        actions.getThemeList().add("spacing-s");

        Icon likeIcon = VaadinIcon.HEART.create();
        likeIcon.addClassName("credit");
        Span likes = new Span(String.valueOf(course.getCredit()));
        likes.addClassName("credit");
        actions.add(likeIcon, likes);

        card.add(header,actions);
        return card;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        // 执行SQL
        CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
        List<Course> courseList = courseMapper.getCourseList();

        // 关闭sqlsession
        sqlSession.close();

        grid.setItems(courseList);
    }
}
