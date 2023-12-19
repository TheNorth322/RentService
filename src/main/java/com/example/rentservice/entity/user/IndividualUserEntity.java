package com.example.rentservice.entity.user;

import com.example.rentservice.entity.AddressEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"user", "passports", "activePassport"})
@ToString(exclude = {"user", "passports", "activePassport"})
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "active_passport_id", referencedColumnName = "id")
    private PassportEntity activePassport;
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
