package com.qianwang.enums;

public enum TarEnum {

    STUDENT(1, "学生"),
    TEACHING_STAFF(2, "教职人员"),
    ENTERPRISE_PERSONNEL(3, "企业人员"),
    OTHER(4, "其他");

    private final int code;
    private final String desc;


    TarEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static boolean contains(int code) {
        for (TarEnum value : values()) {
            if (value.code == code) return true;
        }
        return false;
    }

    public static String getDescription(int code) {
        for (TarEnum value : values()) {
            if (value.code == code) return value.desc;
        }
        return "未知职位";
    }
}
