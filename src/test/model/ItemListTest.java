package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemListTest {
    private ItemList myItemList;
    private static final String initialName = "Thing 5";
    private static final String initialCategory = "Electronics";
    private static final String initialStatus = "Active";
    private static final int initialBal = 5;

    @BeforeEach
    public void runBefore() {
        myItemList = new ItemList();
    }

    @Test
    public void testInitialList() {
        assertEquals(0, myItemList.countListItems());

        myItemList.addItem(initialName, initialCategory, initialStatus, initialBal);
        assertEquals(initialName, myItemList.returnListItem(0).getName());
        assertEquals(initialCategory, myItemList.returnListItem(0).getCategory());
        assertEquals(initialStatus, myItemList.returnListItem(0).getStatus());
        assertEquals(initialBal, myItemList.returnListItem(0).getValue());
    }

    @Test
    public void testAddItem() {
        assertEquals(0, myItemList.countListItems());

        myItemList.addItem(initialName, initialCategory, initialStatus, initialBal);
        assertEquals(1, myItemList.countListItems());

        myItemList.addItem("Thing 17", "Clothing", "Sell", 70);
        assertEquals(2, myItemList.countListItems());
    }

    @Test
    public void testSearchCategory() {
        myItemList.addItem(initialName, initialCategory, initialStatus, initialBal);
        assertEquals("Thing 5\n", myItemList.searchItemCategory("Electronics"));

        myItemList.addItem("Thing 17", "Clothing", "Sell", 70);
        assertEquals("Thing 17\n", myItemList.searchItemCategory("Clothing"));
    }

    @Test
    public void testEditName() {
        myItemList.addItem(initialName, initialCategory, initialStatus, initialBal);
        assertEquals("Ting Five", myItemList.editItemName("Thing 5", "Ting Five"));

        myItemList.addItem("Thing 17", "Clothing", "Sell", 70);
        assertEquals("SEVENTEEN", myItemList.editItemName("Thing 17", "SEVENTEEN"));
    }

    @Test
    public void testDeleteName() {
        assertEquals(0, myItemList.countListItems());

        myItemList.addItem(initialName, initialCategory, initialStatus, initialBal);
        assertEquals(1, myItemList.countListItems());

        myItemList.delItemName("DummyItem");
        assertEquals(1, myItemList.countListItems());

        myItemList.delItemName(initialName);
        assertEquals(0, myItemList.countListItems());


    }

    @Test
    public void testPrintAll() {
        assertEquals("", myItemList.allListItems());

        myItemList.addItem(initialName, initialCategory, initialStatus, initialBal);
        assertEquals(initialName + " : " + initialCategory + ", "
                + initialStatus + ", " + "$" + initialBal + "\n", myItemList.allListItems());

        myItemList.addItem("Thing 17", "Clothing", "Sell", 70);
        assertEquals(initialName + " : " + initialCategory + ", "
                + initialStatus + ", " + "$" + initialBal + "\n"
                + "Thing 17 : Clothing, Sell, $" + 70 + "\n", myItemList.allListItems());
    }

    @Test
    public void testNameExists(){
        assertFalse(myItemList.nameExists("Test"));

        myItemList.addItem(initialName, initialCategory, initialStatus, initialBal);
        assertTrue(myItemList.nameExists(initialName));
        assertFalse(myItemList.nameExists("DummyItem"));
    }
}
