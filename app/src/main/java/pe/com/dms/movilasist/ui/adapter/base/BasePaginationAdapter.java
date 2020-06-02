package pe.com.dms.movilasist.ui.adapter.base;

import android.util.Log;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pe.com.dms.movilasist.annotacion.TypeViewRecycler;

public abstract class BasePaginationAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String TAG = BasePaginationAdapter.class.getSimpleName();

    // region Member Variables
    protected ArrayList<T> mDataList;
    protected OnReloadClickListener onReloadClickListener;
    protected boolean isFooterAdded = false;
    // endregion

    // region Interfaces

    public interface OnReloadClickListener {
        void onReloadClick();
    }
    // endregion

    // region Constructors
    public BasePaginationAdapter() {
        if (mDataList == null)
            mDataList = new ArrayList<>();
    }
    // endregion

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TypeViewRecycler.HEADER:
                return createHeaderViewHolder(parent);
            case TypeViewRecycler.BODY:
                return createBodyViewHolder(parent);
            case TypeViewRecycler.FOOTER:
                return createFooterViewHolder(parent);
            case TypeViewRecycler.PAGINATION:
                return createPaginationViewHolder(parent);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case TypeViewRecycler.HEADER:
                bindHeaderViewHolder(viewHolder, position);
                break;
            case TypeViewRecycler.BODY:
                bindBodyViewHolder(viewHolder, position);
                break;
            case TypeViewRecycler.FOOTER:
                bindFooterViewHolder(viewHolder);
                break;
            case TypeViewRecycler.PAGINATION:
                bindPaginationViewHolder(viewHolder);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mDataList != null && mDataList.size() > 0)
            return mDataList.size();
        else
            return 0;
    }

    // region Abstract Methods
    protected abstract RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent);

    protected abstract RecyclerView.ViewHolder createBodyViewHolder(ViewGroup parent);

    protected abstract RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent);

    protected abstract RecyclerView.ViewHolder createPaginationViewHolder(ViewGroup parent);

    protected abstract void bindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    protected abstract void bindBodyViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    protected abstract void bindFooterViewHolder(RecyclerView.ViewHolder viewHolder);

    protected abstract void bindPaginationViewHolder(RecyclerView.ViewHolder viewHolder);

    protected abstract void displayLoadMoreFooter();

    protected abstract void displayErrorFooter();

    public abstract void addFooter();

    // endregion

    // region Helper Methods
    public boolean notNull() {
        if (mDataList != null)
            return true;
        else
            return false;
    }

    public T getItem(int position) {
        return mDataList.get(position);
    }

    public void add(T data) {
        Log.e(TAG, "add: " + data);
        mDataList.add(data);
        notifyDataSetChanged();
    }

    public void add(T data, int position) {
        Log.e(TAG, "add: " + data + ", position: " + position);
        mDataList.add(position, data);
        notifyItemInserted(position);
    }

    public void addAll(ArrayList<T> dataList) {
        Log.e(TAG, "addAll: " + dataList);
        for (T data : dataList) {
            add(data);
        }
    }

    public void setData(ArrayList<T> dataList) {
        Log.e(TAG, "setData: " + dataList);
        if (mDataList == null)
            mDataList = new ArrayList<>();
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void addData1(ArrayList<T> dataList) {
        Log.e(TAG, "addData1: " + dataList);
        clear();
        for (T model : dataList) {
            mDataList.add(mDataList.size(), model);
            notifyItemInserted(mDataList.size());
            notifyItemChanged(Math.max(mDataList.size(), 1));
        }

    }

    public void addData(final ArrayList<T> items) {
        if (mDataList == null) mDataList = new ArrayList<>();
        final int positionStart = mDataList.size();
        mDataList.addAll(items);
        //notifyItemRangeInserted(positionStart, items.size());
        notifyDataSetChanged();
    }

    public void clear() {
        isFooterAdded = false;
        while (getItemCount() > 0) {
            removeObject(getItem(0));
        }
    }

    private void removeObject(T item) {
        int position = mDataList.indexOf(item);
        removeInt(position);
    }

    public void removeInt(int position) {
        if (position > -1) {
            mDataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public T getObject(int pos) {
        T object = mDataList.get(pos);
        return object;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public boolean isLastPosition(int position) {
        return (position == mDataList.size() - 1);
    }

    public void updateFooter(FooterType footerType) {
        switch (footerType) {
            case LOAD_MORE:
                displayLoadMoreFooter();
                break;
            case ERROR:
                displayErrorFooter();
                break;
            default:
                break;
        }
    }

    public boolean isAddFooter() {
        return isFooterAdded;
    }

    public void removeFooter() {
        if (isFooterAdded) {
            isFooterAdded = false;
            if (mDataList != null && mDataList.size() > 1) {
                int position = mDataList.size() - 1;
                T item = getItem(position);
                if (item != null) {
                    mDataList.remove(position);
                    notifyItemRemoved(position);
                }
            }
        }
    }

    public void changeIsFooterAdded(boolean isVisible) {
        isFooterAdded = isVisible;
    }

    public void setOnReloadClickListener(OnReloadClickListener onReloadClickListener) {
        this.onReloadClickListener = onReloadClickListener;
    }
    // endregion

    // region Inner Classes
    public enum FooterType {
        LOAD_MORE,
        ERROR
    }
    // endregion
}