package pe.com.dms.movilasist.annotacion.media;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({FilePickerType.IMAGE,
        FilePickerType.VIDEO,
        FilePickerType.AUDIO,
        FilePickerType.DOCUMENT})
@Retention(RetentionPolicy.SOURCE)
public @interface FilePickerType {
    int IMAGE = 1;
    int VIDEO = 2;
    int AUDIO = 4;
    int DOCUMENT = 3;
}