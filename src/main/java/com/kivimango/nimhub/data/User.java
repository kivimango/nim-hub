package com.kivimango.nimhub.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "nh_users")
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", unique = true, nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    private String password;

    @OneToMany(mappedBy = "owner")
    private Set<Package> packages = Collections.emptySet();

    private Boolean banned = false;

    private Integer banReason = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Package> getPackages() {
        return packages;
    }

    public void setPackages(Set<Package> packages) {
        this.packages = packages;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Integer getBanReason() {
        return banReason;
    }

    public void setBanReason(Integer banReason) {
        this.banReason = banReason;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        User user = (User) object;
        return id.equals(user.id) &&
                username.equals(user.username) &&
                email.equals(user.email) &&
                java.util.Objects.equals(password, user.password) &&
                java.util.Objects.equals(packages, user.packages) &&
                java.util.Objects.equals(banned, user.banned) &&
                java.util.Objects.equals(banReason, user.banReason);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), id, username, email, password, packages, banned, banReason);
    }
}
