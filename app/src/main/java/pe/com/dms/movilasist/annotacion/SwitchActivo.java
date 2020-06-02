package pe.com.dms.movilasist.annotacion;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({SwitchActivo.DESACTIVO,
        SwitchActivo.ACTIVO})
@Retention(RetentionPolicy.SOURCE)
public @interface SwitchActivo {
    int DESACTIVO = 0;
    int ACTIVO = 1;
}
