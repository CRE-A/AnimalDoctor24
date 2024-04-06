package com.jnb.animaldoctor24.domain.hospital.domain;

import com.jnb.animaldoctor24.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name="likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "ln")     // like number (pk)
    private Long ln;
    @Column
    private Long hn;
    @Column
    private String email;
    @CreationTimestamp
    @Column(name= "creation_date", updatable = false)
    private LocalDateTime creationDate;

}
