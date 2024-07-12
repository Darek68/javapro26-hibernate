package ru.darek.menu;

import lombok.RequiredArgsConstructor;
import ru.darek.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
final class ProductMenu {

    private static final long SHOW_CLIENTS = 1;
    private static final long DELETE = 2;

    private static final Pattern SHOW_CLIENTS_BY_PRODUCT = Pattern.compile("product_(?<productId>\\d+)_clients");
    private static final Pattern DELETE_PRODUCT = Pattern.compile("product_(?<clientId>\\d+)_delete");

    private final Supplier<List<Product>> products;

    public String showChooseMenu(Scanner scan) {
        System.out.println("ProductMenu.showChooseMenu()");
        List<Product> products = this.products.get().stream()
                                              .sorted(Product.SORT_BY_ID_ASC)
                                              .collect(Collectors.toList());
        Map<Long, Product> map = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        System.out.println("--- Продукты: Выбор продукта ---");
        products.forEach(product -> System.out.format("%d. %s\n", product.getId(), product.getName()));
        System.out.print("Выбор: ");

        long productIdId = Menu.requireNumberBetween(scan.nextLine(), map.keySet());
        return productIdId == Menu.ERROR ? null : showChooseActionMenu(scan, map.get(productIdId));
    }

    private static String showChooseActionMenu(Scanner scan, Product product) {
        System.out.format("--- Продукт [%d] %s ---\n", product.getId(), product.getName());
        System.out.println("1. Посмотреть клиентов купивших товар");
        System.out.println("2. Удалить");
        System.out.print("Выбор: ");

        long menuId = Menu.requireNumberBetween(scan.nextLine(), Set.of(SHOW_CLIENTS, DELETE));

        if (menuId == SHOW_CLIENTS)
            return "product_" + product.getId() + "_clients";
        if (menuId == DELETE)
            return "product_" + product.getId() + "_delete";

        return null;
    }

    public boolean isShowClientsByProduct(String action) {
        return SHOW_CLIENTS_BY_PRODUCT.matcher(action).matches();
    }

    public boolean isDeleteProduct(String action) {
        return DELETE_PRODUCT.matcher(action).matches();
    }

    public long getProductId(String action) {
        int lo = action.indexOf('_');
        int hi = action.indexOf('_', lo + 1);
        return Long.parseLong(action.substring(lo + 1, hi));
    }

}
