package DAO;

import model.Product;

public interface ProductDaoInterface {
    
    // adding product to the database table
    public void addProduct(Product product, int quantity) throws Exception ;
    
    // adding item stocks to database table
    public void addStock(Product product, int quantity) throws Exception;

}
