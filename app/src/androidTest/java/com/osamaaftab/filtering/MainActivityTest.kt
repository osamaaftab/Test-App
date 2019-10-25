package com.osamaaftab.filtering

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.osamaaftab.filtering.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.IdlingPolicies
import java.util.concurrent.TimeUnit
import org.junit.Before
import android.text.format.DateUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import com.osamaaftab.filtering.repository.remote.ElapsedTimeIdlingResource
import com.osamaaftab.filtering.repository.remote.RecyclerViewMatcher
import org.hamcrest.CoreMatchers.not


@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun resetTimeout() {
        IdlingPolicies.setMasterPolicyTimeout(60, TimeUnit.SECONDS)
        IdlingPolicies.setIdlingResourceTimeout(26, TimeUnit.SECONDS)
    }

    @Test
    fun onFetchUserList() {

        IdlingPolicies.setMasterPolicyTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)

        val idlingResource = ElapsedTimeIdlingResource(DateUtils.SECOND_IN_MILLIS * 8)
        IdlingRegistry.getInstance().register(idlingResource)

        onView(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.card_view))
            .check(matches(isDisplayed()))

        onView(withId(R.id.userList))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        IdlingRegistry.getInstance().unregister(idlingResource)

    }

    @Test
    fun onFilterListWithPhotoEnabled() {
        IdlingPolicies.setMasterPolicyTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)

        val idlingResource = ElapsedTimeIdlingResource(DateUtils.SECOND_IN_MILLIS * 8)
        IdlingRegistry.getInstance().register(idlingResource)

        onView(withId(R.id.filter))
            .perform(click())

        IdlingRegistry.getInstance().unregister(idlingResource)


        onView(withId(R.id.has_photo_switch))
            .perform(click())

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())
        IdlingRegistry.getInstance().register(idlingResource)

        onView(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_picture))
            .check(matches(isDisplayed()))
        IdlingRegistry.getInstance().unregister(idlingResource)
    }


    @Test
    fun onFilterListWithIsContactEnabled() {
        IdlingPolicies.setMasterPolicyTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)

        val idlingResource = ElapsedTimeIdlingResource(DateUtils.SECOND_IN_MILLIS * 8)
        IdlingRegistry.getInstance().register(idlingResource)
        onView(withId(R.id.filter))
            .perform(click())
        IdlingRegistry.getInstance().unregister(idlingResource)

        onView(withId(R.id.in_contact_switch))
            .perform(click())

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())
        IdlingRegistry.getInstance().register(idlingResource)

        onView(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_contact))
            .check(matches(withText(not("0"))))

        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun onFilterListWithIsFavEnabled() {
        IdlingPolicies.setMasterPolicyTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)

        val idlingResource = ElapsedTimeIdlingResource(DateUtils.SECOND_IN_MILLIS * 8)
        IdlingRegistry.getInstance().register(idlingResource)
        onView(withId(R.id.filter))
            .perform(click())
        IdlingRegistry.getInstance().unregister(idlingResource)

        onView(withId(R.id.in_contact_switch))
            .perform(click())

        onView(withId(R.id.in_fav_switch))
            .perform(click())

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())
        IdlingRegistry.getInstance().register(idlingResource)


        onView(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_fav))
            .check(matches(withText(("false"))))

        IdlingRegistry.getInstance().unregister(idlingResource)
    }

}
