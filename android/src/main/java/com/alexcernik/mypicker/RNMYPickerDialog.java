package com.alexcernik.mypicker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.alexcernik.mypicker.adapter.RNMYPickerProps;
import com.alexcernik.mypicker.builder.PickerViewFactory;

import androidx.fragment.app.DialogFragment;

public class RNMYPickerDialog extends DialogFragment {
  private RNMYPickerProps props;

  public RNMYPickerDialog(RNMYPickerProps props) {
    this.props = props;
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    props.onChange();
    super.dismiss();
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    PickerViewFactory pickerViewFactory = new PickerViewFactory(props, this);
    return pickerViewFactory.build();
  }
}
