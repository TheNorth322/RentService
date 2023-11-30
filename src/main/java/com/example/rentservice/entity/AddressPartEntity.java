package com.example.rentservice.entity;

import com.example.rentservice.enums.AddressLevel;
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
@Table(name = "address_parts")
public class AddressPartEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "object_guid")
    private String objectGuid;

    private String name;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "full_type_name")
    private String fullTypeName;

    private AddressLevel level;

    @ManyToMany(mappedBy = "addressParts")
    private Set<AddressEntity> addresses;
}
