package pe.com.dms.movilasist.ui.fragment.configFragment.configIpFragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.annotacion.TypeActivity;
import pe.com.dms.movilasist.databinding.FragmentServerBinding;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.interfaces.TextWatcherAdapter;
import pe.com.dms.movilasist.ui.activities.loginActivity.LoginActivity;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;
import pe.com.dms.movilasist.ui.fragment.base.BaseFragment;
import pe.com.dms.movilasist.util.PermissionsUtils;
import pe.com.dms.movilasist.util.TextUtils;
import pe.com.dms.movilasist.util.TintUtils;
import pe.com.dms.movilasist.util.Utils;

public class ConfigIpFragment extends BaseFragment
        implements IConfigIpContract.View,
        View.OnClickListener {

    public static final String TAG = ConfigIpFragment.class.getSimpleName();

    private OnFragmentInteractionListener mFragmentInteractionListener;

    private FragmentServerBinding binding;

    @Inject
    ConfigIpPresenter presenter;

    private boolean mHasModified;
    @TypeActivity
    private int mTypeActivity;

    public ConfigIpFragment() {
    }

    public static ConfigIpFragment newInstance(@TypeActivity int typeActivity) {
        ConfigIpFragment fragment = new ConfigIpFragment();
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
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentServerBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        presenter.currentIp();
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
        presenter.detachView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionsUtils.REQUEST_PERMISSION_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    moveToScanner();
                    new IntentIntegrator(getActivity()).initiateScan();
                } else {
                    Utils.showToast(getContext(), R.string.error);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult requestCode: " + requestCode + ", resultCode: " + resultCode + ", data: " + data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                showErrorMessage("No se detecto ningun dato", "");
            } else {
                binding.inputIp.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_config_manual:
                presenter.setEditable(binding.inputIp,
                        true);
                break;
            case R.id.btn_scan:
            case R.id.image_scanner:
                checkPermissionStorageCamera();
                break;
        }
    }

    @Override
    public void mostrarIp(String currentip) {
        if (!TextUtils.isEmpty(currentip))
            binding.inputIp.setText(currentip);
    }

    @Override
    public void setHasModifiedIp(boolean hasModifiedIp) {
        mHasModified = hasModifiedIp;
        Log.e(TAG, "setHasModifiedIp mHasModified: " + mHasModified);
    }

    @Override
    public boolean getHasModifiedIp() {
        Log.e(TAG, "getHasModifiedIp mHasModified: " + mHasModified);
        return mHasModified;
    }

    @Override
    public String getModifiedIp() {
        return binding.inputIp.getText().toString();
    }

    @Override
    public void reiniciar() {
        clearDaggerComponent();
        Intent intent = getBaseActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
        presenter.validateConnection();
    }

    @Override
    public void closed() {
        Intent Intent = new Intent(getBaseActivity(), LoginActivity.class);
        startActivity(Intent);
        getBaseActivity().finish();
    }

    private void init() {
        if (getArguments() != null) {
            mTypeActivity = getArguments().getInt(Constants.Intent.EXTRA_INT_ACTIVITY);
        }
        switch (mTypeActivity) {
            case TypeActivity.CONFIG:
                setRetainInstance(true);
                setHasOptionsMenu(true);
                break;
            default:
                setRetainInstance(false);
                setHasOptionsMenu(false);
                break;
        }
    }

    public boolean onBackPressed() {
        verifiedHasModified();
        return true;
    }

    public void checkPermissionStorageCamera() {
        String[] permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean granted = PermissionsUtils.hasPermissions(getActivity(), permissions);
        if (granted) {
            moveToScanner();
        } else {
            PermissionsUtils.requestPermissions(getActivity(), permissions,
                    PermissionsUtils.REQUEST_PERMISSION_CAMERA);
        }
    }

    private void initEvents() {
        binding.imageScanner.setOnClickListener(this);
        binding.btnScan.setOnClickListener(this);
        binding.textConfigManual.setOnClickListener(this);

        presenter.setEditable(binding.inputIp,
                false);


        binding.inputIp.addTextChangedListener((TextWatcherAdapter)
                (action, charOrEditable, start, count, afterOrBefore) -> {
                    switch (action) {
                        case afterTextChanged:
                            Log.e(TAG, "afterTextChanged charOrEditable: " + charOrEditable + ", start: " + start + ", count: " + count + ", afterOrBefore: " + afterOrBefore);
                            presenter.setNewIp(charOrEditable.toString());
                            break;
                        case beforeTextChanged:
                        case onTextChanged:
                            break;
                    }
                });

        binding.textConfigManual.setPaintFlags(binding.textConfigManual.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void moveToScanner() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Obteniendo Datos");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    private void verifiedHasModified() {

        if (TextUtils.isEmpty(binding.inputIp.getText().toString())) {
            showWarningMessage("La direccion IP no debe estar vacia");
            return;
        }
        if (!TextUtils.checkURL(Constants.URL_SECURITY + "" + binding.inputIp.getText().toString())) {
            showWarningMessage("El direción IP no cumple con el estandar correcto");
            return;
        }

        if (presenter.hasModified()) {
            Log.e(TAG, "hasModifiedIp ip modificada");
            mostrarMensajeConfirmacionIp();
            return;
        }
        switch (mTypeActivity) {
            case TypeActivity.CONFIG:
                closed();
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
}
