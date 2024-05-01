package models;

import java.sql.*;
import myutils.Util;

import javax.swing.*;
import java.util.*;

public class ContactDataAccess {

	private Connection connect;

	public ContactDataAccess() {
		try {
			this.connect = Util.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean addContact(Contact contact) {

		try {
			String query = "INSERT INTO tb_contacts (firstname, lastname, phonenumber, user) VALUES (?, ?, ?, ?)";
			PreparedStatement stm = connect.prepareStatement(query);

			stm.setString(1, contact.getFirstName());
			stm.setString(2, contact.getLastName());
			stm.setString(3, contact.getPhoneNumber());
			stm.setInt(4, UserDataAccess.currentUserId);

			int row = stm.executeUpdate();

            return row > 0;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean updateContact(Contact contact) {

		try {
			String query = "UPDATE tb_contacts SET firstname=?, lastname=?, phonenumber=? WHERE id=? AND user=?";
			PreparedStatement stm = connect.prepareStatement(query);

			stm.setString(1, contact.getFirstName());
			stm.setString(2, contact.getLastName());
			stm.setString(3, contact.getPhoneNumber());
			stm.setInt(4, contact.getId());
			stm.setInt(5, UserDataAccess.currentUserId);

			int row = stm.executeUpdate();

            return row > 0;
		} catch (SQLException e) {
			return false;
		}
	}

	public List<Contact> getContacts() {

		List<Contact> contactList = new ArrayList<>();

		try {
			String query = "SELECT * FROM tb_contacts WHERE user = ?";
			PreparedStatement stm = connect.prepareStatement(query);

			stm.setInt(1, UserDataAccess.currentUserId);

			ResultSet result = stm.executeQuery();

			while (result.next()) {
				int contactId = result.getInt("id");
				String firstName = result.getString("firstname");
				String lastName = result.getString("lastname");
				String phoneNumber = result.getString("phonenumber");

				Contact contact = new Contact(contactId, firstName, lastName, phoneNumber);

				contactList.add(contact);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return contactList;
	}

	public boolean deleteContact(Contact contact) {

		try {
			String query = "DELETE FROM tb_contacts WHERE id=? AND user=?";
			PreparedStatement stm = connect.prepareStatement(query);

			stm.setInt(1, contact.getId());
			stm.setInt(2, UserDataAccess.currentUserId);

			int row = stm.executeUpdate();

			return row > 0;
		} catch (SQLException e) {
			return false;
		}
	}

	public List<Contact> searchContact(String search) {

		List<Contact> contactList = new ArrayList<>();

		try {
			String query = "SELECT * FROM tb_contacts WHERE (firstname LIKE ? OR lastname LIKE ? OR phonenumber LIKE ? OR CONCAT(firstname, ' ', lastname) LIKE ?) AND user = ?";
			PreparedStatement stm = connect.prepareStatement(query);

			stm.setString(1, "%" + search + "%");
			stm.setString(2, "%" + search + "%");
			stm.setString(3, "%" + search + "%");
			stm.setString(4, "%" + search + "%");
			stm.setInt(5, UserDataAccess.currentUserId);

			ResultSet result = stm.executeQuery();

			while (result.next()) {
				int contactId = result.getInt("id");
				String firstName = result.getString("firstname");
				String lastName = result.getString("lastname");
				String phoneNumber = result.getString("phonenumber");

				Contact contact = new Contact(contactId, firstName, lastName, phoneNumber);

				contactList.add(contact);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return contactList;
	}
}
