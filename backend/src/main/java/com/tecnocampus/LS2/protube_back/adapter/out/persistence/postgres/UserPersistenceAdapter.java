package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.UserMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository.UserRepository;
import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.port.out.GetUserPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements GetUserPort, StoreUserPort {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    UserJpaEntity findByUsername(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.toDomain(findByUsername(username));
    }

    @Override
    public void storeUser(User user) {
        try {
            findByUsername(user.username());
            throw new IllegalArgumentException("User already exists");
        } catch (NoSuchElementException ignored) {
            userRepository.save(userMapper.toJpaEntity(user));
        }
    }

    @Override
    public void checkIfUserExists(String username) {
        findByUsername(username);
    }
}
