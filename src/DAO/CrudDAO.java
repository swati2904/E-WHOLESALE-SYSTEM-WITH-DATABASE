package DAO;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.sql.Date;
import model.Product;
import model.User;
import utility.ConnectionManager;

public class CrudDAO  implements CrudDaoInterface{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    ConnectionManager cm = new ConnectionManager();
    Connection con;
 
    // modify products specs
    public void updateProduct(String proid) throws Exception {
	System.out.println("Select What you want to update.");
	System.out.println("1. Product Name.");
	System.out.println("2. Product Price.");
	System.out.println("3. Product Description.");
	System.out.println("4. Delete Product.");
	int choice = Integer.parseInt(br.readLine().trim());
	switch(choice) {
	case 1:
	    // update name
	    System.out.println("Enter New Name  :  ");
	    String name = br.readLine().trim();
	    String updateName = "update product set name ='"+name+"' where id='"+proid+"'";
	    updateDB(updateName);
	    break;
	case 2:
	    // update price
	    System.out.println("Enter New Price  :  ");
	    int price = Integer.parseInt(br.readLine().trim());
	    String updatePrice = "update product set price = "+price+" where id='"+proid+"'";
	    updateDB(updatePrice);
	    break;
	case 3:
	    // update description
	    System.out.println("Enter New Description  :  ");
	    String desc = br.readLine().trim();
	    String updateDes = "update product set description = '"+desc+"' where id='"+proid+"'";
	    updateDB(updateDes);
	    break;
	case 4:
	    // delete item
	    try {
		String delete = "delete from product where id = '"+proid+"'";
		    updateDB(delete);
	    }catch(Exception e) {
		System.out.println("Item cannot be deleted because of maintaining order history.");
	    }
	    break;
	}
    }
    
    // execute statement to update database
    public void updateDB(String update) throws Exception {
	con = cm.getConnection();
	PreparedStatement ps = con.prepareStatement(update);
	    int x = ps.executeUpdate();
		if(x==1) {
		    System.out.println("Updated Successfully.");
		}
 }
 
    // getting result from database
    public ResultSet  getDB(String sql) throws Exception {
     	con = cm.getConnection();
	PreparedStatement ps = con.prepareStatement(sql);
	ResultSet rs = ps.executeQuery();
	return rs;
 }
 
