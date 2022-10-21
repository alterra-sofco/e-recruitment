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
public class Staff implements Serializable {

    @Id
    //primary key's naming to avoid default by the system
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "staff_generator")
    private Long staffId;

    @Column (nullable = false)
    private Long userId;

    @Column (nullable = false)
    private Long departmentId;
}
