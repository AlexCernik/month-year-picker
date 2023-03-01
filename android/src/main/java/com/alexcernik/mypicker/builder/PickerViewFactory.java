package com.alexcernik.mypicker.builder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;

import com.alexcernik.mypicker.R;
import com.alexcernik.mypicker.RNMYPickerDialog;
import com.alexcernik.mypicker.adapter.RNMYPickerProps;

import androidx.fragment.app.FragmentActivity;

public class PickerViewFactory {

  private static final int DARK_VIEW = R.layout.picker_list_view_dark;
  private static final int LIGHT_VIEW = R.layout.picker_list_view;
  private static final int DARK_STYLE = R.style.DarkStyle ;
  private static final int LIGHT_STYLE = R.style.LightStyle;

  private RNMYPickerProps props;
  private RNMYPickerDialog rnMYPickerDialog;

  public PickerViewFactory(RNMYPickerProps props, RNMYPickerDialog rnMYPickerDialog) {
    this.props = props;
    this.rnMYPickerDialog = rnMYPickerDialog;
  }

  public AlertDialog build() {
    if (rnMYPickerDialog.getActivity() == null) {
      throw new NullPointerException();
    }
    final FragmentActivity fragmentActivity = rnMYPickerDialog.getActivity();
    final int dialogStyle = props.darkTheme() ? DARK_STYLE : LIGHT_STYLE;
    final int contentViewStyle = props.darkTheme() ? DARK_VIEW : LIGHT_VIEW;

    AlertDialog.Builder builder = new AlertDialog.Builder(rnMYPickerDialog.getActivity(), dialogStyle);
    LayoutInflater inflater = fragmentActivity.getLayoutInflater();
    View contentView = inflater.inflate(contentViewStyle, null);

    MonthYearScrollListener scrollListener = new MonthYearScrollListener();
    final MonthYearNumberPicker mypicker = new MonthNumberPicker()
        .view(contentView)
        .props(props)
        .onScrollListener(scrollListener)
        .build();
    final MonthYearNumberPicker yearPicker = new YearNumberPicker()
        .view(contentView)
        .props(props)
        .onScrollListener(scrollListener)
        .build();

    scrollListener.addObserver(mypicker);
    scrollListener.addObserver(yearPicker);

    builder.setView(contentView)
        .setPositiveButton(props.okButton(), new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            props.onChange(yearPicker.getValue(), mypicker.getValue(), 0);
            rnMYPickerDialog.getDialog().cancel();
          }
        })
        .setNegativeButton(props.cancelButton(), new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            rnMYPickerDialog.getDialog().cancel();
          }
        });

    if (props.neutralButton() != null) {
      builder.setView(contentView)
          .setNeutralButton(props.neutralButton(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              props.onChange(yearPicker.getValue(), mypicker.getValue(), 1);
              rnMYPickerDialog.getDialog().cancel();
            }
          });
    }

    return builder.create();
  }
}
