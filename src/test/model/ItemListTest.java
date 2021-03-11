package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Tests for my ItemList class
public class ItemListTest {
    private ItemList myItemList;
    private static final String NAME = "Thing 5";
    private static final String CATEGORY = "Electronics";
    private static final String STATUS = "Keep";
    private static final double BALANCE = 5.00;
    private static final String ITEMLISTNAME = "Amy's Inventory";

    @BeforeEach
    public void runBefore() {
        myItemList = new ItemList(ITEMLISTNAME);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, myItemList.countListItems());

        myItemList.addItem(NAME, CATEGORY, STATUS, BALANCE);
        assertEquals(NAME, myItemList.returnListItem(0).getName());
        assertEquals(CATEGORY, myItemList.returnListItem(0).getCategory());
        assertEquals(STATUS, myItemList.returnListItem(0).getStatus());
        assertEquals(BALANCE, myItemList.returnListItem(0).getValue());
    }

    @Test
    public void testAddItem() {
        assertEquals(0, myItemList.countListItems());

        myItemList.addItem(NAME, CATEGORY, STATUS, BALANCE);
        assertEquals(1, myItemList.countListItems());
        assertTrue(myItemList.nameExists(NAME));

        myItemList.addItem("Thing 17", "Clothing", "Sell", 70);
        assertEquals(2, myItemList.countListItems());
        assertTrue(myItemList.nameExists("Thing 17"));
    }

    @Test
    public void testSearchCategory() {
        myItemList.addItem(NAME, CATEGORY, STATUS, BALANCE);
        assertEquals("Thing 5\n", myItemList.searchItemCategory("Electronics"));

        myItemList.addItem("Thing 17", "Clothing", "Sell", 70);
        assertEquals("Thing 17\n", myItemList.searchItemCategory("Clothing"));
    }

    @Test
    public void testEditName() {
        myItemList.addItem(NAME, CATEGORY, STATUS, BALANCE);
        assertEquals("Ting Five", myItemList.editItemName("Thing 5", "Ting Five"));

        myItemList.addItem("Thing 17", "Clothing", "Sell", 70);
        assertEquals("SEVENTEEN", myItemList.editItemName("Thing 17", "SEVENTEEN"));

        assertEquals("", myItemList.editItemName("Does Not Exist", "SEVENTEEN"));
    }

    @Test
    public void testDeleteName() {
        assertEquals(0, myItemList.countListItems());

        myItemList.addItem(NAME, CATEGORY, STATUS, BALANCE);
        assertEquals(1, myItemList.countListItems());
        assertTrue(myItemList.nameExists(NAME));

        myItemList.delItemName("DummyItem");
        assertEquals(1, myItemList.countListItems());
        assertFalse(myItemList.nameExists("DummyItem"));

        myItemList.delItemName(NAME);
        assertEquals(0, myItemList.countListItems());
        assertFalse(myItemList.nameExists(NAME));


    }

    @Test
    public void testPrintAll() {
        assertEquals("", myItemList.allListItems());

        myItemList.addItem(NAME, CATEGORY, STATUS, BALANCE);
        assertEquals(NAME + " : " + CATEGORY + ", "
                + STATUS + ", " + "$" + 5 + "\n", myItemList.allListItems());

        myItemList.addItem("Thing 17", "Clothing", "Sell", 29.99);
        assertEquals(NAME + " : " + CATEGORY + ", "
                + STATUS + ", " + "$" + 5 + "\n"
                + "Thing 17 : Clothing, Sell, $" + 29.99 + "\n", myItemList.allListItems());
    }

    @Test
    public void testNameExists(){
        assertFalse(myItemList.nameExists("Test"));

        myItemList.addItem(NAME, CATEGORY, STATUS, BALANCE);
        assertTrue(myItemList.nameExists(NAME));
        assertFalse(myItemList.nameExists("DummyItem"));
    }

    @Test
    public void testEditCategory(){
        myItemList.addItem(NAME, CATEGORY, STATUS, BALANCE);
        assertEquals(CATEGORY,myItemList.editItemCategory(NAME, CATEGORY));
        assertEquals("Clothing",myItemList.editItemCategory(NAME, "Clothing"));
        assertEquals("Home",myItemList.editItemCategory(NAME, "Home"));
        assertEquals("",myItemList.editItemCategory("Does not exist", "Electronics"));

    }

    @Test
    public void testEditStatus(){
        myItemList.addItem(NAME, CATEGORY, STATUS, BALANCE);
        assertEquals(STATUS,myItemList.editItemStatus(NAME, STATUS));
        assertEquals("Sell",myItemList.editItemStatus(NAME, "Sell"));
        assertEquals("Sold",myItemList.editItemStatus(NAME, "Sold"));
        assertEquals("",myItemList.editItemStatus("Does not exist", "Sold"));

    }

    @Test
    public void testEditValue(){
        myItemList.addItem(NAME, CATEGORY, STATUS, BALANCE);
        assertEquals(BALANCE,myItemList.editItemVal(NAME, BALANCE));
        assertEquals(99,myItemList.editItemVal(NAME, 99));
        assertEquals(160,myItemList.editItemVal(NAME, 160));
        assertEquals(59.99,myItemList.editItemVal(NAME, 59.99));
        assertEquals(100.0,myItemList.editItemVal("Does not exist", 100));
    }


}
