package com.heditra.userservice.mapper;

import com.heditra.userservice.dto.request.CreateUserRequest;
import com.heditra.userservice.dto.request.UpdateUserRequest;
import com.heditra.userservice.dto.response.UserResponse;
import com.heditra.userservice.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    
    public User toEntity(CreateUserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(request.getRole())
                .build();
    }
    
    public UserResponse toResponse(User entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .role(entity.getRole())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    public List<UserResponse> toResponseList(List<User> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public void updateEntityFromRequest(User entity, UpdateUserRequest request) {
        entity.setEmail(request.getEmail());
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        if (request.getRole() != null) {
            entity.setRole(request.getRole());
        }
    }
}
