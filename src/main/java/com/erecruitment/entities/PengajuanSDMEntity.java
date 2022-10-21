package com.erecruitment.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "pengajuan_sdm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

/*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pengajuanSDMEntity")
    @JsonManagedReference(value = "companyUsers")
    private Set<PengajuanSDMSkillEntity> listSkill = new HashSet<>();
*/

    //staff relation by ID, mandatory when it needed
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_job", nullable = false)
    // @OnDelete(action = OnDeleteAction.CASCADE) an option
    @JsonIgnore
    private Staff staff;

}
