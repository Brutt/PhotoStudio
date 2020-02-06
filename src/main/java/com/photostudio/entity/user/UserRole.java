package com.photostudio.entity.user;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    private int id;
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

