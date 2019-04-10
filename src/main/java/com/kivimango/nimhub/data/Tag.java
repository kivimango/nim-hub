package com.kivimango.nimhub.data;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "nh_tags")
class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tid", unique = true, nullable = false)
    private Long id;

    @Column(name = "tag_name", unique = true, nullable = false)
    private String name;

    private String password;

    @ManyToMany
    private Set<Package> packages = Collections.emptySet();
}
