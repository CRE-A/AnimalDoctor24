package com.jnb.animaldoctor24.domain.hospital.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name="review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name="cn")
    private Integer cn;
    @Column(nullable = false,name="bn")
    private Integer bn;

    @Column(nullable = false, name = "id")
    private String id;

    @Column(nullable = false,name="role")
    private String role;

    @Column(nullable = false,name="title")
    private String title;

    @Column(nullable = false,name="contents")
    private String contents;
    @Column(name="imgPath")
    private String imgPath;

}
