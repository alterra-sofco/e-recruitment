package com.erecruitment.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "staff")
public class Staff {

    @Id
    //primary key's naming to avoid default by the system
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "staff_generator")
    private Long staffId;

    // Unidirectional
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "staff_user_id")
    private User user;

    @Column(nullable = false)
    private Long departmentId;
}
