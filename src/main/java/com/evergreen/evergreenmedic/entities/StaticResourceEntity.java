package com.evergreen.evergreenmedic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "static_resources")
public class StaticResourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private short id;

    @Column(name = "name", updatable = false, nullable = false)
    private String name;

    @Column(name = "content_type", updatable = false, nullable = true)
    private String contentType;

    @Column(name = "content_encoding", updatable = false, nullable = true)
    private String contentEncoding;

    @Column(name = "size", updatable = false, nullable = false)
    private String size;

    @Column(name = "path", nullable = false, updatable = true)
    private String path;


}
