package pe.com.dms.movilasist.ui.activities.searchActivity;

import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface ISearchActivityContract {

    interface View extends IBaseContract.View {
        void closed();

        void setColorToolbar(int color);
    }

    interface Presenter extends IBaseContract.Presenter<ISearchActivityContract.View> {

    }
}
