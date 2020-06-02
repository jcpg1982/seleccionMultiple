package pe.com.dms.movilasist.ui.fragment.listMarcFragment.cantDiarioFragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.databinding.FragmentCantDiariaBinding;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.model.MarcaGraphics;
import pe.com.dms.movilasist.model.filterModel.ResultListMarc;
import pe.com.dms.movilasist.ui.adapter.fragments.cantDiaria.CantDiariaAdapter;
import pe.com.dms.movilasist.ui.decorations.DividerItemDecoration;
import pe.com.dms.movilasist.ui.fragment.base.BaseMainFragment;
import pe.com.dms.movilasist.util.DateUtils;
import pe.com.dms.movilasist.util.FragmentUtils;
import pe.com.dms.movilasist.util.Utils;

public class CantDiarioFragment extends BaseMainFragment implements View.OnClickListener,
        ICantDiarioFragmentContract.View {
    public static final String TAG = CantDiarioFragment.class.getSimpleName();

    private FragmentCantDiariaBinding binding;

    private OnFragmentInteractionListener mFragmentInteractionListener;

    private LinearLayoutManager mLayoutManager;
    private CantDiariaAdapter mAdapter;

    @Inject
    CantDiarioFragmentPresenter presenter;

    public CantDiarioFragment() {
    }

    public static CantDiarioFragment newInstance() {
        CantDiarioFragment fragment = new CantDiarioFragment();
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
        binding = FragmentCantDiariaBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        setupRecyclerView();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEvents();
        viewListGraphic(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void initEvents() {
    }

    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(mLayoutManager);
        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(),
                R.drawable.line_divider_light, false, true));
        mAdapter = new CantDiariaAdapter();

        binding.recycler.setAdapter(mAdapter);
        //binding.recycler.addOnScrollListener(recyclerViewOnScrollListener);
    }

    @Override
    public void setModelListMarc(ResultListMarc resultListMarc) {
        Log.e(TAG, "setModelListMarc resultListMarc: " + resultListMarc);
        presenter.setModelListMarc(resultListMarc);
    }

    @Override
    public void viewListGraphic(List<MarcaGraphics> listMarcGraphics) {
        if (!FragmentUtils.isFragmentAttached(this))
            return;
        if (listMarcGraphics != null && listMarcGraphics.size() > 0) {
            binding.containerData.setVisibility(View.VISIBLE);
            binding.txtListEmpty.setVisibility(View.GONE);
            binding.piechart.setUsePercentValues(false);
            binding.piechart.setClickable(false);
            binding.piechart.setEnabled(false);
            ArrayList<Entry> yvalues = new ArrayList<>();
            ArrayList<String> val = new ArrayList<>();
            ArrayList<String> adapter = new ArrayList<>();
            for (MarcaGraphics item : listMarcGraphics) {
                int pos = 0;
                yvalues.add(new Entry(item.getIntCantidad(), ++pos));
                val.add(DateUtils.setDateFormat(item.getVchValor(),
                        DateUtils.PATTERN_LECTURA_1,
                        DateUtils.PATTERN_DATE));
                adapter.add(DateUtils.setDateFormat(item.getVchValor(),
                        DateUtils.PATTERN_LECTURA_1,
                        DateUtils.PATTERN_DATE) + " - " + getResources().getQuantityString(R.plurals.marcas,
                        item.getIntCantidad(), item.getIntCantidad()));
            }
            PieDataSet dataSet = new PieDataSet(yvalues, "");
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            PieData data = new PieData(val, dataSet);
            mAdapter.setData(adapter);
            data.setValueTextSize(getResources().getDimension(R.dimen.dynamic_tiny_text_size));
            data.setValueTextColor(Color.BLACK);
            data.setValueFormatter(new DefaultValueFormatter(0));
            binding.piechart.setData(data);
            binding.piechart.setDrawHoleEnabled(true);
            binding.piechart.setTransparentCircleRadius(20f);
            binding.piechart.setHoleRadius(20f);
        } else {
            binding.containerData.setVisibility(View.GONE);
            binding.txtListEmpty.setVisibility(View.VISIBLE);
        }
    }
}
