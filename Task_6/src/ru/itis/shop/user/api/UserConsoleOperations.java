package ru.itis.shop.user.api;

import ru.itis.shop.user.application.UserService;

import java.util.Scanner;

public class UserConsoleOperations {

    private final UserService userService;
    private final Scanner scanner;

    public UserConsoleOperations(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        printUserMenu();

        String command = scanner.nextLine();

        switch (command) {
            case "1": {
                signUp();
            }
            break;
            case "2": {
                signIn();
            }
            break;
            case "3": {
                findById();
            }
            break;
            case "4": {
                updateProfileDescriptionByEmail();
            }
            break;
            case "5": {
                showAllUsers();
            }
            break;
            case "6": {
                showAllUsersByProfileDescription();
            }
            break;
            case "0": {
                System.exit(0);
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. Обновить описание профиля по email");
        System.out.println("5. Показать всех пользователей");
        System.out.println("6. Показать информацию о пользователях с заданным описанием профиля");
        System.out.println("0. Выход");
    }

    private void signUp() {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.println("Введите имя:");
        String name = scanner.nextLine();
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();

        userService.signUp(name, email, password, profileDescription);
        System.out.println("Регистрация прошла успешно.");
    }


    private void signIn() {
        System.out.println("Вы можете войти в приложение");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();

        if (userService.signIn(email, password)) {
            System.out.println("Вы вошли в приложение");
        } else {
            System.out.println("Email и/или пароль не верны");
        }
    }

    // method prints user`s Email by it`s Id
    private void findById() {
        System.out.println("Введите id пользователя:");
        Integer id = scanner.nextInt();
        scanner.nextLine(); // чтобы избавиться от лишнего \n в буфере

        userService.findById(id);
    }
    private void updateProfileDescriptionByEmail() {
        System.out.println("Вы можете изменить описание профиля.");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        userService.updateProfileDescriptionByEmail(email);
        System.out.println("Вы успешно обновили описание профиля.");
    }

    private void showAllUsers() {
        System.out.println("Ниже представлен список всех пользователей:\n");
        userService.showAllUsers();
    }

    private void showAllUsersByProfileDescription() {
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();
        System.out.println("Все найденные пользователи с этим описанием профиля:\n");
        userService.showAllUsersByProfileDescription(profileDescription);
    }

}
