package com.mrhmt.server.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="categories")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Size(max=50)
    @NotNull
    private String name;

    @NotNull
    @Column(name="is_valid")
    private boolean isValid = true;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    public Category(@Size(max=50) @NotNull String name, @NotNull boolean isValid, Date modified) {
        this.name=name;
        this.isValid=isValid;
        this.modified=modified;
    }

    public Category() {
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

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid=valid;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified=modified;
    }
}
