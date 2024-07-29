package ru.darek.entity;

//import jakarta.persistence.*;
//import lombok.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "products")
@NoArgsConstructor
public class Product {
    public static final Comparator<Product> SORT_BY_ID_ASC = Comparator.comparingLong(one -> one.id);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    // TODO Для ManyToMany REMOVE ставить нельзя, потому что при удалении очистятся все связные таблицы
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "products",
            cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private Set<Client> clients;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
      //  this.clients = new HashSet<>();
    }
    public void addClient(Client client) {
        if (clients == null)
            clients = new HashSet<>();

        clients.add(client);
    }
    public void removeClient(Client client) {
        if (client != null)
            clients.remove(client);
    }
}
