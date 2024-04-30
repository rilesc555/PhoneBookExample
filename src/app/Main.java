package app;

import views.*;
import controllers.*;

public class Main {

	public static void main(String args []) {
		
		LoginView loginView = new LoginView();
		LoginController loginController = new LoginController(loginView);
		loginView.setVisible(true);
		
		// loginView.setLocationRelativeTo(null);
		
		
		
//		RegisterView rv = new RegisterView();
//		RegisterContoller rc = new RegisterContoller(rv);
//		rv.setVisible(true);
	}
}
