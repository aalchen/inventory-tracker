// To build this OrgApp class, I used TellerApp's UI class as a reference and source

package ui;

import model.ItemList;

import java.util.Scanner;

public class OrgApp {
    ItemList itemList = new ItemList();
    private Scanner input;
    private String delName;
    private String command;

    // EFFECTS: runs the Organization application
    public OrgApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        boolean keepGoing = true;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
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
        System.out.println("\tq -> quit\n");
    }

    // EFFECTS: initializes scanner
    private void init() {
        input = new Scanner(System.in);
        System.out.println("\nWelcome to Minimize!"
                + "\nHere, you can keep track of and be more mindful of the things that you own.\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
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
        } else {
            System.out.println("Selection is not valid...\n");
        }
    }

    /*
     * REQUIRES: Name provided is unique, value is 0 or above and a whole number
     * MODIFIES: this
     * EFFECTS: Add a new item to the list, given user inputs
     */
    private void addItem() {
        System.out.print("Enter name of the item: ");
        String itemName = input.next();

        System.out.print("Enter category of the item:\n");
        String itemCategory = categorySelection();

        System.out.print("Enter status of the item:\n");
        String itemStatus = itemStatus();

        System.out.print("Enter the value of the item: $");
        int amount = input.nextInt();

        if (amount >= 0) {
            itemList.addItem(itemName, itemCategory, itemStatus, amount);
            System.out.println("\nYour item " + itemName + " has been added.");
            System.out.println("\nHere is an updated list of all the items :");
            System.out.println(itemList.allListItems());
        } else {
            System.out.println("Cannot give an item a negative value...\n");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user command
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
        String selected = searchSelection();
        System.out.println("\nYour search result for category " + selected + " returns these items : ");
        if (itemList.countListItems() == 0) {
            System.out.println("Your list is empty!");
        } else {
            System.out.println("\nHere is a list of all the items : \n");
            System.out.println(itemList.allListItems());
        }
        return itemList.searchItemCategory(selected);
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user command
     */
    private String searchSelection() {
        String selection = "";

        while (!(selection.equals("f") || selection.equals("e") || selection.equals("c")
                || selection.equals("h") || selection.equals("o"))) {
            System.out.println("\tf for food");
            System.out.println("\te for electronics");
            System.out.println("\tc for clothing");
            System.out.println("\th for home");
            System.out.println("\to for other");
            selection = input.next();
        }

        if (selection.equals("f")) {
            return "Food";
        } else if (selection.equals("e")) {
            return "Electronics";
        } else if (selection.equals("c")) {
            return "Clothing";
        } else if (selection.equals("h")) {
            return "Home";
        } else {
            return "Other";
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user command
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
     * REQUIRES: Name must exist
     * MODIFIES: this
     * EFFECTS: After selecting existing item in list, provide new name, and update name of existing item with new
     */
    private String editItem() {
        String editName = "";
        String editNewName = "";
        if (itemList.countListItems() == 0) {
            System.out.println("Actually.. your list is now empty! There is nothing you can edit.");
        } else {
            System.out.println("\nHere is a list of all the items : \n");
            System.out.println(itemList.allListItems());
            System.out.println("Provide the name of the item you wish to edit");
            editName = input.next();

            if (itemList.nameExists(editName)) {
                return itemList.allListItems();
            } else {
                System.out.println("This item doesn't exist. Please try again. /n");
                return itemList.allListItems();
            }
        }
        return itemList.allListItems();
    }

    private void deleteItem() {
        if (itemList.countListItems() == 0) {
            System.out.println("Actually.. your list is empty! There's nothing to delete.\n");
        } else {
            System.out.println("\nHere is a list of all the items : \n");
            System.out.println(itemList.allListItems());
            System.out.println("Provide the name of the item you wish to delete");
            delName = input.next();

            if (itemList.nameExists(delName)) {
                itemList.delItemName(delName);

                System.out.println("\nYour item " + delName + " has been removed.");
                System.out.println("\nHere is an updated list of all the items : \n");
                if (itemList.countListItems() == 0) {
                    System.out.println("Actually.. your list is now empty!");
                } else {
                    System.out.println(itemList.allListItems());
                }
            } else {
                System.out.println("This item doesn't exist. Please try again. /n");
            }
        }
    }
}
