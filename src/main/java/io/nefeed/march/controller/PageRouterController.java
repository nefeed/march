package io.nefeed.march.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面路由接口拦截器
 * @author 章华隽
 * @mail nefeed@163.com
 * @time 2018-04-12 17:12
 */
@Controller()
@RequestMapping("/")
public class PageRouterController {

    @RequestMapping("/")
    public String home() {
        return "/index";
    }

}
