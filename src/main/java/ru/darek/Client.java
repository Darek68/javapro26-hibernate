package ru.darek;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PERSON")
@Data
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "clients", fetch = FetchType.EAGER)
    private Set<Product> products = new HashSet<>();

    public Client(String name) {
        this.name = name;
    }

    public void addProduct(Product product) {
        products.add(product);
    }


}
