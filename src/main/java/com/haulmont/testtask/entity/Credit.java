package com.haulmont.testtask.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "credit")
public class Credit {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @Column(name = "limit")
    private long limit;

    @Column(name = "interest_rate")
    private double interestRate;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank creditBank;

    public Credit() {
    }

    public Bank getCreditBank() {
        return creditBank;
    }

    public void setCreditBank(Bank creditBank) {
        this.creditBank = creditBank;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getLimit() {
        return limit;
    }

    public String getStringInterestRate() {
        return interestRate + " %";
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
