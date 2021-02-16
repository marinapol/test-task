package com.haulmont.testtask.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "payment_amount")
    private float paymentAmount;

    @Column(name = "credit_amount")
    private float creditPaymentAmount;

    @Column(name = "interest_amount")
    private float interestPaymentAmount;

    public Payment() {
    }

    public Payment(LocalDate paymentDate, float paymentAmount, float creditPaymentAmount, float interestPaymentAmount) {
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.creditPaymentAmount = creditPaymentAmount;
        this.interestPaymentAmount = interestPaymentAmount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public float getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(float paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public float getCreditPaymentAmount() {
        return creditPaymentAmount;
    }

    public void setCreditPaymentAmount(float creditAmount) {
        this.creditPaymentAmount = creditAmount;
    }

    public float getInterestPaymentAmount() {
        return interestPaymentAmount;
    }

    public void setInterestPaymentAmount(float interestAmount) {
        this.interestPaymentAmount = interestAmount;
    }
}
