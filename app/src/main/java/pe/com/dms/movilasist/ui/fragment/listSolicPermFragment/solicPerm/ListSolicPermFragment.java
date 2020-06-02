package pe.com.dms.movilasist.ui.fragment.listSolicPermFragment.solicPerm;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.annotacion.ActionSwipeRecycler;
import pe.com.dms.movilasist.annotacion.OptionRegSolicPerm;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.databinding.FragmentListSolicPermBinding;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.interfaces.OnItemClickActionListener;
import pe.com.dms.movilasist.model.SolicitudesPermiso;
import pe.com.dms.movilasist.model.filterModel.ResultListSolicPers;
import pe.com.dms.movilasist.ui.adapter.fragments.listSolicPermFragment.ListSolicPermAdapter;
import pe.com.dms.movilasist.ui.decorations.DividerItemDecoration;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;
import pe.com.dms.movilasist.ui.fragment.base.BaseMainFragment;
import pe.com.dms.movilasist.ui.fragment.listMarcFragment.ListMarcPagerFragment;
import pe.com.dms.movilasist.ui.fragment.listSolicPermFragment.ListSolicPermisosPagerFragment;
import pe.com.dms.movilasist.ui.fragment.solicPermFragment.SolicPermisoFragment;
import pe.com.dms.movilasist.util.DateUtils;
import pe.com.dms.movilasist.util.ImageConverterUtils;
import pe.com.dms.movilasist.model.ButtonSwipeAction;
import pe.com.dms.movilasist.util.swipe.SwipeHelperUpdate;
import pe.com.dms.movilasist.util.swipe.SwipeHelperUpdate;

