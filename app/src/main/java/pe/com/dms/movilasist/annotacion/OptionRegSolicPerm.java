package pe.com.dms.movilasist.annotacion;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({OptionRegSolicPerm.NEW,
        OptionRegSolicPerm.EDIT})
@Retention(RetentionPolicy.SOURCE)
public @interface OptionRegSolicPerm {
    int NEW = 1;
    int EDIT = 2;
}