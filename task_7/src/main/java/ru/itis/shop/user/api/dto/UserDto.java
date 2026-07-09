package ru.itis.shop.user.api.dto;

import ru.itis.shop.user.domain.User;

//Data Transfer Object
public class UserDto {

    private Integer id;
    private String name;
    private String email;
    private String profileDescription;

    public UserDto(Integer id, String name, String email, String profileDescription) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileDescription = profileDescription;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileDescription = user.getProfileDescription();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileDescription() {
        return profileDescription;
    }
}
