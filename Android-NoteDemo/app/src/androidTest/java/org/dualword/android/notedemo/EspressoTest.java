package org.dualword.android.notedemo;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.RequiresDevice;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by alex on 12/29/16.
 */

@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    private MainActivity mainActivity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(
            MainActivity.class);

    @Before
    public void setUp() throws Exception {
        //mainActivity = mActivityRule.getActivity();

    }

    @Test
    public void changeTextTest(){
        String test = "test note...";
        onView(withId(R.id.menu_add)).perform(click());
        onView(withId(R.id.note_text)).perform(typeText(test), closeSoftKeyboard());
        onView(withId(R.id.note_text)).check(matches(withText(test)));
        onView(withId(R.id.menu_cancel)).perform(click());
    }

}
