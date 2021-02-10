package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private Item myItem;
    private static final String initialName = "Thing 1";
    private static final String initialStatus = "Active";
    private static final String initialCategory = "Clothing";
    private static final int initialBal = 5;

    @BeforeEach
    public void runBefore() {
        myItem = new Item(initialName, initialCategory, initialStatus, initialBal);
    }

    @Test
    public void testGetInitialCard() {
        assertEquals(initialName, myItem.getName());
        assertEquals(initialBal, myItem.getValue());
        assertEquals(initialStatus, myItem.getStatus());
        assertEquals(initialCategory, myItem.getCategory());
    }

    @Test
    public void testSetValues() {
        myItem.setName("Thing 2");
        assertEquals("Thing 2", myItem.getName());

        myItem.setCategory("Electronics");
        assertEquals("Electronics", myItem.getCategory());

        myItem.setStatus("Sell");
        assertEquals("Sell", myItem.getStatus());

        myItem.setValue(123);
        assertEquals(123, myItem.getValue());
    }
}