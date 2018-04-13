package io.nefeed.march.controller;

import com.alibaba.fastjson.JSON;
import io.nefeed.march.service.LocalFileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 分组API
 * @author 章华隽
 * @mail nefeed@163.com
 * @time 2018-04-12 17:10
 */
@RestController()
@RequestMapping("/march")
public class MarchController {

    @Resource
    private LocalFileService localFileService;

    @RequestMapping("/divide")
    public String divide(int size) {
        String content;
        try {
            content = localFileService.readLocalContent();
        } catch (IOException e) {
            e.printStackTrace();
            return "本地文件读取出错！";
        }
        if (content == null || content.isEmpty()) {
            return "本地人员名单还未配置";
        }
        List<String> people;
        try {
            people = JSON.parseArray(content, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "用户名单解析为队列失败";
        }
        if (people == null || people.size() == 0) {
            return "本地用户名单长度为空";
        }
        int groupNum = people.size() / size;
        if (people.size() % size != 0) {
            // 除不尽，组数+1
            groupNum++;
        }
        //默认构造方法，不带种子数字
        Random random = new Random();
        List<Integer> containIndexList = new ArrayList<>();
        String[] group = new String[groupNum];
        int j = -1;
        for (int i = 0; i < people.size(); i++) {
            int temp = random.nextInt(people.size());
            while (containIndexList.contains(temp)) {
                temp = random.nextInt(people.size());
            }
            containIndexList.add(temp);
            if (i % size == 0) {
                j++;
            }
            if (group[j] == null) {
                group[j] = people.get(temp);
            } else {
                group[j] = group[j] + "、" + people.get(temp);
            }
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < group.length; i++) {
            result.append("第 ").append(i + 1).append(" 组人员：").append(JSON.toJSONString(group[i])).append("<br />");
        }
        return "按照 " + size + " 人一组分组结果<br />" + result.toString();
    }
}
