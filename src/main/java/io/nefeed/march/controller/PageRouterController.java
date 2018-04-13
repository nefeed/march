package io.nefeed.march.controller;

import io.nefeed.march.entity.Person;
import io.nefeed.march.enums.SexEnum;
import io.nefeed.march.service.LocalFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 页面路由接口拦截器
 * @author 章华隽
 * @mail nefeed@163.com
 * @time 2018-04-12 17:12
 */
@Controller()
@RequestMapping("/page")
public class PageRouterController {

    private static Logger LOG = LoggerFactory.getLogger(PageRouterController.class);

    @Resource
    private LocalFileService localFileService;

    @RequestMapping("/index")
    public String home() {
        return "/index";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        List<Person> list = localFileService.readPersonList();
        for (Person it : list) {
            it.setSexShow(SexEnum.getByCode(it.getSex()).getName());
        }
        model.addAttribute("list", list);
        return "/list";
    }

}
