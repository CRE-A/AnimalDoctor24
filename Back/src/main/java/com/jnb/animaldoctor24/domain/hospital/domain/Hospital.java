package com.jnb.animaldoctor24.domain.hospital.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "hospitals")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "hn")     // hospital number (pk)
    private Integer hn;
    @Column(nullable = false, name = "email")
    private String email;
    @Column(nullable = false, name = "role")
    private String role;
    @Column(nullable = false, name = "hospital_name")
    private String hospitalName;
    @Column(nullable = false, name = "location")
    private String location;
    @Column(nullable = false, name = "description")
    private String description;
    @Column(nullable = false, name = "businessDay")
    private String businessDay;
    @Column(nullable = false, name = "businessHour")
    private String businessHour;
    @Column(nullable = false, name = "lunch_hour")
    private String lunchHour;
    @Column(nullable = false, name = "hospital_phoneNumber")
    private String hospitalPhoneNumber;
    @Column(nullable = false, name = "image_path", length = 500)
    private String imagePath;
    @CreationTimestamp
    @Column(name= "creation_date")
    private Date creationDate;
    @UpdateTimestamp
    @Column(name= "update_date")
    private Date updateDate;

}
