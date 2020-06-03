package pe.com.dms.movilasist.ui.fragment.drawer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.annotacion.OptionRegSolicPerm;
import pe.com.dms.movilasist.annotacion.TypeActivity;
import pe.com.dms.movilasist.annotacion.TypePerfil;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.databinding.FragmentNavigationDrawerBinding;
import pe.com.dms.movilasist.ennum.NavigationIndex;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.iterator.navigationDrawer.local.INavigationDrawerFragmentInteractor;
import pe.com.dms.movilasist.interfaces.OnSetToolbarListener;
import pe.com.dms.movilasist.ui.activities.loginActivity.LoginActivity;
import pe.com.dms.movilasist.ui.activities.mainActivity.MainActivity;
import pe.com.dms.movilasist.ui.adapter.drawer.NavigationAdapter;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;
import pe.com.dms.movilasist.ui.fragment.base.BaseFragment;
import pe.com.dms.movilasist.ui.fragment.configFragment.ConfigPagerFragment;
import pe.com.dms.movilasist.ui.fragment.listAprobSolic.ListAprobSolicPagerFragment;
import pe.com.dms.movilasist.ui.fragment.listMarcFragment.ListMarcPagerFragment;
import pe.com.dms.movilasist.ui.fragment.listMarcPersFragment.ListMarcPersFragment;
import pe.com.dms.movilasist.ui.fragment.listSolicPermFragment.ListSolicPermisosPagerFragment;
import pe.com.dms.movilasist.ui.fragment.regAsistFragment.RegistroAsistenciaFragment;
import pe.com.dms.movilasist.ui.fragment.solicPermFragment.SolicPermisoFragment;
import pe.com.dms.movilasist.ui.transformations.CircleTransform;
import pe.com.dms.movilasist.util.ResolveUtils;
import pe.com.dms.movilasist.util.Utils;
import pe.com.dms.movilasist.util.ViewUtils;

public class NavigationDrawerFragment extends BaseFragment implements View.OnClickListener{

    public static final String TAG = NavigationDrawerFragment.class.getSimpleName();

    protected Toolbar mToolbar;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected DrawerLayout mDrawer;
    protected NavigationAdapter mNavigationAdapter;

    protected MenuBuilder mMenu;
    protected OnSetToolbarListener mListener;
    protected OnBridgeToParent mBridgeListener;

    protected ArrayList<NavigationAdapter.Item> mDrawerItems = new ArrayList<>();

    protected FragmentManager mFragManager;
    protected FragmentTransaction mFragTransaction;
    protected String mFragmentTag;
    protected int mPosition, mLastPosition;

    @Inject
    PreferenceManager preferenceManager;
    @Inject
    INavigationDrawerFragmentInteractor interactor;
    private Usuario mUser;

    private FragmentNavigationDrawerBinding binding;

    @TypePerfil
    int typeUser;

