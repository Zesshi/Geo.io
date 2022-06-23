package net.ictcampus.geoio;



import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;


import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;


import junit.framework.TestCase;



@RunWith(AndroidJUnit4.class)
public class GeoIo_InstrumentedTest extends TestCase {


    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("net.ictcampus.geoio", appContext.getPackageName());
    }

    @Test
    public void testQuestionNumber_GuessTheFlag() {
        //Clicks and Types performed
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.inputNumber)).perform(typeText("100"), closeSoftKeyboard());
        onView(withId(R.id.buttonSubmit)).perform(click());
        //had to Implement a timer so that there is enough time to read the "textview2"
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.textView2)).check(matches(withText("Question " + "1" + "/" + "100")));
    }

    @Test
    public void CheckTheQuitGameButton() {

        //clicks performed on MainActivity
        onView(withId(R.id.button4)).perform(click());
        onView(withId(R.id.buttonAll)).perform(click());

        //performing a click on the return button
        ViewInteraction imageFilterView = onView(allOf(withId(R.id.returnImg_cap), isDisplayed()));
        imageFilterView.perform(click());

        onView(withId(R.id.quitButton)).perform(click());
        //checking if we are in the MainActivity after quiting the game, by getting a textView form the MainActivity.
        onView(withId(R.id.textView)).check(matches(withText("Geo.io")));


    }


}
