package DAO;

import java.sql.ResultSet;

import model.User;

public interface UserDAOInterface {

    // ------- UPDATE USER AND CUSTOMERS DETAILS AFTER REGISTRATION --------
    public void insertUserDetails(User user) throws Exception;
    
    // ------------------- FETCHING THE DATA FROM THE DATABASE ------------
    public ResultSet getDb(String sql) throws Exception;
    
    //------------------ GENERATE UNIQUE ID FOR EACH USER ------------------
    public int generateUserId() throws Exception;
    
    // ----------------- GENERATE UNIQUE CUSTOMER ID FOR EACH CUSTOMER -----
    public String generateCustomerId() throws Exception;
    
    // -------------- FETCHING USER DETAILS FROM THE DATABASE --------------
    public void fetchUserDetails(String custid) throws Exception ;
    
    // ------------ FETCHING ORDER HISTORY FROM THE DATABSE ----------------
    public void fetchOrderHistory(String custid) throws Exception ;
      
}
