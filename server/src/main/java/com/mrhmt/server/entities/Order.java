package com.mrhmt.server.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max = 150)
    private String description;

    @Column(name="user_id")
    private int userId;

    @Column(name="order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @Column(name="is_shipped")
    @NotNull
    private boolean isShipped = false;

    @Column(name="is_valid")
    @NotNull
    private boolean isValid = true;

    public Order(@NotNull @Size(max=150) String description, int userId, Date orderDate, Date modified, @NotNull boolean isShipped, @NotNull boolean isValid) {
        this.description=description;
        this.userId=userId;
        this.orderDate=orderDate;
        this.modified=modified;
        this.isShipped=isShipped;
        this.isValid=isValid;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId=userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate=orderDate;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified=modified;
    }

    public boolean isShipped() {
        return isShipped;
    }

    public void setShipped(boolean shipped) {
        isShipped=shipped;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid=valid;
    }
}
