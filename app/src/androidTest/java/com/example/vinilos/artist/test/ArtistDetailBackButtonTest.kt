package com.example.vinilos.artist.test


import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.vinilos.ui.main.view.MainActivity
import com.vinylsMobile.vinylsapplication.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ArtistDetailBackButtonTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun artistDetailBackButtonTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.collector_button), withText("Coleccionistas"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.viewRoot),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        SystemClock.sleep(1500);
        materialButton.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.nav_graph_artist), withContentDescription("Artists"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation_view),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        SystemClock.sleep(2500);
        bottomNavigationItemView.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.recyclerViewArtist),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        SystemClock.sleep(1500);
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(2, click()))

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navegar hacia arriba"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withId(R.id.appBarLayout),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        SystemClock.sleep(1500);
        appCompatImageButton.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.textViewElementTitle), withText("Queen"),
                withParent(
                    allOf(
                        withId(R.id.container),
                        withParent(withId(R.id.recyclerViewArtist))
                    )
                ),
                isDisplayed()
            )
        )
        SystemClock.sleep(1500);
        textView.check(matches(withText("Queen")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
