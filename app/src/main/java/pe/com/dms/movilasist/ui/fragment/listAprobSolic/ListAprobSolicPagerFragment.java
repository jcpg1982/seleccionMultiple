package pe.com.dms.movilasist.ui.fragment.listAprobSolic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.annotacion.SelectorType;
import pe.com.dms.movilasist.databinding.FragmentListAprobSolicPagerBinding;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.model.filterModel.ResultListApobSolic;
import pe.com.dms.movilasist.ui.activities.searchActivity.SearchActivity;
import pe.com.dms.movilasist.ui.adapter.pager.TabsPagerAdapter;
import pe.com.dms.movilasist.ui.fragment.base.BaseToolbarFragment;
import pe.com.dms.movilasist.ui.fragment.listAprobSolic.solicHE.ListAprobSolicHEFragment;
import pe.com.dms.movilasist.ui.fragment.listAprobSolic.solicPerm.ListAprobSolicPermFragment;
import pe.com.dms.movilasist.util.TintUtils;
import pe.com.dms.movilasist.util.Utils;
import pe.com.dms.movilasist.util.ViewUtils;
import pe.com.dms.movilasist.util.pager.FullPagesBuilder;

public class ListAprobSolicPagerFragment extends BaseToolbarFragment
        implements IListAprobSolicPagerContract.View {

    public static final String TAG = ListAprobSolicPagerFragment.class.getSimpleName();

    View mTabsInclude;
    ViewGroup mAppBar;
    TabLayout mTabLayout;

    private FullPagesBuilder mPages;
    private TabsPagerAdapter mPagerAdapter;


    private OnFragmentInteractionListener mFragmentInteractionListener;

    private FragmentListAprobSolicPagerBinding binding;

    private ListAprobSolicPermFragment tabSolcPerm;
    private ListAprobSolicHEFragment tabSolicHE;

    public ListAprobSolicPagerFragment() {
    }

    public static ListAprobSolicPagerFragment newInstance() {
        ListAprobSolicPagerFragment fragment = new ListAprobSolicPagerFragment();
        Bundle args = fragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(
                    context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentInteractionListener = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        Log.e(TAG, "onCreate");

        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentListAprobSolicPagerBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        tabSolcPerm = ListAprobSolicPermFragment.newInstance();
        tabSolicHE = ListAprobSolicHEFragment.newInstance();

        addTabsToToolbar();
        setupPages();
        setupPager();
        setupTabs();
        dispatchFragmentUpdateTitle();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEvents();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeTabsFromToolbar();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: requestCode: " + requestCode
                + ", requestCode: " + requestCode
                + ", data: " + data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constants.Intent.REQUEST_CODE_LIST_APROB_SOLIC:
                ResultListApobSolic resultListApobSolic;
                try {
                    resultListApobSolic = (ResultListApobSolic) data.getSerializableExtra(
                            Constants.Intent.EXTRA_SELECTOR_FILTER);
                } catch (Error | Exception e) {
                    e.printStackTrace();
                    Utils.showToast(getContext(), e.getMessage());
                    return;
                }
                if (resultListApobSolic != null) {
                    tabSolcPerm.setResultListApobSolic(resultListApobSolic);
                    tabSolicHE.setResultListApobSolic(resultListApobSolic);
                    Log.e(TAG, "onActivityResult : " + resultListApobSolic + ", requestCode: " + requestCode);
                }
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_marc, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.getIcon() != null)
                menuItem.setIcon(TintUtils.createTintedDrawable(
                        menuItem.getIcon().mutate(), getResources().getColor(R.color.colorCardLight)));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(Constants.Intent.EXTRA_TITLE,
                        getResources().getString(R.string.filter_list_aprob_solic));
                intent.putExtra(Constants.Intent.EXTRA_SELECTOR_TYPE,
                        SelectorType.LIST_APROB_SOLIC);
                startActivityForResult(intent,
                        Constants.Intent.REQUEST_CODE_LIST_APROB_SOLIC);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initEvents() {
        binding.pager.setCurrentItem(0);
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "onPageSelected position: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(TAG, "onPageScrollStateChanged state: " + state);
            }
        });
    }

    private void addTabsToToolbar() {
        mAppBar = getActivity().findViewById(R.id.container_appBar);
        mTabsInclude = getLayoutInflater().inflate(R.layout.inflate_tabs_light,
                (ViewGroup) mAppBar.getParent(), false);
        mAppBar.addView(mTabsInclude, 1);
        mTabLayout = mTabsInclude.findViewById(R.id.tab_layout);
    }

    private void removeTabsFromToolbar() {
        mAppBar.removeView(mTabsInclude);
    }

    private void setupPages() {
        mPages = new FullPagesBuilder(2);
        mPages.add(new FullPagesBuilder.Page(
                getContext(),
                R.drawable.selector_tab_checked,
                R.string.solic_permiso,
                R.string.solic_permiso,
                tabSolcPerm));
        mPages.add(new FullPagesBuilder.Page(
                getContext(),
                R.drawable.selector_tab_checked,
                R.string.solic_HE,
                R.string.solic_HE,
                tabSolicHE));
    }

    private void setupPager() {
        mPagerAdapter = new TabsPagerAdapter(getContext(), getChildFragmentManager(), mPages);
        binding.pager.setAdapter(mPagerAdapter);
        binding.pager.setOffscreenPageLimit(1);
        binding.pager.setPagingEnabled(true);
        ViewUtils.setViewPagerEdgeEffectColor(binding.pager, 0xfff5efdc);
    }

    public void setupTabs() {
        assert mTabLayout != null;
        mTabLayout.setTabMode(mPages.size() > 2 ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(binding.pager);
        binding.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                dispatchFragmentUpdateTitle();
            }
        });

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            addTab(i);
        }

        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
    }

    private void addTab(int position) {
        final TabLayout.Tab tab = mTabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(getTabView(getContext(), position));
    }

    public View getTabView(Context context, int position) {
        View tab = LayoutInflater.from(context).inflate(R.layout.partial_type_purchase_tab, null);
        TextView tabText = (TextView) tab.findViewById(R.id.tab_title);
        tabText.setText(mPages.get(position).titleTab);
        if (position == 0) {
            tab.setSelected(true);
        }
        return tab;
    }

    private void dispatchFragmentUpdateTitle() {
    }

    public boolean onBackPressed() {
        if (setHasModifiedIp())
            return true;
        return false;
    }

    @Override
    public boolean setHasModifiedIp() {
        return false;
    }

    @Override
    public boolean setHasModifiedSystem() {
        return false;
    }
}
