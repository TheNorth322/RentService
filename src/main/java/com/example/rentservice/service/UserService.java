package com.example.rentservice.service;

import com.example.rentservice.dto.agreement.AgreementDto;
import com.example.rentservice.dto.user.UserDto;
import com.example.rentservice.entity.room.RoomEntity;
import com.example.rentservice.entity.user.UserEntity;
import com.example.rentservice.exception.auth.UserNotFoundException;
import com.example.rentservice.exception.room.RoomNotFoundException;
import com.example.rentservice.repository.RoomRepository;
import com.example.rentservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    public UserDto getUserByUsername(String username) throws UserNotFoundException {
        return UserDto.toDto(userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    public List<AgreementDto> getUserAgreements(String username) throws UserNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        return user.getAgreements()
                .stream()
                .map(AgreementDto::toDto)
                .toList();
    }
}
