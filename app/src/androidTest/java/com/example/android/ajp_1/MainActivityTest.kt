package com.example.android.ajp_1

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.android.ajp_1.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
class MainActivityTest{
    private val dummyMovie = FakeDataDummy.generateDummyMovie()
    private val dummyTvShow = FakeDataDummy.generateDummyTv()

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun funSetup(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.expressoTestIdlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.expressoTestIdlingResource)
    }

    @Test
    fun checkNavigation(){
        onView(withId(R.id.navigation_movie)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_tvShow)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_favorite)).perform(click()).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvSHow(){
        onView(withId(R.id.navigation_tvShow)).perform(click())
        onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvShow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShow.size))
    }

    @Test
    fun loadDetailMovie(){
        onView(withId(R.id.navigation_movie)).perform(click())
        onView(withText(R.string.movie_upcoming)).perform(click())
        onView(withId(R.id.rv_movieUp)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movieUp)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_favorite)).perform(click())
        onView(withId(R.id.btn_bck_movie)).perform(click())

    }

    @Test
    fun loadFavoriteTv(){
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_favoriteTv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favoriteTv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }

    @Test
    fun loadFavoritesMovie() {
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withText("MOVIE")).perform(click())
        onView(withId(R.id.rv_favoriteMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favoriteMovie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
    }

        @Test
        fun loadSearch() {
            onView(withId(R.id.actionSearch)).perform(click())
        }

        @Test
        fun loadMovie() {
            onView(withId(R.id.navigation_movie)).perform(click())
            onView(withText(R.string.movie_upcoming)).perform(click())
            onView(withId(R.id.rv_movieUp)).check(matches(isDisplayed()))
            onView(withId(R.id.rv_movieUp)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
        }

        @Test
        fun loadDetailTvShow() {
            onView(withId(R.id.navigation_tvShow)).perform(click())
            onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed()))
            onView(withId(R.id.rv_tvShow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
            onView(withId(R.id.btn_favoriteTv)).perform(click())
            onView(withId(R.id.btn_bck_tvshow)).perform(click())

        }
    }