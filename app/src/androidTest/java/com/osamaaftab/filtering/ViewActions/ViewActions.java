package com.osamaaftab.filtering.ViewActions;

import android.view.View;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
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
}
