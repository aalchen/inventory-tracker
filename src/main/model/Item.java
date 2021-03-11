// To learn how to make doubles return 2 decimal points, I used mkyong.com as reference to use String.format

package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents the properties of an item (its name, category, status, and dollar value)
public class Item implements Writable {
    private String name;
    private String category;
    private double value;
    private String status;

    /*
     * MODIFIES: this
     * EFFECTS: Assigns variables to object, and capitalizes strings
     */
    public Item(String name, String category, String status, double value) {
        this.name = name.trim();
        this.value = value;
        this.category = category;
        this.status = status;
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
     * MODIFIES: this
     * EFFECTS: Changes existing value to the new value
     */
    public void setValue(double newValue) {
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
    public double getValue() {
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

    /*
     * EFFECTS: Returns as a JSON object
     */
    //I used the JsonSerializationDemo as a reference for this code
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", category);
        json.put("status", status);
        json.put("value", value);
        return json;
    }

}
