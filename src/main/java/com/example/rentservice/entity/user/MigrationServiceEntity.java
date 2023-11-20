package com.example.rentservice.entity.user;

import com.example.rentservice.entity.AddressEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "migration_services")
public class MigrationServiceEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @OneToMany(mappedBy = "migrationService")
    private Set<PassportEntity> passports;

    public MigrationServiceEntity addPassport(PassportEntity passport) {
        this.passports.add(passport);
        passport.setMigrationService(this);
        return this;
    }
}
