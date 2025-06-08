package com.bkopec.confintimes.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "topics")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String subject;

    @Column(name = "poster_name", nullable = false, length = 100)
    private String posterName;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_post_at")
    private LocalDateTime lastPostAt;

    @Column(name = "replies_count", nullable = false)
    private Integer repliesCount = 0;

    public Topic(String subject, String posterName) {
        this.subject = subject;
        this.posterName = posterName;
    }
}