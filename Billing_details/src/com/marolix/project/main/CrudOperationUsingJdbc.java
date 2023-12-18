package com.marolix.project.main;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.marolix.project.model.Hotel;
import com.marolix.project.repository.HotelRepository;
import com.marolix.project.service.CrudOperations;

public class CrudOperationUsingJdbc {

 private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hotel_database";
 private static final String USERNAME = "root";
 private static final String PASSWORD = "root";

 public static void main(String[] args) {
     try {
         Class.forName("com.mysql.cj.jdbc.Driver");

         try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
              Statement statement = connection.createStatement()) {

             HotelRepository.createTable(connection);

             CrudOperations crudOperations = new CrudOperations(statement);
             Scanner scanner = new Scanner(System.in);

             while (true) {
                 try {
                     System.out.println("\n=== Hotel Management System ===");
                     System.out.println("1. Display Food Items");
                     System.out.println("2. Create Product");
                     System.out.println("3. Read Products");
                     System.out.println("4. Update Product");
                     System.out.println("5. Delete Product");
                     System.out.println("6. Generate Bill");
                     System.out.println("0. Exit");

                     System.out.print("Enter your choice: ");

                   
                     if (scanner.hasNextInt()) {
                         int choice = scanner.nextInt();

                         switch (choice) {
                             case 1:
                                 List<Hotel> foodItems = HotelRepository.fetchFoodItems(connection);
                                 HotelRepository.displayFoodItems(foodItems);
                                 break;
                             case 2:
                                 crudOperations.createProduct();
                                 break;
                             case 3:
                                 crudOperations.readProducts();
                                 break;
                             case 4:
                                 crudOperations.updateProduct();
                                 break;
                             case 5:
                                 crudOperations.deleteProduct();
                                 break;
                             case 6:
                                 crudOperations.generateBill();
                                 break;
                             case 0:
                                 System.out.println("Exiting... Thank you!");
                                 return;
                             default:
                                 System.out.println("Invalid choice. Please enter a valid option.");
                         }
                     } else {
                         System.out.println("Invalid input. Please enter a valid option.");
                         scanner.next(); // Consume the invalid input
                     }
                 } catch (InputMismatchException e) {
                     System.out.println("Invalid input. Please enter a valid integer.");
                     scanner.next(); // Consume the invalid input
                 }
             }
         }
     } catch (SQLException | ClassNotFoundException e) {
         e.printStackTrace();
     }
 }
}
