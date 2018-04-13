package io.nefeed.march.controller;

import com.alibaba.fastjson.JSON;
import io.nefeed.march.entity.Group;
import io.nefeed.march.entity.Person;
import io.nefeed.march.enums.SexEnum;
import io.nefeed.march.service.LocalFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 分组API
 *
 * @author 章华隽
 * @mail nefeed@163.com
 * @time 2018-04-12 17:10
 */
@RestController()
@RequestMapping("/march")
public class MarchController {

    private static Logger LOG = LoggerFactory.getLogger(MarchController.class);

    @Resource
    private LocalFileService localFileService;

    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public String save(@ModelAttribute(value="person") Person person) {
        return "Waiting";
    }

    @RequestMapping(value = "/delete")
    public String delete(String name) {
        return "Waiting";
    }

    /**
     * 分组
     *
     * @param size   按多少人一组进行划分
     * @param excess 是否需要开出少人的一组
     *
     * @return 分组结果
     */
    @RequestMapping("/divide")
    public String divide(int size, @RequestParam(defaultValue = "false") Boolean excess) {
        List<Person> people = localFileService.readPersonList();
        // 组数
        int groupNum = people.size() / size;
        // 超出size的人数
        int excessNum = people.size() % size;
        int peopleSize = people.size();
        if (excessNum != 0) {
            // 除不尽，组数+1
            if (excess) {
                groupNum++;
            } else {
                peopleSize = peopleSize - excessNum;
            }
        }
        //默认构造方法，不带种子数字
        Random random = new Random();
        List<Integer> containIndexList = new ArrayList<>();
        List<Group> group = new ArrayList<>();
        for (int i = 0; i < groupNum; i++) {
            group.add(new Group());
        }
        // 性别比例是否合适，能力值是否合适
        boolean isFair = false;
        while (!isFair) {
            int index = -1;
            for (int i = 0; i < peopleSize; i++) {
                int temp = random.nextInt(people.size());
                while (containIndexList.contains(temp)) {
                    temp = random.nextInt(people.size());
                }
                containIndexList.add(temp);
                if (i % size == 0) {
                    index++;
                }
                group.get(index).getGroup().add(people.get(temp));
            }
            if (!excess) {
                // 不需要额外分组，把多余的人平分了
                for (int i = 0; i < excessNum; i++) {
                    int temp = random.nextInt(people.size());
                    while (containIndexList.contains(temp)) {
                        temp = random.nextInt(people.size());
                    }
                    containIndexList.add(temp);
                    group.get(i).getGroup().add(people.get(temp));
                }
            }
            // 是否有一组能力过强
            boolean isPowerExcessive = false;
            for (int i = 0; i < group.size(); i++) {
                int frontGroupPower = 0;
                boolean withGirl = false;
                for (Person it : group.get(i).getGroup()) {
                    frontGroupPower += it.getPower();
                    if (SexEnum.WOMAN.getCode() == it.getSex()) {
                        withGirl = true;
                    }
                }
                if (!withGirl) {
                    // 该分组没有女生，从新分组
                    isPowerExcessive = true;
                    LOG.warn("该分组没有女生：{}", JSON.toJSONString(group.get(i)));
                    break;
                } else {
                    for (int j = (i + 1); j < group.size(); j++) {
                        if (j == group.size()) {
                            break;
                        }
                        int behindGroupPower = 0;
                        for (Person it : group.get(j).getGroup()) {
                            behindGroupPower += it.getPower();
                        }
                        double weight = frontGroupPower / (frontGroupPower + behindGroupPower);
                        if (weight > 0.75) {
                            // 改组能力值过强
                            isPowerExcessive = true;
                            LOG.warn("该分组能力过强：{}", JSON.toJSONString(group.get(i)));
                            break;
                        }
                    }
                }
                if (isPowerExcessive) {
                    break;
                }
            }
            if (!isPowerExcessive) {
                isFair = true;
            } else {
                // 清空队列从新开始
                containIndexList.clear();
                group.clear();
                for (int i = 0; i < groupNum; i++) {
                    group.add(new Group());
                }
            }
        }
        LOG.debug("最终分组情况{}", JSON.toJSONString(group));
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < group.size(); i++) {
            result.append("第 ").append(i + 1).append(" 组人员：");
            for (int j = 0; j < group.get(i).getGroup().size(); j++) {
                Person it = group.get(i).getGroup().get(j);
                if (j == group.get(i).getGroup().size() - 1) {
                    result.append(it.getName());
                } else {
                    result.append(it.getName()).append("、");
                }
            }
            result.append("<br />");
        }
        return "按照 " + size + " 人一组分组结果<br />" + result.toString();
    }
}
