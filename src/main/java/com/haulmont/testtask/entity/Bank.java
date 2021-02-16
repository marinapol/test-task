package com.haulmont.testtask.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "creditBank", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Credit> credits;

    @OneToMany(mappedBy = "clientBank", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Client> clients;

    public Bank() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bank(String name) {
        this.name = name;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
