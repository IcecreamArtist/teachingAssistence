package com.example.application.views.schedule;

import com.example.application.data.entity.Course;
import com.example.application.data.entity.Selected;
import com.example.application.data.service.CourseMapper;
import com.example.application.data.service.SelectedMapper;
import com.example.application.utils.MybatisUtils;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

@PageTitle("Selected Courses")
@Route(value = "schedule", layout = MainLayout.class)
public class ScheduleView extends Div implements AfterNavigationObserver {

    Grid<Selected> grid = new Grid<>();

    public ScheduleView() {
        addClassName("card-list-view");
        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(this::createCard);
        add(grid);
    }

    private HorizontalLayout createCard(Selected course) {
        HorizontalLayout realCard = new HorizontalLayout();
        realCard.addClassName("card");
        realCard.getThemeList().add("spacing-s");

        VerticalLayout card = new VerticalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.setPadding(false);
        card.getThemeList().add("spacing-s");

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span name = new Span(course.getName());
        name.addClassName("name");
        Span teacher = new Span(course.getTeacher());
        teacher.addClassName("date");
        header.add(name,teacher);

        HorizontalLayout actions = new HorizontalLayout();
        actions.addClassName("actions");
        actions.setSpacing(false);
        actions.getThemeList().add("spacing-s");

        Icon likeIcon = VaadinIcon.HEART.create();
        likeIcon.addClassName("icon");
        Span likes = new Span(String.valueOf(course.getCredit()));
        likes.addClassName("credit"); // test for class name
        Icon weekDayIcon = VaadinIcon.COMMENT.create();
        weekDayIcon.addClassName("icon");
        Span weekDay = new Span(course.getWeekday());
        weekDay.addClassName("weekday");
        Icon timeIcon = VaadinIcon.CONNECT.create();
        timeIcon.addClassName("icon");
        Span tim = new Span(String.valueOf(course.getTime()));
        tim.addClassName("time");

        actions.add(likeIcon, likes, weekDayIcon, weekDay, timeIcon, tim);

        card.add(header, actions);

        Button btn = new Button("Del");
        btn.addClickListener(e->{
            modifyCourse(course);
            Notification.show("Successful");
        });
        realCard.add(card, btn);
        return realCard;
    }

    void modifyCourse(Selected selected) {
        // 删除课程
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SelectedMapper selectedMapper = sqlSession.getMapper(SelectedMapper.class);

        selectedMapper.deleteSelected(selected.getId());

        SqlSession sqlSession1 = MybatisUtils.getSqlSession();
        CourseMapper courseMapper = sqlSession1.getMapper(CourseMapper.class);

        // 取消选择
        Course course = courseMapper.getCourseById(selected.getId());
        course.setSelected(0);
        courseMapper.updateCourse(course);

        selectedMapper.updateCredit(-course.getCredit());

        sqlSession.commit();
        sqlSession.close();

        sqlSession1.commit();
        sqlSession1.close();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        SelectedMapper selectedMapper = sqlSession.getMapper(SelectedMapper.class);
        List<Selected> selectedsList = selectedMapper.getSelectedList();

        selectedsList.remove(0);
        // 关闭sqlsession
        sqlSession.close();

        grid.setItems(selectedsList);
    }
}
