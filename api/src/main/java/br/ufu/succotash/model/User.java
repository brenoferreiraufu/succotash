package br.ufu.succotash.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull private String fullName;
    @NotNull @Column(unique = true) private String username;
    @NotNull private String password;
    @NotNull @Enumerated(EnumType.STRING) private Role role;

    private User() {}

    public User(String fullName, String username, String password, Role role) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName != null && !fullName.isBlank()) this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null && !password.isBlank()) this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        if (role != null) this.role = role;
    }

    public String getId() {
        return id;
    }
}
