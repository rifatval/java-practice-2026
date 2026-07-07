package ru.itis.shop.user.application;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.IOException;
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
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            System.out.println("Пользователь найден:" + userOptional.get().getEmail());
        } else {
            System.out.println("Пользователь с таким id не был найден");
        }
    }


    /**
     * ищем пользователя по почте
     *     если не нашли: сообщаем о том что пользователь не был найден с такой почтой
     * предлагаю обновить profileDescription : считываю сканером новое описание
     * перезаписываю profileDescription в самом FileRepository
     * */
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
        List<User> allUsers = userRepository.findAll();
        if (allUsers == null) return;
        for (User user : allUsers) {
            System.out.println("Имя: " + user.getName() +
                                ", почта: " + user.getEmail());
        }
    }
}
