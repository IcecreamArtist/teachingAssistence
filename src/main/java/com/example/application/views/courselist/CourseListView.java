package com.example.application.views.courselist;

import com.example.application.data.entity.Course;
import com.example.application.data.entity.History;
import com.example.application.data.entity.Selected;
import com.example.application.data.service.CourseMapper;
import com.example.application.data.service.HistoryMapper;
import com.example.application.data.service.SelectedMapper;
import com.example.application.utils.MybatisUtils;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.component.notification.Notification;

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

        Span prerequiste1 = new Span(course.getPrerequisite11());
        prerequiste1.addClassName("post");
        Span prerequiste2 = new Span(course.getPrerequisite22());
        prerequiste2.addClassName("post");

        HorizontalLayout prerequiste = new HorizontalLayout();
        prerequiste.addClassName("test"); // test for class name,估计跟样式有关
        prerequiste.setSpacing(false);
        prerequiste.getThemeList().add("spacing-s");
        prerequiste.add(prerequiste1,prerequiste2);

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

        card.add(header, prerequiste, actions);

        int isSelected = course.getSelected();
        Button btn = new Button("Add");
        if(isSelected == 1) btn.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        else btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        btn.addClickListener(e->{
            if (isSelected == 0) {
                int ret = modifyCourse(course);
                if (ret == 1) Notification.show("Successfully added");
                else if(ret == 2) Notification.show("Prerequisites not satisfied");
                else if(ret == 3) Notification.show("Curriculum conflict");
                else Notification.show("No enough credit"); // 0
            } else {
                Notification.show("This course has already been selected");
            }
        });
        realCard.add(card, btn);
        return realCard;
    }

    int modifyCourse(Course course) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SelectedMapper selectedMapper = sqlSession.getMapper(SelectedMapper.class);

        // 先考虑学分够不够
        Selected tmp = selectedMapper.getSelectedById(0);
        int credit = tmp.getCredit();
        // 若不够，返回失败
        if (credit < course.getCredit()) {
            sqlSession.close();
            return 0;
        }

        // 再考虑history中是否存在prerequisite
        if (course.getPrerequisite11() != null) {
            // 若存在prerequisite
            SqlSession sqlSession1 = MybatisUtils.getSqlSession();
            HistoryMapper historyMapper = sqlSession1.getMapper(HistoryMapper.class);

            History history = historyMapper.getHistoryById(course.getPrerequisite1());
            // 如果查不到会发生什么???
            if (history == null) {
                sqlSession1.close();
                sqlSession.close();
                return 2;
            }
            if (course.getPrerequisite22() != null) {
                // 存在第二个prerequisite
                history = historyMapper.getHistoryById(course.getPrerequisite2());
                if(history == null) {
                    sqlSession1.close();
                    sqlSession.close();
                    return 2;
                }
            }
            sqlSession1.close();
        }

        // 接下来考虑是否与selected有时间冲突
        Selected selected = selectedMapper.getSelectedByTime(course.getWeekday(),course.getTime());
        if (selected != null) {
            sqlSession.close();
            return 3;
        }

        // 更新courses里面的状态 & 插入到selected里面
        course.setSelected(1);

        SqlSession sqlSession2 = MybatisUtils.getSqlSession();
        CourseMapper courseMapper = sqlSession2.getMapper(CourseMapper.class);
        courseMapper.updateCourse(course);

        sqlSession2.commit();
        sqlSession2.close();

        selected = new Selected(course.getId(),course.getName(),course.getTeacher(),course.getWeekday(),course.getCredit(),course.getTime());
        selectedMapper.addSelected(selected);

        selectedMapper.updateCredit(course.getCredit());
        sqlSession.commit();
        sqlSession.close();
        return 1;
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
