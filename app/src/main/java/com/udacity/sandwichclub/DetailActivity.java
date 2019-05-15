package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return; // Didn't try it, but I guess a null pointer exception would happen without this return
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        Sandwich sandwich = getSandwichAtIndex(position);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private Sandwich getSandwichAtIndex(int sandwichIndex) {
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[sandwichIndex];
        return JsonUtils.parseSandwichJson(json);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        setTitle(sandwich.getMainName());

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_hamburger)
                .into(ingredientsIv);

        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        TextView alsoKnownLabelTextView = findViewById(R.id.also_known_as_label);
        TextView alsoKnownTextView = findViewById(R.id.also_known_tv);
        if (alsoKnownAs != null && !alsoKnownAs.isEmpty()) {
            alsoKnownTextView.setText(TextUtils.join(", ", alsoKnownAs));
            alsoKnownTextView.setVisibility(View.VISIBLE);
            alsoKnownLabelTextView.setVisibility(View.VISIBLE);
        } else {
            alsoKnownTextView.setVisibility(View.GONE);
            alsoKnownLabelTextView.setVisibility(View.GONE);
        }

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        TextView originLabelTextView = findViewById(R.id.place_of_origin_label);
        TextView originTextView = findViewById(R.id.origin_tv);
        if (placeOfOrigin != null && !placeOfOrigin.isEmpty()) {
            originTextView.setText(placeOfOrigin);
            originTextView.setVisibility(View.VISIBLE);
            originLabelTextView.setVisibility(View.VISIBLE);
        } else {
            originTextView.setVisibility(View.GONE);
            originLabelTextView.setVisibility(View.GONE);
        }

        String description = sandwich.getDescription();
        TextView descriptionLabelTextView = findViewById(R.id.description_label);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        if (description != null && !description.isEmpty()) {
            descriptionTextView.setText(description);
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionLabelTextView.setVisibility(View.VISIBLE);
        } else {
            descriptionTextView.setVisibility(View.GONE);
            descriptionLabelTextView.setVisibility(View.GONE);
        }

        List<String> ingredients = sandwich.getIngredients();
        TextView ingredientsLabelTextView = findViewById(R.id.ingredients_label);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        if (ingredients != null && !ingredients.isEmpty()) {
            ingredientsTextView.setText(TextUtils.join(", ", ingredients));
            ingredientsTextView.setVisibility(View.VISIBLE);
            ingredientsLabelTextView.setVisibility(View.VISIBLE);
        } else {
            ingredientsTextView.setVisibility(View.GONE);
            ingredientsLabelTextView.setVisibility(View.GONE);
        }
    }
}
