package com.jnb.animaldoctor24.domain.member.domain;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name="members")
public class Member implements UserDetails {

    @Id
    @Column(nullable = false, name="email")
    private String email;
    @Column(nullable = false, name="first_name")
    private String firstName;
    @Column(nullable = false, name="last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(nullable = false,name="password")
    private String password;
    @ColumnDefault("0")
    private String status;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name="animal_name")
    private String animalName;
    @Column(name="animal_gender")
    private String animalGender;
    @Column(name="animal_breed")
    private String animalBreed;
    @Column(nullable = false, name = "image_path", length = 500)
    private String imagePath;
    @CreationTimestamp
    @Column(name= "creation_date")
    private Date creationDate;
    @UpdateTimestamp
    @Column(name= "update_date")
    private Date updateDate;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
}

