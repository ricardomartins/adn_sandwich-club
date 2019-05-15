package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonSandwich = new JSONObject(json);
            JSONObject name = jsonSandwich.getJSONObject("name");

            return new Sandwich(
                    name.getString("mainName"),
                    extractStringList(name.getJSONArray("alsoKnownAs")),
                    jsonSandwich.getString("placeOfOrigin"),
                    jsonSandwich.getString("description"),
                    jsonSandwich.getString("image"),
                    extractStringList(jsonSandwich.getJSONArray("ingredients"))
            );
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> extractStringList(JSONArray jsonArray) throws JSONException {
        int size = jsonArray.length();
        List<String> strings = new ArrayList<>(size);

        for (int i = 0; i < size; i++) strings.add(jsonArray.getString(i));

        return strings;
    }
}
