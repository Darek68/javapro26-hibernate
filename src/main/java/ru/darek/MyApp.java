package ru.darek;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.darek.entity.Client;
import ru.darek.entity.Product;
import ru.darek.menu.Menu;
import ru.darek.service.ClientService;
import ru.darek.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class MyApp {

    // TODO настройки базы одни для всего приложения (они используются в разных сервисах)
    private final Configuration configuration = new Configuration();
    private final SessionFactory sessionFactory = configuration.configure().buildSessionFactory();

    // TODO делим сервисы - лучше несколько маленьких, чем один большой
    private final ClientService clientService = new ClientService(sessionFactory);
    private final ProductService productService = new ProductService(sessionFactory);
    // TODO работа с меню - отдельная тема. Лучше её выделить в отдельный класс
    private final Menu menu;

    public MyApp() {
        menu = new Menu(clientService::getAllClients, productService::getAllProducts);
        fillDatabase();
    }

    private void fillDatabase() {
        Product yogurt = new Product("yogurt", 7.15);
        Product milk = new Product("milk", 23.5);
        Product beer = new Product("beer", 27.53);
        Product wine = new Product("wine", 46.35);
        Product water = new Product("water", 11.28);

        // TODO не забываем выставить product у client
        // TODO не забываем выставить client у product
        Client ketty = new Client("Ketty", Set.of(yogurt, beer));
        Client john = new Client("John", Set.of(yogurt, milk, beer));
        Client sasha = new Client("Sasha", Set.of(wine));
        Client bill = new Client("Bill", Set.of(water));
        Client eduard = new Client("Eduard", Set.of(yogurt, milk, beer, wine, water));

        clientService.save(List.of(ketty, john, sasha, bill, eduard));
    }

    // TODO управление корневым меню это отдельная история - нужно выносить в отдельный метод
    private void runDemo(Scanner scan) {
        while (true) {
            String action = menu.show(scan);

            if (menu.isExitAction(action))
                return;

            if (menu.isShowProductsByClient(action))
                onShowProductsByClient(menu.getClientId(action));
            else if (menu.isShowClientsByProduct(action))
                onShowClientsByProduct(menu.getProductId(action));
            else if (menu.isDeleteClient(action))
                onDeleteClient(menu.getClientId(action));
            else if (menu.isDeleteProduct(action))
                onDeleteProduct(menu.getProductId(action));
        }
    }

    private void onShowProductsByClient(long clientId) {
        // TODO в данный момент о кешировании данных думать не нужно
        Optional<Client> opt = clientService.getClientById(clientId);

        if (opt.isPresent()) {
            Client client = opt.get();
            List<Product> products = client.getProducts().stream()
                    .sorted(Product.SORT_BY_ID_ASC)
                    .collect(Collectors.toList());

            System.out.format("Покупки клиента [%d] %s:\n", client.getId(), client.getName());

            if (products.isEmpty())
                System.out.println("не найдено");
            else
                products.forEach(product -> System.out.format("[%d] %s\n", product.getId(), product.getName()));
        } else
            System.out.format("Клиент [%d] не найден\n", clientId);

        printSeparator();
    }

    private void onShowClientsByProduct(long productId) {
        // TODO в данный момент о кешировании данных думать не нужно
        Optional<Product> opt = productService.getProductById(productId);

        if (opt.isPresent()) {
            Product product = opt.get();
            List<Client> clients = product.getClients().stream()
                    .sorted(Client.SORT_BY_ID_ASC)
                    .collect(Collectors.toList());

            System.out.format("Клиенты купившие продукт [%d] %s:\n", product.getId(), product.getName());

            if (clients.isEmpty())
                System.out.println("не найдено");
            else
                clients.forEach(client -> System.out.format("[%d] %s\n", client.getId(), client.getName()));
        } else
            System.out.format("Продукт [%d] не найден\n", productId);

        printSeparator();
    }

    private void onDeleteClient(long clientId) {
        Optional<Client> opt = clientService.getClientById(clientId);

        if (opt.isPresent()) {
            Client client = opt.get();
            clientService.deleteClient(client);
            System.out.format("Клиент [%d] %s удалён\n", client.getId(), client.getName());
        } else
            System.out.format("Клиент [%d] не найден\n", clientId);

        printSeparator();
    }

    private void onDeleteProduct(long productId) {
        Optional<Product> opt = productService.getProductById(productId);

        if (opt.isPresent()) {
            Product product = opt.get();
            productService.deleteProduct(product);
            System.out.format("Product [%d] %s удалён\n", product.getId(), product.getName());
        } else
            System.out.format("product [%d] не найден\n", productId);

        printSeparator();
    }

    private static void printSeparator() {
        System.out.println();
        System.out.println("**********");
        System.out.println();
    }

    public static void main(String... args) {
        System.out.println("Hello world!");

        // TODO лучше не использовать статики. нужно сделать объект приложения
        MyApp app = new MyApp();
        // TODO Scanner должен быть один - его лучше вынести на самый верх
        app.runDemo(new Scanner(System.in));
    }
}