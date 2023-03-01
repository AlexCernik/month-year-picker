import React from 'react';
import { requireNativeComponent } from 'react-native';

const RNMYPickerView = props => <RNMYPicker {...props} />;

const RNMYPicker = requireNativeComponent(
  'RNMYPicker',
  RNMYPickerView,
);

export default RNMYPicker;
