package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

//Represents a list of the items in your inventory, with multiple possible operations to change or represent the list
public class ItemList implements Writable {
    private String listName;
    private String name;
    private String checkName;
    private String delItem;
    private ArrayList<Item> itemList;

    // EFFECTS: constructs item list with a name and empty list of thingies
    public ItemList(String listName) {
        this.listName = listName;
        itemList = new ArrayList<>();
    }

    /*
     * MODIFIES: this
     * EFFECTS: Add an item to the list, given the name, category, status, and value of said item
     */
    public void addItem(String name, String category, String status, double value) {
        itemList.add(new Item(name, category, status, value));
    }

    /*
     * EFFECTS: Returns the name of all items in this list
     */
    public String allListItems() {
        String allList = "";
        for (int i = 0; i < itemList.size(); i++) {
            allList = allList + itemList.get(i).getName() + " : ";
            allList = allList + itemList.get(i).getCategory() + ", ";
            allList = allList + itemList.get(i).getStatus() + ", ";
            //This piece of code uses Oracle's documentation regarding NumberFormat as a reference
            NumberFormat properFormat = NumberFormat.getNumberInstance(new Locale("en", "CA"));
            properFormat.setMaximumFractionDigits(2);
            DecimalFormat df = (DecimalFormat) properFormat;
            String newVal = df.format(itemList.get(i).getValue());
            allList = allList + "$" + newVal + "\n";
        }
        return allList;
    }

    /*
     * EFFECTS: Gets the length of the list
     */
    public int countListItems() {
        return itemList.size();
    }

    /*
     * EFFECTS: Gets an item in a list, given an index value
     */
    public Item returnListItem(int val) {
        return itemList.get(val);
    }

    /*
     * EFFECTS: Returns all items in the list, given a particular category
     */
    public String searchItemCategory(String searchCategory) {
        String searchCat = "";
        searchCategory = searchCategory;
        for (int i = 0; i < itemList.size(); i++) {
            if (searchCategory.equals(itemList.get(i).getCategory())) {
                searchCat = searchCat + itemList.get(i).getName() + "\n";
            }
        }
        return searchCat;
    }

    /*
     * REQUIRES: Name being searched is case sensitive
     * MODIFIES: this
     * EFFECTS: Updates the name of an existing item in the list, to a new name
     */
    public String editItemName(String name, String newName) {
        String editItem = "";
        for (int i = 0; i < itemList.size(); i++) {
            if (name.equals(itemList.get(i).getName())) {
                itemList.get(i).setName(newName.trim());
                editItem = editItem + itemList.get(i).getName();
            }
        }
        return editItem;
    }

    /*
     * REQUIRES: Name being searched is case sensitive
     * MODIFIES: this
     * EFFECTS: Given a name, updates the category to the new category
     */
    public String editItemCategory(String name, String category) {
        String editItem = "";
        for (int i = 0; i < itemList.size(); i++) {
            if (name.equals(itemList.get(i).getName())) {
                itemList.get(i).setCategory(category.trim());
                editItem = editItem + itemList.get(i).getCategory();
            }
        }
        return editItem;
    }

    /*
     * REQUIRES: Name being searched is case sensitive
     * MODIFIES: this
     * EFFECTS: Given a name, updates the status to the new status
     */
    public String editItemStatus(String name, String status) {
        String editItem = "";
        for (int i = 0; i < itemList.size(); i++) {
            if (name.equals(itemList.get(i).getName())) {
                itemList.get(i).setStatus(status.trim());
                editItem = editItem + itemList.get(i).getStatus();
            }
        }
        return editItem;
    }

    /*
     * REQUIRES: Name being searched is case sensitive
     * MODIFIES: this
     * EFFECTS: Given a name, updates the value to the new value
     */
    public double editItemVal(String name, double val) {
        double editItem = val;
        for (int i = 0; i < itemList.size(); i++) {
            if (name.equals(itemList.get(i).getName())) {
                itemList.get(i).setValue(val);
                editItem = itemList.get(i).getValue();
            }
        }
        return editItem;
    }

    /*
     * MODIFIES: this
     * EFFECTS: Deletes the item corresponding to the name provided from the list
     */
    public String delItemName(String name) {
        for (int i = 0; i < itemList.size(); i++) {
            if (name.equals(itemList.get(i).getName())) {
                itemList.remove(i);
            }
        }
        return delItem;
    }

    /*
     * EFFECTS: Checks if, given an item name, it exists in the list
     */
    public Boolean nameExists(String checkName) {
        for (int i = 0; i < itemList.size(); i++) {
            if (checkName.trim().equals(itemList.get(i).getName())) {
                return true;
            }
        }
        return false;
    }

    /*
     * EFFECTS: Returns as a JSON object
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", listName);
        json.put("items", itemListToJson());
        return json;
    }

    // EFFECTS: returns things in this item list as a JSON array
    private JSONArray itemListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item t : itemList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    /*
     * EFFECTS: Return name associated with the list of items
     */
    public String getName() {
        return listName;
    }
}

