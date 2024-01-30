package com.example.objectbasket;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.core.app.ActivityScenario;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CosmeticInstrumentedTest {

    private SharedPreferences themePreferences;
    private SharedPreferences basketPreferences;
    private SharedPreferences objectPreferences;
    private Context context;

    public ActivityScenario<Cosmetics> activityScenario;

    @Before
    public void initial() {
        activityScenario = ActivityScenario.launch(Cosmetics.class);
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        themePreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE);
        basketPreferences = context.getSharedPreferences("basket_prefs", Context.MODE_PRIVATE);
        objectPreferences = context.getSharedPreferences("object_prefs", Context.MODE_PRIVATE);
    }
    

    @Test
    public void testSetThemeImage() {
        assertEquals(2131165318, themePreferences.getInt("selected_theme", R.drawable.default_theme));

    }

    @Test
    public void testSetBasket() {
        assertEquals(2131165317, basketPreferences.getInt("selected_basket", R.drawable.default_basket));

    }

    @Test
    public void testSetObjectCategory() {
        assertEquals(0, objectPreferences.getInt("selected_object_category", 0));

    }

    @After
    public void finish() {
        activityScenario.close();
    }
}
