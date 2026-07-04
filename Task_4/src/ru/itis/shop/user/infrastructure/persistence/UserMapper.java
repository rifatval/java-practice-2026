package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public User fromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User(
                String.valueOf(resultSet.getInt("id")).trim(),
                resultSet.getString("email").trim(),
                resultSet.getString("password").trim(),
                resultSet.getString("profile_description").trim());
        user.setName(resultSet.getString("name").trim());
        return user;
    }
}
