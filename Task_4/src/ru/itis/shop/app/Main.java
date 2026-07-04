package ru.itis.shop.app;

import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.infrastructure.persistence.UserFileRepository;
import ru.itis.shop.user.infrastructure.persistence.UserMapper;
import ru.itis.shop.user.infrastructure.persistence.UserRepositoryJdbcImpl;

public class Main {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/user_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "qwertyqwerty";

    public static void main(String[] args) {
        UserFileRepository userFileRepository = new UserFileRepository("Task_4/users.txt", new UserMapper());
        UserRepositoryJdbcImpl userRepositoryJdbc = new UserRepositoryJdbcImpl(DB_URL, DB_USER, DB_PASSWORD, new UserMapper());

        UserService userService = new UserService(userRepositoryJdbc);

        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while (true) {
            operations.showMenu();
        }
    }
}
