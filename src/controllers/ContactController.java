package controllers;

import views.*;
import java.awt.event.*;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

import models.*;
import java.util.List;
import javax.swing.event.*;

public class ContactController {

	private final ContactView contactView;
	
	public ContactController(ContactView cv) {
		
		this.contactView = cv;
		
		updateContactList();
		
		contactView.addAddButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// create an object of a contact
				Contact contact = new Contact();
				contact.setFirstName(contactView.getFirstName());
				contact.setLastName(contactView.getLastName());
				contact.setPhoneNumber(contactView.getPhoneNumber());
				
				ContactDataAccess contactData = new ContactDataAccess();
				if(contactData.addContact(contact)) {
					JOptionPane.showMessageDialog(null, "Contact added successfully");
					updateContactList();
				}
				else {
					JOptionPane.showMessageDialog(null, "An error occured");
				}
			}
		});
		
		contactView.addContactListListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				Contact contact = getSelectedContact();
				
				if(contact != null) {
					updateFields(contact);
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

				contact.setFirstName(fn);
				contact.setLastName(ln);
				contact.setPhoneNumber(pn);

				if(new ContactDataAccess().updateContact(contact)) {
					updateContactList();
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
	}

	private void updateContactList() {
		ContactDataAccess data = new ContactDataAccess();
		
		List<Contact> contacts = data.getContacts();
			
		contactView.setContactsToModel(contacts);
	}
	
	private Contact getSelectedContact() {
		Contact contact = null;
		
		int row = contactView.getContactList().getSelectedIndex();
		
		if(row != -1) {
			contact = new ContactDataAccess().getContacts().get(row);
		}
		return contact;
	}
	
	private void updateFields(Contact contact) {
		
		contactView.getFirstNameField().setText(contact.getFirstName());
		contactView.getLastNameField().setText(contact.getLastName());
		contactView.getPhoneNumberField().setText(contact.getPhoneNumber());
	}
	
	
	
	
	
	
	
	
	
	
}
