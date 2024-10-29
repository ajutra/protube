package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.UserMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.UserRepository;
import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.port.out.GetUserPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements GetUserPort, StoreUserPort {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    Optional<UserJpaEntity> findByUsername(String username) {
        return userRepository.findById(username);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findById(username)
                .map(userMapper::toDomain)
                .orElseThrow(() -> new NoSuchElementException("User not found")
        );
    }

    @Override
    public void storeUser(User user) {
        if (userRepository.existsById(user.username())) {
            throw new IllegalArgumentException("User already exists");
        }

        userRepository.save(userMapper.toJpaEntity(user));
    }

    @Override
    public void checkIfUserExists(String username) {
        if (userRepository.findById(username).isEmpty()) {
            throw new NoSuchElementException("User not found with username: " + username);
        }
    }
}
