package ru.itis.shop.user.repository;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    // for signIn()
    Optional<User> findByEmail(String email);

    Optional<UserDto> findById(Integer id);

    List<UserDto> findAll();

    void update(User user);

    List<UserDto> findAllByProfileDescription(String profileDescription);
}
