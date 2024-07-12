package ru.darek.service;

import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.darek.entity.Client;
import ru.darek.entity.Product;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public final class ClientService {

    private final SessionFactory sessionFactory;

    public void save(Collection<Client> clients) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            clients.forEach(session::persist);
            transaction.commit();
        }
    }

    public List<Client> getAllClients() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Client> cq = session.getCriteriaBuilder().createQuery(Client.class);
            cq.from(Client.class);
            return session.createQuery(cq).getResultList();
        }
    }

    public Optional<Client> getClientById(long clientId) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Client.class, clientId));
        }
    }

    public void deleteClient(long clientId) {
        try (Session session = sessionFactory.openSession()) {
            Client client = session.get(Client.class, clientId);

            if (client != null) {
                Transaction transaction = session.beginTransaction();
                session.remove(client);
                transaction.commit();
            }
        }
    }

}
