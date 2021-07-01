package com.leiran.common.enums;

import java.util.Arrays;

public enum RoleEnum {
    /**
     * 管理员
     */
    ROLE_ADMIN,
    /**
     * 用户
     */
    ROLE_USER,
    /**
     * 游客
     */
    ROLE_VISITOR;

    public static RoleEnum of(String name) {
        return Arrays.stream(RoleEnum.values()).filter(i -> i.name().equals(name)).findFirst().orElse(null);
    }

    public static boolean isAdmin(String name) {
        return RoleEnum.ROLE_ADMIN.name().equals(name);
    }

    public static boolean isVisitor(String name) {
        return RoleEnum.ROLE_VISITOR.name().equals(name);
    }
}
