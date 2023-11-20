package com.example.rentservice.entity.user;

import com.example.rentservice.entity.AddressEntity;
import com.example.rentservice.entity.BankEntity;
import com.example.rentservice.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "entity_user")
public class EntityUserEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "supervisor_first_name")
    private String supervisorFirstName;

    @Column(name = "supervisor_last_name")
    private String supervisorLastName;

    @Column(name = "supervisor_surname")
    private String supervisorSurname;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    private BankEntity bank;

    @Column(name = "checking_account")
    private String checkingAccount;

    @Column(name = "itn_number")
    private String itnNumber;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
}
