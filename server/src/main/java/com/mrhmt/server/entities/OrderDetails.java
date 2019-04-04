package com.mrhmt.server.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="order_details")
public class OrderDetails implements Serializable {
    @EmbeddedId
    private OrderDetailsIdentify orderDetailsIdentify;

    @NotNull
    private double discount;

    @NotNull
    private int quantity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @Column(name="is_valid")
    @NotNull
    private boolean isValid;

    public OrderDetails(OrderDetailsIdentify orderDetailsIdentify, @NotNull double discount, @NotNull int quantity, Date modified, @NotNull boolean isValid) {
        this.orderDetailsIdentify=orderDetailsIdentify;
        this.discount=discount;
        this.quantity=quantity;
        this.modified=modified;
        this.isValid=isValid;
    }

    public OrderDetails() {
    }

    public OrderDetailsIdentify getOrderDetailsIdentify() {
        return orderDetailsIdentify;
    }

    public void setOrderDetailsIdentify(OrderDetailsIdentify orderDetailsIdentify) {
        this.orderDetailsIdentify=orderDetailsIdentify;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount=discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity=quantity;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified=modified;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid=valid;
    }
}
