package com.marolix.project.service;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.marolix.project.model.Hotel;

public class CrudOperations {

	private final Statement statement;

	public CrudOperations(Statement statement) {
		this.statement = statement;
	}

	public void generateBill(String productName, double productPrice, int quantity, double totalPrice) {
		System.out.println("\nBill Details:");
		System.out.println("Product Name: " + productName);
		System.out.println("Unit Price: $" + productPrice);
		System.out.println("Quantity: " + quantity);
		System.out.println("Total Price: $" + totalPrice);
	}
	
	public void createProduct() {
	    try (Scanner scanner = new Scanner(System.in)) {
	        System.out.println("Adding a new product...");

	        System.out.print("Enter product name: ");
	        String productName = scanner.nextLine();

	        System.out.print("Enter product price: $");
	        double productPrice = scanner.nextDouble();

	        if (isProductExists(productName)) {
	            System.out.println("Product with the same name already exists in the database.");
	            return;
	        }

	        String insertQuery = "INSERT INTO food_items (name, price) VALUES ('"
	                + productName + "', " + productPrice + ")";
	        int rowsAffected = statement.executeUpdate(insertQuery);

	        if (rowsAffected > 0) {
	            System.out.println("Product added successfully.");
	        } else {
	            System.out.println("Failed to add product.");
	        }
	    } catch (SQLException | InputMismatchException e) {
	        e.printStackTrace();
	    }
	}

	private boolean isProductExists(String productName) throws SQLException {
	    String selectQuery = "SELECT COUNT(*) AS count FROM food_items WHERE name = ?";
	    
	    try (PreparedStatement preparedStatement = statement.getConnection().prepareStatement(selectQuery)) {
	        preparedStatement.setString(1, productName);

	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            int count = resultSet.getInt("count");
	            return count > 0;
	        }
	    }

	    return false;
	}


	public void readProducts() {
		try {
			System.out.println("Reading all products...");

			String selectQuery = "SELECT * FROM food_items";
			ResultSet resultSet = statement.executeQuery(selectQuery);

			System.out.println("Food Items:");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double price = resultSet.getDouble("price");

				System.out.println("ID: " + id + ", Name: " + name + ", Price: $" + price);
			}

			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateProduct() {
	    try (Scanner scanner = new Scanner(System.in)) {
	        System.out.println("Updating a product...");

	        // Validate input for product ID
	        int productId;
	        while (true) {
	            try {
	                System.out.print("Enter product ID to update: ");
	                productId = scanner.nextInt();
	                break;  
	            } catch (InputMismatchException e) {
	                System.out.println("Invalid input. Please enter a valid integer.");
	                scanner.nextLine();  
	            }
	        }
	        scanner.nextLine(); 

	        String selectQuery = "SELECT * FROM food_items WHERE id = " + productId;
	        ResultSet resultSet = statement.executeQuery(selectQuery);

	        if (resultSet.next()) {
	            Hotel product = new Hotel(
	                    resultSet.getInt("id"),
	                    resultSet.getString("name"),
	                    resultSet.getDouble("price"),
	                    resultSet.getInt("quantity")
	            );

	            System.out.println("Current Product Details:");
	            System.out.println(product);

	            System.out.println("\nSelect the field to update:");
	            System.out.println("1. Update product name");
	            System.out.println("2. Update product price");
	            System.out.println("3. Update product quantity");
	            System.out.println("0. Cancel");

	            int choice;
	            while (true) {
	                try {
	                    System.out.print("Enter your choice: ");
	                    choice = scanner.nextInt();
	                    break; 
	                } catch (InputMismatchException e) {
	                    System.out.println("Invalid input. Please enter a valid integer.");
	                    scanner.nextLine(); 
	                }
	            }

	            scanner.nextLine(); 

	            switch (choice) {
	                case 1:
	                    System.out.print("Enter new product name: ");
	                    String newName = scanner.nextLine();
	                    product.setProductName(newName);
	                    break;
	                case 2:
	                    System.out.print("Enter new product price: $");
	                    double newPrice = scanner.nextDouble();
	                    product.setProductPrice(newPrice);
	                    break;
	                case 3:
	                    System.out.print("Enter new product quantity: ");
	                    int newQuantity = scanner.nextInt();
	                    product.setQuantity(newQuantity);
	                    break;
	                case 0:
	                    System.out.println("Update operation canceled.");
	                    return;
	                default:
	                    System.out.println("Invalid choice. Update operation canceled.");
	                    return;
	            }

	           
	            String updateQuery = "UPDATE food_items SET name = ?, price = ?, quantity = ? WHERE id = ?";
	            try (PreparedStatement preparedStatement = statement.getConnection().prepareStatement(updateQuery)) {
	                preparedStatement.setString(1, product.getProductName());
	                preparedStatement.setDouble(2, product.getProductPrice());
	                preparedStatement.setInt(3, product.getQuantity());
	                preparedStatement.setInt(4, product.getId());

	                int rowsAffected = preparedStatement.executeUpdate();

	                if (rowsAffected > 0) {
	                    System.out.println("Product updated successfully.");
	                } else {
	                    System.out.println("Failed to update product.");
	                }
	            }

	        } else {
	            System.out.println("Product not found. (Check if the ID exists)");
	        }

	        resultSet.close();
	    } catch (SQLException | InputMismatchException e) {
	        e.printStackTrace();
	    }
	}

	public void deleteProduct() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Deleting a product...");

			System.out.print("Enter product ID to delete: ");
			int productId = scanner.nextInt();

			String deleteQuery = "DELETE FROM food_items WHERE id = " + productId;

			int rowsAffected = statement.executeUpdate(deleteQuery);

			if (rowsAffected > 0) {
				System.out.println("Product deleted successfully.");
			} else {
				System.out.println("Failed to delete product. (Check if the ID exists)");
			}
		} catch (SQLException | InputMismatchException e) {
			e.printStackTrace();
		}
		
	}
	public void generateBill() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Generating a bill...");

            try {
                System.out.print("Enter product ID: ");
                int productId = scanner.nextInt();

                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();

                if (quantity <= 0) {
                    System.out.println("Invalid quantity. Please enter a positive integer.");
                    return;
                }

                String selectQuery = "SELECT * FROM food_items WHERE id = " + productId;
                ResultSet resultSet = statement.executeQuery(selectQuery);

                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");

                    double totalAmount = price * quantity;

                    System.out.println("\nBill Details:");
                    System.out.println("Product Name: " + name);
                    System.out.println("Price per item: $" + price);
                    System.out.println("Quantity: " + quantity);
                    System.out.println("Total Amount: $" + totalAmount);
                } else {
                    System.out.println("Product not found. (Check if the ID exists)");
                }

                resultSet.close();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
