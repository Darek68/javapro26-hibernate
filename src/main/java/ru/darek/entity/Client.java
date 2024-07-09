package ru.darek.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
//@Table(name = "PERSON")
@Data
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "clients", fetch = FetchType.LAZY)
//    private Set<Product> products = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "client_product",
            joinColumns = {@JoinColumn(name = "client_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private Set<Product> products = new HashSet<>();

    public Client(String name) {
        this.name = name;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public String toString() {
        return name;
    }
}
