package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /** Tag for the log messages */
    public static final String LOG_TAG = JsonUtils.class.getSimpleName();

    private static Sandwich sandwich;

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        // Try to parse the JSON response.
        try {
            JSONObject rootJsonObject = new JSONObject(json);
            JSONObject name = rootJsonObject.optJSONObject("name");

            // Extract the value for the key called "mainName"
            String mainName = name.optString("mainName");

            // Create an ArrayList for "also known as"
            List<String> alsoKnownAs = new ArrayList<>();
            JSONArray alsoKnownAsArray = name.optJSONArray("alsoKnownAs");
            for (int j = 0; j < alsoKnownAsArray.length(); j++) {
                    alsoKnownAs.add(alsoKnownAsArray.optString(j));
                }

            // Extract the value for the key called "placeOfOrigin"
            String placeOfOrigin = rootJsonObject.optString("placeOfOrigin");

            // Extract the value for the key called "description"
            String description = rootJsonObject.optString("description");

            // Extract the value for the key called "image"
            String image = rootJsonObject.optString("image");

            // Create an ArrayList for "ingredients"
            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientsArray = rootJsonObject.optJSONArray("ingredients");
            for (int j = 0; j < ingredientsArray.length(); j++) {
                ingredients.add(ingredientsArray.optString(j));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        }
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON response", e);
        }
        return sandwich;
    }
}
