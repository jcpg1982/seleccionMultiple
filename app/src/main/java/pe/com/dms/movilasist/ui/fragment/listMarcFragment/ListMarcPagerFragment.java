package pe.com.dms.movilasist.ui.fragment.listMarcFragment;

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

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.annotacion.SelectorType;
import pe.com.dms.movilasist.databinding.FragmentListMarcPagerBinding;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.model.filterModel.ResultListMarc;
import pe.com.dms.movilasist.ui.activities.searchActivity.SearchActivity;
import pe.com.dms.movilasist.ui.adapter.pager.TabsPagerAdapter;
import pe.com.dms.movilasist.ui.fragment.base.BaseToolbarFragment;
import pe.com.dms.movilasist.ui.fragment.listMarcFragment.cantDiarioFragment.CantDiarioFragment;
import pe.com.dms.movilasist.ui.fragment.listMarcFragment.cantMensualFragment.CantMensualFragment;
import pe.com.dms.movilasist.ui.fragment.listMarcFragment.cantSemanalFragment.CantSemanalFragment;
import pe.com.dms.movilasist.ui.fragment.listMarcFragment.fechaFragment.FechaFragment;
import pe.com.dms.movilasist.util.TintUtils;
import pe.com.dms.movilasist.util.Utils;
import pe.com.dms.movilasist.util.ViewUtils;
import pe.com.dms.movilasist.util.pager.FullPagesBuilder;

public class ListMarcPagerFragment extends BaseToolbarFragment
        implements IListMarcPagerContract.View {

    public static final String TAG = ListMarcPagerFragment.class.getSimpleName();

    View mTabsInclude;
    ViewGroup mAppBar;
    TabLayout mTabLayout;

    private FullPagesBuilder mPages;
    private TabsPagerAdapter mPagerAdapter;


    private OnFragmentInteractionListener mFragmentInteractionListener;

    private FragmentListMarcPagerBinding binding;

    private FechaFragment tabFecha;
    private CantDiarioFragment tabCantDiario;
    private CantSemanalFragment tabCantSemanal;
    private CantMensualFragment tabCantMensual;

    private ResultListMarc mResultListMarc;

    @Inject
    ListMarcPagerPresenter presenter;

    public ListMarcPagerFragment() {
    }

    public static ListMarcPagerFragment newInstance() {
        ListMarcPagerFragment fragment = new ListMarcPagerFragment();
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
        binding = FragmentListMarcPagerBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        tabFecha = FechaFragment.newInstance();
        tabCantDiario = CantDiarioFragment.newInstance();
        tabCantSemanal = CantSemanalFragment.newInstance();
        tabCantMensual = CantMensualFragment.newInstance();

        addTabsToToolbar();
        setupPages();
        setupPager();
        setupTabs();
        initEvents();
        dispatchFragmentUpdateTitle();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeTabsFromToolbar();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constants.Intent.REQUEST_CODE_LIST_MARC:
                ResultListMarc resultListMarc;
                try {
                    resultListMarc = (ResultListMarc) data.getSerializableExtra(
                            Constants.Intent.EXTRA_SELECTOR_FILTER);
                } catch (Error | Exception e) {
                    e.printStackTrace();
                    Utils.showToast(getContext(), e.getMessage());
                    return;
                }
                if (resultListMarc != null) {
                    loadData(resultListMarc);
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
        //@ColorInt int tintColorToolbar = TintUtils.getToolbarColor(getContext(), true);
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
                        getResources().getString(R.string.filter_list_marc));
                intent.putExtra(Constants.Intent.EXTRA_SELECTOR_TYPE,
                        SelectorType.LIST_MARC);
                startActivityForResult(intent,
                        Constants.Intent.REQUEST_CODE_LIST_MARC);
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
                Log.e(TAG, "onPageSelected: " + mResultListMarc);
                Log.e(TAG, "initEvents onPageSelected position: " + position);
                if (mResultListMarc != null) {
                    switch (position) {
                        case 0:
                            tabFecha.setModelListMarc(mResultListMarc);
                            break;
                        case 1:
                            tabCantDiario.setModelListMarc(mResultListMarc);
                            break;
                        case 2:
                            tabCantSemanal.setModelListMarc(mResultListMarc);
                            break;
                        case 3:
                            tabCantMensual.setModelListMarc(mResultListMarc);
                            break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(TAG, "initEvents onPageScrollStateChanged state: " + state);
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
        mPages = new FullPagesBuilder(4);
        mPages.add(new FullPagesBuilder.Page(
                getContext(),
                R.drawable.selector_tab_checked,
                R.string.fecha,
                R.string.fecha,
                tabFecha));
        mPages.add(new FullPagesBuilder.Page(
                getContext(),
                R.drawable.selector_tab_checked,
                R.string.cant_diario,
                R.string.cant_diario,
                tabCantDiario));
        mPages.add(new FullPagesBuilder.Page(
                getContext(),
                R.drawable.selector_tab_checked,
                R.string.cant_semanal,
                R.string.cant_semanal,
                tabCantSemanal));
        mPages.add(new FullPagesBuilder.Page(
                getContext(),
                R.drawable.selector_tab_checked,
                R.string.cant_mensual,
                R.string.cant_mensual,
                tabCantMensual));
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
        mTabLayout.setTabMode(mPages.size() > 4 ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
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

    private void loadData(ResultListMarc resultListMarc) {
        mResultListMarc = resultListMarc;
        Log.e(TAG, "loadData: " + mResultListMarc);
        tabFecha.setModelListMarc(resultListMarc);
        tabCantDiario.setModelListMarc(resultListMarc);
        /*tabCantSemanal.setModelListMarc(resultListMarc);
        tabCantMensual.setModelListMarc(resultListMarc);*/
    }

}
