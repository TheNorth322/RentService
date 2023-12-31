package com.example.rentservice.entity.user;

import com.example.rentservice.entity.AddressEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "migration_services")
@ToString(exclude = {"address", "passports"})
@EqualsAndHashCode(exclude = {"address", "passports"})
public class MigrationServiceEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @OneToMany(mappedBy = "migrationService", cascade = CascadeType.ALL)
    private Set<PassportEntity> passports;

    public MigrationServiceEntity addPassport(PassportEntity passport) {
        this.passports.add(passport);
        passport.setMigrationService(this);
        return this;
    }
}
