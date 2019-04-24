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

    @ManyToMany
    private Set<Package> packages = Collections.emptySet();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Package> getPackages() {
        return packages;
    }

    public void setPackages(Set<Package> packages) {
        this.packages = packages;
    }
}
