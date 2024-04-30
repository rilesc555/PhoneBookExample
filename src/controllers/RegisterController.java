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
				
				// UserDataAccess uda = new UserDataAccess();
				
				if(new UserDataAccess().registerUser(user))
					JOptionPane.showMessageDialog(null, "Registered successfully");
				else
					JOptionPane.showMessageDialog(null, "Registeration failed");
				
			}
		});
		
		registerView.addLoginButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				LoginView lv = new LoginView();
				LoginController lc = new LoginController(lv);
				
				// lv.setLocationRelativeTo(null);
				lv.setVisible(true);
				
				registerView.setVisible(false);
			}
		});
	}
}
