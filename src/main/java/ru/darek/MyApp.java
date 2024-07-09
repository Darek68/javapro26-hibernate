package ru.darek;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.darek.entity.Client;
import ru.darek.entity.Product;
import ru.darek.service.ShopService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@RequiredArgsConstructor
public class MyApp {
    private static final Log LOGGER = LogFactory.getLog(MyApp.class);

    //    private static Configuration configuration = new Configuration();
//    private static SessionFactory sessionFactory = configuration.configure().buildSessionFactory();
    private static List<Client> clients = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();
//private static ShopService shopService;
    public static void main(String[] args) {

        System.out.println("Hello world!");
        ShopService shopService = new ShopService();
        shopService.initBD();
        clients = shopService.getClients();
        products = shopService.getProducts();
     //   System.out.println(shopService.getProductsByClient(clients.get(2)).size());
        consoleService(shopService);

    }
/*
 посмотреть, какие товары покупал клиент,
 посмотреть какие клиенты купили определенный товар,
 удалить из базы товары/покупателей.
 */
    public static void consoleService(ShopService shopService) {
        Scanner scanner = new Scanner(System.in);
        String command;
        String  select;
        int idx;
        Client client;
        Product product;
        System.out.println("client1 - посмотреть, какие товары покупал клиент 1..5");
        System.out.println("product1 - посмотреть какие клиенты купили товар 1..5");
        System.out.println("delclient1 - удалить из базы покупателя 1..5");
        System.out.println("delproduct1 - удалить из базы товар 1..5");
        while (true) {
            command = scanner.nextLine();
            command = command.trim();
            select = command.substring(0,4);
            System.out.println("select: " + select);
            if (command.startsWith("exit")) return;
            switch (select) {
                case  ("exit"):
                    return;
                case  ("clie"):
                    idx = Integer.parseInt(command.substring(6,7));
//                    client = clients.get(idx);
//                    System.out.println(client.getName() + "'s products: " + shopService.getProductsByClient(client));
                    System.out.println(clients.get(idx - 1).getName() + "'s products: " + shopService.getProductsById(idx));
                    break;
                case ("prod"):
                    System.out.println(command.substring(7,8));
                    idx = Integer.parseInt(command.substring(7,8));
                    System.out.println("idx: " + idx);
                    break;
                case ("delc"):
                    idx = Integer.parseInt(command.substring(9,10));
                    System.out.println("idx: " + idx);
                    break;
                case ("delp"):
                    idx = Integer.parseInt(command.substring(10,11));
                    System.out.println("idx: " + idx);
                    break;
                default:
                    System.out.println("Команда не верна!");;
                    break;
            }
        }
    }


 /*   private static void initBD2() {
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
        clients.get(0).addProduct(products.get(2));
        //  clients.get(0).getProducts().add(products.get(2));

//        products.get(0).getClients().add(clients.get(0));
//        products.get(2).getClients().add(clients.get(0));


//        System.out.println("client0 before commit: " + clients.get(0));
//        LOGGER.fatal("client0 before commit: " + clients.get(0));
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
//            session.persist(client);
//            session.persist(product);
//            session.persist(products.get(0));
//            session.persist(products.get(2));
            session.persist(clients.get(0));

            //   session.persist(products.get(2));
            //  T merged = session.merge(entity);
            transaction.commit();
            System.out.println("client0 after commit: " + clients.get(0));
            LOGGER.fatal("client0 after commit: " + clients.get(0));
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
    } */
//    public Set<Product> getProductsOfClient(Long clientId) {
//        try (Session session = sessionFactory.getCurrentSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.persist(clients.get(0));
//            transaction.commit();
//
//
//        }
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        Client client = entityManager.find(Client.class, clientId);
//        return client.getProducts();
//    }
//    public Set<Client> getClientsOfProduct(Long productId) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        Product product = entityManager.find(Product.class, productId);
//        return product.getClients();
//    }
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