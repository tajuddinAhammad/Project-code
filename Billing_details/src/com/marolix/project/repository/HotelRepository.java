package com.marolix.project.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.marolix.project.model.Hotel;

public class HotelRepository {

	static final String JDBC_URL = "jdbc:mysql://localhost:3306/hotel_database";
	static final String USERNAME = "root";
	static final String PASSWORD = "root";


	public static void displayFoodItems(List<Hotel> foodItems) {
		System.out.println("Food Items:");
		for (Hotel product : foodItems) {
			System.out.print(product);
			System.out.print(", Quantity: " + product.getQuantity());
			System.out.println();
		}
	}

	public static void createTable(Connection connection) {
		try (Statement statement = connection.createStatement()) {
			String createTableSQL = "CREATE TABLE IF NOT EXISTS food_items (" +
					"id INT PRIMARY KEY AUTO_INCREMENT," +
					"name VARCHAR(255) NOT NULL," +
					"price DOUBLE NOT NULL)";
			statement.executeUpdate(createTableSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void displayFoodItems(Connection connection) {
		String selectQuery = "SELECT * FROM food_items";
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(selectQuery)) {

			System.out.println("Food Items:");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double price = resultSet.getDouble("price");

				// Corrected constructor call
				Hotel product = new Hotel(id, name, price, 0);  // Assuming default quantity is 0
				System.out.print(product);
				System.out.print(", Quantity: " + product.getQuantity());
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Hotel> fetchFoodItems(Connection connection) {

		List<Hotel> foodItems = new ArrayList<>();

		String selectQuery = "SELECT * FROM food_items";

		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(selectQuery)) {

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double price = resultSet.getDouble("price");

				// Corrected constructor call
				Hotel product = new Hotel(id, name, price, 0);  // Assuming default quantity is 0
				foodItems.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foodItems;
	}

}

