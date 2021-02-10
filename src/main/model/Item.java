package model;

import static com.sun.xml.internal.ws.util.StringUtils.capitalize;

public class Item {
    private String name;
    private String category;
    private int value;
    private String status;

    /*
     * MODIFIES: this
     * EFFECTS: Assigns variables to object, and capitalizes strings
     */
    public Item(String name, String category, String status, int value) {
        this.name = capitalize(name).trim();
        this.value = value;
        this.category = capitalize(category);
        this.status = capitalize(status);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Changes existing name to the new name
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /*
     * MODIFIES: this
     * EFFECTS: Changes existing category to the new name
     */
    public void setCategory(String newCategory) {
        this.category = newCategory;
    }

    /*
     * REQUIRES: Value cannot be in the negative, must be 0 or above and whole numbers
     * MODIFIES: this
     * EFFECTS: Changes existing value to the new value
     */
    public void setValue(int newValue) {
        this.value = newValue;
    }

    /*
     * MODIFIES: this
     * EFFECTS: Changes existing status to the new status
     */
    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    /*
     * EFFECTS: Return the name of the item
     */
    public String getName() {
        return name;
    }

    /*
     * EFFECTS: Return the value of the item
     */
    public int getValue() {
        return value;
    }

    /*
     * EFFECTS: Return the status of the item
     */
    public String getStatus() {
        return status;
    }

    /*
     * EFFECTS: Return the category of the item
     */
    public String getCategory() {
        return category;
    }
}
