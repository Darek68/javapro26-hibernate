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
    public static void main(String[] args) {

        System.out.println("Hello world!");
        ShopService shopService = new ShopService();
        shopService.initBD();
        clients = shopService.getClients();
        products = shopService.getProducts();
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
        System.out.println("client1 - посмотреть, какие товары покупал клиент 1..5");
        System.out.println("product1 - посмотреть какие клиенты купили товар 1..5");
        System.out.println("delclient1 - удалить из базы покупателя 1..5");
        System.out.println("delproduct1 - удалить из базы товар 1..5");
        while (true) {
            command = scanner.nextLine().trim();
            select = command.substring(0,4);
            if (command.startsWith("exit")) return;
            idx = Integer.parseInt(command.substring(command.length() - 1));
            switch (select) {
                case  ("exit"):
                    return;
                case  ("clie"):
                 //   System.out.println(clients.get(idx - 1).getName() + "'s products: " + shopService.getProductsById(idx));
                    System.out.println(clients.get(idx - 1).getName() + "'s products: " + shopService.getProductsById1(idx));
                    break;
                case ("prod"):
                 //   System.out.println(products.get(idx - 1).getName() + "'s clients: " + shopService.getClientsByProduct(idx));
                    System.out.println(products.get(idx - 1).getName() + "'s clients: " + shopService.getClientsByProduct1(idx));
                    break;
                case ("delc"):
                    shopService.deleteClientById(idx);
                    System.out.println("All clients:\n");
                    for (Client c: clients) {
                        System.out.println(c.getName());
                    }
                    break;
                case ("delp"):
                    shopService.deleteProductById(idx);
                    System.out.println("All products:\n");
                    for (Product p: products) {
                        System.out.println(p);
                    }
                    break;
                default:
                    System.out.println("Команда не верна!");;
                    break;
            }
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