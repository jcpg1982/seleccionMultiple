package pe.com.dms.movilasist.annotacion;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({TypeActivity.CONFIG,
        TypeActivity.MAIN})
@Retention(RetentionPolicy.SOURCE)
public @interface TypeActivity {
    int CONFIG = 1;
    int MAIN = 2;
}