public class ListSolicPermFragment extends BaseMainFragment implements View.OnClickListener,
        IListSolicPermFragmentContract.View {

    public static final String TAG = ListSolicPermFragment.class.getSimpleName();

    private FragmentListSolicPermBinding binding;

    private OnFragmentInteractionListener mFragmentInteractionListener;
    private int page = 1;
    @Inject
    ListSolicPermFragmentPresenter presenter;

    private ListSolicPermAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ItemTouchHelper mItemTouchHelper;

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener =
            new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                    if (!presenter.islastPage()) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0
                                && totalItemCount >= presenter.PAGE_SIZE()) {
                            presenter.getMoreListSolicPermOnLine(++page);
                        }
                    }
                }
            };

    public ListSolicPermFragment() {
    }

    public static ListSolicPermFragment newInstance() {
        ListSolicPermFragment fragment = new ListSolicPermFragment();
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
        binding = FragmentListSolicPermBinding.inflate(inflater, container, false);
        initEvents();
        setupRecyclerView();
        //setupSwipeRefresh();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewListSolicPerm(null);
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
        binding.recycler.setAdapter(mAdapter);

        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(),
                R.drawable.line_divider_primary, false, true));
        mAdapter = new ListSolicPermAdapter();

        binding.recycler.setAdapter(mAdapter);
        binding.recycler.addOnScrollListener(recyclerViewOnScrollListener);

        ItemTouchHelper.Callback callback = new SwipeHelperUpdate(getContext(), binding.recycler) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder,
                                                  List<ButtonSwipeAction> createButtonSwipes) {
                createButtonSwipes.add(new ButtonSwipeAction(
                        "Borrar",
                        ImageConverterUtils.drawableToBitmap(getResources().getDrawable(R.drawable.ic_action_delete)),
                        Color.parseColor("#FF1744"),
                        new OnItemClickActionListener() {
                            @Override
                            public void onClick(int pos) {
                                Log.e(TAG, "onClick pos: " + pos);
                                Log.e(TAG, "onClick marcacion: " + mAdapter.getObject(pos));
                                switch (mAdapter.getObject(pos).getIntEstadoSolicitud()) {
                                    case 0:
                                        mostrarMensajeConfirmacionDeleteMarc(mAdapter.getObject(pos));
                                        break;
                                    default:
                                        showWarningMessage("No puede realizar esta acción");
                                        break;
                                }

                            }
                        }
                ));
                createButtonSwipes.add(new ButtonSwipeAction(
                        "Editar",
                        ImageConverterUtils.drawableToBitmap(getResources().getDrawable(R.drawable.ic_action_edit)),
                        Color.parseColor("#3F51B5"),
                        new OnItemClickActionListener() {
                            @Override
                            public void onClick(int pos) {
                                Log.e(TAG, "onClick pos: " + pos);
                                Log.e(TAG, "onClick mAdapter.getObject(pos): " + mAdapter.getObject(pos));
                                switch (mAdapter.getObject(pos).getIntEstadoSolicitud()) {
                                    case 0:
                                        mostrarMensajeConfirmacionEditMarc(mAdapter.getObject(pos));
                                        break;
                                    default:
                                        showWarningMessage("No puede realizar esta acción");
                                        break;
                                }

                            }
                        }
                ));
            }
        };
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(binding.recycler);
    }

    /*private void setupSwipeRefresh() {
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

        SwipeHelper swipeHelper = new SwipeHelper(getContext(), binding.recycler) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder,
                                                  List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        ImageConverterUtils.drawableToBitmap(getResources().getDrawable(R.drawable.ic_action_delete)),
                        Color.parseColor("#FF1744"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Log.e(TAG, "onClick pos: " + pos);
                                Log.e(TAG, "onClick marcacion: " + mAdapter.getObject(pos));
                                mostrarMensajeConfirmacionDeleteMarc(mAdapter.getObject(pos));

                            }
                        }
                ));

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Edit",
                        ImageConverterUtils.drawableToBitmap(getResources().getDrawable(R.drawable.ic_action_edit)),
                        Color.parseColor("#3F51B5"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Log.e(TAG, "onClick pos: " + pos);
                                Log.e(TAG, "onClick marcacion: " + mAdapter.getObject(pos));
                                //mostrarMensajeConfirmacionDeleteMarc(mAdapter.getObject(pos));
                            }
                        }
                ));
            }
        };
        swipeHelper.attachSwipe();
    }*/

    @Override
    public void setResultListSolicPers(ResultListSolicPers resultListSolicPers) {
        presenter.setResultListSolicPers(resultListSolicPers);
    }

    @Override
    public void viewListSolicPerm(List<SolicitudesPermiso> ListSolicPerm) {
        if (ListSolicPerm != null) {
            binding.recycler.setVisibility(View.VISIBLE);
            binding.txtListEmpty.setVisibility(View.GONE);
            mAdapter.setData((ArrayList<SolicitudesPermiso>) ListSolicPerm);
        } else {
            binding.recycler.setVisibility(View.GONE);
            binding.txtListEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void mostrarMensaje(String message) {
        new DefaultDialog.Builder(getContext())
                .title(getResources().getString(R.string.attention))
                .message(message)
                .cancelable(false)
                .setIcon(R.drawable.ic_help_outline)
                .textPositiveButton(getResources().getString(R.string.button_accept))
                .textColorPositiveButton(R.color.success)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.dismiss();
                    }
                })
                .buildAndShow(((AppCompatActivity) getActivity()).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_DELETE_MARC);
    }

    private void mostrarMensajeConfirmacionDeleteMarc(SolicitudesPermiso solicitudesPermiso) {
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
                        presenter.setRequestDeleteSolicitud(solicitudesPermiso);
                    }
                })
                .onNegative(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.dismiss();
                    }
                })
                .buildAndShow(((AppCompatActivity) getActivity()).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_DELETE_MARC);
    }

    private void mostrarMensajeConfirmacionEditMarc(SolicitudesPermiso solicitudesPermiso) {
        new DefaultDialog.Builder(getContext())
                .title(getResources().getString(R.string.attention))
                .message("Va a Edita su permiso\n" +
                        "¿Desea Editar su permiso seleccionada?")
                .cancelable(false)
                .setIcon(R.drawable.ic_help_outline)
                .textPositiveButton(getResources().getString(R.string.button_edit))
                .textColorPositiveButton(R.color.danger)
                .textNegativeButton(getResources().getString(R.string.button_cancel))
                .textColorNegativeButton(R.color.success)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        SolicitudPermiso solicitudPermiso = new SolicitudPermiso(solicitudesPermiso.getVchCodPersonal(),
                                solicitudesPermiso.getVchCodConcepto(), solicitudesPermiso.getDtmFechaInicio(),
                                solicitudesPermiso.getDtmFechaFin(),
                                DateUtils.setDateFormat(
                                        solicitudesPermiso.getVchHoraInicio(),
                                        DateUtils.PATTERN_TIME,
                                        DateUtils.PATTERN_TIME_TAREO),
                                DateUtils.setDateFormat(solicitudesPermiso.getVchHoraFin(),
                                        DateUtils.PATTERN_TIME,
                                        DateUtils.PATTERN_TIME_TAREO), solicitudesPermiso.getIntPertenenciaInicio(),
                                solicitudesPermiso.getIntPertenenciaFin(), solicitudesPermiso.getVchObservacion(),
                                solicitudesPermiso.getVchCodSupervisor(), solicitudesPermiso.getVchEmailSupervisor(),
                                -1, -1, solicitudesPermiso.getIntEstadoSolicitud());

                        Fragment fragment = getParentFragment().getFragmentManager().findFragmentByTag(ListSolicPermisosPagerFragment.TAG);
                        if (mFragmentInteractionListener != null)
                            mFragmentInteractionListener.onAddFragmentToStack(
                                    SolicPermisoFragment.newInstance(OptionRegSolicPerm.EDIT,
                                            solicitudPermiso),
                                    "Editar Solicitud",
                                    null,
                                    true,
                                    true,
                                    true,
                                    SolicPermisoFragment.TAG);
                    }
                })
                .onNegative(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.cancel();
                        //closed();
                    }
                })
                .buildAndShow(((AppCompatActivity) getActivity()).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_DELETE_MARC);
    }
}
