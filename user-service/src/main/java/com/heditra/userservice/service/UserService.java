package com.heditra.userservice.service;

import com.heditra.userservice.dto.request.CreateUserRequest;
import com.heditra.userservice.dto.request.UpdateUserRequest;
import com.heditra.userservice.dto.response.UserResponse;
import com.heditra.userservice.model.UserRole;

import java.util.List;

public interface UserService {
    
    UserResponse createUser(CreateUserRequest request);
    
    UserResponse getUserById(Long id);
    
    UserResponse getUserByUsername(String username);
    
    UserResponse getUserByEmail(String email);
    
    List<UserResponse> getAllUsers();
    
    List<UserResponse> getUsersByRole(UserRole role);
    
    UserResponse updateUser(Long id, UpdateUserRequest request);
    
    void deleteUser(Long id);
}
