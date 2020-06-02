package pe.com.dms.movilasist.annotacion;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({TypeViewRecycler.HEADER,
        TypeViewRecycler.BODY,
        TypeViewRecycler.FOOTER,
        TypeViewRecycler.PAGINATION})
@Retention(RetentionPolicy.SOURCE)
public @interface TypeViewRecycler {
    int HEADER = 1;
    int BODY = 2;
    int FOOTER = 3;
    int PAGINATION = 4;
}