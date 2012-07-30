package com.dhemery.serializing;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//todo: Throw exception instead of returning null. Will need to give users a way to determine whether an element exists.

/**
 * Uses Gson to retrieve information from a JSON representation of an object.
 */
public class GsonJsonInspector implements JsonInspector {
    private final JsonElement root;

    /**
     * Create a Gson-based JSON inspector to inspect the object represented by the given JSON string.
     */
    public GsonJsonInspector(String jsonString) {
        root = new JsonParser().parse(jsonString);
    }

    @Override
    public Set<String> names(Object... path) {
        JsonElement element = element(root, path);
        if(element == null || !element.isJsonObject()) return Collections.emptySet();
        Set<Map.Entry<String, JsonElement>> entries = element.getAsJsonObject().entrySet();
        Set<String> keys = new HashSet<String>();
        for(Map.Entry<String,JsonElement> entry : entries) {
            keys.add(entry.getKey());
        }
        return keys;
    }

    @Override
    public Integer size(Object... path) {
        JsonElement element = element(root, path);
        if(element == null || !element.isJsonArray()) return 0;
        return element.getAsJsonArray().size();
    }

    @Override
    public String stringValue(Object... path) {
        return asString(element(root, path));
    }

    private JsonElement child(JsonElement element, Object path) {
        if(path instanceof String) return childByName(element, (String) path);
        if(path instanceof Integer) return childByIndex(element, (Integer) path);
        return null;
    }

    private JsonElement childByIndex(JsonElement element, Integer index) {
        if(!element.isJsonArray()) return null;
        JsonArray array = element.getAsJsonArray();
        if(index >= array.size()) return null;
        return array.get(index);
    }

    private JsonElement childByName(JsonElement element, String name) {
        if(!element.isJsonObject()) return null;
        JsonObject object = element.getAsJsonObject();
        if(!object.has(name)) return null;
        return object.get(name);
    }

    private JsonElement element(JsonElement element, Object...specifiers) {
        JsonElement specifiedElement = element;
        for(Object specifier : specifiers) {
            specifiedElement = child(specifiedElement, specifier);
            if(specifiedElement == null)  return null;
        }
        return specifiedElement;
    }

    private String asString(JsonElement element) {
        if(element == null) return null;
        return element.getAsString();
    }
}
