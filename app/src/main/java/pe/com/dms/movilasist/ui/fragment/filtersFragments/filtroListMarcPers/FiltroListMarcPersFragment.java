package pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroListMarcPers;

import android.content.Context;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.databinding.FragmentFilterListMarcPersBinding;
import pe.com.dms.movilasist.interfaces.OnSelectorListener;
import pe.com.dms.movilasist.model.filterModel.ResultListMarcPers;
import pe.com.dms.movilasist.ui.fragment.base.BaseToolbarFragment;
import pe.com.dms.movilasist.util.DateUtils;
import pe.com.dms.movilasist.util.TextUtils;
import pe.com.dms.movilasist.util.TintUtils;

public class FiltroListMarcPersFragment extends BaseToolbarFragment implements View.OnClickListener,
        IFiltroListMarcPersFragmentContract.View {
    public static final String TAG = FiltroListMarcPersFragment.class.getSimpleName();

    private FragmentFilterListMarcPersBinding binding;

    @Inject
    FiltroListMarcPersFragmentPresenter presenter;

    private OnSelectorListener<ResultListMarcPers> mListener;
    Calendar calendar;

    public FiltroListMarcPersFragment() {
    }

    public static FiltroListMarcPersFragment newInstance() {
        FiltroListMarcPersFragment fragment = new FiltroListMarcPersFragment();
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
        binding = FragmentFilterListMarcPersBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        initEvents();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                actionResult();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                actionResult();
                break;
            case R.id.btn_calendar_ini:
            case R.id.input_date_ini:
                presenter.showDatePickerDialog(getFragmentManager(), binding.inputDateIni,
                        null);
                binding.inputDateFin.setText(null);
                break;
            case R.id.btn_calendar_fin:
            case R.id.input_date_fin:
                if (TextUtils.isEmpty(binding.inputDateIni)) {
                    showWarningMessage("Debe colocar la fecha de inicio");
                    return;
                } else {
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
                }
                break;
        }
    }

    private void initEvents() {
        binding.btnSearch.setOnClickListener(this);

        binding.inputDateIni.setOnClickListener(this);
        binding.inputDateIni.setKeyListener(null);
        binding.btnCalendarIni.setOnClickListener(this);

        binding.inputDateFin.setOnClickListener(this);
        binding.inputDateFin.setKeyListener(null);
        binding.btnCalendarFin.setOnClickListener(this);

    }

    private void initData() {
        binding.inputDateIni.setText(DateUtils.dateToStringFormat(calendar.getTime(), DateUtils.PATTERN_DATE_LECTURA));
        binding.inputDateFin.setText(DateUtils.dateToStringFormat(calendar.getTime(), DateUtils.PATTERN_DATE_LECTURA));
    }

    private ResultListMarcPers saveDataInModel() {
        ResultListMarcPers resultListMarcPers = new ResultListMarcPers();
        resultListMarcPers.setCodPersonal(binding.inputCodPersonal.getText().toString());
        resultListMarcPers.setNameLastName(binding.inputNameLastName.getText().toString());
        resultListMarcPers.setDateIni(binding.inputDateIni.getText().toString());
        resultListMarcPers.setDateFin(binding.inputDateFin.getText().toString());
        Log.e(TAG, "saveDataInModel: " + resultListMarcPers);
        return resultListMarcPers;
    }


    private void actionResult() {
        if (TextUtils.isEmpty(binding.inputDateIni)) {
            showWarningMessage("Debe colocar la fecha de inicio");
            return;
        }
        if (TextUtils.isEmpty(binding.inputDateFin)) {
            showWarningMessage("Debe colocar la fecha de termino");
            return;
        }
        if (mListener != null)
            mListener.onSelected(saveDataInModel());
    }
}
