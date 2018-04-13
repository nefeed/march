package io.nefeed.march.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 人员实体
 * @author 章华隽
 * @mail nefeed@163.com
 * @time 2018-04-13 09:04
 */
public class Person implements Serializable {
    /**
     * 姓名
     */
    @NotNull(message = "名称不能为空")
    private String name;
    /**
     * 性别
     */
    @NotNull(message = "请选择性别")
    private Integer sex;
    /**
     * 能力值
     */
    @NotNull(message = "请输入战斗力")
    private Integer power;
    /**
     * 前端回显的用户名
     */
    private String sexShow;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getSexShow() {
        return sexShow;
    }

    public void setSexShow(String sexShow) {
        this.sexShow = sexShow;
    }
}
