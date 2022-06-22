package net.ictcampus.geoio;

import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.util.Pair;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import junit.framework.TestCase;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NumberOfQuestions_InstrumentedTest extends TestCase {



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
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.inputNumber)).perform(typeText("100"), closeSoftKeyboard());
        onView(withId(R.id.buttonSubmit)).perform(click());
        onView(withId(R.id.textView2)).check(matches(withText("Question " + "1" + "/" + "100")));
    }



}
