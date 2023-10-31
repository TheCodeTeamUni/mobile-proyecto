package com.example.vinilos.album.test


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
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumDetailDiscographyTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun albumDetailDiscographyTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.userNameEt), withText("Coleccionistas"),
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

        val recyclerView = onView(
            allOf(
                withId(R.id.recyclerView),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        SystemClock.sleep(2500);
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(3, click()))

        val textView = onView(
            allOf(
                withId(R.id.textContentRecord), withText("EMI"),
                withParent(
                    allOf(
                        withId(R.id.constraintLayout),
                        withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        SystemClock.sleep(1500);
        textView.check(matches(withText("EMI")))
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
