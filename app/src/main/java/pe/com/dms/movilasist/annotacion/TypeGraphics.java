package pe.com.dms.movilasist.annotacion;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({TypeGraphics.DIARIO,
        TypeGraphics.SEMANAL,
        TypeGraphics.MENSUAL})
@Retention(RetentionPolicy.SOURCE)
public @interface TypeGraphics {
    int DIARIO = 1;
    int SEMANAL = 2;
    int MENSUAL = 3;
}