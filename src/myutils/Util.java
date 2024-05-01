package myutils;

import models.Contact;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.security.*;
import java.util.List;

public class Util {

	private static final String URL = "jdbc:mysql:///phonebook_schema";
	private static final String USER = "root";
	private static final String PASSWORD = "Password123.";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = md.digest(password.getBytes());
			StringBuilder stringBuilder = new StringBuilder();
			for (byte b : hashedBytes) {
				stringBuilder.append(String.format("%02x", b));
			}
			return stringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean exportToCSV(String filename, List<Contact> contacts) {
		try {
			FileWriter writer = new FileWriter(filename);
			writer.write("First Name,Last Name,Phone Number\n");
			for(Contact contact : contacts) {
				writer.write(contact.getFirstName() + "," + contact.getLastName() + "," + contact.getPhoneNumber() + "\n");
			}
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
