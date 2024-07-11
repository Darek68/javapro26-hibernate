package ru.darek.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
//@Table(name = "GOODS")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    @ManyToMany(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
 /*   @JoinTable(name = "product_client",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")) */
    private Set<Client> clients;

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
        this.clients = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
