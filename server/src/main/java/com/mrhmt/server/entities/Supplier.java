package com.mrhmt.server.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="suppliers")
public class Supplier implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max=50)
    private String name;

    @NotNull
    @Size(max=50)
    private String company;

    @NotNull
    @Size(max=150)
    private String address;

    @NotNull
    @Size(max=50)
    private String city;

    @NotNull
    @Size(max=25)
    private String phone;

    @NotNull
    @Size(max=25)
    private String fax;

    @NotNull
    @Column(name="is_valid")
    private boolean isValid;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    public Supplier(@NotNull @Size(max=50) String name, @NotNull @Size(max=50) String company, @NotNull @Size(max=150) String address, @NotNull @Size(max=50) String city, @NotNull @Size(max=25) String phone, @NotNull @Size(max=25) String fax, @NotNull boolean isValid, Date modified) {
        this.name=name;
        this.company=company;
        this.address=address;
        this.city=city;
        this.phone=phone;
        this.fax=fax;
        this.isValid=isValid;
        this.modified=modified;
    }

    public Supplier() {
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company=company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address=address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city=city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone=phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax=fax;
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
