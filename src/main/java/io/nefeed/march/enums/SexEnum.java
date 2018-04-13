package io.nefeed.march.enums;

/**
 * 性别枚举
 * @author 章华隽
 * @mail nefeed@163.com
 * @time 2018-04-13 09:04
 */
public enum SexEnum {
    WOMAN(0, "女", "woman"),
    MAN(1, "男", "man"),
    UNKNOWN(404, "未知", "unknown"),
    ;

    SexEnum(int code, String name, String alias) {
        this.code = code;
        this.name = name;
        this.alias = alias;
    }

    private int code;
    private String name;
    private String alias;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 通过枚举 code 获得枚举
     *
     * @param code 枚举Code
     *
     * @return SexEnum
     */
    public static SexEnum getByCode(Integer code) {
        for (SexEnum cacheCode : values()) {
            if (cacheCode.getCode() == code) {
                return cacheCode;
            }
        }
        return UNKNOWN;
    }
}