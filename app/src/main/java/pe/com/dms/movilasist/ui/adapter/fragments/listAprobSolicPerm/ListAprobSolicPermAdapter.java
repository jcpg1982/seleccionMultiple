package pe.com.dms.movilasist.ui.adapter.fragments.listAprobSolicPerm;

import android.content.Context;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
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

    private OnLongClickListener mListener;
    private Context mContext;

    public ListAprobSolicPermAdapter(Context context) {
        if (mDataList == null)
            mDataList = new ArrayList<>();
        if (mDataListSelect == null)
            mDataListSelect = new ArrayList<>();
        mDataListSelect.clear();
        mContext = context;
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

    public void setListener(OnLongClickListener listener) {
        mListener = listener;
        notifyDataSetChanged();
    }

    public boolean ifExist(SolicitudesPermiso model) {
        if (mDataListSelect != null && mDataListSelect.size() > 0) {
            for (SolicitudesPermiso item : mDataListSelect) {
                if (item.getIntIdmSolicitud() == model.getIntIdmSolicitud()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void removeList(SolicitudesPermiso model) {
        Iterator<SolicitudesPermiso> iterator = mDataListSelect.iterator();
        if (mDataListSelect != null && mDataListSelect.size() > 0) {
            while (iterator.hasNext()) {
                SolicitudesPermiso item = iterator.next();
                if (item.getIntIdmSolicitud() == model.getIntIdmSolicitud()) {
                    iterator.remove();
                }
            }
        }
    }

    public class BodyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private SolicitudesPermiso model;
        private int getPosition;

        public BodyViewHolder(View view) {
            super(view);
            bindingBody.containerItem.setOnClickListener(this);
        }

        public void bind(int pos, SolicitudesPermiso item) {
            this.model = item;
            getPosition = pos;
            bindingBody.txtCodUser.setText(model.getVchCodPersonal());
            bindingBody.txtDateIni.setText(model.getDtmFechaInicio() + " " + model.getVchHoraInicio());
            bindingBody.txtDateFin.setText(model.getDtmFechaFin() + " " + model.getVchHoraFin());
            bindingBody.txtTypePermiso.setText(model.getVchConcepto());
            bindingBody.txtStatus.setText(model.getVchEstadoSolicitud());

            bindingBody.cbChecked.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_action_unchecked));

            if (model.isChecked()) {
                bindingBody.cbChecked.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_action_checked));
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.container_item:
                    if (!model.isChecked()) {
                        notifyItemChanged(getPosition);
                        model.setChecked(true);

                        if (!ifExist(model)) {
                            mDataListSelect.add(model);
                        }
                    } else {
                        removeList(model);
                        notifyItemChanged(getPosition);
                        model.setChecked(false);
                        bindingBody.cbChecked.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_action_unchecked));
                    }

                    if (mListener != null)
                        mListener.itemLongClick(model, getPosition, false);

                    Log.e(TAG, "onClick mDataListSelect; " + mDataListSelect);
                    break;
            }
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
        boolean itemLongClick(SolicitudesPermiso item, int pos, boolean longPress);
    }
}