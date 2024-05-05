package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.List;
import models.*;
import javax.swing.table.DefaultTableModel;

public class ContactView extends JFrame {

	// create component references
	private final JTextField txtFirstname, txtLastname, txtPhoneNumber;
	private final JButton btnAdd, btnUpdate, btnDelete, btnSearch, btnLogout, btnRefresh, btnExport;
	private JTable contactTable;
	private final DefaultTableModel tableModel;
	

	// constructor
	public ContactView() {

		setTitle("Phonebook");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		// initialize the components
		tableModel = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableModel.setColumnCount(4);
		tableModel.setColumnIdentifiers(new String[] {"First Name", "Last Name", "Phone Number", "id"});

		setContactTable(new JTable(tableModel));
		contactTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		txtFirstname = new JTextField(20);
		txtLastname = new JTextField(20);
		txtPhoneNumber = new JTextField(20);
		btnAdd = new JButton("Add");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		btnSearch = new JButton("Search");
		btnLogout = new JButton("Logout");
		btnRefresh = new JButton("Refresh");
		btnExport = new JButton("Export");
		contactTable.setShowGrid(false);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// input panel
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(4, 2));
		inputPanel.add(new JLabel("Firstname:"));
		inputPanel.add(txtFirstname);
		inputPanel.add(new JLabel("Lastname:"));
		inputPanel.add(txtLastname);
		inputPanel.add(new JLabel("Phone number:"));
		inputPanel.add(txtPhoneNumber);
		inputPanel.add(btnAdd);
		inputPanel.add(btnUpdate);
		
		// list panel with scroll pane and label that stays close to jscrollpane
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		listPanel.add(new JLabel("Contact list"));
//		listPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		listPanel.add(new JScrollPane(getContactTable()));

		// buttons panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(btnSearch);
		buttonPanel.add(btnDelete);
		buttonPanel.add(btnLogout);
		buttonPanel.add(btnRefresh);
		buttonPanel.add(btnExport);
		
		/*// add panel to the window
		setLayout(new BorderLayout());
		add(inputPanel, BorderLayout.NORTH);
		add(listPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);*/

		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;

		gbc.weighty = 0.3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(inputPanel, gbc);

		gbc.weighty = 0.4;
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(listPanel, gbc);

		gbc.weighty = 0.3;
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(buttonPanel, gbc);
		
		pack();
		setLocationRelativeTo(null);
	}
	
	public void addAddButtonListener(ActionListener listener) {
		btnAdd.addActionListener(listener);
	}
	
	public void addUpdateButtonListener(ActionListener listener) {
		btnUpdate.addActionListener(listener);
	}
	
	public void addDeleteButtonListener(ActionListener listener) {
		btnDelete.addActionListener(listener);
	}
	
	public void addSearchButtonListener(ActionListener listener) {
		btnSearch.addActionListener(listener);
	}
	
	public void addLogoutButtonListener(ActionListener listener) {
		btnLogout.addActionListener(listener);
	}
	
	public void addContactListListener(ListSelectionListener listener) {
		contactTable.getSelectionModel().addListSelectionListener(listener);
	}

	public void addRefreshButtonListener(ActionListener listener) {
		btnRefresh.addActionListener(listener);
	}

	public void addExportButtonListener(ActionListener listener) {
		btnExport.addActionListener(listener);
	}
	
	// getters
	public JTextField getFirstNameField() {
		return txtFirstname;
	}
	
	public String getFirstName() {
		return getFirstNameField().getText();
	}
	
	public JTextField getLastNameField() {
		return txtLastname;
	}
	public String getLastName() {
		return getLastNameField().getText();
	}
	
	public JTextField getPhoneNumberField() {
		return txtPhoneNumber;
	}
	public String getPhoneNumber() {
		return getPhoneNumberField().getText();
	}
	
	public void setContactsToTableModel(List<Contact> contacts) {
		
		tableModel.setRowCount(0);
		tableModel.setColumnCount(4);
		tableModel.setColumnIdentifiers(new String[] {"First Name", "Last Name", "Phone Number", "id"});

		//Add first name, last name and phone number to the list model. Each column is set number of characters wide
		for(Contact c : contacts) {
			tableModel.addRow(new String[] {c.getFirstName(), c.getLastName(), c.getPhoneNumber(), String.valueOf(c.getId())});
		}
	}

	public JTable getContactTable(){
		return contactTable;
	}

	public void setContactTable(JTable contactTable) {
		this.contactTable = contactTable;
	}

	public String getSearch() {
		return JOptionPane.showInputDialog("Contact or Phone number to search:");
	}
}
