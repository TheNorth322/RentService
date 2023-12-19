package com.example.rentservice.service;

import com.example.rentservice.dto.EntityUserDto;
import com.example.rentservice.dto.agreement.AgreementDto;
import com.example.rentservice.dto.room.UserRoomDto;
import com.example.rentservice.dto.user.UserDto;
import com.example.rentservice.entity.agreement.AgreementEntity;
import com.example.rentservice.entity.room.RoomEntity;
import com.example.rentservice.entity.room.UserRoomEntity;
import com.example.rentservice.entity.user.UserEntity;
import com.example.rentservice.exception.UserIsNotEntityException;
import com.example.rentservice.exception.UserRoomsNotFoundException;
import com.example.rentservice.exception.auth.UserNotFoundException;
import com.example.rentservice.exception.room.RoomNotFoundException;
import com.example.rentservice.repository.RoomRepository;
import com.example.rentservice.repository.UserRepository;
import com.example.rentservice.repository.UserRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRoomRepository userRoomRepository;

    public UserDto getUserByUsername(String username) throws UserNotFoundException {
        return UserDto.toDto(userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    public List<AgreementDto> getUserAgreements(String username) throws UserNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        Set<AgreementEntity> agreements = user.getAgreements();

        return agreements
                .stream()
                .map(AgreementDto::toDto)
                .toList();
    }

    public EntityUserDto getUserEntityInfo(String username) throws UserNotFoundException, UserIsNotEntityException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getEntityUser() == null)
            throw new UserIsNotEntityException("User is not entity");

        return EntityUserDto.toDto(user.getEntityUser());
    }

    public List<UserRoomDto> getUserRooms(String username) throws UserNotFoundException, UserRoomsNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<UserRoomEntity> userRooms = userRoomRepository.findById_UserId(user.getId());

        if (userRooms.isEmpty())
            throw new UserRoomsNotFoundException("User rooms not found");

        return userRooms.stream().map(UserRoomDto::toDto).toList();
    }

    public String deleteUserRoom(String username, Long roomId) throws UserNotFoundException, UserRoomsNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        UserRoomEntity userRoom = userRoomRepository.findById_UserIdAndId_RoomId(user.getId(), roomId).orElseThrow(() -> new UserRoomsNotFoundException("User room not found"));
        userRoomRepository.delete(userRoom);
        return "User room was successfully deleted";
    }
}
