package budget;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Purchase implements Comparable {
    String name;
    double price;

    Purchase(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s $%.2f", name, price);
    }

    @Override
    public int compareTo(@NotNull Object o) {
        Purchase p = (Purchase) o;
        if (price > p.price) {
            return 1;
        } else if (price < p.price) {
            return -1;
        }
        return 0;
    }
}

class TypedPurchase extends Purchase {
    String type;

    public TypedPurchase(String name, double price, String type) {
        super(name, price);
        this.type = type;
    }
}

public class Main {
    static List<TypedPurchase> purchases = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static double balance = 0;

    public static void main(String[] args) {
        // write your code here
        showMenu();
        int choice = Integer.parseInt(scanner.nextLine());
        while (choice != 0) {
            switch (choice) {
                case 1:
                    addIncome();
                    break;
                case 2:
                    addPurchase();
                    break;
                case 3:
                    showPurchases();
                    break;
                case 4:
                    showBalance();
                    break;
                case 5:
                    save();
                    break;
                case 6:
                    load();
                    break;
                case 7:
                    analyze();
                    break;
                default:
                    System.out.println("error");

            }
            showMenu();
            choice = Integer.parseInt(scanner.nextLine());
        }
        System.out.println("\nBye!");
    }

    private static void analyze() {
        String type = showSortMenu();
        while (!"4".equals(type)) {
            switch (type) {
                case "1":
                    sortAll();
                    break;
                case "2":
                    sortByType();
                    break;
                case "3":
                    sortType();
                    break;
                default:
                    System.out.println("error");
            }
            type = showSortMenu();
        }
    }

    private static void sortType() {
        String type = getType();
        List<TypedPurchase> filtered;
        filtered = purchases.stream()
                .filter(e -> e.type.equals(type)).collect(Collectors.toList());
        if (filtered.isEmpty()) {
            System.out.println("Purchase list is empty");
            return;
        }
        Collections.sort(filtered, Collections.reverseOrder());
        System.out.println("\n" + type);
        double total = 0.0;
        for (TypedPurchase p : filtered) {
            System.out.printf("%s $%.2f\n", p.name, p.price);
            total += p.price;
        }
        System.out.printf("Total: $%.2f\n", total);
    }

    private static String getType() {
        System.out.println("\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n");
        switch (scanner.nextLine()) {
            case "1":
                return "Food";
            case "2":
                return "Clothes";
            case "3":
                return "Entertainment";
            case "4":
                return "Other";
            default:
                return "";
        }
    }

    private static void sortByType() {
        System.out.println("\nTypes:");
        double food = 0.0;
        double ent = 0.0;
        double cloth = 0.0;
        double other = 0.0;
        double total = 0.0;
        for (TypedPurchase purchase : purchases) {
            if (purchase.type.equals("Food")) {
                food += purchase.price;
            } else if (purchase.type.equals("Entertainment")) {
                ent += purchase.price;
            } else if (purchase.type.equals("Clothes")) {
                cloth += purchase.price;
            } else if (purchase.type.equals("Other")) {
                other += purchase.price;
            }
            total += purchase.price;
        }
        System.out.printf("Food - $%.2f\n" +
                "Entertainment - $%.2f\n" +
                "Clothes - $%.2f\n" +
                "Other - $%.2f\n" +
                "Total sum: $%.2f\n", food, ent, cloth, other, total);
    }

    private static void sortAll() {
        if (purchases.isEmpty()) {
            System.out.println("\nPurchase list is empty!");
            return;
        }
        List<TypedPurchase> sorted = new ArrayList<>(purchases);
        Collections.sort(sorted, Collections.reverseOrder());

        System.out.println("All:");
        double total = 0.0;
        for (TypedPurchase p : sorted) {
            System.out.printf("%s $%.2f\n", p.name, p.price);
            total += p.price;
        }
        System.out.printf("Total: $%.2f\n", total);
    }

    private static String showSortMenu() {
        System.out.println("\nHow do you want to sort?\n" +
                "1) Sort all purchases\n" +
                "2) Sort by type\n" +
                "3) Sort certain type\n" +
                "4) Back\n");
        return scanner.nextLine();
    }

    private static void load() {
        File file = new File("purchases.txt");
        try {
            FileReader reader = new FileReader(file);
            Scanner scanner = new Scanner(reader);
            int count = Integer.parseInt(scanner.nextLine());
            balance = Double.parseDouble(scanner.nextLine());
            for (int i = 0; i < count; i++) {
                String type = scanner.nextLine();
                String name = scanner.nextLine();
                double price = Double.parseDouble(scanner.nextLine());
                purchases.add(new TypedPurchase(name, price, type));
            }
            System.out.println("\nPurchases were loaded!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void save() {
        File file = new File("purchases.txt");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(String.valueOf(purchases.size()) + "\n");
            writer.write(String.valueOf(balance) + "\n");
            for (TypedPurchase purchase : purchases) {
                writer.write(purchase.type + "\n");
                writer.write(purchase.name + "\n");
                writer.write(String.valueOf(purchase.price) + "\n");
            }
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        System.out.println("\nPurchases were saved!");
    }

    private static void addPurchase() {
        String type = getPurchaseType();
        while (!type.isBlank()) {
            System.out.println("\nEnter purchase name:");
            String name = scanner.nextLine();
            System.out.println("Enter its price:");
            String priceString = scanner.nextLine();
            double price = Double.valueOf(priceString);
            TypedPurchase purchase = new TypedPurchase(name, price, type);
            purchases.add(purchase);
            balance -= price;
            System.out.println("Purchase was added!");
            type = getPurchaseType();
        }
    }

    private static String getPurchaseType() {
        System.out.println("\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) Back");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                return "Food";
            case 2:
                return "Clothes";
            case 3:
                return "Entertainment";
            case 4:
                return "Other";
            default:
                return "";
        }
    }

    private static void showPurchases() {
        if (purchases.isEmpty()) {
            System.out.println("\nPurchase list is empty");
            return;
        }
        String type = getPurchasesType();
        while (!type.isBlank()) {
            System.out.println("\n" + type + ":");
            String finalType = type;
            List<TypedPurchase> filtered;
            if (!type.equals("All")) {
                filtered = purchases.stream()
                        .filter(e -> e.type.equals(finalType)).collect(Collectors.toList());
                if (filtered.isEmpty()) {
                    System.out.println("Purchase list is empty");
                    return;
                }
            } else {
                filtered = purchases;
            }
            double total = 0.0;
            for (Purchase e : filtered) {
                System.out.println(e);
                total += e.price;
            }
            System.out.printf("Total: $%.2f\n", total);
            type = getPurchasesType();
        }
    }

    private static String getPurchasesType() {
        System.out.println("\nChoose the type of purchases\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) All\n" +
                "5) Back");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                return "Food";
            case 2:
                return "Clothes";
            case 3:
                return "Entertainment";
            case 4:
                return "Other";
            case 5:
                return "All";
            default:
                return "";
        }
    }

    private static void showBalance() {
        if (balance < 0) {
            balance = 0;
        }
        System.out.printf("\nBalance: $%.2f\n", balance);
    }

    private static void addIncome() {
        System.out.println("\nEnter income:");
        balance += Double.parseDouble(scanner.nextLine());
        System.out.println("Income was added");
    }

    private static void showMenu() {
        System.out.println("\nChoose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "7) Analyze (Sort)\n" +
                "0) Exit");
    }
}
