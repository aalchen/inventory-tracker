package persistence;

import model.Item;
import model.ItemList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Test for Json
//I used the JsonSerializationDemo as a reference for this code
public class JsonTest {
    protected void checkItem(String name, Item item) {
        assertEquals(name, item.getName());
    }
}
