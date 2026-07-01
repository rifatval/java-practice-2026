package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;

public class UserMapper {

    public User fromLine(String line) {
        String[] userData = line.split("\\|");

        return new User(userData[0], userData[1], userData[2], userData[3]);
    }

    public String toLine(User user) {
        return user.getId() + "|" +
                user.getEmail() + "|" +
                user.getPassword() + "|" +
                user.getProfileDescription();
    }
}
