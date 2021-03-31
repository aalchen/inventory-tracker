package ui;

import exceptions.DuplicateNameException;
import model.ItemList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

//Represents a console based UI for my Organization App, to interact with the items in the item list
//To build this OrgApp class, I used TellerApp's UI class as a reference and source
//I used the JsonSerializationDemo to ensure the Json reading/writing was working
public class OrgApp {
    ItemList itemList = new ItemList("Amy's Item List");
    private Scanner input;
    private String delName;
    private String command;
    private static final String JSON_STORE = "./data/itemlist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Organization application
    public OrgApp() throws DuplicateNameException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runApp();
    }

    // EFFECTS: saves the item list to file
    private void saveItemList() {
        try {
            jsonWriter.open();
            jsonWriter.write(itemList);
            jsonWriter.close();
            System.out.println("Saved " + itemList.getName() + " to " + JSON_STORE + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads item list from file
    private void loadItemList() {
        try {
            itemList = jsonReader.read();
            System.out.println("Loaded " + itemList.getName() + " from " + JSON_STORE + "\n");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() throws DuplicateNameException {
        boolean keepGoing = true;
        init();
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                System.out.println("\nDid you remember to save your file?\n");
                System.out.println("Type 'sa' to save your file, or 'q' again to quit for good.\n");
                String finalAction = input.next();
                if (finalAction.equals("q")) {
                    keepGoing = false;
                } else if (finalAction.equals("sa")) {
                    System.out.println("Thanks for saving! Quitting now...");
                    saveItemList();
                    keepGoing = false;
                }
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nBye bye!");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("Select an option below :\n");
        System.out.println("\ta -> add an item");
        System.out.println("\ts -> search for a category");
        System.out.println("\te -> edit an item");
        System.out.println("\tv -> view all items");
        System.out.println("\td -> delete an item");
        System.out.println("\tsa -> save item list to file");
        System.out.println("\tl -> load item list from file");
        System.out.println("\tq -> quit\n");
    }

    // EFFECTS: initializes scanner and provides welcome message
    private void init() {
        input = new Scanner(System.in);
        System.out.println("\nWelcome to Minimize!"
                + "\nHere, you can keep track of and be more mindful of the things that you own.\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) throws DuplicateNameException {
        if (command.equals("a")) {
            addItem();
        } else if (command.equals("s")) {
            searchItem();
        } else if (command.equals("e")) {
            editItem();
        } else if (command.equals("v")) {
            viewItem();
        } else if (command.equals("d")) {
            deleteItem();
        } else if (command.equals("q")) {
            System.out.println("quit");
        } else if (command.equals("sa")) {
            saveItemList();
        } else if (command.equals("l")) {
            loadItemList();
        } else {
            System.out.println("Selection is not valid...\n");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Add a new item to the list, given user inputs
     */
    private void addItem() throws DuplicateNameException {
        System.out.print("Enter name of the item: ");
        String itemName = input.next();
        itemName += input.nextLine();

        if (itemList.nameExists(itemName)) {
            System.out.println("This name already exists, choose another name.\n");
        } else {
            System.out.print("Enter category of the item:\n");
            String itemCategory = categorySelection();
            System.out.print("Enter status of the item:\n");
            String itemStatus = itemStatus();
            System.out.print("Enter the value of the item: $");
            double amount = input.nextDouble();

            if (amount >= 0) {
                itemList.addItem(itemName, itemCategory, itemStatus, amount);
                System.out.println("\nYour item " + itemName + " has been added.");
                System.out.println("\nHere is an updated list of all the items :");
                System.out.println(itemList.allListItems());
            } else {
                System.out.println("Cannot give an item a negative value...\n");
            }
        }
    }

    /*
     * EFFECTS: prompts user to select which category to select, and returns it
     */
    private String categorySelection() {
        String categorySel = "";
        while (!(categorySel.equals("f") || categorySel.equals("e") || categorySel.equals("c")
                || categorySel.equals("h") || categorySel.equals("o"))) {
            System.out.println("\tf for food");
            System.out.println("\te for electronics");
            System.out.println("\tc for clothing");
            System.out.println("\th for home");
            System.out.println("\to for other");
            categorySel = input.next();
        }

        if (categorySel.equals("f")) {
            return "Food";
        } else if (categorySel.equals("e")) {
            return "Electronics";
        } else if (categorySel.equals("c")) {
            return "Clothing";
        } else if (categorySel.equals("h")) {
            return "Home";
        } else {
            return "Other";
        }
    }


    /*
     * MODIFIES: this
     * EFFECTS: Given a category name, return names of all items in list
     */
    private String searchItem() {
        System.out.println("Please enter the category you wish to search for :");
        String selected = categorySelection();
        System.out.println("\nYour search result for category " + selected + " returns these items : ");
        if (itemList.countListItems() == 0) {
            System.out.println("Your list is empty!\n");
        } else {
            if (itemList.searchItemCategory(selected).length() == 0) {
                System.out.println("Nothing is in this category.\n");
            } else {
                System.out.println(itemList.searchItemCategory(selected));
            }
        }
        return itemList.searchItemCategory(selected);
    }


    /*
     * EFFECTS: prompts user to select which status to choose, and returns it
     */
    private String itemStatus() {
        String statusSel = "";

        while (!(statusSel.equals("k") || statusSel.equals("se") || statusSel.equals("so"))) {
            System.out.println("\tk for keep");
            System.out.println("\tse for sell");
            System.out.println("\tso for sold");
            statusSel = input.next();
        }

        if (statusSel.equals("k")) {
            return "Keep";
        } else if (statusSel.equals("se")) {
            return "Sell";
        } else {
            return "Sold";
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Return names of all items in the list
     */
    private String viewItem() {
        if (itemList.countListItems() == 0) {
            System.out.println("Your list is empty!");
        } else {
            System.out.println("\nHere is a list of all the items : \n");
            System.out.println(itemList.allListItems());
        }
        return itemList.allListItems();
    }

    /*
     * MODIFIES: this
     * EFFECTS: Given name of item to edit, will make the appropriate changes
     */
    private String editItem() throws DuplicateNameException {
        String editName = "";
        String editNewName = "";
        if (itemList.countListItems() == 0) {
            System.out.println("Actually.. your list is now empty! There is nothing you can edit.");
        } else {
            System.out.println("\nHere is a list of all the items : \n");
            System.out.println(itemList.allListItems());
            System.out.println("Provide the name of the item you wish to edit");
            editName = input.next();
            editName += input.nextLine();

            checkEditField(editName);

            System.out.println("\nHere is a list of all the items : \n");
            System.out.println(itemList.allListItems());
        }
        return itemList.allListItems();
    }

    /*
     * MODIFIES: this
     * EFFECTS: Check if the item exists in list, then triggers action based on field requested to edit
     */
    private void checkEditField(String editName) throws DuplicateNameException {
        if (itemList.nameExists(editName)) {
            System.out.println("\nWhat do you want to edit?");
            String editField = editSelection();
            if (editField.equals("name")) {
                updateName(editName);
            } else if (editField.equals("category")) {
                updateCategory(editName);
            } else if (editField.equals("status")) {
                updateStatus(editName);
            } else {
                updateValue(editName);
            }
        } else {
            System.out.println("This item doesn't exist. Please try again. \n");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Update name given new input string
     */
    private void updateName(String editName) throws DuplicateNameException {
        String editNewName = "";
        System.out.println("Provide the new name you wish to use:");
        editNewName = input.next();
        editNewName += input.nextLine();
        if (itemList.nameExists(editNewName)) {
            System.out.println("This name already exists, choose another name.");
        } else {
            itemList.editItemName(editName, editNewName);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Update category given new selection
     */
    private void updateCategory(String editName) {
        System.out.println("Provide the new category you wish to use:");
        String newCategory = categorySelection();
        itemList.editItemCategory(editName, newCategory);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Update status given new selection
     */
    private void updateStatus(String editName) {
        System.out.println("Provide the new status:");
        String newStatus = itemStatus();
        itemList.editItemStatus(editName, newStatus);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Update value given new number
     */
    private void updateValue(String editName) {
        System.out.println("Provide the new value:");
        double newAmount = input.nextDouble();

        if (newAmount >= 0) {
            itemList.editItemVal(editName, newAmount);
            System.out.println("\nHere is an updated list of all the items :");
            System.out.println(itemList.allListItems());
        } else {
            System.out.println("Cannot give an item a negative value...\n");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Deletes item from list, given a name
     */
    private void deleteItem() throws DuplicateNameException {
        if (itemList.countListItems() == 0) {
            System.out.println("Actually.. your list is empty! There's nothing to delete.\n");
        } else {
            System.out.println("\nHere is a list of all the items : \n");
            System.out.println(itemList.allListItems());
            System.out.println("Provide the name of the item you wish to delete");
            delName = input.next();
            delName += input.nextLine();

            if (itemList.nameExists(delName)) {
                itemList.delItemName(delName);

                System.out.println("\nYour item " + delName + " has been removed.");
                System.out.println("\nHere is an updated list of all the items : \n");
                if (itemList.countListItems() == 0) {
                    System.out.println("Actually.. your list is now empty!\n");
                } else {
                    System.out.println(itemList.allListItems());
                }
            } else {
                System.out.println("This item doesn't exist. Please try again.\n");
            }
        }
    }

    /*
     * EFFECTS: prompts user to select which field to edit, and returns it
     */
    private String editSelection() {
        String fieldSel = "";

        while (!(fieldSel.equals("n") || fieldSel.equals("c") || fieldSel.equals("s") || fieldSel.equals("v"))) {
            System.out.println("\tn for name");
            System.out.println("\tc for category");
            System.out.println("\ts for status");
            System.out.println("\tv for value");
            fieldSel = input.next();
        }

        if (fieldSel.equals("n")) {
            return "name";
        } else if (fieldSel.equals("c")) {
            return "category";
        } else if (fieldSel.equals("s")) {
            return "status";
        } else {
            return "value";
        }
    }


}