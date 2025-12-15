package com.heditra.userservice.service.impl;

import com.heditra.events.user.UserCreatedEvent;
import com.heditra.events.user.UserDeletedEvent;
import com.heditra.events.user.UserUpdatedEvent;
import com.heditra.userservice.dto.request.CreateUserRequest;
import com.heditra.userservice.dto.request.UpdateUserRequest;
import com.heditra.userservice.dto.response.UserResponse;
import com.heditra.userservice.exception.UserAlreadyExistsException;
import com.heditra.userservice.exception.UserNotFoundException;
import com.heditra.userservice.mapper.UserMapper;
import com.heditra.userservice.model.User;
import com.heditra.userservice.model.UserRole;
import com.heditra.userservice.repository.UserRepository;
import com.heditra.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final String USER_CREATED_TOPIC = "user-created";
    private static final String USER_UPDATED_TOPIC = "user-updated";
    private static final String USER_DELETED_TOPIC = "user-deleted";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creating new user with username: {}", request.getUsername());

        validateUserUniqueness(request);

        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        
        publishUserCreatedEvent(savedUser);

        log.info("User created successfully with ID: {}", savedUser.getId());
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public UserResponse getUserById(Long id) {
        log.debug("Fetching user by ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponse(user);
    }

    @Override
    @Cacheable(value = "users", key = "'username:' + #username")
    public UserResponse getUserByUsername(String username) {
        log.debug("Fetching user by username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return userMapper.toResponse(user);
    }

    @Override
    @Cacheable(value = "users", key = "'email:' + #email")
    public UserResponse getUserByEmail(String email) {
        log.debug("Fetching user by email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        log.debug("Fetching all users");
        List<User> users = userRepository.findAll();
        return userMapper.toResponseList(users);
    }

    @Override
    public List<UserResponse> getUsersByRole(UserRole role) {
        log.debug("Fetching users by role: {}", role);
        List<User> users = userRepository.findByRole(role);
        return userMapper.toResponseList(users);
    }

    @Override
    @Transactional
    @CachePut(value = "users", key = "#id")
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        log.info("Updating user with ID: {}", id);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        userMapper.updateEntityFromRequest(existingUser, request);

        User updatedUser = userRepository.save(existingUser);
        publishUserUpdatedEvent(updatedUser);

        log.info("User updated successfully with ID: {}", id);
        return userMapper.toResponse(updatedUser);
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        userRepository.delete(user);
        publishUserDeletedEvent(user);

        log.info("User deleted successfully with ID: {}", id);
    }

    private void validateUserUniqueness(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + request.getEmail());
        }
    }

    private void publishUserCreatedEvent(User user) {
        UserCreatedEvent event = UserCreatedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .aggregateId(user.getId().toString())
                .version(1)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
        
        kafkaTemplate.send(USER_CREATED_TOPIC, user.getId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("UserCreatedEvent published successfully for user ID: {}", user.getId());
                    } else {
                        log.error("Failed to publish UserCreatedEvent for user ID: {}", user.getId(), ex);
                    }
                });
    }

    private void publishUserUpdatedEvent(User user) {
        UserUpdatedEvent event = UserUpdatedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .aggregateId(user.getId().toString())
                .version(1)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
        
        kafkaTemplate.send(USER_UPDATED_TOPIC, user.getId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("UserUpdatedEvent published successfully for user ID: {}", user.getId());
                    } else {
                        log.error("Failed to publish UserUpdatedEvent for user ID: {}", user.getId(), ex);
                    }
                });
    }

    private void publishUserDeletedEvent(User user) {
        UserDeletedEvent event = UserDeletedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .aggregateId(user.getId().toString())
                .version(1)
                .userId(user.getId())
                .build();
        
        kafkaTemplate.send(USER_DELETED_TOPIC, user.getId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("UserDeletedEvent published successfully for user ID: {}", user.getId());
                    } else {
                        log.error("Failed to publish UserDeletedEvent for user ID: {}", user.getId(), ex);
                    }
                });
    }
}

