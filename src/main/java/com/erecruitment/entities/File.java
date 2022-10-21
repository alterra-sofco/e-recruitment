package com.erecruitment.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "file")
@Getter
@Setter
public class File extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    private String type;

    @Lob
    @Column(nullable = false)
    private byte[] data;
}
