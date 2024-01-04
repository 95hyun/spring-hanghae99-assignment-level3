package com.sparta.adminserver.admin.entity;

public enum AdminRoleEnum {
    MANAGER(Authority.MANAGER), // manager권한 (커리큘럼, 개발)
    STAFF(Authority.STAFF); // staff권한 (default)

    private final String authority;

    AdminRoleEnum(String authority) {

        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String MANAGER = "ROLE_MANAGER";
        public static final String STAFF = "ROLE_STAFF";
    }
}
