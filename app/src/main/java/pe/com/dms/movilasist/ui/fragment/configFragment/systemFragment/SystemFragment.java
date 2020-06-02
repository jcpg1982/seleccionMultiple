package pe.com.dms.movilasist.ui.fragment.configFragment.systemFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.inject.Inject;

import pe.com.dms.movilasist.annotacion.SwitchActivo;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.databinding.FragmentSystemBinding;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.interfaces.TextWatcherAdapter;
import pe.com.dms.movilasist.ui.fragment.base.BaseFragment;
import pe.com.dms.movilasist.util.TextUtils;

public class SystemFragment extends BaseFragment implements ISystemContract.View {

    public static final String TAG = SystemFragment.class.getSimpleName();

    private OnFragmentInteractionListener mFragmentInteractionListener;

    private FragmentSystemBinding binding;

    @Inject
    SystemPresenter presenter;

    private Boolean mHasModifiedIdTerminal = false;

    public SystemFragment() {
    }

    public static SystemFragment newInstance() {
        SystemFragment fragment = new SystemFragment();
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
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSystemBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        presenter.validaConnection();
        presenter.currentIdTerminal();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniEvents();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void mostrarIdTerminal(String currentId) {
        if (!TextUtils.isEmpty(currentId))
            binding.inputCodTerminal.setText(currentId);
    }

    @Override
    public void cargarConfiguraciones(Configuracion configuracion) {
        Log.e(TAG, "cargarConfiguraciones:" + configuracion);
        if (configuracion != null) {
            Log.e(TAG, "cargarConfiguraciones:" + configuracion);
            binding.swAsistGps.setChecked(configuracion.getBitConLocalizacion() == SwitchActivo.ACTIVO);
            binding.swMarcGrupo.setChecked(configuracion.getBitMarcacionGrupal() == SwitchActivo.ACTIVO);
            binding.swIdentMarc.setChecked(configuracion.getBitIdentificacionMarca() == SwitchActivo.ACTIVO);
            binding.swPersNoExist.setChecked(configuracion.getBitMarcaPersonalNoExis() == SwitchActivo.ACTIVO);
            binding.swCodBarras.setChecked(configuracion.getBitLecturaPorCamara() == SwitchActivo.ACTIVO);
            binding.swIntVaWeb.setChecked(configuracion.getBitIntegracionVAWEB() == SwitchActivo.ACTIVO);
            binding.swAddPhoto.setChecked(configuracion.getBitColocarFotoMarca() == SwitchActivo.ACTIVO);

            binding.inputTimeMarc.setText(String.valueOf(configuracion.getIntTiempoEntreMarca()));
            binding.inputMostMarc.setText(String.valueOf(configuracion.getIntMostrarMarca()));
            binding.inputMostPerm.setText(String.valueOf(configuracion.getIntMostrarPermisos()));
        }
    }

    @Override
    public boolean getHasModifiedConfig() {
        Log.e(TAG, "getHasModifiedConfig presenter.getModifiedConfig(): " + presenter.getModifiedConfig());
        Log.e(TAG, "getHasModifiedConfig mHasModifiedIdTerminal: " + mHasModifiedIdTerminal);
        if (!presenter.getModifiedConfig() || mHasModifiedIdTerminal)
            return true;
        else
            return false;
    }

    @Override
    public void setHasModifiedIdTerminal(boolean hasModifiedConfig) {
        mHasModifiedIdTerminal = hasModifiedConfig;
        Log.e(TAG, "setHasModifiedIdTerminal mHasModifiedConfig: " + mHasModifiedIdTerminal);
    }

    @Override
    public String getModifiedIdTerminal() {
        return binding.inputCodTerminal.getText().toString();
    }

    @Override
    public Configuracion getConfiguracion() {
        Log.e(TAG, "getConfiguracion:" + presenter.getConfiguracion());
        return presenter.getConfiguracion();
    }

    private void iniEvents() {

        binding.swAsistGps.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                presenter.setBitConGPS(SwitchActivo.ACTIVO);
            } else {
                presenter.setBitConGPS(SwitchActivo.DESACTIVO);
            }
        });
        binding.swMarcGrupo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                presenter.setBitMarcacionGrupal(SwitchActivo.ACTIVO);
            } else {
                presenter.setBitMarcacionGrupal(SwitchActivo.DESACTIVO);
            }
        });
        binding.swIdentMarc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.containerIdentMarc.setVisibility(View.VISIBLE);
                presenter.setBitIdentificacionMarca(SwitchActivo.ACTIVO);
            } else {
                binding.containerIdentMarc.setVisibility(View.GONE);
                presenter.setBitIdentificacionMarca(SwitchActivo.DESACTIVO);
            }
        });
        binding.swPersNoExist.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                presenter.setBitMarcaPersonalNoExis(SwitchActivo.ACTIVO);
            } else {
                presenter.setBitMarcaPersonalNoExis(SwitchActivo.DESACTIVO);
            }
        });
        binding.swCodBarras.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                presenter.setBitLecturaPorCamara(SwitchActivo.ACTIVO);
            } else {
                presenter.setBitLecturaPorCamara(SwitchActivo.DESACTIVO);
            }
        });
        binding.swIntVaWeb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                presenter.setBitIntegracionVAWEB(SwitchActivo.ACTIVO);
            } else {
                presenter.setBitIntegracionVAWEB(SwitchActivo.DESACTIVO);
            }
        });
        binding.swAddPhoto.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                presenter.setBitColocarFotoMarca(SwitchActivo.ACTIVO);
            } else {
                presenter.setBitColocarFotoMarca(SwitchActivo.DESACTIVO);
            }
        });

        binding.inputCodTerminal.addTextChangedListener((TextWatcherAdapter)
                (action, charOrEditable, start, count, afterOrBefore) -> {
                    switch (action) {
                        case afterTextChanged:
                            Log.e(TAG, "afterTextChanged charOrEditable: " + charOrEditable + ", start: " + start + ", count: " + count + ", afterOrBefore: " + afterOrBefore);
                            if (charOrEditable.length() > 0)
                                presenter.setNewIdTerminal(charOrEditable.toString());
                            break;
                        case beforeTextChanged:
                        case onTextChanged:
                            break;
                    }
                });

        binding.inputTimeMarc.addTextChangedListener((TextWatcherAdapter)
                (action, charOrEditable, start, count, afterOrBefore) -> {
                    switch (action) {
                        case afterTextChanged:
                            Log.e(TAG, "afterTextChanged charOrEditable: " + charOrEditable + ", start: " + start + ", count: " + count + ", afterOrBefore: " + afterOrBefore);
                            if (charOrEditable.length() > 0)
                                presenter.setIntTiempoEntreMarca(Integer.parseInt(charOrEditable.toString()));
                            break;
                        case beforeTextChanged:
                        case onTextChanged:
                            break;
                    }
                });

        binding.inputMostMarc.addTextChangedListener((TextWatcherAdapter)
                (action, charOrEditable, start, count, afterOrBefore) -> {
                    switch (action) {
                        case afterTextChanged:
                            Log.e(TAG, "afterTextChanged charOrEditable: " + charOrEditable + ", start: " + start + ", count: " + count + ", afterOrBefore: " + afterOrBefore);
                            if (charOrEditable.length() > 0)
                                presenter.setIntMostrarMarca(Integer.parseInt(charOrEditable.toString()));
                            break;
                        case beforeTextChanged:
                        case onTextChanged:
                            break;
                    }
                });

        binding.inputMostPerm.addTextChangedListener((TextWatcherAdapter)
                (action, charOrEditable, start, count, afterOrBefore) -> {
                    switch (action) {
                        case afterTextChanged:
                            Log.e(TAG, "afterTextChanged charOrEditable: " + charOrEditable + ", start: " + start + ", count: " + count + ", afterOrBefore: " + afterOrBefore);
                            if (charOrEditable.length() > 0)
                                presenter.setIntMostrarPermisos(Integer.parseInt(charOrEditable.toString()));
                            break;
                        case beforeTextChanged:
                        case onTextChanged:
                            break;
                    }
                });
    }
}
