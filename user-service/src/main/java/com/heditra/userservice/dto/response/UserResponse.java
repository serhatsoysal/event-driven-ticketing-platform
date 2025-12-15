package com.heditra.userservice.dto.response;

import com.heditra.userservice.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
