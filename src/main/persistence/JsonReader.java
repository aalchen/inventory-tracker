package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.ItemList;
import org.json.*;

// Represents a reader that reads item list from JSON data stored in file
//I used the JsonSerializationDemo as a reference for this code
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads item list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ItemList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseItemList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses item list from JSON object and returns it
    private ItemList parseItemList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ItemList il = new ItemList(name);
        addItemList(il, jsonObject);
        return il;
    }

    // MODIFIES: il
    // EFFECTS: parses thingies from JSON object and adds them to item list
    private void addItemList(ItemList il, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(il, nextItem);
        }
    }

    // MODIFIES: il
    // EFFECTS: parses thingy from JSON object and adds it to item list
    private void addItem(ItemList il, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String category = jsonObject.getString("category");
        Double value = jsonObject.getDouble("value");
        String status = jsonObject.getString("status");
        il.addItem(name, category, status, value);
    }
}
