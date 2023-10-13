package com.example.rentservice.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "individual_user")
public class IndividualUserEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(mappedBy = "user")
    private Set<PassportEntity> passports;

    public IndividualUserEntity addPassport(PassportEntity passport) {
        passports.add(passport);
        passport.setUser(this);
        return this;
    }

    public IndividualUserEntity deletePassport(PassportEntity passport) {
        passports.remove(passport);
        passport.setUser(null);
        return this;
    }
}
