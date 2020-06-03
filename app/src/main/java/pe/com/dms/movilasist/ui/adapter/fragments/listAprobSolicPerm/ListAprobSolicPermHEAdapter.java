package pe.com.dms.movilasist.ui.adapter.fragments.listAprobSolicPerm;

import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.annotacion.TypeViewRecycler;
import pe.com.dms.movilasist.databinding.ItemFooterPaginationBinding;
import pe.com.dms.movilasist.databinding.ItemListAprobPermBinding;
import pe.com.dms.movilasist.databinding.ItemListAprobPermHEBinding;
import pe.com.dms.movilasist.model.SolicitudesPermiso;
import pe.com.dms.movilasist.ui.adapter.base.BasePaginationAdapter;

public class ListAprobSolicPermHEAdapter extends BasePaginationAdapter<SolicitudesPermiso> {

    public static final String TAG = ListAprobSolicPermHEAdapter.class.getSimpleName();

    private ItemListAprobPermHEBinding bindingBody;
    private ItemFooterPaginationBinding bindingPagination;
    private List<SolicitudesPermiso> mDataListSelect;

    private OnLongClickListener mListener;

    public ListAprobSolicPermHEAdapter() {
        if (mDataListSelect == null)
            mDataListSelect = new ArrayList<>();
        mDataListSelect.clear();
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
        bindingBody = ItemListAprobPermHEBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        View v = bindingBody.getRoot();
        final BodyViewHolder holder = new BodyViewHolder(v);
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

    public void setListener(OnLongClickListener listener) {
        mListener = listener;
    }

    public void updateSelectItem(boolean isChecked, int pos) {
        Log.e(TAG, "updateSelectItem isChecked: " + isChecked + ", pos:" + pos);
        mDataList.get(pos).setChecked(isChecked);
        notifyItemChanged(pos);
    }

    public class BodyViewHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener {

        private SolicitudesPermiso model;
        private int getPosition;

        public BodyViewHolder(View view) {
            super(view);
            bindingBody.containerItem.setOnLongClickListener(this);
        }

        public void bind(int pos, SolicitudesPermiso item) {
            this.model = item;
            getPosition = pos;

            bindingBody.txtCodUser.setText(model.getVchCodPersonal());
            bindingBody.txtDateIni.setText(model.getDtmFechaInicio() + " " + model.getVchHoraInicio());
            bindingBody.txtDateFin.setText(model.getDtmFechaFin() + " " + model.getVchHoraFin());
            bindingBody.txtTypePermiso.setText(model.getVchConcepto());
            bindingBody.txtStatus.setText(model.getVchEstadoSolicitud());

            if (model.isChecked()) {
                bindingBody.imgSelect.setVisibility(View.VISIBLE);
            } else {
                bindingBody.imgSelect.setVisibility(View.GONE);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()) {
                case R.id.container_item:
                    if (mDataListSelect.contains(model)) {
                        mDataListSelect.remove(model);
                    } else {
                        mDataListSelect.add(model);
                    }
                    v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    notifyItemChanged(getPosition, model);
                    Log.e(TAG, "mDataListSelect: " + mDataListSelect + ", mDataList.indexOf(data): " + mDataList.indexOf(model));

                    if (mListener != null)
                        mListener.itemLongClick(model, getPosition, false);

                    return true;
            }
            return false;
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

    public interface OnLongClickListener {
        void itemLongClick(SolicitudesPermiso item, int pos, boolean longPress);
    }
}