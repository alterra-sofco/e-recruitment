package com.erecruitment.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "staff")
public class Staff {

    @Id
    //primary key's naming to avoid default by the system
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "staff_generator")
    private Long staffId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column (nullable = false)
    private Long departmentId;
}
