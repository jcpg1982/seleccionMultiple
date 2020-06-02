package pe.com.dms.movilasist.annotacion.media;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({ChooserPickerType.PICK_GALLERY,
        ChooserPickerType.PICK_DOCUMENT})
@Retention(RetentionPolicy.SOURCE)
public @interface ChooserPickerType {
    int PICK_GALLERY = 1;
    int PICK_DOCUMENT = 2;
}