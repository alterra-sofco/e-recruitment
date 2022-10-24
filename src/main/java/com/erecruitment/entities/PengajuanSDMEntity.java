package com.erecruitment.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "pengajuan_sdm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PengajuanSDMEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pengajuan")
    private Long idPengajuan;

    @Column(length = 125)
    private String posisi;

    @Column(columnDefinition = "text", nullable = true)
    private String description;

    @Column(columnDefinition = "text", nullable = true)
    private String remark_staff;

    @Column(columnDefinition = "INT2", length = 1)
    private Short status;

    @Column(name ="number_required",  columnDefinition = "integer default 0", nullable = true)
    private Integer numberRequired;

    @Column(name ="number_applicant",  columnDefinition = "integer default 0", nullable = true)
    private Integer numberApplicant;

    @Column(nullable = true)
    private Date deadline;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;


}
