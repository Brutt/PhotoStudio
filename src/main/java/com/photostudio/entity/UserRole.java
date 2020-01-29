package com.photostudio.entity;

public enum UserRole {
    USER ("USER"),
    ADMIN("ADMIN");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public static UserRole getByUserRole(String name) {
        for (UserRole userRole : values()) {
            if (userRole.name.equalsIgnoreCase(name)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("No UserRole with name: " + name);
    }

}

