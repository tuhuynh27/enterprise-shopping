package com.mrhmt.server.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="order_details")
public class OrderDetail implements Serializable {
    @EmbeddedId
    private OrderDetailIdentity orderDetailIdentify;

    @NotNull
    @Min(value = 0)
    private double discount = 0;

    @NotNull
    @Min(value = 1)
    @Max(value = Long.MAX_VALUE, message = "Mus less than " + Long.MAX_VALUE)
    private int quantity = 1;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @Column(name="is_valid")
    @NotNull
    private boolean isValid = true;

    public OrderDetail(OrderDetailIdentity orderDetailIdentify, @NotNull double discount, @NotNull int quantity, Date modified, @NotNull boolean isValid) {
        this.orderDetailIdentify=orderDetailIdentify;
        this.discount=discount;
        this.quantity=quantity;
        this.modified=modified;
        this.isValid=isValid;
    }

    public OrderDetail() {
    }

    public OrderDetailIdentity getOrderDetailIdentify() {
        return orderDetailIdentify;
    }

    public void setOrderDetailIdentify(OrderDetailIdentity orderDetailIdentify) {
        this.orderDetailIdentify=orderDetailIdentify;
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
