package ui;

import model.ItemList;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

//This class represents the GUI for my application
//I used the Oracle Java Documentation "ListDemo.java" file as reference

//TODO: drop down input for category and status
//TODO: restrict value input to only non-negative numbers
//TODO: Formatting for text input and buttons
//TODO: Add functionality for Edit, Delete, Search, Load
//TODO: show all info for all items in the main display screen, instead of just item name (or see all details button)
public class TestGUI extends JPanel
        implements ListSelectionListener {
    private JList list;
    private ItemList listModel;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final String addString = "Add Item";
    private static final String deleteString = "Delete Item";
    private static final String editString = "Edit Item";
    private static final String loadString = "Load List";
    private static final String saveString = "Save List";
    private static final String viewString = "View List";
    private JButton deleteButton;
    private JButton editButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton viewButton;
    private JTextField itemName;
    private JTextField categoryName;
    private JTextField statusName;
    private JTextField valueNum;
    private JLabel newName;
    private JLabel newCategory;
    private JLabel newStatus;
    private JLabel newValue;
    private DefaultListModel<String> modelName;
    ItemList itemList = new ItemList("Amy's Item List");
    private static final String JSON_STORE = "./data/itemlist.json";
    JButton addButton;
    AddListener addListener;
    JPanel buttonPane;

    //turn into double later
//    private JTextField valueNum;


    public TestGUI() {
        super(new BorderLayout());

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        listModel = new ItemList("Amy's List");
        listModel.addItem("Cheese", "Food", "Keep", 9.99);
        listModel.addItem("Sweater", "Clothing", "Sell", 15);

        makeTable();

        //Create the list and put it in a scroll pane.
        list = new JList(modelName);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(15);
        JScrollPane listScrollPane = new JScrollPane(list);

        makeButton();
        makeInputBoxes();

        //Create a panel that uses BoxLayout.
        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));

        buttonPanelUI();

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    public void makeTable() {
        //TODO: put into DefaultTableModel
//        DefaultTableModel tableModel = new DefaultTableModel();
//        JTable table = new JTable(tableModel);

        modelName = new DefaultListModel<>();
        for (int i = 0; i < listModel.countListItems(); i++) {
            modelName.addElement(listModel.returnListItem(i).getName());
        }
    }

    public void makeButton() {
        addButton = new JButton(addString);
        addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        deleteButton = new JButton(deleteString);
        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(new DeleteListener());

        editButton = new JButton(editString);
        editButton.setActionCommand(editString);
        editButton.addActionListener(new EditListener());

        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());

        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());

        viewButton = new JButton(viewString);
        viewButton.setActionCommand(viewString);
        viewButton.addActionListener(new ViewListener());
    }

    public void makeInputBoxes() {
        itemName = new JTextField(10);
        itemName.addActionListener(addListener);
        itemName.getDocument().addDocumentListener(addListener);
        String name = listModel.returnListItem(
                list.getSelectedIndex()).toString();

        categoryName = new JTextField(10);
        categoryName.addActionListener(addListener);
        categoryName.getDocument().addDocumentListener(addListener);

        statusName = new JTextField(10);
        statusName.addActionListener(addListener);
        statusName.getDocument().addDocumentListener(addListener);

        valueNum = new JTextField(10);
        valueNum.addActionListener(addListener);
        valueNum.getDocument().addDocumentListener(addListener);
    }

    public void buttonPanelUI() {
        newName = new JLabel("Name:");
        newCategory = new JLabel("Category:");
        newStatus = new JLabel("Status:");
        newValue = new JLabel("Value ($):");
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(newName);
        buttonPane.add(itemName);
        buttonPane.add(newCategory);
        buttonPane.add(categoryName);
        buttonPane.add(newStatus);
        buttonPane.add(statusName);
        buttonPane.add(newValue);
        buttonPane.add(valueNum);
        buttonPane.add(addButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(deleteButton);
        buttonPane.add(editButton);
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(saveButton);
        buttonPane.add(loadButton);
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(viewButton);
//        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.

            int index = list.getSelectedIndex();
            listModel.delItemName(listModel.returnListItem(index).getName());
            modelName.remove(index);

            int size = listModel.countListItems();

            if (size == 0) { //Nobody's left, disable firing.
                deleteButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.countListItems()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    class EditListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JPanel buttonPane = new JPanel();
            JOptionPane.showMessageDialog(buttonPane,
                    "editing...");
        }
    }

    class ViewListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JPanel buttonPane = new JPanel();
            JOptionPane.showMessageDialog(buttonPane,
                    listModel.allListItems());
        }
    }

    class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loadItemList();
            JPanel buttonPane = new JPanel();
            JOptionPane.showMessageDialog(buttonPane,
                    "Loading... ");
        }
    }

    class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            saveItemList();
            JPanel buttonPane = new JPanel();
            JOptionPane.showMessageDialog(buttonPane,
                    "Saved!");
        }
    }

    private void saveItemList() {
        try {
            jsonWriter.open();
            jsonWriter.write(listModel);
            jsonWriter.close();
            System.out.println("Saved " + listModel.getName() + " to " + JSON_STORE + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void loadItemList() {
        try {
            listModel = jsonReader.read();
            System.out.println("Loaded " + listModel.getName() + " from " + JSON_STORE + "\n");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //This listener is shared by the text field and the hire button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = itemName.getText();
            String category = categoryName.getText();
            String status = statusName.getText();
            Double value = Double.parseDouble(valueNum.getText());

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                itemName.requestFocusInWindow();
                itemName.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.addItem(name, category, status, value);

            modelName.insertElementAt(name, index);

            //Reset the text field
            resetValues();

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        public void resetValues() {
            itemName.requestFocusInWindow();
            itemName.setText("");
            categoryName.setText("");
            statusName.setText("");
            valueNum.setText("");
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.nameExists(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                deleteButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                deleteButton.setEnabled(true);
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Minimize");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new TestGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
