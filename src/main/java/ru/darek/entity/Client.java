package ru.darek.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "clients")
@NoArgsConstructor
public class Client {

    public static final Comparator<Client> SORT_BY_ID_ASC = Comparator.comparingLong(one -> one.id);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // TODO Для ManyToMany REMOVE ставить нельзя, потому что при удалении очистятся все связные таблицы
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private Set<Product> products = new HashSet<>();

    public Client(String name, Set<Product> products) {
        this.name = name;
        this.products.addAll(products);

        // TODO не забываем обновлять сами продукты
        products.forEach(product -> product.addClient(this));
    }

}
