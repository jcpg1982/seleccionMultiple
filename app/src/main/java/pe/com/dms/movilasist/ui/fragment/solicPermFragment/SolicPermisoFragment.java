package pe.com.dms.movilasist.ui.fragment.solicPermFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.annotacion.OptionRegSolicPerm;
import pe.com.dms.movilasist.annotacion.SwitchActivo;
import pe.com.dms.movilasist.bd.entity.Conceptos;
import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.bd.entity.Supervisor;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.databinding.FragmentSolicitudPermisoBinding;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;
import pe.com.dms.movilasist.ui.fragment.base.BaseMainFragment;
import pe.com.dms.movilasist.ui.fragment.lastPermFragment.LastPermFragment;
import pe.com.dms.movilasist.util.DateUtils;
import pe.com.dms.movilasist.util.TextUtils;
import pe.com.dms.movilasist.util.TintUtils;

public class SolicPermisoFragment extends BaseMainFragment implements View.OnClickListener,
        SolicPermisoFragmentContract.View {

    public static final String TAG = SolicPermisoFragment.class.getSimpleName();

    private FragmentSolicitudPermisoBinding binding;

    private OnFragmentInteractionListener mFragmentInteractionListener;

    @Inject
    SolicPermisoFragmentPresenter presenter;
    private Calendar calendar;
    private SolicitudPermiso mSolicitudPermiso;
    private Conceptos mConceptos;
    private Supervisor mSupervisor;
    @OptionRegSolicPerm
    private int mOptionType;

    public SolicPermisoFragment() {
    }

    public static SolicPermisoFragment newInstance(@OptionRegSolicPerm int optionType,
                                                   SolicitudPermiso solicitudPermiso) {
        SolicPermisoFragment fragment = new SolicPermisoFragment();
        Bundle args = fragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        args.putSerializable(Constants.Intent.EXTRA_SOLICITUD_PERMISO, solicitudPermiso);
        args.putInt(Constants.Intent.EXTRA_OPTION_TYPE_VIEW, optionType);
        fragment.setArguments(args);
        Log.e(TAG, "newInstance solicitudPermiso: " + solicitudPermiso);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        Log.e(TAG, "onCreate");
        presenter.attachView(this);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        calendar = Calendar.getInstance(Locale.getDefault());
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSolicitudPermisoBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        initEvents();
        presenter.obtainListSolicitud();
        presenter.obtainListsupervisor();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillData(mSolicitudPermiso);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_solic_permiso, menu);
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
            case R.id.menu_save:
                sadeDataInVariables();

                if (TextUtils.isEmpty(binding.inputCodPersonal.getText().toString())) {
                    showWarningMessage("el codigo no debe estar vacio");
                    binding.inputCodPersonal.requestFocus();
                    break;
                }

                if (mConceptos == null) {
                    showWarningMessage("El tipó de solicitud no debe estar vacio");
                    binding.spSolicitud.requestFocus();
                    break;
                }

                if (binding.containerInputDateIni.getVisibility() == View.VISIBLE)
                    if (TextUtils.isEmpty(binding.inputDateIni.getText().toString())) {
                        showWarningMessage("La fecha de inicio no debe estar vacio");
                        binding.inputDateIni.requestFocus();
                        break;
                    }

                if (binding.containerInputTimeIni.getVisibility() == View.VISIBLE)
                    if (TextUtils.isEmpty(binding.inputTimeIni.getText().toString())) {
                        showWarningMessage("La hora de inicio no debe estar vacio");
                        binding.inputTimeIni.requestFocus();
                        break;
                    }

                if (binding.containerInputDateFin.getVisibility() == View.VISIBLE)
                    if (TextUtils.isEmpty(binding.inputDateFin.getText().toString())) {
                        showWarningMessage("La fecha de fin no debe estar vacio");
                        binding.inputDateFin.requestFocus();
                        break;
                    }

                if (binding.containerInputTimeFin.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(binding.inputTimeFin.getText().toString())) {
                        showWarningMessage("La hora de fin no debe estar vacio");
                        binding.inputTimeFin.requestFocus();
                        break;
                    }
                    if (presenter.validarHora(
                            binding.inputDateIni.getText().toString() + " " + binding.inputTimeIni.getText().toString(),
                            binding.inputDateFin.getText().toString() + " " + binding.inputTimeFin.getText().toString())) {
                        showWarningMessage("La hora final no debe ser igual ni menor  que la hora final");
                        break;
                    }
                }

                if (mSupervisor == null) {
                    showWarningMessage("Seleccione su supervisor");
                    binding.spSuper.requestFocus();
                    break;
                }

                switch (mOptionType) {
                    case OptionRegSolicPerm.NEW:
                        presenter.validatedConnection();
                        break;
                    case OptionRegSolicPerm.EDIT:
                        presenter.setEditSolicPermOnLine(mSolicitudPermiso);
                        break;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_calendar_ini:
            case R.id.input_date_ini:
                presenter.showDatePickerDialog(getFragmentManager(), binding.inputDateIni,
                        null);
                binding.inputDateFin.setText(null);
                break;
            case R.id.btn_calendar_fin:
            case R.id.input_date_fin:
                if (!TextUtils.isEmpty(binding.inputDateIni.getText().toString())) {
                    try {
                        String dateIni = DateUtils.setDateFormat(binding.inputDateIni.getText().toString(),
                                DateUtils.PATTERN_DATE_LECTURA,
                                DateUtils.PATTERN_DATE_RESULTADO);

                        calendar.setTime(DateUtils.fromStringToDate(dateIni,
                                new SimpleDateFormat(DateUtils.PATTERN_DATE_RESULTADO)));
                        presenter.showDatePickerDialog(getFragmentManager(), binding.inputDateFin,
                                calendar.getTimeInMillis());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    showWarningMessage("Debe colocar la fecha de de inicio");
                }
                break;
            case R.id.input_time_ini:
                presenter.showTimePickerDialog(getFragmentManager(), binding.inputTimeIni);
                break;
            case R.id.input_time_fin:
                presenter.showTimePickerDialog(getFragmentManager(), binding.inputTimeFin);
                break;
            case R.id.container_bottom:
                if (mFragmentInteractionListener != null)
                    mFragmentInteractionListener.onAddFragmentToStack(
                            LastPermFragment.newInstance(),
                            "Ultimos permisos",
                            null,
                            true,
                            true,
                            true,
                            LastPermFragment.TAG);
                break;
        }
    }

    @Override
    public void viewInfoUser(Usuario user) {
        if (user != null) {
            binding.inputCodPersonal.setText(user.getVchCodigoPersonal());
            binding.textNameLastName.setText(user.getVchNombre() + " " + user.getVchApellidos());
        }
    }

    @Override
    public void cleanViews() {
        binding.inputDateIni.setText(null);
        binding.inputTimeIni.setText(null);
        binding.inputDateFin.setText(null);
        binding.inputTimeFin.setText(null);
        binding.inputObserv.setText(null);
    }

    @Override
    public void viewMessageExitoso() {
        new DefaultDialog.Builder(getContext())
                .title(getResources().getString(R.string.attention))
                .message("Su marcacion fue enviada con exito")
                .cancelable(false)
                .setIcon(R.drawable.ic_help_outline)
                .textPositiveButton(getResources().getString(R.string.button_accept))
                .textColorPositiveButton(R.color.success)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        switch (mOptionType) {
                            case OptionRegSolicPerm.NEW:
                                cleanViews();
                                break;
                            case OptionRegSolicPerm.EDIT:
                                closed();
                                break;
                        }
                        dialog.dismiss();

                    }
                })
                .buildAndShow(((AppCompatActivity) getActivity()).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_SAVE_SOLIC_PERM_EXITOSO);
    }

    @Override
    public void llenarSpinerSolicitud(List<Conceptos> listConceptos) {
        if (listConceptos != null
                && listConceptos.size() > 0) {
            Log.e(TAG, "llenarSpiner if");
            ArrayList<String> itemSelect = new ArrayList<>();
            for (Conceptos iten : listConceptos) {
                itemSelect.add(iten.getVchConcepto());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,
                    itemSelect);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spSolicitud.setAdapter(adapter);
            switch (mOptionType) {
                case OptionRegSolicPerm.EDIT:
                    binding.spSolicitud.setSelection(presenter.positionSolicitud(binding.spSolicitud,
                            mSolicitudPermiso.getVchCodConcepto()));
                    break;
            }
        }
    }

    @Override
    public void llenarSpinnerSupervisor(List<Supervisor> listSupervisor) {
        if (listSupervisor != null
                && listSupervisor.size() > 0) {
            Log.e(TAG, "llenarSpiner if");
            ArrayList<String> itemSelect = new ArrayList<>();
            for (Supervisor iten : listSupervisor) {
                itemSelect.add(iten.getVchNombreApellido());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,
                    itemSelect);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spSuper.setAdapter(adapter);
            switch (mOptionType) {
                case OptionRegSolicPerm.EDIT:
                    binding.spSuper.setSelection(presenter.positionSuper(binding.spSuper,
                            mSolicitudPermiso.getVchCodSupervisor()));
                    break;
            }
        }
    }

    @Override
    public void closed() {
        if (mFragmentInteractionListener != null)
            mFragmentInteractionListener.onRemoveFragmentToStack(true);
    }

    private void init() {
        if (getArguments() != null) {
            mSolicitudPermiso = (SolicitudPermiso) getArguments().getSerializable(Constants.Intent.EXTRA_SOLICITUD_PERMISO);
            mOptionType = getArguments().getInt(Constants.Intent.EXTRA_OPTION_TYPE_VIEW);
        }
    }

    private void initEvents() {
        presenter.obtainUser();
        presenter.obtainConfig();
        binding.inputDateIni.setOnClickListener(this);
        binding.inputDateIni.setKeyListener(null);
        binding.btnCalendarIni.setOnClickListener(this);

        binding.inputDateFin.setOnClickListener(this);
        binding.inputDateFin.setKeyListener(null);
        binding.btnCalendarFin.setOnClickListener(this);

        binding.inputTimeIni.setOnClickListener(this);
        binding.inputTimeIni.setKeyListener(null);
        binding.inputTimeFin.setOnClickListener(this);
        binding.inputTimeFin.setKeyListener(null);

        binding.inputCodPersonal.setKeyListener(null);

        binding.containerBottom.setOnClickListener(this);

        binding.containerDesde.setVisibility(View.GONE);
        binding.containerHasta.setVisibility(View.GONE);
        binding.textFecha.setVisibility(View.GONE);

        binding.spSolicitud.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    binding.containerDesde.setVisibility(View.VISIBLE);
                    binding.containerHasta.setVisibility(View.VISIBLE);
                    binding.textFecha.setVisibility(View.VISIBLE);
                    mConceptos = presenter.getListConceptos().get(position);
                    switch (mOptionType) {
                        case OptionRegSolicPerm.NEW:
                            presenter.setConcepto(mConceptos);
                            break;
                        case OptionRegSolicPerm.EDIT:
                            mSolicitudPermiso.setVchCodConcepto(mConceptos.getVchCodConcepto());
                            mSolicitudPermiso.setIntTipoUso(mConceptos.getIntTipoUso());
                            break;
                    }
                    switch (mConceptos.getIntTipoUnidad()) {
                        case 0:
                            binding.containerInputTimeIni.setVisibility(View.VISIBLE);
                            binding.containerInputTimeFin.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            binding.containerInputTimeIni.setVisibility(View.GONE);
                            binding.containerInputTimeFin.setVisibility(View.GONE);
                            break;
                    }
                }
                Log.e(TAG, "onItemSelected motivo: " + mConceptos);
                Log.e(TAG, "onItemSelected posicion: " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spSuper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    mSupervisor = presenter.getListSupervisor().get(position);
                    switch (mOptionType) {
                        case OptionRegSolicPerm.NEW:
                            presenter.setSupervisor(mSupervisor);
                            break;
                        case OptionRegSolicPerm.EDIT:
                            mSolicitudPermiso.setVchCodSupervisor(mSupervisor.getVchCodigoPersonal());
                            mSolicitudPermiso.setVchEmailSupervisor(mSupervisor.getVchCorreo());
                            break;
                    }
                }
                Log.e(TAG, "onItemSelected motivo: " + mSupervisor);
                Log.e(TAG, "onItemSelected posicion: " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.cbNextDayIni.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                presenter.setIntPertenenciaInicio(SwitchActivo.ACTIVO);
                binding.cbNextDayIni.setButtonDrawable(R.drawable.ic_action_checked);
            } else {
                binding.cbNextDayIni.setButtonDrawable(R.drawable.ic_action_unchecked);
                presenter.setIntPertenenciaInicio(SwitchActivo.DESACTIVO);
            }
        });

        binding.cbNextDayFin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                presenter.setIntPertenenciaFin(SwitchActivo.ACTIVO);
                binding.cbNextDayFin.setButtonDrawable(R.drawable.ic_action_checked);
            } else {
                presenter.setIntPertenenciaFin(SwitchActivo.DESACTIVO);
                binding.cbNextDayFin.setButtonDrawable(R.drawable.ic_action_unchecked);
            }
        });
    }

    private void fillData(SolicitudPermiso solicitudPermiso) {
        if (solicitudPermiso != null) {

            binding.inputDateIni.setText(solicitudPermiso.getDtmFechaInicio());
            binding.inputDateFin.setText(solicitudPermiso.getDtmFechaFin());

            binding.inputTimeIni.setText(solicitudPermiso.getVchHoraInicio());
            binding.inputTimeFin.setText(solicitudPermiso.getVchHoraFin());

            binding.inputObserv.setText(solicitudPermiso.getVchObservacion());

            binding.cbNextDayIni.setChecked(solicitudPermiso.getIntPertenenciaInicio() == SwitchActivo.ACTIVO);
            binding.cbNextDayFin.setChecked(solicitudPermiso.getIntPertenenciaFin() == SwitchActivo.ACTIVO);

        } else {
            binding.inputDateIni.setText(DateUtils.dateToStringFormat(calendar.getTime(), DateUtils.PATTERN_DATE_LECTURA));
            binding.inputDateFin.setText(DateUtils.dateToStringFormat(calendar.getTime(), DateUtils.PATTERN_DATE_LECTURA));

            binding.inputTimeIni.setText(DateUtils.dateToStringFormat(calendar.getTime(), DateUtils.PATTERN_TIME_TAREO));
            binding.inputTimeFin.setText(DateUtils.dateToStringFormat(calendar.getTime(), DateUtils.PATTERN_TIME_TAREO));
        }
    }

    private void sadeDataInVariables() {
        switch (mOptionType) {
            case OptionRegSolicPerm.NEW:
                if (binding.containerInputDateIni.getVisibility() == View.VISIBLE)
                    presenter.setDtmFechaInicio(binding.inputDateIni.getText().toString());
                else
                    presenter.setDtmFechaInicio("");

                if (binding.containerInputTimeIni.getVisibility() == View.VISIBLE)
                    presenter.setVchHoraInicio(binding.inputTimeIni.getText().toString());
                else
                    presenter.setVchHoraInicio("");

                if (binding.containerInputDateFin.getVisibility() == View.VISIBLE)
                    presenter.setDtmFechaFin(binding.inputDateFin.getText().toString());
                else
                    presenter.setDtmFechaFin("");

                if (binding.containerInputTimeFin.getVisibility() == View.VISIBLE)
                    presenter.setVchHoraFin(binding.inputTimeFin.getText().toString());
                else
                    presenter.setVchHoraFin("");

                presenter.setVchObservacion(binding.inputObserv.getText().toString());
                presenter.setIntEstadoSolicitud(0);
                break;
            case OptionRegSolicPerm.EDIT:
                if (binding.containerInputDateIni.getVisibility() == View.VISIBLE)
                    mSolicitudPermiso.setDtmFechaInicio(binding.inputDateIni.getText().toString());
                else
                    mSolicitudPermiso.setDtmFechaInicio("");

                if (binding.containerInputTimeIni.getVisibility() == View.VISIBLE)
                    mSolicitudPermiso.setVchHoraInicio(binding.inputTimeIni.getText().toString());
                else
                    mSolicitudPermiso.setVchHoraInicio("");

                if (binding.containerInputDateFin.getVisibility() == View.VISIBLE)
                    mSolicitudPermiso.setDtmFechaFin(binding.inputDateFin.getText().toString());
                else
                    mSolicitudPermiso.setDtmFechaFin("");

                if (binding.containerInputTimeFin.getVisibility() == View.VISIBLE)
                    mSolicitudPermiso.setVchHoraFin(binding.inputTimeFin.getText().toString());
                else
                    mSolicitudPermiso.setVchHoraFin("");

                mSolicitudPermiso.setIntIntegracionVAWEB(presenter.integracionVAWEB());
                break;
        }
    }
}
