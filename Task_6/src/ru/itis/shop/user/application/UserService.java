package ru.itis.shop.user.application;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String name, String email, String password, String profileDescription) {
        User user = new User(name, email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get().getPassword().equals(password);
        } else return false;
    }

    public void findById(Integer id) {
        Optional<UserDto> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            System.out.println("Пользователь найден:" + userOptional.get().getEmail());
        } else {
            System.out.println("Пользователь с таким id не был найден");
        }
    }


    public void updateProfileDescriptionByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите новое описание профиля:");
            String profileDescription = scanner.nextLine();

            User user = userOptional.get();
            user.setProfileDescription(profileDescription);

            userRepository.update(user);
        } else {
            System.out.println("Пользователь с таким email не был найден");
        }
    }

    public void showAllUsers() {
        List<UserDto> allUsers = userRepository.findAll();
        showInfoAboutUser(allUsers);
    }

    private static void showInfoAboutUser(List<UserDto> allUsers) {
        for (UserDto user : allUsers) {
            System.out.println("Имя: " + user.getName() +
                                ", почта: " + user.getEmail());
        }
    }

    public void showAllUsersByProfileDescription(String profileDescription) {
        List<UserDto> users = userRepository.findAllByProfileDescription(profileDescription);
        showInfoAboutUser(users);
    }

}
