package pe.com.dms.movilasist.ui.fragment.listLastMarcFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.annotacion.TypeOption;
import pe.com.dms.movilasist.databinding.FragmentLastMarcBinding;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.interfaces.OnItemClickActionListener;
import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.request.RequestDeleteLastMarc;
import pe.com.dms.movilasist.ui.activities.mainActivity.MainActivity;
import pe.com.dms.movilasist.ui.adapter.fragments.ListMarcAdapter.ListMarcAdapter;
import pe.com.dms.movilasist.ui.decorations.DividerItemDecoration;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;
import pe.com.dms.movilasist.ui.fragment.base.BaseFragment;
import pe.com.dms.movilasist.util.ImageConverterUtils;
import pe.com.dms.movilasist.util.swipe.SwipeHelper;
import pe.com.dms.movilasist.model.ButtonSwipeAction;

public class ListLastMarcFragment extends BaseFragment implements View.OnClickListener,
        IListLastMarcFragmentContract.View {

    public static final String TAG = ListLastMarcFragment.class.getSimpleName();

    private OnFragmentInteractionListener mFragmentInteractionListener;

    @Inject
    ListLastMarcFragmentPresenter presenter;

    private FragmentLastMarcBinding binding;

    private ListMarcAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    public ListLastMarcFragment() {
    }

    public static ListLastMarcFragment newInstance() {
        ListLastMarcFragment fragment = new ListLastMarcFragment();
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
        Log.e(TAG, "onCreateView");
        binding = FragmentLastMarcBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        setupSwipeRefresh();
        setupRecyclerView();
        loadData();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated");
        initEvents();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onCreateView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        presenter.detachView();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void closed() {
        ((MainActivity) getActivity()).closedMain();
    }

    @Override
    public void mostrarLastMarc(ArrayList<Marcaciones> listLastMarc) {
        mAdapter.setData(listLastMarc);
    }

    @Override
    public void loadData() {
        presenter.validateConnection(TypeOption.OBTAIN_LAST_MARC, null, null);
    }

    private void initEvents() {
    }

    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(mLayoutManager);
        binding.recycler.setAdapter(mAdapter);

        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(),
                R.drawable.line_divider_primary, false, true));
        mAdapter = new ListMarcAdapter();

        binding.recycler.setAdapter(mAdapter);
        //binding.recycler.addOnScrollListener(recyclerViewOnScrollListener);

        SwipeHelper swipeHelper = new SwipeHelper(getContext(), binding.recycler) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<ButtonSwipeAction> underlayButtons) {
                underlayButtons.add(new ButtonSwipeAction(
                        "Delete",
                        ImageConverterUtils.drawableToBitmap(getResources().getDrawable(R.drawable.ic_action_delete)),
                        Color.parseColor("#FF1744"),
                        new OnItemClickActionListener() {
                            @Override
                            public void onClick(int pos) {
                                Log.e(TAG, "onClick pos: " + pos);
                                Log.e(TAG, "onClick marcacion: " + mAdapter.getObject(pos));
                                mostrarMensajeConfirmacionDeleteMarc(mAdapter.getObject(pos));

                            }
                        }
                ));
            }
        };
        swipeHelper.attachSwipe();
    }

    private void setupSwipeRefresh() {
        binding.swipeRecycler.setProgressBackgroundColorSchemeColor(
                getResources().getColor(R.color.colorCardLight));
        binding.swipeRecycler.setColorSchemeResources(R.color.colorAccent);
        binding.swipeRecycler.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        loadData();
                        binding.swipeRecycler.setRefreshing(false);
                    }
                });
    }

    private void mostrarMensajeConfirmacionDeleteMarc(Marcaciones marcaciones) {
        new DefaultDialog.Builder(getContext())
                .title(getResources().getString(R.string.attention))
                .message("Va a eliminar su marcacion\n" +
                        "¿Desea eleminar su marcacion seleccionada?")
                .cancelable(false)
                .setIcon(R.drawable.ic_help_outline)
                .textPositiveButton(getResources().getString(R.string.button_delete))
                .textColorPositiveButton(R.color.danger)
                .textNegativeButton(getResources().getString(R.string.button_cancel))
                .textColorNegativeButton(R.color.success)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        Log.e(TAG, "mostrarMensajeConfirmacionDeleteMarc: " + marcaciones);
                        presenter.validateConnection(TypeOption.DELETE_LAST_MARC, null,
                                new RequestDeleteLastMarc(marcaciones.getVchCodPersonal(),
                                        marcaciones.getIntTipoMarca(),
                                        0));
                    }
                })
                .onNegative(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.cancel();
                        closed();
                    }
                })
                .buildAndShow(((AppCompatActivity) getActivity()).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_DELETE_MARC);
    }
}
