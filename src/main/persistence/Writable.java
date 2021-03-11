package persistence;

import org.json.JSONObject;

// Interface to ensure all subclasses are writable
//I used the JsonSerializationDemo as a reference for this code
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
