package io.github.hlg212.cas.controller;

import io.github.hlg212.fcf.api.AppApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 首页控制器
 * 如果直接登录 认证中心 ，默认跳转到一个 导航页面
 *
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
//@RestController
public class IndexController {

    @Autowired
    private AppApi appApi;

    @RequestMapping("/" )
    public ModelAndView index() {

        ModelAndView mav = new ModelAndView("/welcome");
/*        List list = new ArrayList();
        App app = new App();
        app.setCode("system");
        app.setName("基础数据");
        list.add(app);
        app = new App();
        app.setCode("gateway");
        app.setName("网关");
        list.add(app);*/

        //QueryProperty q = new QueryProperty();
        //List l = appApi.find(q);
        List l = new ArrayList();
        mav.addObject("apps",l);
        return mav;
    }
}
