package pe.com.dms.movilasist.annotacion;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({ActionSwipeRecycler.NONE, ActionSwipeRecycler.DELETE,
        ActionSwipeRecycler.EDIT,
        ActionSwipeRecycler.DELETE_AND_EDIT})
@Retention(RetentionPolicy.SOURCE)
public @interface ActionSwipeRecycler {
    int NONE = 0;
    int DELETE = 1;
    int EDIT = 2;
    int DELETE_AND_EDIT = 3;
}
