package pe.com.dms.movilasist.ui.adapter.dialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.databinding.ItemListDialogDefaultBinding;
import pe.com.dms.movilasist.util.TextUtils;

public class DialogDefaultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = DialogDefaultAdapter.class.getSimpleName();

    private final int VIEW_TYPE_ITEM = 0;

    private ArrayList<String> mDataList;
    private OnItemClickListener mListener;
    private ItemListDialogDefaultBinding binding;

    public DialogDefaultAdapter() {
        this.mDataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            binding = ItemListDialogDefaultBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            View v = binding.getRoot();
            return new ItemRowHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemRowHolder) {
            final String item = mDataList.get(position);
            ItemRowHolder holder = (ItemRowHolder) viewHolder;
            holder.bind(item);

            Log.d(TAG, String.format(
                    Locale.getDefault(), "Status Account %d (%s) is complete!",
                    position, item));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ITEM;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mDataList != null && mDataList.size() > 0) {
            return mDataList.size();
        } else {
            return 0;
        }
    }

    public void setData(ArrayList<String> dataList) {
        if (dataList == null)
            dataList = new ArrayList<>();

        this.mDataList.clear();
        this.mDataList.addAll(dataList);

        notifyDataSetChanged();
    }

    public void clearData() {
        this.mDataList.clear();
        notifyDataSetChanged();
    }

    public void setOnListener(OnItemClickListener listener) {
        mListener = listener;
        notifyDataSetChanged();
    }

    class ItemRowHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        String model;

        ItemRowHolder(View itemView) {
            super(itemView);
            binding.containerItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                switch (view.getId()) {
                    case R.id.container_item:
                        if (mListener != null)
                            mListener.onItemClick(view, mDataList.get(getAdapterPosition()), pos, false);
                        break;
                }
            }
        }

        public void bind(final String item) {
            this.model = item;

            if (!TextUtils.isEmpty(model)) {
                binding.textItem.setVisibility(View.VISIBLE);
                binding.textItem.setText(model);
            } else {
                binding.textItem.setText(null);
                binding.textItem.setVisibility(View.GONE);
            }
        }
    }

    public interface OnItemClickListener {
        boolean onItemClick(View view, final String item, int position, boolean longPress);
    }
}