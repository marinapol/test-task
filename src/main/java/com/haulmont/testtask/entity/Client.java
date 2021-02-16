package com.haulmont.testtask.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "passport_id")
    private String passportID;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank clientBank;

    public Client() {
    }

    public Bank getClientBank() {
        return clientBank;
    }

    public void setClientBank(Bank clientBank) {
        this.clientBank = clientBank;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }
}
