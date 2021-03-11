package persistence;

import model.ItemList;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

//Tests for JsonReader class
//I used the JsonSerializationDemo as a reference for this code
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ItemList il = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyItemList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyItemList.json");
        try {
            ItemList il = reader.read();
            assertEquals("My item list", il.getName());
            assertEquals(0, il.countListItems());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralItemList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralItemList.json");
        try {
            ItemList il = reader.read();
            assertEquals("My item list", il.getName());
            assertEquals(2, il.countListItems());
            checkItem("Shirt", il.returnListItem(0));
            checkItem("Kettle", il.returnListItem(1));

            assertEquals(2, il.countListItems());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}