package pe.com.dms.movilasist.annotacion;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({TypeMarcacion.ENTRADA,
        TypeMarcacion.SALIDA,
        TypeMarcacion.SALIDA_REFRIGERIO,
        TypeMarcacion.RETORNO_REFRIGERIO})
@Retention(RetentionPolicy.SOURCE)
public @interface TypeMarcacion {
    int ENTRADA = 1;
    int SALIDA = 2;
    int SALIDA_REFRIGERIO = 3;
    int RETORNO_REFRIGERIO = 4;
}
