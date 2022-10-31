package com.erecruitment.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "master_skill", uniqueConstraints = @UniqueConstraint(name = "UniqueSkillName", columnNames = {"skill_name"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE master_skill set is_deleted=true where skill_id=?")
@Where(clause = " is_deleted is false ")
@SQLInsert(sql = "insert into master_skill (created_at, is_deleted, updated_at, skill_name) values (?, ?, ?, ?) " +
        "ON CONFLICT (skill_name) DO UPDATE set updated_at=$3, skill_name=$4 RETURNING skill_id")
public class SkillEntity extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skillId;

    @Column(length = 100, name = "skill_name", nullable = false)
    private String skillName;

}
