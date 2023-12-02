package com.example.rentservice.dto.user;

import com.example.rentservice.entity.user.UserEntity;
import com.example.rentservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private boolean emailVerified;
    private Role role;

    public static UserDto toDto(UserEntity entity) {
        return UserDto
                .builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .emailVerified(entity.isEmailVerified())
                .role(entity.getRole())
                .build();
    }
}
