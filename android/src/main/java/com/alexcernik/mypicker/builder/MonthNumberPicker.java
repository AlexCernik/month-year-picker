package com.alexcernik.mypicker.builder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormatSymbols;

class MonthNumberPicker extends MonthYearNumberPicker {
  private static final Integer DEFAULT_MONTH_SIZE = 3000;

  @Override
  MonthNumberPicker onScrollListener(MonthYearScrollListener scrollListener) {
    mypicker.setOnScrollListener(scrollListener);
    mypicker.setOnValueChangedListener(scrollListener);
    return this;
  }

  @Override
  MonthNumberPicker build() {
    final DateFormatSymbols dfs = new DateFormatSymbols(props.locale());

    mypicker.setMinValue(0);
    mypicker.setMaxValue(DEFAULT_MONTH_SIZE);
    mypicker.setFormatter(MonthFormatter.getMonthFormatter(props.mode(), dfs));
    mypicker.setWrapSelectorWheel(false);
    mypicker.setValue((DEFAULT_MONTH_SIZE / 2) + props.value().getMonth());
    // Fix for Formatter blank initial rendering
    try {
      final Method method = mypicker.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
      method.setAccessible(true);
      method.invoke(mypicker, true);

    } catch (final NoSuchMethodException | InvocationTargetException |
        IllegalAccessException | IllegalArgumentException e) {
      e.printStackTrace();
    }
    return this;
  }

  @Override
  synchronized void setValue() {
    final int row = mypicker.getValue();
    final int month = (row % 12);
    final int year = yearPicker.getValue();
    int value = row;
    if (props.minimumValue() != null &&
        year == props.minimumValue().getYear() &&
        month < props.minimumValue().getMonth()) {
      value = row + props.minimumValue().getMonth() - month;
    } else if (props.maximumValue() != null &&
        year == props.maximumValue().getYear() &&
        month > props.maximumValue().getMonth()) {
      value = row + props.maximumValue().getMonth() - month;
    }
    mypicker.setValue(value);
  }

  @Override
  int getValue() {
    return mypicker.getValue() % 12;
  }
}
