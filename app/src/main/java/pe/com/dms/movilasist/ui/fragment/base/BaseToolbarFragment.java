package pe.com.dms.movilasist.ui.fragment.base;

import android.view.Menu;
import android.view.MenuInflater;

import pe.com.dms.movilasist.ui.activities.base.BaseToolbarActivity;

public abstract class BaseToolbarFragment extends BaseFragment {

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (getActivity() != null) {
            BaseToolbarActivity.themeMenu(getActivity(), menu);
        }
    }
}