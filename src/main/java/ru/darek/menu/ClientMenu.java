package ru.darek.menu;

import lombok.RequiredArgsConstructor;
import ru.darek.entity.Client;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
final class ClientMenu {

    private static final long SHOW_PRODUCTS = 1;
    private static final long DELETE = 2;

    private static final Pattern SHOW_PRODUCTS_BY_CLIENT = Pattern.compile("client_(?<clientId>\\d+)_products");
    private static final Pattern DELETE_CLIENT = Pattern.compile("client_(?<clientId>\\d+)_delete");

    private final Supplier<List<Client>> clients;

    public String showChooseMenu(Scanner scan) {
        System.out.println("ClientMenu.showChooseMenu()");
        List<Client> clients = this.clients.get().stream()
                .sorted(Client.SORT_BY_ID_ASC)
                .collect(Collectors.toList());
        Map<Long, Client> map = clients.stream().collect(Collectors.toMap(Client::getId, Function.identity()));

        System.out.println("--- Клиенты: Выбор клиента ---");
        clients.forEach(client -> System.out.format("%d. %s\n", client.getId(), client.getName()));
        System.out.print("Выбор: ");

        long clientId = Menu.requireNumberBetween(scan.nextLine(), map.keySet());
        return clientId == Menu.ERROR ? null : showChooseActionMenu(scan, map.get(clientId));
    }

    private static String showChooseActionMenu(Scanner scan, Client client) {
        System.out.format("--- Клиент [%d] %s ---\n", client.getId(), client.getName());
        System.out.println("1. Посмотреть купленные товары");
        System.out.println("2. Удалить");
        System.out.print("Выбор: ");

        long menuId = Menu.requireNumberBetween(scan.nextLine(), Set.of(SHOW_PRODUCTS, DELETE));

        if (menuId == SHOW_PRODUCTS)
            return "client_" + client.getId() + "_products";
        if (menuId == DELETE)
            return "client_" + client.getId() + "_delete";

        return null;
    }

    public boolean isShowProductsByClient(String action) {
        return SHOW_PRODUCTS_BY_CLIENT.matcher(action).matches();
    }

    public boolean isDeleteClient(String action) {
        return DELETE_CLIENT.matcher(action).matches();
    }

    public long getClientId(String action) {
        int lo = action.indexOf('_');
        int hi = action.indexOf('_', lo + 1);
        return Long.parseLong(action.substring(lo + 1, hi));
    }

}
