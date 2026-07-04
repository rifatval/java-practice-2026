package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class UserFileRepository implements UserRepository {

    private final String fileName;

    private final UserMapper userMapper;

    public UserFileRepository(String fileName, UserMapper userMapper) {
        this.fileName = fileName;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            writer.write(userMapper.toLine(user));
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findBy(user -> user.getEmail().equals(email));
    }

    @Override
    public Optional<User> findById(String id) {
        return findBy(user -> user.getId().equals(id));
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = userMapper.fromLine(line);
                list.add(user);
            }
            return list;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Optional<User> findBy(Predicate<User> predicate) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = userMapper.fromLine(line);
                if (predicate.test(user)) {
                    return Optional.of(user);
                }
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    // перезапись всего файла, при этом конкретный user полностью обновится
    // (все измененные поля, а не только profileDiscription)
    @Override
    public void update(User user) {
        List<String> lines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User userCurrent = userMapper.fromLine(line);
                if (userCurrent.getEmail().equals(user.getEmail())) {
                    lines.add(userMapper.toLine(user));
                    updated = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка при чтении файла", e);
        }

        if (updated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                throw new IllegalStateException("Ошибка при записи в файл", e);
            }
        }
    }
}
