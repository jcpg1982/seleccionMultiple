package pe.com.dms.movilasist.annotacion;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({StatusSolicitud.REGISTRADO,
        StatusSolicitud.APROBADO_PARCIAL,
        StatusSolicitud.APROBADO,
        StatusSolicitud.DESAPROBADO})
@Retention(RetentionPolicy.SOURCE)
public @interface StatusSolicitud {
    int REGISTRADO = 0;
    int APROBADO_PARCIAL = 1;
    int APROBADO = 2;
    int DESAPROBADO = 3;
}
