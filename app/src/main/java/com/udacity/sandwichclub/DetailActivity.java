package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = this.getSupportActionBar();
        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    // Helper method to update the UI
    private void populateUI() {

        TextView mainName = findViewById(R.id.name_tv);
        mainName.setText(sandwich.getMainName());

        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        List<String> otherNamesList = sandwich.getAlsoKnownAs();
        StringBuilder otherNamesString = new StringBuilder();
        if (otherNamesList.isEmpty()){
            otherNamesString.toString();
        } else {
            otherNamesString.append(otherNamesList.get(0));
            for (int i = 1; i < otherNamesList.size(); i++){
                otherNamesString.append(", ").append(otherNamesList.get(i));
            }
        }
        alsoKnownAs.setText(otherNamesString);

        TextView placeOfOrigin = findViewById(R.id.place_of_origin_tv);
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());

        TextView description = findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());

        TextView ingredients = findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = sandwich.getIngredients();
        StringBuilder ingredientsString = new StringBuilder();
        if (ingredientsList.isEmpty()){
            ingredientsString.toString();
        } else {
            ingredientsString.append(ingredientsList.get(0));
            for (int i = 1; i < ingredientsList.size(); i++){
                ingredientsString.append(", ").append(ingredientsList.get(i));
            }
        }
        ingredients.setText(ingredientsString);

        ImageView image = findViewById(R.id.image_iv);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(image);
    }
}
