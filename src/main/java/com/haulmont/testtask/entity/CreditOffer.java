package com.haulmont.testtask.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "credit_offer")
public class CreditOffer {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @Column(name = "credit_amount")
    private long creditAmount;

    @OneToOne()
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_offer_id")
    private List<Payment> paymentSchedule;

    public CreditOffer() {
    }

    public List<Payment> getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(List<Payment> paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(long creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

}
