package com.example.submission01

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.submission01.data.Data
import org.junit.Test
import org.junit.Rule

class MainActivityTest {

    private val dataSeries = Data.getTvShows()
    private val dataMovies = Data.getMovies()

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun loadMovies(){
        onView(withId(R.id.recycle_view_movies)).check(matches(isDisplayed()))

        for(i in 0..9){
            onView(withId(R.id.recycle_view_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(i, click()))
            onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
            onView(withId(R.id.tv_title)).check(matches(withText(dataMovies[i].title)))
            onView(withId(R.id.tv_category)).check(matches(isDisplayed()))
            onView(withId(R.id.tv_category)).check(matches(withText(dataMovies[i].rating)))
            onView(withId(R.id.tv_director)).check(matches(isDisplayed()))
            onView(withId(R.id.tv_director)).check(matches(withText(dataMovies[i].directors)))
            onView(withId(R.id.synopsis)).check(matches(isDisplayed()))
            onView(withId(R.id.synopsis)).check(matches(withText(dataMovies[i].description)))
            onView(isRoot()).perform(ViewActions.pressBack());
        }

    }

    @Test
    fun loadSeries(){
        onView(withText("TV SERIES")).perform(click())
        for(i in 0..9){
            onView(withId(R.id.recycle_view_series)).check(matches(isDisplayed()))
            onView(withId(R.id.recycle_view_series)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(i, click()))
            onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
            onView(withId(R.id.tv_title)).check(matches(withText(dataSeries[i].title)))
            onView(withId(R.id.tv_category)).check(matches(isDisplayed()))
            onView(withId(R.id.tv_category)).check(matches(withText(dataSeries[i].rating)))
            onView(withId(R.id.tv_director)).check(matches(isDisplayed()))
            onView(withId(R.id.tv_director)).check(matches(withText(dataSeries[i].directors)))
            onView(withId(R.id.synopsis)).check(matches(isDisplayed()))
            onView(withId(R.id.synopsis)).check(matches(withText(dataSeries[i].description)))
            onView(isRoot()).perform(ViewActions.pressBack());
        }

    }

}