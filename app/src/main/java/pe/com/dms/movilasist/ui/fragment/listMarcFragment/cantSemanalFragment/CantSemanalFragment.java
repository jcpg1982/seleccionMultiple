package pe.com.dms.movilasist.ui.fragment.listMarcFragment.cantSemanalFragment;

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
import pe.com.dms.movilasist.databinding.FragmentCantSemanalBinding;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.model.MarcaGraphics;
import pe.com.dms.movilasist.model.filterModel.ResultListMarc;
import pe.com.dms.movilasist.ui.adapter.fragments.cantSemanal.CantSemanalAdapter;
import pe.com.dms.movilasist.ui.decorations.DividerItemDecoration;
import pe.com.dms.movilasist.ui.fragment.base.BaseMainFragment;

public class CantSemanalFragment extends BaseMainFragment implements View.OnClickListener,
        ICantSemanalFragmentContract.View {

    public static final String TAG = CantSemanalFragment.class.getSimpleName();

    private FragmentCantSemanalBinding binding;

    private OnFragmentInteractionListener mFragmentInteractionListener;

    private LinearLayoutManager mLayoutManager;
    private CantSemanalAdapter mAdapter;

    @Inject
    CantSemanalFragmentPresenter presenter;

    public CantSemanalFragment() {
    }

    public static CantSemanalFragment newInstance() {
        CantSemanalFragment fragment = new CantSemanalFragment();
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
        presenter.detachView();
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
        binding = FragmentCantSemanalBinding.inflate(inflater, container, false);
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
        binding.piechart.setUsePercentValues(true);

        ArrayList<Entry> yvalues = new ArrayList<>();
        yvalues.add(new Entry(50, 0));
        yvalues.add(new Entry(50, 1));
        yvalues.add(new Entry(20, 2));

        PieDataSet dataSet = new PieDataSet(yvalues, "");

        //dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        //dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        /*dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);*/

        ArrayList<String> val = new ArrayList<>();
        val.add("Semana 1");
        val.add("semana 2");
        val.add("aemana 3");
        PieData data = new PieData(val, dataSet);

        mAdapter.setData(val);

        data.setValueTextSize(getResources().getDimension(R.dimen.dynamic_tiny_text_size));
        data.setValueTextColor(Color.BLACK);

        data.setValueFormatter(new DefaultValueFormatter(0));

        binding.piechart.setData(data);
        binding.piechart.setDrawHoleEnabled(true);
        binding.piechart.setTransparentCircleRadius(20f);
        binding.piechart.setHoleRadius(20f);
    }

    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(mLayoutManager);
        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(),
                R.drawable.line_divider_light, false, true));
        mAdapter = new CantSemanalAdapter();

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
                val.add(item.getVchValor());
                adapter.add(item.getVchValor() + " - " + getResources().getQuantityString(R.plurals.marcas,
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
