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
        IListAprobSolicPermFragmentContract.View,
        ListAprobSolicPermAdapter.OnReloadClickListener/*,
        ActionMode.Callback */ {

    public static final String TAG = ListAprobSolicPermFragment.class.getSimpleName();

    private FragmentListAprobSolicPermBinding binding;

    private OnFragmentInteractionListener mFragmentInteractionListener;

    @Inject
    ListAprobSolicPermFragmentPresenter presenter;

    private ListAprobSolicPermAdapter mAdapter;
    SelectionTracker<String> selectionTracker;
    /*private SelectionTracker<Long> selectionTracker;
    private ActionMode actionMode;*/

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
        if (selectionTracker.hasSelection()) {
            selectionTracker.clearSelection();
        }
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
        View rootView = binding.getRoot();
        setupRecyclerView();
        initEvents();
        return rootView;
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
        mAdapter = new ListAprobSolicPermAdapter();
        //binding.recycler.addOnScrollListener(recyclerViewOnScrollListener);
        binding.recycler.setAdapter(mAdapter);

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

        /*selectionTracker =
                new SelectionTracker.Builder<>(
                        "card_selection",
                        binding.recycler,
                        new ListAprobSolicPermAdapter.KeyProvider(mAdapter),
                        new ListAprobSolicPermAdapter.DetailsLookup(binding.recycler),
                        StorageStrategy.createLongStorage())
                        .withSelectionPredicate(SelectionPredicates.createSelectAnything())
                        .build();

        mAdapter.setSelectionTracker(selectionTracker);
        selectionTracker.addObserver(
                new SelectionTracker.SelectionObserver<Long>() {
                    @Override
                    public void onSelectionChanged() {
                        if (selectionTracker.getSelection().size() > 0) {
                            if (actionMode == null) {
                                actionMode = startSupportActionMode(ListAprobSolicPermFragment.this);
                            }
                            actionMode.setTitle(String.valueOf(selectionTracker.getSelection().size()));
                        } else if (actionMode != null) {
                            actionMode.finish();
                        }
                    }
                });*/
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

            selectionTracker = new SelectionTracker.Builder<>(
                    "intIdmSolicitud",//unique id
                    binding.recycler,
                    new PermisoItemKeyProvider(listSolicPerm),
                    new PermisoItemDetailsLookup(binding.recycler),
                    StorageStrategy.createStringStorage())
                    .build();
            mAdapter.setSelectionTracker(selectionTracker);
            setUpViews();

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

    @Override
    public void onReloadClick() {

    }

    void setUpViews() {
        /*toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionTracker.clearSelection();
            }
        });*/

        updateViewsBasedOnSelection();
        selectionTracker.addObserver(new SelectionTracker.SelectionObserver<String>() {
            @Override
            public void onSelectionChanged() {
                updateViewsBasedOnSelection();
                super.onSelectionChanged();
            }

            @Override
            public void onSelectionRestored() {
                updateViewsBasedOnSelection();
                super.onSelectionRestored();
            }
        });
    }

    private void updateViewsBasedOnSelection() {
        /*if (selectionTracker.hasSelection()) {
            toolbarView.setVisibility(View.VISIBLE);
            toolbarView.setTitle(selectionTracker.getSelection().size() + " selected");
        } else {
            toolbarView.setVisibility(View.GONE);
        }*/
    }

    private static class PermisoItemKeyProvider extends ItemKeyProvider<String> {

        private final Map<String, Integer> mKeyToPosition;
        private List<SolicitudesPermiso> mDataList;

        PermisoItemKeyProvider(List<SolicitudesPermiso> dataList) {
            super(SCOPE_CACHED);
            mDataList = dataList;

            mKeyToPosition = new HashMap<>(mDataList.size());
            int i = 0;
            for (SolicitudesPermiso pokemon : dataList) {
                mKeyToPosition.put(String.valueOf(pokemon.getIntEstadoSolicitud()), i);
                i++;
            }
        }

        @Nullable
        @Override
        public String getKey(int i) {
            Log.e(TAG, "getKey i: " + i);
            Log.e(TAG, "getKey mPokemonList.get(i).id: " + mDataList.get(i).getIntEstadoSolicitud());
            return String.valueOf(mDataList.get(i).getIntEstadoSolicitud());// directly from position to key
        }

        @Override
        public int getPosition(@NonNull String s) {
            Log.e(TAG, "getPosition s: " + s);
            Log.e(TAG, "getPosition mKeyToPosition.get(s): " + mKeyToPosition.get(s));
            return mKeyToPosition.get(s);
        }
    }

    private static class PermisoItemDetailsLookup extends ItemDetailsLookup<String> {
        RecyclerView mRecyclerView;

        PermisoItemDetailsLookup(RecyclerView recyclerView) {
            this.mRecyclerView = recyclerView;
        }

        @Nullable
        @Override
        public ItemDetails<String> getItemDetails(@NonNull MotionEvent motionEvent) {
            View view = mRecyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if (view != null) {
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(view);
//                int position = viewHolder.getAdapterPosition();
                if (viewHolder instanceof ListAprobSolicPermAdapter.BodyViewHolder) {
                    return ((ListAprobSolicPermAdapter.BodyViewHolder) viewHolder).getPermisoItemDetails(motionEvent);
                }
            }
            return null;
        }
    }

    /*@Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        selectionTracker.clearSelection();
        this.actionMode = null;
    }*/
}
