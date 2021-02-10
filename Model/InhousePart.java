package Model;

public class InhousePart extends Part {
    private int machineId;


    /**
     * @param id Create a unique int ID
     * @param name String containing name
     * @param price Double containing price of part
     * @param stock int with stock/inventory
     * @param min int minimum of part
     * @param max int maximum of part
     * @param machineId int machine ID of the producing machine
     */
    public InhousePart(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId Inhouse part function for setting the machineID int
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * @return int machine ID
     */
    public int getMachineId() {
        return machineId;
    }
}
