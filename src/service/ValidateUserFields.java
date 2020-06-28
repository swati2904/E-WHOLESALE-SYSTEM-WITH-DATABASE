package service;

public class ValidateUserFields {
	// --------------------- VALIDATION ON USERNAME -------------------------------
	
	public boolean validateUserName(String username) {
		boolean captial = false;
		boolean small = false;
		char c;
		for (int i = 0; i < username.length(); i++) {
			c = username.charAt(i);
			if (Character.isUpperCase(c)) {
				captial = true;
			} else if (Character.isLowerCase(c)) {
				small = true;
			}
			if (captial && small) {
				return true;
			}
		}
		return false;
	}

	// --------------------- VALIDATION ON PASSWORD-------------------------------

	public boolean validatePass(String password) {

		boolean number = false;
		boolean captial = false;
		boolean small = false;
		char c;
		for (int i = 0; i < password.length(); i++) {
			c = password.charAt(i);
			if (Character.isDigit(c)) {
				number = true;
			} else if (Character.isUpperCase(c)) {
				captial = true;
			} else if (Character.isLowerCase(c)) {
				small = true;
			}
			if (number && captial && small) {
				return true;
			}
		}
		return false;
	}

	// ------------------- CHECK THE LENGTH OF THE USERNAME --------------------------
	
	public boolean lengthUserName(String username) {
		if (username.length() > 5) {
			if (validateUserName(username)) {
				return true;
			} else {
				System.out.println("Username must contain at least one Uppercase and Lowercase character.");
				return false;
			}
		} else {
			System.out.println("Username must have more than five charaters");
			return false;
		}
	}

	// ------------------- CHECK THE LENGTH OF THE PASSWORD --------------------------
	
	public boolean lengthPass(String password) {
		if (password.length() > 6) {
			if (validatePass(password)) {
				return true;
			} else {
				System.out.println("Password must contain at least one Upper,Lower case and a Number.");
				return false;
			}
		} else {
			System.out.println("Password must have more than six charaters");
			return false;
		}
	}

	//----------------------- CHECKING USERNAME AND PASSWORD FOR NER CUSTOMER--------------------
	
	public boolean checkUserDetails(String username, String password) {
		boolean name = false;
		boolean pass = false;
		if (validatePass(password)) {
			pass = true;
		}
		if (validateUserName(username)) {
			name = true;
		}
		if (name && pass) {
			return true;
		} else {
			return false;
		}
	}

	// --------------------------------- FOR USER REGISTRATION ---------------------------------
	
	public boolean newRegistration(String username, String password) {
		if (checkUserDetails(username, password)) {
			System.out.println("");
			System.out.println("\t \t Successfully Registered!!!!");
			System.out.println("\t \t -----------------------------------------");
			System.out.println("\t \t Please, enter the additional information.");
			System.out.println();
			return true;
		}
		return false;
	}

}
