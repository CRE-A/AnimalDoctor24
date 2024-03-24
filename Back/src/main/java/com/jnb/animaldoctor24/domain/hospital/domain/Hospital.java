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
@Table(name="hospital")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name="hn")
    private Integer hn;
    @Column(nullable = false,name="id")
    private String id;

    @Column(nullable = false, name = "role")
    private String role;

    @Column(nullable = false,name="title")
    private String title;

    @Column(nullable = false,name="contents")
    private String contents;

    @Column(nullable = false,name="tag")
    private String tag;

//    @Column(nullable = false, name="desc")
//    private String desc;

    @Column(nullable = false, name="imgPath")
    private String imgPath;

//    @Column(nullable = false, name="like")
//    private String like;

}
