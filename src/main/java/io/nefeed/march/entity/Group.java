package io.nefeed.march.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组
 * @author 章华隽
 * @mail nefeed@163.com
 * @time 2018-04-13 09:21
 */
public class Group {
    /**
     * 每组名单
     */
    private List<Person> group = new ArrayList<>();

    public List<Person> getGroup() {
        return group;
    }

    public void setGroup(List<Person> group) {
        this.group = group;
    }
}
