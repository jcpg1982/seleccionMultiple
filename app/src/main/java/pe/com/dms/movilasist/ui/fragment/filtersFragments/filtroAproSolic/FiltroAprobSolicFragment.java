package pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroAproSolic;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.bd.entity.StatusSolicitud;
import pe.com.dms.movilasist.databinding.FragmentFilterListAprobSolicBinding;
import pe.com.dms.movilasist.interfaces.OnSelectorListener;
import pe.com.dms.movilasist.model.filterModel.ResultListApobSolic;
import pe.com.dms.movilasist.ui.fragment.base.BaseToolbarFragment;
import pe.com.dms.movilasist.util.DateUtils;
import pe.com.dms.movilasist.util.TextUtils;
import pe.com.dms.movilasist.util.TintUtils;

public class FiltroAprobSolicFragment extends BaseToolbarFragment implements View.OnClickListener,
        IFiltroAprobSolicFragmentContract.View {

    public static final String TAG = FiltroAprobSolicFragment.class.getSimpleName();

    private FragmentFilterListAprobSolicBinding binding;

    private OnSelectorListener<ResultListApobSolic> mListener;

    //private String mDateIni, mDateFin, mcodPers, mNameLastName, mStatusSolicitud;

    @Inject
    FiltroAprobSolicFragmentPresenter presenter;

    private Calendar calendar;
    private int idStatusSolicitud;

    public FiltroAprobSolicFragment() {
    }

    public static FiltroAprobSolicFragment newInstance() {
        FiltroAprobSolicFragment fragment = new FiltroAprobSolicFragment();
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
        if (context instanceof OnSelectorListener) {
            mListener = (OnSelectorListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnSelectorListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFilterListAprobSolicBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        presenter.getListStatusSolicitud();;
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEvents();
        initData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
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
                resultFilter();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                resultFilter();
                break;
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
                    showWarningMessage("Primero debe colocar la fecha de inicio");
                }
                break;
        }
    }

    private void resultFilter() {
        saveDataInVariable();
        if (TextUtils.isEmpty(binding.inputDateIni.getText().toString())) {
            showWarningMessage("La fecha de inicio no debe estar vacia");
            return;
        }

        if (TextUtils.isEmpty(binding.inputDateFin.getText().toString())) {
            showWarningMessage("La fecha final no debe estar vacia");
            return;
        }

        if (idStatusSolicitud < 0) {
            showWarningMessage("El estado de la solicitud no debe estar vacio");
            return;
        }

        if (mListener != null)
            mListener.onSelected(presenter.filtroListAprobSolic());
    }

    private void initEvents() {
        binding.btnSearch.setOnClickListener(this);

        binding.inputDateIni.setOnClickListener(this);
        binding.inputDateIni.setKeyListener(null);
        binding.btnCalendarIni.setOnClickListener(this);

        binding.inputDateFin.setOnClickListener(this);
        binding.inputDateFin.setKeyListener(null);
        binding.btnCalendarFin.setOnClickListener(this);

        binding.spStatusSolicitud.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idStatusSolicitud = presenter.listStatusSolicitud().get(position).getId();
                Log.e(TAG, "onItemSelected Tipo marcacion: " + presenter.listStatusSolicitud().get(position));
                Log.e(TAG, "onItemSelected posicion: " + position);
                Log.e(TAG, "onItemSelected idStatusSolicitud: " + idStatusSolicitud);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initData() {
        binding.inputDateIni.setText(DateUtils.dateToStringFormat(calendar.getTime(), DateUtils.PATTERN_DATE_LECTURA));
        binding.inputDateFin.setText(DateUtils.dateToStringFormat(calendar.getTime(), DateUtils.PATTERN_DATE_LECTURA));
    }

    private void saveDataInVariable() {
        presenter.setDateIni(binding.inputDateIni.getText().toString());
        presenter.setDateFin(binding.inputDateFin.getText().toString());
        presenter.setCodPersonal(binding.inputCodPersonal.getText().toString());
        presenter.setNameLastName(binding.inputNameLastName.getText().toString());
    }

    @Override
    public void llenarSpinnerSolicitud(List<StatusSolicitud> listStatusSolicitud) {
        if (listStatusSolicitud != null
                && listStatusSolicitud.size() > 0) {
            Log.e(TAG, "llenarSpiner if");
            ArrayList<String> itemSelect = new ArrayList<>();
            for (StatusSolicitud rellenas : listStatusSolicitud) {
                itemSelect.add(rellenas.getDescripcion());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,
                    itemSelect);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spStatusSolicitud.setAdapter(adapter);
        }
    }
}
