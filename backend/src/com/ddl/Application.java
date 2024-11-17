package com.ddl;

import com.ddl.service.UserService;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== User Management ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String regUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String regPassword = scanner.nextLine();
                    System.out.println(userService.register(regUsername, regPassword));
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String logUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String logPassword = scanner.nextLine();
                    System.out.println(userService.login(logUsername, logPassword));
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
