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
        //Fix This
        return allParts.get(0);
    }

    public Part lookupPart(String partName) {
        //Fix this
        return allParts.get(0);
    }

    public Product lookupProduct(int productId) {
        //FIX ME!!!!
        return allProducts.get(0);
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
        //FIX ME!
    }

    public boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    public boolean deleteProduct(Product selectedProduct) {
        ///FFIIIIIIIZXXXZXZX
        return false;
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
