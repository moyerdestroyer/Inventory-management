package Model;

public class OutsourcedPart extends Part {
    private String companyName;


    /**
     * @param id set unique ID
     * @param name String of the name
     * @param price double price of part
     * @param stock int inventory of stock
     * @param min int minimum of part
     * @param max int maximum of part
     * @param companyName String with the company name of the manufacturer
     */
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param companyName Sets compannyName string variable
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return returns the company name variable
     */
    public String getCompanyName() {
        return companyName;
    }
}
