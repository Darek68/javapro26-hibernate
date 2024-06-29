package ru.darek;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MyApp {
    private static final Log LOGGER = LogFactory.getLog(MyApp.class);
    private static Configuration configuration = new Configuration();
    private static SessionFactory sessionFactory = configuration.configure().buildSessionFactory();
    private static List<Client> clients = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("Hello world!");
        initBD();
    }

    private static void initBD() {
        Long l;
        LOGGER.info("Работает initBD");
        clients.add(new Client("Ketty"));
        clients.add(new Client("John"));
        clients.add(new Client("Sasha"));
        clients.add(new Client("Bill"));
        products.add(new Product("yogurt", 7.15));
        products.add(new Product("milk", 23.5));
        products.add(new Product("beer", 27.53));
        products.add(new Product("wine", 46.35));
        products.add(new Product("water", 11.28));
        clients.get(0).addProduct(products.get(0));
        clients.get(0).getProducts().add(products.get(2));
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
//            session.persist(client);
//            session.persist(product);
            session.persist(clients.get(0));
            session.persist(products.get(0));
            session.persist(products.get(2));
            //  T merged = session.merge(entity);
            transaction.commit();
            //  return merged;
        }
        l = clients.get(0).getId();
        clients.get(0);
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Client bdClient = null;
            System.out.println("bdClient before " + bdClient);
            bdClient = session.get(Client.class, l);
            System.out.println("bdClient after " + bdClient);
            transaction.commit();
        }
    }
}

/**
 * В базе данных необходимо реализовать возможность хранить информацию о покупателях (id, имя) и товарах (id, название, стоимость).
 * У каждого покупателя свой набор купленных товаров.
 * Необходимо написать консольное приложение, которое позволит:
 * посмотреть, какие товары покупал клиент,
 * посмотреть какие клиенты купили определенный товар,
 * удалить из базы товары/покупателей.
 * (*) Добавить детализацию по паре «покупатель — товар»: сколько стоил товар в момент покупки клиентом.
 */