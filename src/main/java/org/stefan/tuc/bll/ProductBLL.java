package org.stefan.tuc.bll;

import org.stefan.tuc.dao.ProductDAO;
import org.stefan.tuc.model.Product;
import org.stefan.tuc.presentation.GenerateTable;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents the class where it handles the processing data of Product.
 */
public class ProductBLL {

    private final ProductDAO productDAO;

    /**
     * Creates a ProductDAO constructor.
     */
    public ProductBLL()  {
        productDAO = new ProductDAO();
    }

    /**
     * Finds a product by his id.
     * @param id The id of the product.
     * @return The instance of the product.
     */
    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id =" + " was not found!");
        }

        return product;
    }

    /**
     * Finds all the products in the database.
     * @return A list containing details about the products.
     */
    public List<Product> findAllProducts() {
        List<Product> products = productDAO.findAll();
        if (products == null || products.isEmpty()) {
            throw new NoSuchElementException("There are no products in the database.");
        }

        return products;
    }

    /**
     * If a product with the name specified doesn't exist then
     *      Creates a new {@link Product}
     * Else
     *      Updates the existing {@link Product}
     *
     * @param name The name of the product.
     * @param quantity The quantity of the product.
     * @param price The price of the product.
     */
    public Product insertProduct(String name, String price, String quantity) {
        Product product = productDAO.findByName(name);
        boolean productAlreadyExists = true;
        if (product == null) {
            product = new Product();
            productAlreadyExists = false;
        }

        product.setName(name);
        product.setPrice(Double.parseDouble(price));

        if (product.getQuantity() != 0) {
            product.setQuantity(product.getQuantity() + Integer.parseInt(quantity));
        } else {
            product.setQuantity(Integer.parseInt(quantity));
        }

        if (productAlreadyExists) {
            productDAO.update(product);
        } else {
            productDAO.insert(product);
        }

        return product;
    }

    /**
     * Deletes the {@link Product} with the name specified if it exists.
     * @param name The name of the product to be deleted.
     */
    public void delete(String name) {
        productDAO.deleteByName(name);
    }

    /**
     Generates a product table.
     Retrieves all products from the database, converts them into a list,
     and generates a table based on the retrieved data.
     */
    public static void generateProductTable() {
        ProductDAO productDAO1 = new ProductDAO();
        GenerateTable table = new GenerateTable();
        List<Product> products = productDAO1.findAll();
        products.forEach(i -> {
            i.name1 = i.getName();
            i.id1 = i.getId();
        });
        table.generateTable(products, productDAO1);
    }
}
