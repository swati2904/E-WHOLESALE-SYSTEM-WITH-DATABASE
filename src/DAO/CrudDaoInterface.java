package DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.itextpdf.text.DocumentException;

import model.Product;
import model.User;

public interface CrudDaoInterface {
    // modify products specs
    public void updateProduct(String proid) throws NumberFormatException, IOException, Exception;
    
    // display product list to user
    public void displayProductlist() throws Exception;
    
    // execute statement to update database
    public void updateDB(String update) throws Exception;
    
    // getting result from database
    public ResultSet  getDB(String sql) throws Exception ;
    
    // add items to cart
    public ArrayList<Product> addTocart(String nm, ArrayList<Product> list,int num) throws Exception ;
    
    // display user's cart and its item in cart
    public  int  displayCart(ArrayList<Product> cartlist) throws Exception;
    
  
    // remove item from cart
    public ArrayList<Product> removeItem(int n,ArrayList<Product> cartlist);
    
    // place order fuction and insert data into table / orders
    public String placeOrder(ArrayList<Product> cartlist,String custid) throws Exception;
    
    //generate pdf bill
    public void pdfBillGeneration(ArrayList<Product> cartlist,User user) throws FileNotFoundException, DocumentException;
    
    // update  stock details after order placed
    public void updateStock(ArrayList<Product> cartlist,String orderNo) throws Exception;
    
    // insert order details to database
    public void InsertOrderDetails(String prodid, String orderNo, int no) throws SQLException;
    
    // generate unique order number to link with placed order  
    public String generateOrderNo() throws Exception;
    
    // update shippment details  in database
    public void shippingDetails(User user, String orderid, String shipid) throws SQLException;
    
    // generate unique shipping id for order shipment
    public String generateShipId() throws Exception;
    
    // make payment and update details of payment in database
    public void payment(String type,String inv,String orderid,int cartTotal) throws Exception;
    
    // generate unique invoice number for payment 
    public String generateInvoice() throws Exception ;
}
