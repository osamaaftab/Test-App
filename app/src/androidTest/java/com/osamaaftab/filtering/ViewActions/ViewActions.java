package com.osamaaftab.filtering.ViewActions;

import android.view.View;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import com.osamaaftab.filtering.ui.customView.RangeSeekBar;
import com.warkiz.widget.IndicatorSeekBar;
import org.hamcrest.Matcher;

public class ViewActions {

    public static ViewAction setProgress(final int progress) {

        return new ViewAction() {

            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }

            @Override
            public void perform(androidx.test.espresso.UiController uiController, View view) {
                IndicatorSeekBar seekBar = (IndicatorSeekBar) view;
                seekBar.setProgress(progress);
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(IndicatorSeekBar.class);
            }
        };
    }


    public static ViewAction setRange(final int min, int max) {

        return new ViewAction() {

            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }

            @Override
            public void perform(androidx.test.espresso.UiController uiController, View view) {
                RangeSeekBar seekBar = (RangeSeekBar) view;
                seekBar.setSelectedMaxValue(max);
                seekBar.setSelectedMinValue(min);
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(RangeSeekBar.class);
            }
        };
    }
}
