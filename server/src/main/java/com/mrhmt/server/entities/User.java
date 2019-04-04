package com.mrhmt.server.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String name;
    private String password;
    private String email;
    private String role;
    private boolean active = true;
    @CreationTimestamp
    @Column(name="created_date")
    private Timestamp createdDate;
    @UpdateTimestamp
    @Column(name="updated_date")
    private Timestamp updatedDate;

    public User(String name, String password, String email, String role, boolean active, Timestamp createdDate, Timestamp updatedDate) {
        this.name=name;
        this.password=password;
        this.email=email;
        this.role=role;
        this.active=active;
        this.createdDate=createdDate;
        this.updatedDate=updatedDate;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role=role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active=active;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate=createdDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate=updatedDate;
    }
}
