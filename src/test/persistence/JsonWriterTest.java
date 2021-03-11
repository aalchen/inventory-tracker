package persistence;

import model.ItemList;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

//Tests for JsonWriter class
//I used the JsonSerializationDemo as a reference for this code
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ItemList il = new ItemList("My item list");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyItemList() {
        try {
            ItemList il = new ItemList("My item list");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyItemList.json");
            writer.open();
            writer.write(il);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyItemList.json");
            il = reader.read();
            assertEquals("My item list", il.getName());
            assertEquals(0, il.countListItems());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralItemList() {
        try {
            ItemList il = new ItemList("My item list");
            il.addItem("Shirt", "Clothing", "Keep", 30);
            il.addItem("Kettle", "Home", "Sell", 15);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralItemList.json");
            writer.open();
            writer.write(il);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralItemList.json");
            il = reader.read();
            assertEquals("My item list", il.getName());
            assertEquals(2, il.countListItems());
            checkItem("Shirt", il.returnListItem(0));
            checkItem("Kettle", il.returnListItem(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}