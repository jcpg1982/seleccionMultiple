package pe.com.dms.movilasist.ui.adapter.pager;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import pe.com.dms.movilasist.util.pager.FullPagesBuilder;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private final FullPagesBuilder mPages;

    public TabsPagerAdapter(Context context, FragmentManager fm, FullPagesBuilder pages) {
        super(fm);
        this.mContext = context;
        mPages = pages;
    }

    @Override
    public Fragment getItem(int position) {
        return mPages.get(position).fragment;
    }

    @Override
    public int getCount() {
        return mPages.size();
    }
}