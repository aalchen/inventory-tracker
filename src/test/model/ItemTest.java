package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Tests for my Item class
class ItemTest {
    private Item myItem;
    private static final String NAME = "Thing 1";
    private static final String STATUS = "Active";
    private static final String CATEGORY = "Clothing";
    private static final int BALANCE = 5;

    @BeforeEach
    public void runBefore() {
        myItem = new Item(NAME, CATEGORY, STATUS, BALANCE);
    }

    @Test
    public void testGetInitialCard() {
        assertEquals(NAME, myItem.getName());
        assertEquals(BALANCE, myItem.getValue());
        assertEquals(STATUS, myItem.getStatus());
        assertEquals(CATEGORY, myItem.getCategory());
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