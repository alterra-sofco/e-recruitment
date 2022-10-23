package com.erecruitment.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "applicant")
public class Applicant extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long applicantId;

    @OneToOne(fetch = FetchType.LAZY)
    private File avatar;

    private String bio;

    @Column(length = 200)
    private String portofolioURL;

    @OneToOne(fetch = FetchType.LAZY)
    private File cv;

    private String address;

    @Temporal(TemporalType.DATE)
    private Date dob;

    @OneToOne(fetch = FetchType.EAGER)
    private User ownedBy;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<SkillEntity> skills;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Experience> experiences;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Education> educations;

}
