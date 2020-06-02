package pe.com.dms.movilasist.ui.fragment.configFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.annotacion.TypeActivity;
import pe.com.dms.movilasist.annotacion.TypeOption;
import pe.com.dms.movilasist.databinding.FragmentConfigPagerBinding;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.ui.activities.loginActivity.LoginActivity;
import pe.com.dms.movilasist.ui.adapter.pager.TabsPagerAdapter;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;
import pe.com.dms.movilasist.ui.fragment.base.BaseFragment;
import pe.com.dms.movilasist.ui.fragment.configFragment.configIpFragment.ConfigIpFragment;
import pe.com.dms.movilasist.ui.fragment.configFragment.systemFragment.SystemFragment;
import pe.com.dms.movilasist.util.TextUtils;
import pe.com.dms.movilasist.util.TintUtils;
import pe.com.dms.movilasist.util.Utils;
import pe.com.dms.movilasist.util.ViewUtils;
import pe.com.dms.movilasist.util.pager.FullPagesBuilder;

public class ConfigPagerFragment extends BaseFragment
        implements IConfigPagerContract.View {

    public static final String TAG = ConfigPagerFragment.class.getSimpleName();

    View mTabsInclude;
    ViewGroup mAppBar;
    TabLayout mTabLayout;

    private FullPagesBuilder mPages;
    private TabsPagerAdapter mPagerAdapter;


    private OnFragmentInteractionListener mFragmentInteractionListener;

    private FragmentConfigPagerBinding binding;

    private ConfigIpFragment tabServer;
    private SystemFragment tabSystem;

    @Inject
    ConfigPagerPresenter presenter;

    public ConfigPagerFragment() {
    }

    @TypeActivity
    private int mTypeActivity;

    public static ConfigPagerFragment newInstance(@TypeActivity int typeActivity) {
        ConfigPagerFragment fragment = new ConfigPagerFragment();
        Bundle args = fragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        args.putInt(Constants.Intent.EXTRA_INT_ACTIVITY, typeActivity);
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
        presenter.attachView(this);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        tabServer = ConfigIpFragment.newInstance(TypeActivity.MAIN);
        tabSystem = SystemFragment.newInstance();
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentConfigPagerBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        setRetainInstance(true);

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
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
            case R.id.menu_save:
                verifiedHasModified();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onBackPressed() {
        verifiedHasModified();
        return true;
    }

    private void init() {
        if (getArguments() != null) {
            mTypeActivity = getArguments().getInt(Constants.Intent.EXTRA_INT_ACTIVITY);
        }
        presenter.setTypeActivity(mTypeActivity);
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
                R.string.server,
                R.string.server,
                tabServer));
        mPages.add(new FullPagesBuilder.Page(
                getContext(),
                R.drawable.selector_tab_checked,
                R.string.system,
                R.string.system,
                tabSystem));
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

    @Override
    public void reiniciar() {
        clearDaggerComponent();
        Intent intent = getBaseActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void moveToLogin() {
        Intent Intent = new Intent(getBaseActivity(), LoginActivity.class);
        startActivity(Intent);
        getBaseActivity().finish();
    }

    private void verifiedHasModified() {
        presenter.setModifiedIp(tabServer.getModifiedIp());
        presenter.setModifiedIdTerminal(tabSystem.getModifiedIdTerminal());
        presenter.setConfiguracion(tabSystem.getConfiguracion());

        if (TextUtils.isEmpty(tabServer.getModifiedIp())) {
            showWarningMessage("La direccion IP no debe estar vacia");
            return;
        }
        if (TextUtils.isEmpty(tabSystem.getModifiedIdTerminal())) {
            showWarningMessage("El ID del terminal no debe estar vacio");
            return;
        }
        if (!TextUtils.checkURL(Constants.URL_SECURITY + "" + tabServer.getModifiedIp())) {
            showWarningMessage("El direción IP no cumple con el estandar correcto");
            return;
        }

        if (tabServer.getHasModifiedIp() && tabSystem.getHasModifiedConfig()) {
            Log.e(TAG, "hasModifiedIp ambos estan modificados");
            mostrarMensajeConfirmacionAmbos();
            return;
        } else if (tabServer.getHasModifiedIp()) {
            Log.e(TAG, "hasModifiedIp ip modificada");
            mostrarMensajeConfirmacionIp();
            return;
        } else if (tabSystem.getHasModifiedConfig()) {
            Log.e(TAG, "hasModifiedIp configuracion modificada");
            mostrarMensajeConfirmacionConfig();
            return;
        }
        switch (mTypeActivity) {
            case TypeActivity.CONFIG:
                moveToLogin();
                break;
        }
    }

    private void mostrarMensajeConfirmacionIp() {
        new DefaultDialog.Builder(getContext())
                .title(getResources().getString(R.string.attention))
                .message("Su dirección IP fue modificada\n" +
                        "¿Desea guardar su nueva configuración?")
                .cancelable(false)
                .setIcon(R.drawable.ic_help_outline)
                .textPositiveButton(getResources().getString(R.string.button_save))
                .textColorPositiveButton(R.color.success)
                .textNegativeButton(getResources().getString(R.string.button_cancel))
                .textColorNegativeButton(R.color.danger)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        presenter.saveIp();
                    }
                })
                .onNegative(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.cancel();
                    }
                })
                .buildAndShow(((AppCompatActivity) getActivity()).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_SAVE_CONFIG);
    }

    private void mostrarMensajeConfirmacionConfig() {
        new DefaultDialog.Builder(getContext())
                .title(getResources().getString(R.string.attention))
                .message("Su configuracion fue modificada\n" +
                        "¿Desea guardar su nueva configuración?")
                .cancelable(false)
                .setIcon(R.drawable.ic_help_outline)
                .textPositiveButton(getResources().getString(R.string.button_save))
                .textColorPositiveButton(R.color.success)
                .textNegativeButton(getResources().getString(R.string.button_cancel))
                .textColorNegativeButton(R.color.danger)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        presenter.saveConfig();
                    }
                })
                .onNegative(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.cancel();
                    }
                })
                .buildAndShow(((AppCompatActivity) getActivity()).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_SAVE_CONFIG);
    }

    private void mostrarMensajeConfirmacionAmbos() {
        new DefaultDialog.Builder(getContext())
                .title(getResources().getString(R.string.attention))
                .message("Sus configuraciones fueron modificadas\n" +
                        "¿Desea guardar su nueva configuración?")
                .cancelable(false)
                .setIcon(R.drawable.ic_help_outline)
                .textPositiveButton(getResources().getString(R.string.button_save))
                .textColorPositiveButton(R.color.success)
                .textNegativeButton(getResources().getString(R.string.button_cancel))
                .textColorNegativeButton(R.color.danger)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        presenter.saveConfigOffLine();
                        presenter.saveIp();
                    }
                })
                .onNegative(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.cancel();
                    }
                })
                .buildAndShow(((AppCompatActivity) getActivity()).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_SAVE_CONFIG);
    }
}
