package com.example.vinilos.collectors.test


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
class CollectorDetailColl {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun collectorDetailColl() {
        val materialButton = onView(
            allOf(
                withId(R.id.visitor_button), withText("Visitantes"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.viewRoot),
                        2
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.action_books), withContentDescription("Collectors"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation_view),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val bottomNavigationItemView2 = onView(
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
        bottomNavigationItemView2.perform(click())

        val bottomNavigationItemView3 = onView(
            allOf(
                withId(R.id.action_books), withContentDescription("Collectors"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation_view),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView3.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.recyclerViewCollector),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(1, click()))

        val textView = onView(
            allOf(
                withText("Coleccionista"),
                withParent(
                    allOf(
                        withId(R.id.toolbar),
                        withParent(withId(R.id.appBarLayout))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Coleccionista")))
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
