package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    /**
     * @param newPart adds a new Part in the observablelist allParts
     */
    public void addPart(Part newPart) {
        try {
            allParts.add(newPart);
        } catch (Exception e){
            allParts = FXCollections.observableArrayList(newPart);
        }
    }

    /**
     * @param newProduct add new product to the observablelist variable
     */
    public void addProduct(Product newProduct) {
        try {
            allProducts.add(newProduct);
        } catch (Exception e) {
            allProducts = FXCollections.observableArrayList(newProduct);
        }
    }

    /**
     * @param partId Id of part you want to search for
     * @return returns the part indexed at 0 by default
     */
    public Part lookupPart(int partId) {
        //will return product at index 0 by default
        int indexOfMatch = 0;
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == partId) {
                indexOfMatch = i;
            }
        }
        return allParts.get(indexOfMatch);
    }

    /**
     * @param partName Name of part you are looking for
     * @return returns part identified by name
     */
    public Part lookupPart(String partName) {
        //What is this used for
        return allParts.get(0);
    }

    /**
     * @param productId ID of product to lookup
     * @return product searched for by ID
     */
    public Product lookupProduct(int productId) {
        //will return product at index 0 by default
        int indexOfMatch = 0;
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == productId) {
                indexOfMatch = i;
            }
        }
        return allProducts.get(indexOfMatch);
    }

    /**
     * @param productName NAme of product to look for
     * @return returns the entire product of lookup result
     */
    public Product lookupProduct(String productName) {
        //Idk what this is for
        return allProducts.get(0);
    }

    /**
     * @param index the get(?) index of part you wish to replace
     * @param selectedPart new part to be placed into the allParts Observablelist
     */
    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
        System.out.println("Part updated");
    }

    /**
     * @param index index location of the product you wish to update
     * @param newProduct new product to replace the previous one
     */
    public void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
        System.out.println("Product Updated");
    }

    /**
     * @param selectedPart attempts to remove a part from list
     * @return true if deletion successful, false if deletion failed
     */
    public boolean deletePart(Part selectedPart) {
        try {
            allParts.remove(selectedPart);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param selectedProduct Product to be deleted
     * @return true if deletion successful, false if there is an error
     */
    public boolean deleteProduct(Product selectedProduct) {
        try {
            allProducts.remove(selectedProduct);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @return Returns observablelist allParts
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return returns Observablelist allProducts
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
