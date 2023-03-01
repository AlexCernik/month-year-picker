package com.alexcernik.mypicker.adapter;

import java.util.Locale;

public interface RNMYPickerProps {

  RNDate value();

  RNDate minimumValue();

  RNDate maximumValue();

  String okButton();

  String cancelButton();

  String neutralButton();

  Locale locale();

  String mode();

  Boolean darkTheme();

  void onChange(int year, int month, int flag);

  void onChange();
}
