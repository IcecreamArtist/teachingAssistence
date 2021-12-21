package com.example.application.views.helloworld;

import com.example.application.data.service.SelectedMapper;
import com.example.application.utils.MybatisUtils;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.apache.ibatis.session.SqlSession;

@PageTitle("Personal Information")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HelloWorldView extends VerticalLayout {

    public HelloWorldView() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SelectedMapper selectedMapper = sqlSession.getMapper(SelectedMapper.class);

        getStyle().set("text-align", "center");
        setMargin(true);

        setSpacing(false);

        add(new H2("Your total credit is : 23"));
        add(new H2("Your current credit is: " + selectedMapper.getSelectedById(0).getCredit()));
        sqlSession.close();

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }


}