    private static int INDEX_HOME;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSetToolbarListener) {
            mListener = (OnSetToolbarListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnSetToolbarListener");
        }
        if (context instanceof OnBridgeToParent) {
            mBridgeListener = (OnBridgeToParent) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnBridgeToParent");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mBridgeListener = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(Constants.Intent.EXTRA_POSITION_DRAWER, mPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreate:");
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mPosition = mLastPosition = 0;
        if (savedInstanceState != null) {
            mPosition = mLastPosition = savedInstanceState.getInt(
                    Constants.Intent.EXTRA_POSITION_DRAWER, 0);
        }

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            int position = bundle.getInt(Constants.Intent.EXTRA_POSITION_DRAWER, -1);
            if (position >= 0 && position < getPositionForItemNoChecked()) {
                mPosition = mLastPosition = position;
            }
        }

        preferenceManager = new PreferenceManager(getContext());
        typeUser = preferenceManager.getTypeUser();
        mUser = preferenceManager.getUser();

        switch (typeUser) {
            case TypePerfil.SUPER:
                NavigationIndex.CONFIG.setIndex(-1);
                NavigationIndex.REG_ASIST.setIndex(0);
                NavigationIndex.LIST_MARC.setIndex(1);
                NavigationIndex.LIST_MAR_PERS.setIndex(2);
                NavigationIndex.SOL_PERM.setIndex(3);
                NavigationIndex.LIST_SOL_PERM.setIndex(4);
                NavigationIndex.APRO_SOL.setIndex(5);
                NavigationIndex.LOGOUT.setIndex(6);

                INDEX_HOME = NavigationIndex.APRO_SOL.getIndex();
                break;
            case TypePerfil.USER:
                NavigationIndex.CONFIG.setIndex(-1);
                NavigationIndex.REG_ASIST.setIndex(0);
                NavigationIndex.LIST_MARC.setIndex(1);
                NavigationIndex.LIST_MAR_PERS.setIndex(-2);
                NavigationIndex.SOL_PERM.setIndex(2);
                NavigationIndex.LIST_SOL_PERM.setIndex(3);
                NavigationIndex.APRO_SOL.setIndex(-3);
                NavigationIndex.LOGOUT.setIndex(4);

                INDEX_HOME = NavigationIndex.REG_ASIST.getIndex();
                break;
            case TypePerfil.SYSTEM:
            default:
                NavigationIndex.CONFIG.setIndex(0);
                NavigationIndex.REG_ASIST.setIndex(-1);
                NavigationIndex.LIST_MARC.setIndex(-2);
                NavigationIndex.LIST_MAR_PERS.setIndex(-3);
                NavigationIndex.SOL_PERM.setIndex(-4);
                NavigationIndex.LIST_SOL_PERM.setIndex(-5);
                NavigationIndex.APRO_SOL.setIndex(-6);
                NavigationIndex.LOGOUT.setIndex(1);
                INDEX_HOME = NavigationIndex.CONFIG.getIndex();
                break;
        }

        mPosition = mLastPosition = INDEX_HOME;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView:");
        binding = FragmentNavigationDrawerBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        init();
        setupRecyclerView();
        initEvents();
        loadDataNavigation();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated:");
        loadDataHeader();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated:");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.root_nav_header:
                onNavigationHeaderClick();
                break;
        }
    }

    public void loadDataHeader() {
        Log.e(TAG, "loadDataHeader");
        final int textColor = ResolveUtils.resolveColor(getContext(), R.attr.textColorPrimaryInverse);
        final int colorPrimary = getResources().getColor(R.color.overlay_dark);

        Drawable placeholder = getResources().getDrawable(R.drawable.ic_action_person);
        if (mUser != null) {
            binding.rootNavHeader.navHeaderTypeProfile.setText(mUser.getVchPerfil());
            binding.rootNavHeader.navHeaderName.setText(mUser.getVchNombre() + " " + mUser.getVchApellidos());
            binding.rootNavHeader.navHeaderId.setText(mUser.getVchCorreo());
        }

        //foto del fondo del nav
       /* Glide.with(getActivity())
                .load("")
                .placeholder(placeholder)
                .error(placeholder)
                .centerCrop()
                .into(binding.rootNavHeader.navHeaderBackground);*/

        //foto circular del nav
        Glide.with(getContext())
                .load("")
                .placeholder(placeholder)
                .error(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(new CircleTransform())
                .into(binding.rootNavHeader.navHeaderProfile);
    }

    private void init() {
        binding.rootNavHeader.containerNavHeader.setPadding(0, ViewUtils.getStatusBarHeight(getContext()), 0, 0);
        binding.navFooterVersion.setText(String.format(Locale.getDefault(),
                "v %s (%d)",
                Utils.getAppVersionName(getContext()),
                Utils.getAppVersionCode(getContext())));
    }

    private void initEvents() {
        binding.rootNavHeader.rootNavHeader.setOnClickListener(this);
    }

    private void setupRecyclerView() {
        mNavigationAdapter = new NavigationAdapter(new NavigationAdapter.OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, NavigationAdapter.Item item, int position,
                                       boolean longPress) {
                if (longPress) {
                    return true;
                } else {
                    boolean selected = onNavigationItemSelected(mDrawerItems.get(position), position);
                    if (!selected) {
                        mPosition = mLastPosition;
                        setSelectedPosition(mPosition);
                    } else {
                        if (mPosition >= getPositionForItemNoChecked()) {
                            mPosition = mLastPosition;
                            setSelectedPosition(mPosition);
                            return false;
                        }

                        if (mPosition != mLastPosition) {
                            mLastPosition = mPosition;
                            setFragment(getFragment(mPosition));
                            mDrawer.closeDrawers();
                        }
                    }
                    return false;
                }
            }

            @Override
            public boolean onExpanded(View view, NavigationAdapter.Item item,
                                      int position, boolean longPress) {
                if (longPress) {
                    return true;
                } else {
                    mPosition = mLastPosition;
                    onNavigationItemExpanded(item);
                    return false;
                }
            }
        });
        binding.navRecycler.setAdapter(mNavigationAdapter);
        binding.navRecycler.setHasFixedSize(true);
        binding.navRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        ViewCompat.setNestedScrollingEnabled(binding.navRecycler, false);
    }

    protected int getPositionForItemNoChecked() {
        return NavigationIndex.LOGOUT.getIndex() + 1;
    }

    private void loadDataNavigation() {
        Log.e(TAG, "loadDataNavigation");
        Log.e(TAG, "typeUser" + typeUser);
        switch (typeUser) {
            case TypePerfil.SUPER:
                inflateMenuAndLoad(R.menu.menu_drawer_user_super);
                break;
            case TypePerfil.USER:
                inflateMenuAndLoad(R.menu.menu_drawer_user);
                break;
            case TypePerfil.SYSTEM:
            default:
                inflateMenuAndLoad(R.menu.menu_drawer_user_system);
                break;
        }
        mNavigationAdapter.setData(mDrawerItems);
    }

    @SuppressWarnings("RestrictedApi")
    private void inflateMenuAndLoad(@MenuRes int menuRes) {
        try {
            MenuInflater inflater = getActivity().getMenuInflater();
            mMenu = new MenuBuilder(getContext());
            inflater.inflate(menuRes, mMenu);
            processMenuInDataListNavigation(mMenu);
        } catch (Exception e) {
            showErrorMessage("", "");
        }
    }

    protected void processMenuInDataListNavigation(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).isVisible()) {
                NavigationAdapter.Item item = new NavigationAdapter.Item();
                MenuItem menuItem = menu.getItem(i);
                item.setId(menuItem.getItemId());
                item.setIcon(menuItem.getIcon());
                item.setTitle(menuItem.getTitle().toString());
                item.setSubtitle(menuItem.getTitleCondensed().toString());
                item.setVisible(menuItem.isVisible());
                item.setEnabled(menuItem.isEnabled());
                item.setChecked(menuItem.isChecked());
                item.setExpanded(menuItem.isCheckable());
                mDrawerItems.add(item);
            }
        }
    }

    protected Fragment getFragment(int position) {
        if (position == NavigationIndex.CONFIG.getIndex()) {
            mFragmentTag = ConfigPagerFragment.TAG;
            return ConfigPagerFragment.newInstance(TypeActivity.MAIN);
        } else if (position == NavigationIndex.REG_ASIST.getIndex()) {
            mFragmentTag = RegistroAsistenciaFragment.TAG;
            return RegistroAsistenciaFragment.newInstance();
        } else if (position == NavigationIndex.LIST_MARC.getIndex()) {
            mFragmentTag = ListMarcPagerFragment.TAG;
            return ListMarcPagerFragment.newInstance();
        } else if (position == NavigationIndex.LIST_MAR_PERS.getIndex()) {
            mFragmentTag = ListMarcPersFragment.TAG;
            return ListMarcPersFragment.newInstance();
        } else if (position == NavigationIndex.SOL_PERM.getIndex()) {
            mFragmentTag = SolicPermisoFragment.TAG;
            return SolicPermisoFragment.newInstance(OptionRegSolicPerm.NEW,
                    null);
        } else if (position == NavigationIndex.LIST_SOL_PERM.getIndex()) {
            mFragmentTag = ListSolicPermisosPagerFragment.TAG;
            return ListSolicPermisosPagerFragment.newInstance();
        } else if (position == NavigationIndex.APRO_SOL.getIndex()) {
            mFragmentTag = ListAprobSolicPagerFragment.TAG;
            return ListAprobSolicPagerFragment.newInstance();
        }
        return null;
    }

    protected void onNavigationHeaderClick() {
        switch (typeUser) {
            case TypePerfil.SUPER:
            case TypePerfil.USER:
                mPosition = NavigationIndex.REG_ASIST.getIndex();
                break;
            case TypePerfil.SYSTEM:
            default:
                mPosition = NavigationIndex.CONFIG.getIndex();
                break;
        }
        setFragment(getFragment(mPosition));
        mDrawer.closeDrawer(GravityCompat.START);
    }

    protected boolean onNavigationItemSelected(NavigationAdapter.Item item,
                                               int position) {
        int id = item.getId();
        if (id == R.id.nav_logout) {
            new DefaultDialog.Builder(getActivity())
                    .title("Cerrar sesion")
                    .message("Desea cerrar su sesión")
                    .textPositiveButton("Aceptar")
                    .textColorPositiveButton(R.color.danger)
                    .textNegativeButton("Cancelar")
                    .setIcon(R.drawable.ic_help_outline)
                    .textColorNegativeButton(R.color.success)
                    .cancelable(true)
                    .dialogType(DefaultDialog.DialogType.CONFIRM)
                    .onPositive(new DefaultDialog.OnSingleButtonListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog) {
                            Intent Intent = new Intent(getBaseActivity(), LoginActivity.class);
                            ((MainActivity) getActivity()).overridePendingTransition(R.anim.transition_slide_bottom_in, R.anim.transition_slide_left_out);
                            startActivity(Intent);
                            ((MainActivity) getActivity()).closedMain();
                        }
                    })
                    .onNegative(new DefaultDialog.OnSingleButtonListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    })
                    .onCancelListener(new DefaultDialog.OnCancelListener() {
                        @Override
                        public void onCancel(@NonNull DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    })
                    .buildAndShow(getChildFragmentManager(),
                            Constants.TAG_DIALOG_CONFIRM_LOGOUT);
            return false;
        } else {
            mPosition = position;
        }
        item.setChecked(true);
        mDrawer.closeDrawers();
        return true;
    }

    protected boolean onNavigationItemExpanded(NavigationAdapter.Item item) {
        Utils.showToast(getContext(), item.getTitle());
        return false;
    }

    public Fragment onMoveFragmentTo(int position) {
        mPosition = position;
        Fragment fragment = getFragment(position);
        setFragment(fragment);
        return fragment;
    }

    public boolean onBackPressed() {
        switch (typeUser) {
            case TypePerfil.SUPER:
            case TypePerfil.USER:
                if (!mFragmentTag.equals(RegistroAsistenciaFragment.TAG)) {
                    mPosition = mLastPosition = INDEX_HOME;
                    setFragment(getFragment(mPosition));
                } else {
                    createExitAlertDialog();
                }
                break;
            case TypePerfil.SYSTEM:
            default:
                if (!mFragmentTag.equals(ConfigPagerFragment.TAG)) {
                    mPosition = mLastPosition = INDEX_HOME;
                    setFragment(getFragment(mPosition));
                } else {
                    createExitAlertDialog();
                }
                break;
        }
        return true;
    }

    /**
     * Limpia el back stack
     */
    protected void clearBackStack() {
        if (mFragManager.getBackStackEntryCount() > 0) {
            mFragManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    protected void setFragment(Fragment fragment) {
        if (fragment == null) return;

        clearBackStack();

        mFragTransaction = mFragManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, mFragmentTag)
                .setBreadCrumbTitle(getItemList().get(mPosition).getTitle());
        try {
            mFragTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            mFragTransaction.commitAllowingStateLoss();
        }

        setSelectedPosition(mPosition);

        if (mListener != null) {
            mListener.onSetTitle(getItemList().get(mPosition).getTitle());
            mListener.onSetSubTitle(getItemList().get(mPosition).getSubtitle());
        }
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragManager = fragmentManager;
    }

    public void setToolbar(Toolbar toolbar) {
        this.mToolbar = toolbar;
    }

    public void setDrawer(DrawerLayout drawerLayout) {
        this.mDrawer = drawerLayout;
    }

    public void initData() {
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawer, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                loadDataHeader();
            }
        };

        mDrawerToggle.syncState();

        mDrawerToggle.setDrawerIndicatorEnabled(false);

        if (mBridgeListener != null) mBridgeListener.setToolbarNavigationFromDrawer(mPosition);

        mDrawer.setDrawerShadow(R.drawable.shadow_navigation_drawer, GravityCompat.START);
        mDrawer.addDrawerListener(mDrawerToggle);

        setFragment(getFragment(mPosition));
    }

    public ArrayList<NavigationAdapter.Item> getItemList() {
        return mDrawerItems;
    }

    public void setSelectedPosition(int position) {
        binding.navRecycler.post(new Runnable() {
            @Override
            public void run() {
                mNavigationAdapter.select(position);
            }
        });
    }

    public int getSelectedPosition() {
        return mNavigationAdapter.getSelectedPosition() != NavigationAdapter.NO_SELECTED ?
                mNavigationAdapter.getSelectedPosition() : mPosition;
    }

    public interface OnBridgeToParent {
        void setToolbarNavigationFromDrawer(int position);
    }

    private void createExitAlertDialog() {
        new DefaultDialog.Builder(getContext())
                .title("Salir de la aplicacion")
                .message("Esta a punto de cerrar su aplicacion")
                .cancelable(false)
                .setIcon(R.drawable.ic_help_outline)
                .textPositiveButton("Aceptar")
                .textColorPositiveButton(R.color.danger)
                .textNegativeButton("Cancelar")
                .textColorNegativeButton(R.color.success)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        ((MainActivity) getActivity()).closedMain();
                    }
                })
                .onNegative(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.cancel();
                    }
                })
                .buildAndShow(((AppCompatActivity) getActivity()).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_EXIT_APP);
    }
}