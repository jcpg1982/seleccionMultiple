package pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroListMarc;

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
import pe.com.dms.movilasist.databinding.FragmentFilterListMarcBinding;
import pe.com.dms.movilasist.interfaces.OnSelectorListener;
import pe.com.dms.movilasist.model.filterModel.ResultListMarc;
import pe.com.dms.movilasist.ui.fragment.base.BaseToolbarFragment;
import pe.com.dms.movilasist.util.DateUtils;
import pe.com.dms.movilasist.util.TextUtils;
import pe.com.dms.movilasist.util.TintUtils;

public class FiltroListMarcFragment extends BaseToolbarFragment implements View.OnClickListener,
        IFiltroListMarcFragmentContract.View {

    public static final String TAG = FiltroListMarcFragment.class.getSimpleName();

    private FragmentFilterListMarcBinding binding;

    private OnSelectorListener<ResultListMarc> mListener;

    @Inject
    FiltroListMarcFragmentPresenter presenter;

    Calendar calendar;

    public FiltroListMarcFragment() {
    }

    public static FiltroListMarcFragment newInstance() {
        FiltroListMarcFragment fragment = new FiltroListMarcFragment();
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
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFilterListMarcBinding.inflate(inflater, container, false);
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
                if (TextUtils.isEmpty(binding.inputDateIni.getText().toString())) {
                    showWarningMessage("Debe colocar la fecha de inicio");
                    binding.inputDateIni.requestFocus();
                    break;
                }
                if (TextUtils.isEmpty(binding.inputDateFin.getText().toString())) {
                    showWarningMessage("Debe colocar la fecha de fin");
                    binding.inputDateFin.requestFocus();
                    break;
                }
                if (mListener != null)
                    mListener.onSelected(saveDataInModel());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                if (TextUtils.isEmpty(binding.inputDateIni.getText().toString())) {
                    showWarningMessage("Debe colocar la fecha de inicio");
                    binding.inputDateIni.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(binding.inputDateFin.getText().toString())) {
                    showWarningMessage("Debe colocar la fecha de fin");
                    binding.inputDateFin.requestFocus();
                    return;
                }
                if (mListener != null)
                    mListener.onSelected(saveDataInModel());
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
                    showWarningMessage("Debe colocar la fecha de de inicio");
                }
                break;
        }
    }

    private void init() {
        calendar = Calendar.getInstance(Locale.getDefault());
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
        /*binding.inputDateIni.setText(DateUtils.dateToStringFormat(calendar.getTime(), DateUtils.PATTERN_DATE_LECTURA));
        binding.inputDateFin.setText(DateUtils.dateToStringFormat(calendar.getTime(), DateUtils.PATTERN_DATE_LECTURA));*/
    }

    private ResultListMarc saveDataInModel() {
        ResultListMarc listMarc = new ResultListMarc();
        listMarc.setDateIni(binding.inputDateIni.getText().toString());
        listMarc.setDateFin(binding.inputDateFin.getText().toString());
        Log.e(TAG, "saveDataInModel: " + listMarc);
        return listMarc;
    }
}
