package com.example.submission01

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.submission01.data.source.local.Data
import com.example.submission01.utils.EspressoIdlingResource
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import java.util.concurrent.TimeUnit

class MainActivityTest {

    private val dataSeries = Data.getTvShows()
    private val dataMovies = Data.getMovies()

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
        IdlingPolicies.setMasterPolicyTimeout(1, TimeUnit.MINUTES);
        IdlingPolicies.setIdlingResourceTimeout(1, TimeUnit.MINUTES)
    }

    @Test
    fun loadMovies(){
        onView(withId(R.id.recycle_view_movies)).check(matches(isDisplayed()))
        val i = 0
        onView(withId(R.id.recycle_view_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(i, click()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        customWithText(R.id.tv_title)?.matches(matches(withText(dataMovies[i].title.toString())))
        onView(withId(R.id.tv_category_movie)).check(matches(isDisplayed()))
        customWithText(R.id.tv_category_movie)?.matches(dataMovies[i].rating)
        onView(withId(R.id.tv_director)).check(matches(isDisplayed()))
        customWithText(R.id.tv_director)?.matches(matches(withText(dataMovies[i].directors)))
        onView(withId(R.id.synopsis)).check(matches(isDisplayed()))
        customWithText(R.id.synopsis)?.matches(matches(withText(dataMovies[i].description)))
        onView(isRoot()).perform(ViewActions.pressBack())

    }

    @Test
    fun loadSeries(){
        onView(withText("TV SERIES")).perform(click())
        val i = 0
        onView(withId(R.id.recycle_view_series)).check(matches(isDisplayed()))
        onView(withId(R.id.recycle_view_series)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(i, click()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        customWithText(R.id.tv_title)?.matches(matches(withText(dataSeries[i].title)))
        onView(withId(R.id.tv_category)).check(matches(isDisplayed()))
        customWithText(R.id.tv_category)?.matches(dataSeries[i].rating)
        onView(withId(R.id.tv_director)).check(matches(isDisplayed()))
        customWithText(R.id.tv_director)?.matches(matches(withText(dataSeries[i].directors)))
        onView(withId(R.id.synopsis)).check(matches(isDisplayed()))
        customWithText(R.id.synopsis)?.matches(matches(withText(dataSeries[i].description)))
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    @Test
    fun loadFavorite() {
        onView(withId(R.id.recycle_view_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.recycle_view_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.starMovies)).check(matches(isDisplayed()))
        onView(withId(R.id.starMovies)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        onView(withText("Favorite")).perform(click())
        onView(withId(R.id.recycle_view_fav_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.recycle_view_fav_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        customWithText(R.id.tv_title).matches(dataMovies[0].title)
        onView(isRoot()).perform(ViewActions.pressBack())
    }



    private fun customWithText(resourceId: Int): Matcher<View?> {
        return object : BoundedMatcher<View?, TextView>(TextView::class.java) {
            private var resourceName: String? = null
            private var expectedText: String? = null
            override fun describeTo(description: Description) {
                description.appendText("with string from resource id: ")
                description.appendValue(resourceId)
                if (null != resourceName) {
                    description.appendText("[")
                    description.appendText(resourceName)
                    description.appendText("]")
                }
                if (null != expectedText) {
                    description.appendText(" value: ")
                    description.appendText(expectedText)
                }
            }

            override fun matchesSafely(textView: TextView): Boolean {
                if (null == expectedText) {
                    try {
                        expectedText = textView.resources.getString(
                            resourceId
                        )
                        resourceName = textView.resources
                            .getResourceEntryName(resourceId)
                    } catch (ignored: Resources.NotFoundException) {
                        /*
                         * view could be from a context unaware of the resource
                         * id.
                         */
                    }
                }
                return if (null != expectedText) {
                    expectedText == textView.text
                        .toString()
                } else {
                    false
                }
            }
        }
    }

}