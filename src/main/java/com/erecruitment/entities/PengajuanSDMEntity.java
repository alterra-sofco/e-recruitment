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

    @Column(name = "remark_staff", columnDefinition = "text", nullable = true)
    private String remarkStaff;

    @Column(name = "remark_hr", columnDefinition = "text", nullable = true)
    private String remarkHR;

    @Column(columnDefinition = "INT2", length = 1)
    private Short status;

    @Column(name = "number_required", columnDefinition = "integer default 0", nullable = true)
    private Integer numberRequired;

    @Column(name = "number_applicant", columnDefinition = "integer default 0", nullable = true)
    private Integer numberApplicant;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date deadline;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

/*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pengajuanSDMEntity")
    @JsonManagedReference(value = "companyUsers")
    private Set<PengajuanSDMSkillEntity> listSkill = new HashSet<>();
*/

}
