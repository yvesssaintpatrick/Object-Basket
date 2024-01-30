package com.example.objectbasket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class Cosmetics extends AppCompatActivity{
    public SharedPreferences themePreferences;
    public SharedPreferences basketPreferences;
    public SharedPreferences objectPreferences;
    private static final int CATEGORY_SPORTS = 1;
    private static final int CATEGORY_HOUSEHOLD = 2;
    private static final int CATEGORY_FRUITS = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetics);

        themePreferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        basketPreferences = getSharedPreferences("basket_prefs", MODE_PRIVATE);
        objectPreferences = getSharedPreferences("object_prefs", MODE_PRIVATE);


        // Set onClickListener for theme images
        ImageView imageTheme1 = findViewById(R.id.imageTheme1);
        imageTheme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setThemeImage(R.drawable.theme1, imageTheme1);

            }
        });

        ImageView imageTheme2 = findViewById(R.id.imageTheme2);
        imageTheme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setThemeImage(R.drawable.theme2, imageTheme2);

            }
        });

        ImageView imageTheme3 = findViewById(R.id.imageTheme3);
        imageTheme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setThemeImage(R.drawable.default_theme, imageTheme3);

            }
        });

        // Set onClickListener for basket images
        ImageView imageBasket1 = findViewById(R.id.imageBasket1);
        imageBasket1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBasket(R.drawable.basket1, imageBasket1);

            }
        });

        ImageView imageBasket2 = findViewById(R.id.imageBasket2);
        imageBasket2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBasket(R.drawable.basket2, imageBasket2);

            }
        });

        ImageView imageBasket3 = findViewById(R.id.imageBasket3);
        imageBasket3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBasket(R.drawable.default_basket, imageBasket3);

            }
        });


        // Set onClickListener for object categories
        ImageView objectCategory1 = findViewById(R.id.imageObject1);
        objectCategory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setObjectCategory(1 , objectCategory1);
            }
        });

        ImageView objectCategory2 = findViewById(R.id.imageObject2);
        objectCategory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setObjectCategory(2 , objectCategory2);
            }
        });

        ImageView objectCategory3 = findViewById(R.id.imageObject3);
        objectCategory3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setObjectCategory(3, objectCategory3);
            }
        });


    }

    public void setThemeImage(int themeResId, ImageView imageView) {
        SharedPreferences.Editor editor = themePreferences.edit();
        editor.putInt("selected_theme", themeResId);
        editor.apply();

        // Deselect all theme images
        ImageView[] themeImages = {findViewById(R.id.imageTheme1), findViewById(R.id.imageTheme2),findViewById(R.id.imageTheme3)};
        for (ImageView themeImage : themeImages) {
            themeImage.setSelected(false);
        }

        // Highlight the selected theme
        imageView.setSelected(true);


    }

    public void setBasket(int basketResId, ImageView imageView) {
        // Retrieve the previous basket size and position
        float previousBasketSize = basketPreferences.getFloat("basket_size", 0);
        float previousBasketPositionX = basketPreferences.getFloat("basket_position_x", 0);
        float previousBasketPositionY = basketPreferences.getFloat("basket_position_y", 0);

        // Save the new basket information
        SharedPreferences.Editor editor = basketPreferences.edit();
        editor.putInt("selected_basket", basketResId);
        // Use the previous size and position for the new basket
        editor.putFloat("basket_size", previousBasketSize);
        editor.putFloat("basket_position_x", previousBasketPositionX);
        editor.putFloat("basket_position_y", previousBasketPositionY);
        editor.apply();

        // Deselect all basket images
        ImageView[] basketImages = {findViewById(R.id.imageBasket1), findViewById(R.id.imageBasket2),findViewById(R.id.imageBasket3)};
        for (ImageView basketImage : basketImages) {
            basketImage.setSelected(false);
        }

        // Highlight the selected basket
        imageView.setSelected(true);
    }

    public void setObjectCategory(int category, ImageView imageView) {
        SharedPreferences.Editor editor = objectPreferences.edit();
        editor.putInt("selected_object_category", category);
        editor.apply();


        ImageView[] objectCategoryViews = {findViewById(R.id.imageObject1), findViewById(R.id.imageObject2), findViewById(R.id.imageObject3)};
        for (ImageView objectCategoryView : objectCategoryViews) {
            objectCategoryView.setSelected(false);
        }

        imageView.setSelected(true);
    }




}
