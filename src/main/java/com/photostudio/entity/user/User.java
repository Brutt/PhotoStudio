package com.photostudio.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class User {
    private long id;
    private String email;
    private UserRole userRole;
    private String passwordHash;
    private String salt;
    private Long phoneNumber;
    private Gender gender;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private int zip;
    private String address;
}
