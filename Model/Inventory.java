package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    //## Functions ##//

    public void addPart(Part newPart) {
        try {
            allParts.add(newPart);
        } catch (Exception e){
            allParts = FXCollections.observableArrayList(newPart);
        }
    }

    public void addProduct(Product newProduct) {
        try {
            allProducts.add(newProduct);
        } catch (Exception e) {
            allProducts = FXCollections.observableArrayList(newProduct);
        }
    }

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

    public Part lookupPart(String partName) {
        //Fix this
        return allParts.get(0);
    }

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

    public Product lookupProduct(String productName) {
        //Fix ME!
        return allProducts.get(0);
    }

    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
        System.out.println("Part updated");
    }

    public void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
        System.out.println("Product Updated");
    }

    public boolean deletePart(Part selectedPart) {
        try {
            allParts.remove(selectedPart);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteProduct(Product selectedProduct) {
        try {
            allProducts.remove(selectedProduct);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
