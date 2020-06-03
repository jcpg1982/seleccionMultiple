package pe.com.dms.movilasist.ui.fragment.listAprobSolic.solicPerm;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.databinding.FragmentListAprobSolicPermBinding;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.interfaces.OnItemClickActionListener;
import pe.com.dms.movilasist.model.ButtonSwipeAction;
import pe.com.dms.movilasist.model.SolicitudesPermiso;
import pe.com.dms.movilasist.model.filterModel.ResultListApobSolic;
import pe.com.dms.movilasist.ui.adapter.fragments.listAprobSolicPerm.ListAprobSolicPermAdapter;
import pe.com.dms.movilasist.ui.decorations.DividerItemDecoration;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;
import pe.com.dms.movilasist.ui.fragment.base.BaseMainFragment;
import pe.com.dms.movilasist.util.ImageConverterUtils;
import pe.com.dms.movilasist.util.swipe.SwipeHelperUpdate;

public class ListAprobSolicPermFragment extends BaseMainFragment implements View.OnClickListener,
        IListAprobSolicPermFragmentContract.View {

    public static final String TAG = ListAprobSolicPermFragment.class.getSimpleName();

    private FragmentListAprobSolicPermBinding binding;

    private OnFragmentInteractionListener mFragmentInteractionListener;

    @Inject
    ListAprobSolicPermFragmentPresenter presenter;

    private ListAprobSolicPermAdapter mAdapter;

    public ListAprobSolicPermFragment() {
    }

    public static ListAprobSolicPermFragment newInstance() {
        ListAprobSolicPermFragment fragment = new ListAprobSolicPermFragment();
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
    public void onDestroy() {
        super.onDestroy();
    }

    /*@Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
            selectionTracker.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        selectionTracker.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }*/

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
        binding = FragmentListAprobSolicPermBinding.inflate(inflater, container, false);
        setupRecyclerView();
        initEvents();
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(),
                R.drawable.line_divider_primary, false, true));
        mAdapter = new ListAprobSolicPermAdapter(getContext());
        //binding.recycler.addOnScrollListener(recyclerViewOnScrollListener);
        binding.recycler.setAdapter(mAdapter);

        mAdapter.setListener((item, pos, longPress) -> {
            if (!longPress) {
                if (mAdapter.getDataListSelect().size() > 0) {
                    binding.containerCount.setVisibility(View.VISIBLE);
                    binding.txtCount.setText(getResources().getString(R.string.aprobe_x_permisos,
                            getResources().getQuantityString(R.plurals.solicitud,
                                    mAdapter.getDataListSelect().size(), mAdapter.getDataListSelect().size())));
                } else {
                    binding.containerCount.setVisibility(View.GONE);
                }
                return true;
            }
            return false;

            /*if (!longPress) {
                if (mAdapter.getDataListSelect().size() > 0) {
                    binding.containerCount.setVisibility(View.VISIBLE);
                    binding.txtCount.setText(getResources().getString(R.string.aprobe_x_permisos,
                            getResources().getQuantityString(R.plurals.solicitud,
                                    mAdapter.getDataListSelect().size(), mAdapter.getDataListSelect().size())));
                } else {
                    binding.containerCount.setVisibility(View.GONE);
                }
                if (isExistInList(item)) {
                    mAdapter.updateSelectItem(false, pos);
                } else {
                    mAdapter.updateSelectItem(true, pos);
                }
            }*/
        });

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
                        "Aprobar",
                        ImageConverterUtils.drawableToBitmap(getResources().getDrawable(R.drawable.ic_action_done)),
                        Color.parseColor("#3F51B5"),
                        new OnItemClickActionListener() {
                            @Override
                            public void onClick(int pos) {
                                Log.e(TAG, "onClick pos: " + pos);
                                Log.e(TAG, "onClick mAdapter.getObject(pos): " + mAdapter.getObject(pos));
                                switch (mAdapter.getObject(pos).getIntEstadoSolicitud()) {
                                    case 0:
                                        mostrarMensajeConfirmacionAprobMarc(mAdapter.getObject(pos));
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
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.recycler);
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
    public void setResultListApobSolic(ResultListApobSolic resultListApobSolic) {
        presenter.setResultListApobSolic(resultListApobSolic);
    }

    @Override
    public void viewListSolicPerm(List<SolicitudesPermiso> listSolicPerm) {
        if (listSolicPerm != null) {
            binding.recycler.setVisibility(View.VISIBLE);
            binding.txtListEmpty.setVisibility(View.GONE);

            mAdapter.setData((ArrayList<SolicitudesPermiso>) listSolicPerm);

        } else {
            binding.recycler.setVisibility(View.GONE);
            binding.txtListEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void mostrarMensajeConfirmacionDeleteMarc(SolicitudesPermiso solicitudesPermiso) {
        new DefaultDialog.Builder(getContext())
                .title(getResources().getString(R.string.attention))
                .message("Va a eliminar un permiso\n" +
                        "¿Desea eleminar  el permiso seleccionada?")
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
                        dialog.dismiss();
                        presenter.deleteSolicitudOnLine(solicitudesPermiso);
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

    private void mostrarMensajeConfirmacionAprobMarc(SolicitudesPermiso solicitudesPermiso) {
        new DefaultDialog.Builder(getContext())
                .title(getResources().getString(R.string.attention))
                .message("Va a Aprobar un permiso\n" +
                        "¿Desea aprobar el permiso seleccionada?")
                .cancelable(false)
                .setIcon(R.drawable.ic_help_outline)
                .textPositiveButton(getResources().getString(R.string.button_aprobe))
                .textColorPositiveButton(R.color.danger)
                .textNegativeButton(getResources().getString(R.string.button_cancel))
                .textColorNegativeButton(R.color.success)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.dismiss();
                        presenter.aprobeSolicitudOnLine(solicitudesPermiso);
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

    private boolean isExistInList(SolicitudesPermiso solicitudesPermiso) {
        if (mAdapter.getDataListSelect().size() > 0) {
            for (SolicitudesPermiso item : mAdapter.getDataListSelect()) {
                if (item.getIntIdmSolicitud() == solicitudesPermiso.getIntIdmSolicitud())
                    return true;
            }
            return false;
        } else {
            return false;
        }
    }
}
