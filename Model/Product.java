package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    public ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    /**
     * @param id Unique ID int to set
     * @param name String of name for product
     * @param price double price of product
     * @param stock int count of stock
     * @param min int minimum stock
     * @param max int maximum
     */
    public Product(int id, String name, double price, int stock, int min, int max)  {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return return ID of product
     */
    public int getId() {
        return id;
    }

    /**
     * @param id Set ID of product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Get name of product
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Set string name of product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Get Price of product
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price Set the price of product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return Returns the stock count of product
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock Set inventory count of product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return Returns minimum number of product
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min Sets minimum of product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return Gets the maximum of product
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max Set maximum number of product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part Takes part, and adds to an observable list variable associated parts
     */
    public void addAssociatedPart(Part part) {
        try {
            associatedParts.add(part);
        } catch (Exception e){
            associatedParts = FXCollections.observableArrayList(part);
        }
    }

    /**
     * @param selectedAssociatedPart Part to be removed from association
     * @return Exception means the part was unable to be removed, return false
     * true means the removal worked
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        try {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @return Returns the associatedParts variable
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
}
