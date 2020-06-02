package pe.com.dms.movilasist.annotacion;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({SelectorType.LIST_MARC,
        SelectorType.LIST_MARC_PERS,
        SelectorType.LIST_SOLIC_PERS,
        SelectorType.LIST_APROB_SOLIC})
@Retention(RetentionPolicy.SOURCE)
public @interface SelectorType {
    int LIST_MARC = 1;
    int LIST_MARC_PERS = 2;
    int LIST_SOLIC_PERS = 3;
    int LIST_APROB_SOLIC = 4;
}