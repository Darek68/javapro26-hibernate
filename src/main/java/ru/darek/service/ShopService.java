package ru.darek.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.darek.MyApp;
import ru.darek.entity.Client;
import ru.darek.entity.Product;

import java.util.ArrayList;
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

    public Set<Product> getProductsByClient(Client client){
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            System.out.println("before " + client.getId());
            Set<Product> products = session.get(Client.class, client.getId()).getProducts();
            System.out.println("after " + session.get(Client.class, client.getId()));
            transaction.commit();
            return products;
        }
    }
    public Set<Product> getProductsById(int idx){
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Set<Product> products = session.get(Client.class, idx).getProducts();
            transaction.commit();
            return products;
        }
    }
    public Set<Client> getClientsByProduct(Product product){
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Set<Client> clients = session.get(Product.class, product.getId()).getClients();
            transaction.commit();
            return clients;
        }
    }
    public void deleteClient(Client client){
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Client delClient = session.load(Client.class, client.getId());
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


        //  clients.get(0).getProducts().add(products.get(2));

//        products.get(0).getClients().add(clients.get(0));
//        products.get(2).getClients().add(clients.get(0));


//        System.out.println("client0 before commit: " + clients.get(0));
//        LOGGER.fatal("client0 before commit: " + clients.get(0));
     /*   try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
//            session.persist(client);
//            session.persist(product);
//            session.persist(products.get(0));
//            session.persist(products.get(2));
            session.persist(clients.get(0));
            session.persist(clients.get(1));

            //   session.persist(products.get(2));
            //  T merged = session.merge(entity);
            transaction.commit();
            System.out.println("client0 after commit: " + clients.get(0));
            LOGGER.fatal("client0 after commit: " + clients.get(0));
            //  return merged;
        } */
     /*   l = clients.get(0).getId();
        clients.get(0);
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Client bdClient = null;
            System.out.println("bdClient before " + bdClient);
            bdClient = session.get(Client.class, l);
            System.out.println("bdClient after " + bdClient);
            transaction.commit();
        } */
    }

}
