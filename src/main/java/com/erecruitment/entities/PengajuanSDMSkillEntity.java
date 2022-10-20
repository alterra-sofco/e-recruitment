package com.erecruitment.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pengajuan_sdm_skill_required")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PengajuanSDMSkillEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "skill_id")
    private Long skillId;

    @Column(length = 100, name = "skill_name", nullable = false)
    private String skillName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pengajuan_id", nullable = false)
    private PengajuanSDMEntity pengajuanSDMEntity;

}
