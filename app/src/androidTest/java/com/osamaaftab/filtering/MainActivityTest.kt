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
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import com.osamaaftab.filtering.ViewActions.ViewActions
import com.osamaaftab.filtering.repository.remote.ElapsedTimeIdlingResource
import com.osamaaftab.filtering.repository.remote.RecyclerViewMatcher
import com.warkiz.widget.IndicatorSeekBar
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.hamcrest.Matchers.lessThanOrEqualTo


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
        onView(withId(R.id.filter))
            .perform(click())

        onView(withId(R.id.has_photo_switch))
            .perform(click())

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())


        IdlingPolicies.setMasterPolicyTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)

        val idlingResource = ElapsedTimeIdlingResource(DateUtils.SECOND_IN_MILLIS * 8)
        IdlingRegistry.getInstance().register(idlingResource)

        onView(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_picture))
            .check(matches(isDisplayed()))

        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun onFilterListWithIsContactEnabled() {
        onView(withId(R.id.filter))
            .perform(click())

        onView(withId(R.id.in_contact_switch))
            .perform(click())

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())


        IdlingPolicies.setMasterPolicyTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)

        val idlingResource = ElapsedTimeIdlingResource(DateUtils.SECOND_IN_MILLIS * 8)
        IdlingRegistry.getInstance().register(idlingResource)

        onView(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_contact))
            .check(matches(withText(not("0"))))

        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun onFilterListWithIsFavEnabled() {
        onView(withId(R.id.filter))
            .perform(click())

        onView(withId(R.id.in_contact_switch))
            .perform(click())

        onView(withId(R.id.in_fav_switch))
            .perform(click())

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())


        IdlingPolicies.setMasterPolicyTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)

        val idlingResource = ElapsedTimeIdlingResource(DateUtils.SECOND_IN_MILLIS * 8)
        IdlingRegistry.getInstance().register(idlingResource)

        onView(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_fav))
            .check(matches(withText(("true"))))

        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun onFilterListWithDistance() {

        val radius = 160.0 // In Km
        val currentLat = 52.412811
        val currentLon = -1.778197


        onView(withId(R.id.filter))
            .perform(click())

        onView(withId(R.id.in_contact_switch))
            .perform(click())

        onView(withId(R.id.in_fav_switch))
            .perform(click())

        onView(withClassName(Matchers.equalTo(IndicatorSeekBar::class.java.name))).perform(scrollTo())
            .perform(ViewActions.setProgress(160))

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())

        IdlingPolicies.setMasterPolicyTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)

        val idlingResource = ElapsedTimeIdlingResource(DateUtils.SECOND_IN_MILLIS * 8)
        IdlingRegistry.getInstance().register(idlingResource)

        var lat = getText(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_lat))
        var lon = getText(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_lon))

        assertThat(
            getDistanceFromLatLonInKm(
                lat.toString().toDouble(),
                lon.toString().toDouble(),
                currentLat,
                currentLon
            ), lessThanOrEqualTo<Double>(radius)
        )
        IdlingRegistry.getInstance().unregister(idlingResource)


    }


    @Test
    fun onFilterListWithAgeRange() {

        onView(withId(R.id.filter))
            .perform(click())

        onView(withId(R.id.in_contact_switch))
            .perform(click())

        onView(withId(R.id.in_fav_switch))
            .perform(click())

        onView(withId(R.id.age_range_seek)).perform(scrollTo())
            .perform(ViewActions.setRange(35, 45))

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())

        IdlingPolicies.setMasterPolicyTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout((DateUtils.SECOND_IN_MILLIS * 8) * 2, TimeUnit.MILLISECONDS)

        val idlingResource = ElapsedTimeIdlingResource(DateUtils.SECOND_IN_MILLIS * 8)
        IdlingRegistry.getInstance().register(idlingResource)

        var age = getText(
            RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_age)
        )
        assertThat(
            age.toString().toInt(), allOf(greaterThanOrEqualTo(35), lessThanOrEqualTo(45))
        )

        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    private fun getText(withId: Matcher<View>?): CharSequence? {
        val stringHolder = arrayOfNulls<String>(1)
        onView(withId).perform(object : ViewAction {
            override fun perform(uiController: UiController?, view: View?) {
                val tv = view as TextView //Save, because of check in getConstraints()
                stringHolder[0] = tv.text.toString()
            }

            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "getting text from a TextView"
            }
        })
        return stringHolder[0]
    }

    private fun getDistanceFromLatLonInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // Radius of the earth in km
        val dLat = deg2rad(lat2 - lat1)  // deg2rad below
        val dLon = deg2rad(lon2 - lon1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return R * c
    }

    private fun deg2rad(deg: Double): Double {
        return deg * (Math.PI / 180)
    }

}
