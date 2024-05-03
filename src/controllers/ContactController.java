package controllers;

import myutils.Util;
import views.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import models.*;
import java.util.List;
import javax.swing.event.*;

public class ContactController {

	private final ContactView contactView;
	
	public ContactController(ContactView cv) {
		
		this.contactView = cv;
		
		updateContactTable();
		
		contactView.addAddButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// create an object of a contact
				Contact contact = new Contact();
				contact.setFirstName(contactView.getFirstName());
				contact.setLastName(contactView.getLastName());
				String phoneNumber = contactView.getPhoneNumber();
				// check if the phone number is 10 digits and contains only numbers
				if(phoneNumber.length() != 10 || !phoneNumber.matches("[0-9]+")) {
					JOptionPane.showMessageDialog(null, "Phone number must be 10 digits and contain only numbers");
					return;
				}

				contact.setPhoneNumber(contactView.getPhoneNumber());

				ContactDataAccess contactData = new ContactDataAccess();
				if(contactData.addContact(contact)) {
					JOptionPane.showMessageDialog(null, "Contact added successfully");
					updateContactTable();
				}
				else {
					JOptionPane.showMessageDialog(null, "An error occurred");
				}
			}
		});
		
		contactView.addContactListListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					Contact contact = getSelectedContact();
					if(contact != null) {
						updateFields(contact);
					}

				}
			}
		});
		
		// Update button click
		contactView.addUpdateButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Contact contact = getSelectedContact();

				String fn = contactView.getFirstName();
				String ln = contactView.getLastName();
				String pn = contactView.getPhoneNumber();
				if(pn.length() != 10 || !pn.matches("[0-9]+")) {
					JOptionPane.showMessageDialog(null, "Phone number must be 10 digits and contain only numbers");
					updateFields(contact);
					return;
				}

				contact.setFirstName(fn);
				contact.setLastName(ln);
				contact.setPhoneNumber(pn);

				if(new ContactDataAccess().updateContact(contact)) {
					updateContactTable();
					JOptionPane.showMessageDialog(null, "contact update successfully");
				}
			}
		});
		
		contactView.addLogoutButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				UserDataAccess.currentUserId = 0;
				
				contactView.setVisible(false);
				contactView.dispose();
				LoginView lv = new LoginView();
				lv.setVisible(true);
				new LoginController(lv);
				
			}
		});

		contactView.addDeleteButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contact contact = getSelectedContact();

				if(contact != null) {
					if(new ContactDataAccess().deleteContact(contact)) {
						updateContactTable();
						JOptionPane.showMessageDialog(null, "Contact deleted successfully");
					}
				}
			}
		});

		contactView.addSearchButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String search = contactView.getSearch();

				List<Contact> contacts = new ContactDataAccess().searchContact(search);

				contactView.setContactsToTableModel(contacts);
			}
		});

		contactView.addRefreshButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateContactTable();
			}
		});

		contactView.addExportButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateContactTable();
				String filename = JOptionPane.showInputDialog("Enter filename");
				String csvName = filename + ".csv";
				if (Util.exportToCSV(csvName, new ContactDataAccess().getContacts())) {
					JOptionPane.showMessageDialog(null, "Contacts exported successfully");
				} else {
					JOptionPane.showMessageDialog(null, "Export failed");
				}
			}
		});
	}

	private void updateContactTable() {
		ContactDataAccess data = new ContactDataAccess();
		
		List<Contact> contacts = data.getContacts();
			
		contactView.setContactsToTableModel(contacts);
	}
	
	private Contact getSelectedContact() {
		Contact contact = null;
		
		int row = contactView.getContactTable().getSelectedRow();

		if(row != -1) {
			contact = new Contact();
			contact.setFirstName((String) contactView.getContactTable().getValueAt(row, 0));
			contact.setLastName((String) contactView.getContactTable().getValueAt(row, 1));
			contact.setPhoneNumber((String) contactView.getContactTable().getValueAt(row, 2));
			contact.setId(Integer.parseInt((String) contactView.getContactTable().getModel().getValueAt(row, 3)));
		}

		return contact;
	}
	
	private void updateFields(Contact contact) {
		
		contactView.getFirstNameField().setText(contact.getFirstName());
		contactView.getLastNameField().setText(contact.getLastName());
		contactView.getPhoneNumberField().setText(contact.getPhoneNumber());
	}
	
	
	
	
	
	
	
	
	
	
}
