package model;

import java.util.ArrayList;

//This ItemList class represents a list of the Items, with possible operations (ex. add, delete, edit, and more)
public class ItemList {
    private ArrayList<Item> itemList = new ArrayList<>();
    private String checkName;
    private String delItem;

    /*
     * REQUIRES: Name must be unique, dollar value must be a non-negative integer
     * MODIFIES: this
     * EFFECTS: Add an item to the list, given the name, category, status, and value of said item
     */
    public void addItem(String name, String category, String status, int value) {
        itemList.add(new Item(name, category, status, value));
    }

    /*
     * EFFECTS: Returns the name of all items in this list
     */
    //TODO : clean this up + add more tests
    public String allListItems() {
        String allList = "";
        for (int i = 0; i < itemList.size(); i++) {
            allList = allList + itemList.get(i).getName() + " : ";
            allList = allList + itemList.get(i).getCategory() + ", ";
            allList = allList + itemList.get(i).getStatus() + ", ";
            allList = allList + "$" + itemList.get(i).getValue() + "\n";
        }
        return allList;
    }

    /*
     * EFFECTS: Returns the size of the list
     */
    public int countListItems() {
        return itemList.size();
    }

    /*
     * EFFECTS: Returns an item in a list, given an index value
     */
    public Item returnListItem(int val) {
        return itemList.get(val);
    }

    /*
     * EFFECTS: Returns all items in the list, given a particular category
     */
    //TODO: also be able to search by type, status, or name
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
     * REQUIRES: New name must be unique, name being searched is case sensitive
     * MODIFIES: Item, this
     * EFFECTS: Updates the name of an existing item in the list, to a new name
     */
    //TODO : be able to edit any field, not just name + update README
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
     * REQUIRES: Name is be case sensitive
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
     * EFFECTS: Checks if, given an item name, it exists in the list. Name is case sensitive.
     */
    public Boolean nameExists(String checkName) {
        for (int i = 0; i < itemList.size(); i++) {
            if (checkName.trim().equals(itemList.get(i).getName())) {
                return true;
            }
        }
        return false;
    }

}

