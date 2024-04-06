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
@Table(name="reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name="rn")       // review number (pk)
    private Long rn;
    @Column(nullable = false,name="hn")
    private Long hn;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false,name="role")
    private String role;

    @Column(nullable = false,name="title")
    private String title;

    @Column(nullable = false,name="contents")
    private String contents;
    @Column(nullable = false, name = "image_path", length = 500)
    private String imagePath;
    @CreationTimestamp
    @Column(name= "creation_date", updatable = false)
    private Date creationDate;
    @UpdateTimestamp
    @Column(name= "update_date")
    private Date updateDate;

}
