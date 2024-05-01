package controllers;

import java.awt.event.*;

import javax.swing.JOptionPane;

import views.*;
import models.*;

public class RegisterController {

	private final RegisterView registerView;
	
	public RegisterController(RegisterView rv)
	{
		this.registerView = rv;
		
		registerView.addRegisterButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				User user = new User();
				user.setUsername(registerView.getUsername());
				user.setPassword(registerView.getPassword());
				
				if(new UserDataAccess().registerUser(user)) {
					JOptionPane.showMessageDialog(null, "Registered successfully. Returning to login page");
					registerView.setVisible(false);
					registerView.dispose();

					LoginView lv = new LoginView();
					LoginController lc = new LoginController(lv);
					lv.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "Registration failed");
				
			}
		});
		
		registerView.addLoginButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				registerView.setVisible(false);
				registerView.dispose();

				LoginView lv = new LoginView();
				LoginController lc = new LoginController(lv);
				
				// lv.setLocationRelativeTo(null);
				lv.setVisible(true);
				

			}
		});
	}
}
