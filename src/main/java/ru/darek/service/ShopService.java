package ru.darek.service;

import jakarta.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import ru.darek.MyApp;
import ru.darek.entity.Client;
import ru.darek.entity.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ShopService {
    private static final Log LOGGER = LogFactory.getLog(ShopService.class);
    private  Configuration configuration;
    private  SessionFactory sessionFactory;
    private  List<Client> clients;
    private  List<Product> products;

    public ShopService() {
        this.configuration = new Configuration();
        this.sessionFactory = this.configuration.configure().buildSessionFactory();
        this.clients = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Set<Product> getProductsById(int idx){
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Set<Product> products = session.get(Client.class, idx).getProducts();
            transaction.commit();
            return products;
        }
    }
    public Set<Product> getProductsById1(int idx){
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            String SQL = "select p.* from client c, client_product cp, product p where p.id = cp.products_id and cp.client_id = c.id and c.id = :clientid";
            NativeQuery<Product> query = session.createNativeQuery(SQL, Product.class);
            query.setParameter("clientid", idx);
            Set<Product> products = new HashSet<Product>(query.list());

            transaction.commit();
            return products;
        }
    }

    public Set<Client> getClientsByProduct(int idx){
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Set<Client> clients = session.get(Product.class, idx).getClients();
            transaction.commit();
            return clients;
        }
    }
    public Set<Client> getClientsByProduct1(int idx){
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            String SQL = "select c.* from client c, client_product cp, product p where p.id = cp.products_id and cp.client_id = c.id and p.id = :productid";
            NativeQuery<Client> query = session.createNativeQuery(SQL, Client.class);
            query.setParameter("productid", idx);
            Set<Client> clients = new HashSet<Client>(query.list());
            transaction.commit();
            return clients;
        }
    }

    public void deleteClientById(int idx){
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Client delClient = session.load(Client.class, idx);
            session.delete(delClient);
            transaction.commit();
            transaction.commit();
        }
    }

    public void deleteProductById(int idx){
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Client delClient = session.load(Client.class, idx);
            session.delete(delClient);
            transaction.commit();
            transaction.commit();
        }
    }
    public void initBD() {
        Long l;
        LOGGER.info("Работает initBD");
        clients.add(new Client("Ketty"));
        clients.add(new Client("John"));
        clients.add(new Client("Sasha"));
        clients.add(new Client("Bill"));
        clients.add(new Client("Eduard"));
        products.add(new Product("yogurt", 7.15));
        products.add(new Product("milk", 23.5));
        products.add(new Product("beer", 27.53));
        products.add(new Product("wine", 46.35));
        products.add(new Product("water", 11.28));

        clients.get(0).addProduct(products.get(0));
        clients.get(0).addProduct(products.get(2));
        clients.get(1).addProduct(products.get(0));
        clients.get(1).addProduct(products.get(1));
        clients.get(1).addProduct(products.get(2));
        clients.get(2).addProduct(products.get(3));
        clients.get(3).addProduct(products.get(4));
        clients.get(4).addProduct(products.get(1));
        clients.get(4).addProduct(products.get(2));
        clients.get(4).addProduct(products.get(3));
        clients.get(4).addProduct(products.get(4));

        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            for (Client client: clients) {
                session.persist(client);
            }
            transaction.commit();
        }
    }

}
