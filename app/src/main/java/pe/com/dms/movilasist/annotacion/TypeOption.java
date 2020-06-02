package pe.com.dms.movilasist.annotacion;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({TypeOption.OBTAIN_LAST_MARC,
        TypeOption.DELETE_LAST_MARC,
        TypeOption.SEND_MARC})
@Retention(RetentionPolicy.SOURCE)
public @interface TypeOption {
    int OBTAIN_LAST_MARC = 1;
    int DELETE_LAST_MARC = 2;
    int SEND_MARC = 3;
}
