package ru.itis.shop.user.application;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserDto toDto(User user) {
        return new UserDto(user);
    }

    private List<UserDto> toDtoList(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
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

    public void printUserById(Integer id) {
        Optional<UserDto> userDtoOptional = Optional.of(toDto(userRepository.findById(id).get()));
        if (userDtoOptional.isPresent()) {
            System.out.println("Пользователь найден: " + userDtoOptional.get().getEmail());
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
        List<UserDto> userDtos = toDtoList(userRepository.findAll());
        showInfoAboutUser(userDtos);
    }

    private void showInfoAboutUser(List<UserDto> allUsers) {
        List<UserDto> userDtos = allUsers;
        for (UserDto userDto : userDtos) {
            System.out.println("Имя: " + userDto.getName() +
                    ", почта: " + userDto.getEmail());
        }
    }

    public void showAllUsersByProfileDescription(String profileDescription) {
        List<UserDto> userDtos = toDtoList(userRepository.findAllByProfileDescription(profileDescription));
        showInfoAboutUser(userDtos);
    }

}
