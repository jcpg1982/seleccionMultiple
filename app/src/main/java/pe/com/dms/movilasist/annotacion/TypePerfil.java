package pe.com.dms.movilasist.annotacion;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({TypePerfil.SYSTEM,
        TypePerfil.SUPER,
        TypePerfil.USER})
@Retention(RetentionPolicy.SOURCE)
public @interface TypePerfil {
    int SYSTEM = 1;
    int SUPER = 2;
    int USER = 3;
}
