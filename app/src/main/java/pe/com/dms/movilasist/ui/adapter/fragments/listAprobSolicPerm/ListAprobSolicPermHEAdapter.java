package pe.com.dms.movilasist.ui.adapter.fragments.listAprobSolicPerm;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private ItemFooterPaginationBinding bindingFooter;
    private List<SolicitudesPermiso> mDataListSelect;

    public ListAprobSolicPermHEAdapter() {
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
        bindingFooter = ItemFooterPaginationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        View v = bindingFooter.getRoot();
        final PaginationViewHolder holder = new PaginationViewHolder(v);
        bindingFooter.btnReload.setOnClickListener(new View.OnClickListener() {
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
        final BodyViewHolder holder = (BodyViewHolder) viewHolder;
        final SolicitudesPermiso solicitudPermiso = getItem(position);
        if (solicitudPermiso != null) {
            holder.bind(solicitudPermiso);
        }
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
        if (bindingFooter != null) {
            bindingFooter.btnReload.setVisibility(View.GONE);
            bindingFooter.progressLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void displayErrorFooter() {
        if (bindingFooter != null) {
            bindingFooter.btnReload.setVisibility(View.VISIBLE);
            bindingFooter.progressLoading.setVisibility(View.GONE);
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

    class BodyViewHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener {

        private SolicitudesPermiso data;

        public BodyViewHolder(View view) {
            super(view);
            bindingBody.containerItem.setOnLongClickListener(this);
        }

        public void bind(final SolicitudesPermiso model) {
            data = model;
            bindingBody.txtCodUser.setText(data.getVchCodPersonal());
            bindingBody.txtDateIni.setText(data.getDtmFechaFin() + " " + data.getVchHoraInicio());
            bindingBody.txtDateFin.setText(data.getDtmFechaFin() + " " + data.getVchHoraFin());
            bindingBody.txtTypePermiso.setText(data.getVchConcepto());
            bindingBody.txtStatus.setText(data.getVchEstadoSolicitud());

            if (mDataListSelect.contains(data)) {
                bindingBody.imgSelect.setVisibility(View.VISIBLE);
            } else {
                bindingBody.imgSelect.setVisibility(View.GONE);
            }
        }


        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()) {
                case R.id.container_item:
                    if (mDataListSelect.contains(data)) {
                        mDataListSelect.remove(data);
                    } else {
                        mDataListSelect.add(data);
                    }
                    notifyItemChanged(mDataList.indexOf(data));
                    Log.e(TAG, "mDataListSelect: " + mDataListSelect);
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
            bindingFooter.progressLoading.setIndeterminate(true);
        }
    }
}