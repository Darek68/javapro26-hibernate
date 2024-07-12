package ru.darek.service;

import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.darek.entity.Product;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public final class ProductService {

    private final SessionFactory sessionFactory;

    public List<Product> getAllProducts() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Product> cq = session.getCriteriaBuilder().createQuery(Product.class);
            cq.from(Product.class);
            return session.createQuery(cq).getResultList();
        }
    }

    public Optional<Product> getProductById(long clientId) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Product.class, clientId));
        }
    }

    public void deleteProduct(Product product) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(product);
            transaction.commit();
        }
    }

}
