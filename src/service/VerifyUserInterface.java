package service;

import java.sql.SQLException;

import model.User;

public interface VerifyUserInterface {

    // ---------------------- VERIFACTION ON EXITING USERS -----------------
    public boolean login(String name,String pass) throws Exception;
    
   // ---------------- FETCHING THE DATA FROM THE DATABASE -------------------
    public User loadUserdetails(String name , String pass) throws SQLException, Exception;
    
    // ------------- SETTING THE USER DETAILS FOR THE USER CLASS ----------
    public User loadCustomerDetails(User user) throws Exception;
    
}
