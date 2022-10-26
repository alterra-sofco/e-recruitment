package com.erecruitment.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "submission")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long submissionId;

    private String coverLetter;

    @Column(name = "applied_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date appliedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    private User appliedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private PengajuanSDMEntity jobPosting;

    private String description;

    private StatusRecruitment status;
}
