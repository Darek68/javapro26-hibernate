package ru.darek;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@RequiredArgsConstructor
public class Main {
    private static Configuration configuration = new Configuration();
   // private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static SessionFactory sessionFactory = configuration.configure().buildSessionFactory();

    /*   {
          System.out.println("Блок инициализации!");
          Client client1 = new Client("John");
          Client client2 = new Client("Sasha");
          Client client3 = new Client("Bill");
          Product product1 = new Product("milk",23.5);
          Product product2 = new Product("beer",27.53);
          Product product3 = new Product("wine",46.35);
          Product product4 = new Product("water",11.28);

          try (Session session = sessionFactory.getCurrentSession()) {
              Transaction transaction = session.beginTransaction();
              session.persist(client1);
            //  T merged = session.merge(entity);
              transaction.commit();

            //  return merged;
          }

      } */
    public static void main(String[] args) {

        System.out.println("Hello world!");
        initBD();
    }

    private static void initBD() {
        System.out.println("Блок инициализации!");
        Client client1 = new Client("John");
        Client client2 = new Client("Sasha");
        Client client3 = new Client("Bill");
        Product product1 = new Product("milk", 23.5);
        Product product2 = new Product("beer", 27.53);
        Product product3 = new Product("wine", 46.35);
        Product product4 = new Product("water", 11.28);
        configuration.addAnnotatedClass(ru.darek.Client.class);
        configuration.addAnnotatedClass(ru.darek.Product.class);
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(client1);
            //  T merged = session.merge(entity);
            transaction.commit();

            //  return merged;
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