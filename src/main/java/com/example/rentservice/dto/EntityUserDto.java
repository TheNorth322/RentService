package com.example.rentservice.dto;

import com.example.rentservice.dto.bank.BankDto;
import com.example.rentservice.entity.user.EntityUserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntityUserDto {
    private String name;
    private String supervisorFirstName;
    private String supervisorLastName;
    private String supervisorSurname;
    private AddressDto address;
    private BankDto bank;
    private String checkingAccount;
    private String itnNumber;

    public static EntityUserDto toDto(EntityUserEntity entity) {
        return EntityUserDto
                .builder()
                .name(entity.getName())
                .supervisorFirstName(entity.getSupervisorFirstName())
                .supervisorLastName(entity.getSupervisorLastName())
                .supervisorSurname(entity.getSupervisorSurname())
                .address(AddressDto.toDto(entity.getAddress()))
                .bank(BankDto.toDto(entity.getBank()))
                .checkingAccount(entity.getCheckingAccount())
                .itnNumber(entity.getItnNumber())
                .build();
    }
}
