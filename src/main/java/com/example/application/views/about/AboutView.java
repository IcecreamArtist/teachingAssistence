package com.example.application.views.about;

import com.example.application.data.entity.Course;
import com.example.application.data.entity.History;
import com.example.application.data.entity.Selected;
import com.example.application.data.service.CourseMapper;
import com.example.application.data.service.HistoryMapper;
import com.example.application.data.service.SelectedMapper;
import com.example.application.utils.MybatisUtils;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

@PageTitle("Learned History")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends Div implements AfterNavigationObserver {

    Grid<History> grid = new Grid<>();

    public AboutView() {
        addClassName("card-list-view");
        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(this::createCard);
        add(grid);
    }

    private VerticalLayout createCard(History course) {

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
        header.add(name);

        card.add(header);

        return card;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        HistoryMapper historyMapper = sqlSession.getMapper(HistoryMapper.class);
        List<History> historyList = historyMapper.getHistoryList();

        // 关闭sqlsession
        sqlSession.close();

        grid.setItems(historyList);
    }
}