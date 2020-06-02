package pe.com.dms.movilasist.ui.adapter.fragments.listAprobSolicPerm;

import android.content.Context;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.annotacion.TypeViewRecycler;
import pe.com.dms.movilasist.databinding.ItemFooterPaginationBinding;
import pe.com.dms.movilasist.databinding.ItemListAprobPermBinding;
import pe.com.dms.movilasist.model.SolicitudesPermiso;
import pe.com.dms.movilasist.ui.adapter.base.BasePaginationAdapter;

public class ListAprobSolicPermAdapter extends BasePaginationAdapter<SolicitudesPermiso> {

    public static final String TAG = ListAprobSolicPermAdapter.class.getSimpleName();

    private ItemListAprobPermBinding bindingBody;
    private ItemFooterPaginationBinding bindingPagination;
    private List<SolicitudesPermiso> mDataListSelect;

    @Nullable
    private SelectionTracker<String> mSelectionTracker;

    public ListAprobSolicPermAdapter() {
        if (mDataListSelect == null)
            mDataListSelect = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return (isLastPosition(position) && isFooterAdded) ?
                TypeViewRecycler.PAGINATION : TypeViewRecycler.BODY;
    }

    @Override
    protected RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder createBodyViewHolder(ViewGroup parent) {
        bindingBody = ItemListAprobPermBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        final BodyViewHolder holder = new BodyViewHolder(bindingBody.getRoot());
        return holder;
    }

    @Override
    protected RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder createPaginationViewHolder(ViewGroup parent) {
        bindingPagination = ItemFooterPaginationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        View v = bindingPagination.getRoot();
        final PaginationViewHolder holder = new PaginationViewHolder(v);
        bindingPagination.btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReloadClickListener != null) {
                    onReloadClickListener.onReloadClick();
                }
            }
        });
        return holder;
    }

    @Override
    protected void bindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

    }

    @Override
    protected void bindBodyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        BodyViewHolder holder = (BodyViewHolder) viewHolder;
        SolicitudesPermiso permiso = mDataList.get(position);
        holder.bind(position, permiso);
    }

    @Override
    protected void bindFooterViewHolder(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    protected void bindPaginationViewHolder(RecyclerView.ViewHolder viewHolder) {
        PaginationViewHolder holder = (PaginationViewHolder) viewHolder;
        holder.bind();
    }

    @Override
    protected void displayLoadMoreFooter() {
        if (bindingPagination != null) {
            bindingPagination.btnReload.setVisibility(View.GONE);
            bindingPagination.progressLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void displayErrorFooter() {
        if (bindingPagination != null) {
            bindingPagination.btnReload.setVisibility(View.VISIBLE);
            bindingPagination.progressLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void addFooter() {
        isFooterAdded = true;
        add(null);
    }

    public List<SolicitudesPermiso> getDataListSelect() {
        return mDataListSelect;
    }

    public void setSelectionTracker(SelectionTracker<String> selectionTracker) {
        mSelectionTracker = selectionTracker;
    }

    public class BodyViewHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener {

        private SolicitudesPermiso model;
        private final PermisoItemDetails permisoItemDetails;

        public BodyViewHolder(View view) {
            super(view);
            bindingBody.containerItem.setOnLongClickListener(this);
            //bindingBody.containerItem.setOnClickListener(this);
            permisoItemDetails = new PermisoItemDetails();
        }

        public void bind(int pos, SolicitudesPermiso item) {
            this.model = item;
            permisoItemDetails.position = pos;
            permisoItemDetails.identifier = String.valueOf(model.getIntEstadoSolicitud());

            bindingBody.txtCodUser.setText(model.getVchCodPersonal());
            bindingBody.txtDateIni.setText(model.getDtmFechaInicio() + " " + model.getVchHoraInicio());
            bindingBody.txtDateFin.setText(model.getDtmFechaFin() + " " + model.getVchHoraFin());
            bindingBody.txtTypePermiso.setText(model.getVchConcepto());
            bindingBody.txtStatus.setText(model.getVchEstadoSolicitud());

            if (mDataListSelect.contains(model)) {
                bindingBody.imgSelect.setVisibility(View.VISIBLE);
            } else {
                bindingBody.imgSelect.setVisibility(View.GONE);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()) {
                case R.id.container_item:
                    notifyDataSetChanged();
                    if (mDataListSelect.contains(model)) {
                        mDataListSelect.remove(model);
                    } else {
                        mDataListSelect.add(model);
                    }
                    v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    notifyItemChanged(mDataList.indexOf(model), model);
                    Log.e(TAG, "mDataListSelect: " + mDataListSelect + ", mDataList.indexOf(data): " + mDataList.indexOf(model));
                    return true;
            }
            return false;
        }

        public ItemDetailsLookup.ItemDetails<String> getPermisoItemDetails(@NonNull MotionEvent motionEvent) {
            return permisoItemDetails;
        }
    }

    static class PermisoItemDetails extends ItemDetailsLookup.ItemDetails<String> {
        private int position;
        private String identifier;

        @Override
        public int getPosition() {
            return position;
        }

        @Nullable
        @Override
        public String getSelectionKey() {
            return identifier;
        }

        @Override
        public boolean inSelectionHotspot(@NonNull MotionEvent e) {
            return true;
        }

        @Override
        public boolean inDragRegion(@NonNull MotionEvent e) {
            return true;
        }
    }

    class PaginationViewHolder extends RecyclerView.ViewHolder {

        public PaginationViewHolder(View itemView) {
            super(itemView);
        }

        protected void bind() {
            bindingPagination.progressLoading.setIndeterminate(true);
        }
    }
}