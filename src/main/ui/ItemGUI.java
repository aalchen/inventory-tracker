package ui;

import model.ItemList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.*;


//This class represents the GUI for my application
//I used the Oracle Java Documentation "ListDemo.java" and "BorderLayoutDemo.java" file as reference
//TODO: Add functionality for Edit, Search, reminder to save
//TODO: show all info for all items in the main display screen, instead of just item name (or see all details button)
public class ItemGUI extends JPanel
        implements ListSelectionListener {
    private JList list;
    private ItemList listModel;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private String[] itemCategory;
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
    private JLabel catName;
    private String selectedCategory;
    private String selectedStatus;
    private String updatedStatus;
    private String[] itemStatus;
    private JComboBox<String> itemCategoryList;
    private JComboBox<String> itemStatusList;
    private String updatedCategory;
    private DefaultListModel<String> modelName;
    private static final String JSON_STORE = "./data/itemlist.json";
    private JButton addButton;
    private AddListener addListener;
    private JPanel addPane;
    private JPanel secondPane;
    private JScrollPane listScrollPane;
    private JFrame frame;

    /*
     * MODIFIES: this
     * EFFECTS: Constructor of the GUI, and establishing JSON file location
     */
    public ItemGUI() {

        super(new BorderLayout());

        frame = new JFrame("Minimize");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        showItems();

        makeButton();
        makeInputBoxes();

        addPane = new JPanel();
        addPane.setLayout(new BoxLayout(addPane,
                BoxLayout.LINE_AXIS));

        secondPane = new JPanel();
        secondPane.setLayout(new BoxLayout(secondPane,
                BoxLayout.LINE_AXIS));

        buttonPanelUI();

        frameSetUp();

        frame.pack();
        frame.setVisible(true);

    }

    /*
     * MODIFIES: this
     * EFFECTS: Actual items in the list show up on GUI, and creates initial list with minimum dimensions
     */
    public void showItems() {
//        DefaultTableModel tableModel = new DefaultTableModel();
//        JTable table = new JTable(tableModel);
//        tableModel.addColumn("Item Name");
        listModel = new ItemList("Amy's List");
        listModel.addItem("Cheese", "Food", "Keep", 9.99);
        listModel.addItem("Sweater", "Clothing", "Sell", 15);

        modelName = new DefaultListModel<>();
        for (int i = 0; i < listModel.countListItems(); i++) {
            modelName.addElement(listModel.returnListItem(i).getName());
        }
        list = new JList(modelName);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(15);
        listScrollPane = new JScrollPane(list);
        listScrollPane.setMinimumSize(new Dimension(300, 300));
        listScrollPane.setPreferredSize(new Dimension(300, 300));
    }

    /*
     * MODIFIES: this
     * EFFECTS: Organizing the main frame with JPanel components
     */
    public void frameSetUp() {
        frame.setLayout(new BorderLayout());

        frame.getContentPane().add(listScrollPane, BorderLayout.PAGE_START);
        frame.getContentPane().add(addPane, BorderLayout.CENTER);
        frame.getContentPane().add(secondPane, BorderLayout.PAGE_END);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Plays the sound given the name of a wav file
     */
    // I got help for this code via http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Creating buttons, then assigning corresponding actions and listeners
     */
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

    /*
     * MODIFIES: this
     * EFFECTS: Creating input boxes for the add item button
     */
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

    /*
     * MODIFIES: this
     * EFFECTS: Organization of the GUI pane
     */
    public void buttonPanelUI() {
        newName = new JLabel("Name:");
        newCategory = new JLabel("Category:");
        newStatus = new JLabel("Status:");
        newValue = new JLabel("Value ($):");
        addPane.add(Box.createHorizontalStrut(5));
        addPane.add(newName);
        addPane.add(itemName);
        addPane.add(newCategory);
        comboBoxCategory();
        addPane.add(newStatus);
        comboBoxStatus();
        addPane.add(newValue);
        addPane.add(valueNum);
        addPane.add(addButton);

        secondPane.add(deleteButton);
        secondPane.add(editButton);
        secondPane.add(new JSeparator(SwingConstants.VERTICAL));
        secondPane.add(saveButton);
        secondPane.add(loadButton);
        secondPane.add(new JSeparator(SwingConstants.VERTICAL));
        secondPane.add(viewButton);
//        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    /*
     * MODIFIES: this
     * EFFECTS: Combo box for category, with all the available selection items
     */
    //I also used the Oracle Java Documentation "ComboBoxDemo.java" file as reference
    private void comboBoxCategory() {
        itemCategory = new String[]{"Food", "Electronics",
                "Clothing", "Home", "Other"};

        itemCategoryList = new JComboBox<>(itemCategory);
        itemCategoryList.setSelectedIndex(0);
        updatedCategory = listModel.returnListItem(0).getCategory();
        itemCategoryList.addActionListener(
                e -> {
                    JComboBox combo = (JComboBox) e.getSource();
                    updatedCategory = (String) combo.getSelectedItem();
                });

        addPane.add(itemCategoryList);
        selectedCategory = itemCategoryList.getSelectedItem().toString();
        itemCategoryList.setName("");

    }


    /*
     * MODIFIES: this
     * EFFECTS: Combo box for status, with all the available selection items
     */
    private void comboBoxStatus() {
        itemStatus = new String[]{"Keep", "Sell",
                "Sold"};

        itemStatusList = new JComboBox<>(itemStatus);
        itemStatusList.setSelectedIndex(0);
        updatedStatus = listModel.returnListItem(0).getStatus();
        itemStatusList.addActionListener(
                e -> {
                    JComboBox combo = (JComboBox) e.getSource();
                    updatedStatus = (String) combo.getSelectedItem();
                });

        addPane.add(itemStatusList);
        selectedStatus = itemStatusList.getSelectedItem().toString();
        itemStatusList.setName("");

    }


    /*
     * MODIFIES: this
     * EFFECTS: Delete the item from list selected by the cursor, and plays a sound
     */
    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.delItemName(listModel.returnListItem(index).getName());
            modelName.remove(index);

            int size = listModel.countListItems();

            if (size == 0) {
                deleteButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.countListItems()) {
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
            playSound("jasmine.wav");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Edit the item selected by the cursor, if edit button is clicked
     */
    class EditListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JPanel buttonPane = new JPanel();
            JOptionPane.showMessageDialog(buttonPane,
                    "editing...");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Show all items in the item list in an dialog box
     */
    class ViewListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JPanel buttonPane = new JPanel();
            JOptionPane.showMessageDialog(buttonPane,
                    listModel.allListItems());
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Load JSON file from memory, and create dialog box to advise user that it is loading
     */
    class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loadItemList();
            JPanel buttonPane = new JPanel();
            JOptionPane.showMessageDialog(buttonPane, "Loaded!");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Save JSON file to memory, and create dialog box to advise user that it successfully saved
     */
    class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            saveItemList();
            JPanel buttonPane = new JPanel();
            JOptionPane.showMessageDialog(buttonPane,
                    "Saved!");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Save JSON file to memory
     */
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

    /*
     * MODIFIES: this
     * EFFECTS: Load JSON file from memory
     */
    private void loadItemList() {
        try {
            listModel = jsonReader.read();
            modelName.clear();
            for (int i = 0; i < listModel.countListItems(); i++) {
                modelName.addElement(listModel.returnListItem(i).getName());
            }
            System.out.println("Loaded " + listModel.getName() + " from " + JSON_STORE + "\n");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Add the items via the text and combo boxes into the item list, and makes a noise
     */
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            String name = itemName.getText();
            String status = statusName.getText();
            Double value = Double.parseDouble(valueNum.getText());

            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                itemName.requestFocusInWindow();
                itemName.selectAll();
                return;
            }

            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            listModel.addItem(name, updatedCategory, updatedStatus, value);

            modelName.insertElementAt(name, index);

            resetValues();

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
            playSound("gia.wav");


        }

        /*
         * MODIFIES: this
         * EFFECTS: Sets all fields to empty
         */
        public void resetValues() {
            itemName.requestFocusInWindow();
            itemName.setText("");
            categoryName.setText("");
            statusName.setText("");
            valueNum.setText("");
        }

        /*
         * EFFECTS: Check if an item name is already in the list
         */
        protected boolean alreadyInList(String name) {
            return listModel.nameExists(name);
        }

        /*
         * EFFECTS: Insert an update; required by DocumentListener
         */
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        /*
         * EFFECTS: Remove an update; required by DocumentListener
         */
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        /*
         * EFFECTS: Change an update; required by DocumentListener
         */
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        /*
         * MODIFIES: this
         * EFFECTS: Enabling the button; required by DocumentListener
         */
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        /*
         * MODIFIES: this
         * EFFECTS: Enabling button if it has values, else disable button; required by DocumentListener
         */
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Adjusting values, and enabling delete button if an item exists; required by DocumentListener
     */
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                deleteButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                deleteButton.setEnabled(true);
            }
        }
    }

}

