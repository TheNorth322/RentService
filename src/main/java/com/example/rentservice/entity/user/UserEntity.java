package com.example.rentservice.entity.user;

import com.example.rentservice.entity.agreement.AgreementEntity;
import com.example.rentservice.entity.auth.EmailVerificationTokenEntity;
import com.example.rentservice.entity.auth.PasswordResetTokenEntity;
import com.example.rentservice.entity.room.RoomEntity;
import com.example.rentservice.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String email;

    @ManyToMany
    @JoinTable(
            name = "user_rooms",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private Set<RoomEntity> cart_rooms;

    @Column(name = "email_verified")
    private boolean emailVerified;

    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<AgreementEntity> agreements;

    @OneToOne(mappedBy = "user")
    private IndividualUserEntity individualUser;

    @OneToOne(mappedBy = "user")
    private EntityUserEntity entityUser;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PasswordResetTokenEntity passwordResetToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private EmailVerificationTokenEntity emailVerificationToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserEntity addAgreement(AgreementEntity agreement) {
        agreement.setUser(this);
        this.agreements.add(agreement);
        return this;
    }
}