    // display product list to user
    @Override
    public void displayProductlist() throws Exception {
	String sql = "select * from product";
	ResultSet rs = getDB(sql);
	int srNo = 1;
	while(rs.next()) {
	   String id = rs.getString(1);
	   String name = rs.getString(2);
	   int price = rs.getInt(3);
	   String desc = rs.getString(4);
	   System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
	   System.out.println(srNo+".\t "+"Product ID :  "+id+"\t\t Product Name :  "+name+"\t\t Price :  "+price+"\t\t Description :  "+desc);
	   srNo++;
	}
	   System.out.println();
	   System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    
    // add items to cart
    public ArrayList<Product> addTocart(String nm, ArrayList<Product> list,int num) throws Exception {
	    String sql = "select product.id,product.name,product.price,product.description,stock.quantity from product inner join stock on product.name = stock.stockid where product.name='"+nm+"'";
		ResultSet rs = getDB( sql);
		while(rs.next()) {
		   String id = rs.getString(1);
		   String name = rs.getString(2);
		   int price = rs.getInt(3);
		   String desc = rs.getString(4);
		   int qaunt = rs.getInt(5);
		   if(qaunt>=num) {
		       list.add(new Product(id,name,price*num, desc)); 
		   }else {
		       System.out.println("Stock not available");
		   }
		}
		return list;
    }
    
  
    // display user's cart and its item in cart
    public  int  displayCart(ArrayList<Product> cartlist) throws Exception{
	    String sql = null;
	    int cartTotal = 0; 
	    try {
		int lenOfList = cartlist.size();
		int n = 1;
		int itemprice=0;
		System.out.println();
		System.out.println("ITEMS IN CART : ");
		System.out.println();
		for (int i = 0; i < lenOfList; i++) {
			Product product = cartlist.get(i);
			String pid = product.getProductId();
			cartTotal = cartTotal+product.getPrice();
			sql = "select price from product where id = '"+pid+"'";
			 ResultSet rs = getDB(sql);
			 while(rs.next()) {
			     itemprice = rs.getInt(1);
			 }
			System.out.println("___________________________________________________________________________________________________________________________________________________________________________");
			System.out.println("itemNo. : "+(i+n)+"\tItem : " +product.getPrice()/itemprice+" piece of "+product.getProductName()+" \tPrice : "+product.getPrice()/itemprice+"x"+itemprice+" = "+product.getPrice()+" Rs. \tDescription : "+product.getDescription());
			
		}
		/// total amount to pay
		System.out.println();
		System.out.println("TOTAL CART VALUE : "+cartTotal);
		System.out.println();
	    	}catch(Exception e) {
		System.out.println("CART IS EMPTY.");
		System.out.println();
		}
	    return  cartTotal;
	}
	
    // remove item from cart
    public ArrayList<Product> removeItem(int n,ArrayList<Product> cartlist) {
	    int index = n-1;
	    cartlist.remove(index);
	    return cartlist;
	}
	
	
    // place order fuction and insert data into table / orders
    public String placeOrder(ArrayList<Product> cartlist,String custid) throws Exception {
	    LocalDate date = LocalDate.now();
	    String orderNo = generateOrderNo();
	    String sql = "insert into orders(orderno,custid,orderdate) values(?,?,?)";
		 con = cm.getConnection();
		 PreparedStatement ps = con.prepareStatement(sql);
		 ps.setString(1, orderNo);
		 ps.setString(2, custid);
		 ps.setDate(3,Date.valueOf(date));
		 int y =  ps.executeUpdate();
//		 ---------------------------------------------------------------------
//		if(y==1) {
//		    System.out.println("Order placed Successfuly.");
//		}
//		 -----------------------------------------------------------------------
		return orderNo;
	}
    
    
    //generate pdf bill
    public void pdfBillGeneration(ArrayList<Product> cartlist,User user) throws FileNotFoundException, DocumentException {

	LocalDate date = LocalDate.now(); 
	LocalDate bdate = (date);  
	Document document = new Document();
	String custid = user.getCustomerId();
	String name = user.getFirstName()+" "+user.getLastName();
	String mail = user.getEmailadd();
	String fno = user.getContact();
	String add = user.getAddress();
	int billno = 1;
	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/home/swati/eclipse-workspace/E-Whole-Sale-SYSTEM"+custid+billno+".pdf"));
	document.open();
	/// attributes
			document.addAuthor("E-WHOLE SALE ONLINE SHOPPING");
			document.addCreationDate();
			document.addCreator("E-WHOLE SALE");
			document.addTitle("SHOPPING BILL");
		try {
			int lenOfList = cartlist.size();
			int cartTotal = 0; 
			int n = 1;
			document.add(new Paragraph("Customer's bill copy"));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("============================================="));
			document.add(new Paragraph("ITEMS : "));
			document.add(new Paragraph(" "));
			for (int i = 0; i < lenOfList; i++) {
			    Product product = cartlist.get(i);
			    String pid = product.getProductId();
			    cartTotal = cartTotal+product.getPrice();
			    int itemprice = 0;
			    String sql = "select price from product where id = '"+pid+"'";
			    ResultSet rs = getDB(sql);
			    while(rs.next()) {
				     itemprice = rs.getInt(1);
				 }
				document.add(new Paragraph("itemNo. "+(i+n)+"\t \t Item :  " +product.getPrice()/itemprice+" Piece of "+product.getProductName()+" \t\tPrice : "+product.getPrice()/itemprice+"x"+itemprice+" = "+product.getPrice()+" Rs. \t\t Description : "+product.getDescription()));
				document.add(new Paragraph(" "));
			}
			document.add(new Paragraph("**********************************************************"));
			/// total amount to pay
			document.add(new Paragraph(" "));
			document.add(new Paragraph("PAID AMOUNT      :       "+cartTotal));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("CUSTOMER NAME : "+name));
			document.add(new Paragraph("EMAIL Address : "+mail));
			document.add(new Paragraph("Mobile Number : "+fno));
			document.add(new Paragraph("SHIPPING ADDRESS : "+add));
			document.add(new Paragraph("***********************************************************"));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("DATE      : "+bdate));
		}catch(Exception e) {
			System.out.println(e);
		}finally{
			billno++;
			document.close();
			writer.close();
    }}
	
    // update  stock details after order placed
    public void updateStock(ArrayList<Product> cartlist,String orderNo) throws Exception {
	    	for(int i=0;i<cartlist.size();i++) {
	    	    Product p = cartlist.get(i);
	    	    String nm = p.getProductName();
	    	    String prodid = p.getProductId();
	    	    String sql = "select product.price,stock.quantity from product inner join stock on product.name=stock.stockid where product.name='"+nm+"'";
	    	    //// getting quantity from stock
	    	    ResultSet rs = getDB(sql);
	    	    int quantity = 0;
	    	    int price = 0;
	    	    while(rs.next()) {
	    		price  = rs.getInt(1);
	    		quantity= rs.getInt(2);
	    	    }
	    	    int Amount = p.getPrice();
	    	    int no = Amount/price;
	    	    quantity = quantity-no;
	    	    String updatestock = "update  stock set quantity = ?  where stockid ='"+nm+"'";
	    	    con = cm.getConnection();
	    	    PreparedStatement ps1 = con.prepareStatement(updatestock);
	    	    ps1.setInt(1, quantity);
	    	    int y =ps1.executeUpdate();
//	    	    if(y==1) {
//			    System.out.println("Updated quantity in database.");
//			}
	    	    /// update table orderdetails
	    	    InsertOrderDetails(prodid,orderNo,no);
	    	}
	}
	
	
    // insert order details to database
    public void InsertOrderDetails(String prodid, String orderNo, int no) throws SQLException {
	    String insertOrderDetails = "insert into orderdetails(productid,orderid,quantity) values(?,?,?)";
    	    PreparedStatement ps2 = con.prepareStatement(insertOrderDetails);
    	    ps2.setString(1, prodid);
    	    ps2.setString(2, orderNo);
    	    ps2.setInt(3, no);
    	    int y = ps2.executeUpdate();
//    	    if(y==1) {
//    		System.out.println("update orderdetails.");
//    	    }
	}
	
    // generate unique order number to link with placed order  
    public String generateOrderNo() throws Exception {
		 int id = 0;
		 String sql = "select count(orderno)+1 from orders";
		     ResultSet rs = getDB(sql);
		     if(rs.next()) {
			 id = Integer.parseInt(rs.getString(1));
		     }
		     String ordNo = "order"+id;
		     return ordNo ;
	    }
	
    // update shipment details  in database
    public void shippingDetails(User user, String orderid, String shipid) throws SQLException {
	    LocalDate shipdate = LocalDate.now();
	    String shippingAddress = user.getAddress();
	    String contactNumber = user.getContact();
	    String sql = "insert into shipment(id,address,shipdate,contact,orderid) values(?,?,?,?,?)";
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setString(1, shipid);
	    ps.setString(2, shippingAddress);
	    ps.setDate(3, Date.valueOf(shipdate));
	    ps.setString(4,contactNumber);
	    ps.setString(5, orderid);
	    ps.executeUpdate();
	}
	
    // generate unique shipping id for order shipment
    public String generateShipId() throws Exception {
		 int id = 0;
		 String sql = "select count(orderno)+1 from orders";
		     ResultSet rs = getDB(sql);
		     if(rs.next()) {
			 id = Integer.parseInt(rs.getString(1));
		     }
		     String ShipNo = "ShipNo"+id;
		     return ShipNo ;
	    }
	
    // make payment and update details of payment in database
    public void payment(String type,String inv,String orderid,int cartTotal) throws Exception {
	    LocalDate pdate = LocalDate.now();
	    String sql = "insert into payment values(?,?,?,?,?)";
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setString(1, inv);
	    ps.setString(2, type);
	    ps.setDate(3, Date.valueOf(pdate));
	    ps.setInt(4, cartTotal);
	    ps.setString(5,orderid);
	    ps.executeUpdate();
	}
	
    // generate unique invoice number for payment 
    public String generateInvoice() throws Exception {
	    String sql = "select count(payno)+1 from payment";
	    int shipid = 0;
	     ResultSet rs = getDB(sql);
	     if(rs.next()) {
		 shipid = Integer.parseInt(rs.getString(1));
	     }
	    String inv = "INVO"+shipid;
	    return inv;
	}
}