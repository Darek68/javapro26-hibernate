package ru.darek.menu;

import org.apache.commons.lang3.StringUtils;
import ru.darek.entity.Client;
import ru.darek.entity.Product;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class Menu {

    static final long ERROR = 0;
    private static final long CHOOSE_CLIENT = 1;
    private static final long PRODUCTS = 2;
    private static final long EXIT = 3;

    private static final String ACTION_EXIT = "exit";

    private final ClientMenu clientMenu;
    private final ProductMenu productMenu;

    public Menu(Supplier<List<Client>> clients, Supplier<List<Product>> products) {
        clientMenu = new ClientMenu(clients);
        productMenu = new ProductMenu(products);
    }

    public String show(Scanner scan) {
        while (true) {
            System.out.println("--- Основное меню ---");
            System.out.println("1. Клиенты");
            System.out.println("2. Товары");
            System.out.println("3. Выход");
            System.out.print("Выбор [1..3]: ");

            long menuId = requireNumberBetween(scan.nextLine(), Set.of(CHOOSE_CLIENT, PRODUCTS, EXIT));
            String action = null;

            System.out.println("menuId: " + menuId);

            if (menuId == CHOOSE_CLIENT)
                action = clientMenu.showChooseMenu(scan);
            else if (menuId == PRODUCTS)
                action = productMenu.showChooseMenu(scan);
            else if (menuId == EXIT)
                return ACTION_EXIT;

            if (action != null)
                return action;
        }
    }

    static long requireNumberBetween(String str, Set<Long> available) {
        if (StringUtils.isNumeric(str)) {
            long num = Long.parseLong(str);

            if (available.contains(num))
                return num;
        }

        System.out.println("Ожидаем " + available.stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(",", "[", "]")));

        return ERROR;
    }

    public boolean isExitAction(String action) {
        return ACTION_EXIT.equalsIgnoreCase(action);
    }

    public boolean isShowProductsByClient(String action) {
        return clientMenu.isShowProductsByClient(action);
    }

    public boolean isShowClientsByProduct(String action) {
        return productMenu.isShowClientsByProduct(action);
    }

    public boolean isDeleteClient(String action) {
        return clientMenu.isDeleteClient(action);
    }

    public boolean isDeleteProduct(String action) {
        return productMenu.isDeleteProduct(action);
    }

    public long getClientId(String action) {
        return clientMenu.getClientId(action);
    }

    public long getProductId(String action) {
        return productMenu.getProductId(action);
    }

}
