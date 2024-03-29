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
import android.view.View
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import com.osamaaftab.filtering.ViewActions.ViewActions
import com.osamaaftab.filtering.repository.remote.RecyclerViewMatcher
import com.warkiz.widget.IndicatorSeekBar
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.hamcrest.Matchers.lessThanOrEqualTo
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)


    @Test
    fun onFetchUserList() {

        onView(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.card_view))
            .inRoot(not(isDialog()))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onFilterListWithPhotoEnabled() {

        onView(withId(R.id.filter)).inRoot(not(isDialog())).perform(click())

        onView(withId(R.id.has_photo_switch))
            .perform(click())

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())


        onView(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_picture)).inRoot(not(isDialog()))
            .check(matches(isDisplayed()))

    }

    @Test
    fun onFilterListWithIsContactEnabled() {
        onView(withId(R.id.filter)).inRoot(not(isDialog())).perform(click())


        onView(withId(R.id.in_contact_switch))
            .perform(click())

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())


        onView(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_contact)).inRoot(not(isDialog()))
            .check(matches(withText(not("0"))))

    }

    @Test
    fun onFilterListWithIsFavEnabled() {
        onView(withId(R.id.filter)).inRoot(not(isDialog())).perform(click())


        onView(withId(R.id.in_contact_switch))
            .perform(click())

        onView(withId(R.id.in_fav_switch))
            .perform(click())

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())


        onView(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_fav)).inRoot(not(isDialog()))
            .check(matches(withText(("true"))))

    }


    @Test
    fun onFilterListWithDistance() {

        val radius = 160.0 // In Km
        val currentLat = 52.412811
        val currentLon = -1.778197


        onView(withId(R.id.filter)).inRoot(not(isDialog())).perform(click())

        onView(withId(R.id.in_contact_switch))
            .perform(click())

        onView(withId(R.id.in_fav_switch))
            .perform(click())

        onView(withClassName(Matchers.equalTo(IndicatorSeekBar::class.java.name))).perform(scrollTo())
            .perform(ViewActions.setProgress(160))

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())


        var lat = getText(
            onView(
                RecyclerViewMatcher(R.id.userList).atPositionOnView(
                    0,
                    R.id.user_lat
                )
            ).inRoot(not(isDialog()))
        )
        var lon = getText(
            onView(
                RecyclerViewMatcher(R.id.userList).atPositionOnView(
                    0,
                    R.id.user_lon
                )
            ).inRoot(not(isDialog()))
        )

        assertThat(
            getDistanceFromLatLonInKm(
                lat.toString().toDouble(),
                lon.toString().toDouble(),
                currentLat,
                currentLon
            ), lessThanOrEqualTo<Double>(radius)
        )

    }

    @Test
    fun onFilterListWithScoreRange() {

        onView(withId(R.id.filter)).inRoot(not(isDialog())).perform(click())

        onView(withId(R.id.in_contact_switch))
            .perform(click())

        onView(withId(R.id.in_fav_switch))
            .perform(click())

        onView(withId(R.id.score_range_seek)).perform(scrollTo())
            .perform(ViewActions.setRange(5, 95))

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())


        var score = getText(
            onView(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_score)).inRoot(not(isDialog()))
        )
        assertThat(
            score.toString().toDouble(), allOf(greaterThanOrEqualTo(5.0), lessThanOrEqualTo(95.0))
        )

    }

    @Test
    fun onFilterListWithHeightRange() {

        onView(withId(R.id.filter)).inRoot(not(isDialog())).perform(click())

        onView(withId(R.id.in_contact_switch))
            .perform(click())

        onView(withId(R.id.in_fav_switch))
            .perform(click())

        onView(withId(R.id.height_range_seek)).perform(scrollTo())
            .perform(ViewActions.setRange(140, 180))

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())

        var height = getText(
            onView(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_height)).inRoot(not(isDialog()))
        )
        assertThat(
            height.toString().toInt(), allOf(greaterThanOrEqualTo(140), lessThanOrEqualTo(180))
        )

    }


    @Test
    fun onFilterListWithAgeRange() {

        onView(withId(R.id.filter)).inRoot(not(isDialog())).perform(click())

        onView(withId(R.id.in_contact_switch))
            .perform(click())

        onView(withId(R.id.in_fav_switch))
            .perform(click())

        onView(withId(R.id.age_range_seek)).perform(scrollTo())
            .perform(ViewActions.setRange(35, 45))

        onView(withId(R.id.apply)).perform(scrollTo())
            .perform(click())

        var age = getText(
            onView(RecyclerViewMatcher(R.id.userList).atPositionOnView(0, R.id.user_age)).inRoot(not(isDialog()))
        )
        assertThat(
            age.toString().toInt(), allOf(greaterThanOrEqualTo(35), lessThanOrEqualTo(45))
        )

    }

    private fun getText(withId: ViewInteraction): CharSequence? {
        val stringHolder = arrayOfNulls<String>(1)
        withId.perform(object : ViewAction {
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
        val a = sin(dLat / 2) * sin(dLat / 2) + cos(deg2rad(lat1)) * cos(deg2rad(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return R * c
    }

    private fun deg2rad(deg: Double): Double {
        return deg * (Math.PI / 180)
    }

}
