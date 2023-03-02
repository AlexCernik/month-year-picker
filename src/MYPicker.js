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
  isOpen,
  onClose,
  ...restProps
}) => {
  invariant(value, 'value prop is required!');

  if (isOpen) {
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
            onClose();
            case ACTION_NEUTRAL:
            date = moment(`${month}-${year}`, NATIVE_FORMAT).toDate();
            break;
          case ACTION_DISMISSED:
            onClose();
          default:
            date = undefined;
        }
        onChange && onChange(date);
      },
      error => {
        throw error;
      },
    );
  }

  return null;
};

export default MYPicker;
