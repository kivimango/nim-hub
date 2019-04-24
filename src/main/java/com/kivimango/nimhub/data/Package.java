package com.kivimango.nimhub.data;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "nh_packages")
public class Package extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User owner;

    @Column(name = "package_name", unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "license", length = 50)
    private String license;

    private String web;

    @Column(name = "package_version", length = 50)
    private String version;

    @ManyToMany(mappedBy = "packages", cascade = CascadeType.PERSIST)
    private Set<Tag> tags = Collections.emptySet();

    private Boolean hidden = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
