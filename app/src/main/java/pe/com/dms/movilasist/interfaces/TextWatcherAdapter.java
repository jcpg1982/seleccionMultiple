package pe.com.dms.movilasist.interfaces;

import android.text.Editable;
import android.text.TextWatcher;

public interface TextWatcherAdapter extends TextWatcher {
    enum Action {
        beforeTextChanged,
        onTextChanged,
        afterTextChanged
    }

    default void beforeTextChanged(CharSequence s, int start, int count, int after) {
        this.action(Action.beforeTextChanged, s, start, count, after);
    }

    default void onTextChanged(CharSequence s, int start, int before, int count) {
        this.action(Action.onTextChanged, s, start, count, before);
    }

    default void afterTextChanged(Editable editable) {
        this.action(Action.afterTextChanged, editable, 0, 0, 0);
    }

    void action(Action action, CharSequence charOrEditable, int start, int count, int afterOrBefore);
}