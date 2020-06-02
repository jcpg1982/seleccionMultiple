package pe.com.dms.movilasist.ui.adapter.fragments.ListMarcAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import pe.com.dms.movilasist.annotacion.TypeMarcacion;
import pe.com.dms.movilasist.annotacion.TypeViewRecycler;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.databinding.ItemFooterPaginationBinding;
import pe.com.dms.movilasist.databinding.ItemListMarcBinding;
import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.ui.adapter.base.BasePaginationAdapter;
import pe.com.dms.movilasist.util.DateUtils;

public class ListMarcAdapter extends BasePaginationAdapter<Marcaciones> {

    public static final String TAG = ListMarcAdapter.class.getSimpleName();

    private ItemListMarcBinding bindingBody;
    private ItemFooterPaginationBinding bindingFooter;

    public ListMarcAdapter() {
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
        bindingBody = ItemListMarcBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
        final Marcaciones marcacion = getItem(position);
        if (marcacion != null) {
            holder.bind(marcacion);
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

    public void dataListClear() {
        clear();
    }

    class BodyViewHolder extends RecyclerView.ViewHolder {

        public BodyViewHolder(View view) {
            super(view);
        }

        public void bind(final Marcaciones model) {
            bindingBody.txtAddress.setText(model.getVchLugarReferencia());
            bindingBody.txtCodUser.setText(model.getVchCodPersonal());
            bindingBody.txtDateTime.setText(model.getDtmFechaMarca());
            bindingBody.txtTypePermiso.setText(model.getTipoMarca());
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