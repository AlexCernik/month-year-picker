import moment from 'moment';
import invariant from 'invariant';

import RNMYPickerDialogModule from './RNMYPickerDialogModule';
import {
  ACTION_DATE_SET,
  ACTION_DISMISSED,
  ACTION_NEUTRAL,
  NATIVE_FORMAT,
} from './constants';

const MYPicker = ({
  value,
  onChange,
  minimumDate,
  maximumDate,
  ...restProps
}) => {
  invariant(value, 'value prop is required!');

  RNMYPickerDialogModule.open({
    value: value.getTime(),
    minimumDate: minimumDate?.getTime() ?? null,
    maximumDate: maximumDate?.getTime() ?? null,
    darkTheme: false,
    ...restProps,
  }).then(
    ({ action, year, month }) => {
      let date;
      switch (action) {
        case ACTION_DATE_SET:
        case ACTION_NEUTRAL:
          date = moment(`${month}-${year}`, NATIVE_FORMAT).toDate();
          break;
        case ACTION_DISMISSED:
        default:
          date = undefined;
      }
      onChange && onChange(action, date);
    },
    error => {
      throw error;
    },
  );

  return null;
};

export default MYPicker;
