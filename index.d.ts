import {FunctionComponent} from 'react';

export type EventTypes = 'dateSetAction'|'dismissedAction'|'neutralAction';

export interface MYPickerProps {
    onChange?: (event:EventTypes, newDate:Date) => void;
    value:Date;
    locale?:string;
    mode?:'full'|'short'|'number'|'shortNumber';
    darkTheme?:boolean;
    maximumDate?:Date;
    minimumDate?:Date;
    okButton?:string;
    cancelButton?:string;
    neutralButton?:string;
}

declare const MYPicker: FunctionComponent<MYPickerProps>;
export default MYPicker;