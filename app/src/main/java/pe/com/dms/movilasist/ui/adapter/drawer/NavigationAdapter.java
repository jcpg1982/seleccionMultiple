package pe.com.dms.movilasist.ui.adapter.drawer;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.databinding.FragmentNavigationDrawerBinding;
import pe.com.dms.movilasist.databinding.ItemNavDrawerBinding;
import pe.com.dms.movilasist.util.TextUtils;
import pe.com.dms.movilasist.util.TintUtils;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.NavigationViewHolder> {

    private static final String TAG = NavigationAdapter.class.getSimpleName();

    public static final int NO_SELECTED = -1;

    private ArrayList<Item> mNavigationItemList;
    private final OnItemClickListener mListener;
    private int mSelectedPosition = NO_SELECTED;


    public NavigationAdapter(OnItemClickListener listener) {
        this.mNavigationItemList = new ArrayList<>();
        this.mListener = listener;
    }

    @NonNull
    @Override
    public NavigationAdapter.NavigationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nav_drawer, parent, false);
        return new NavigationViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final NavigationAdapter.NavigationViewHolder holder, int position) {
        final Item item = mNavigationItemList.get(position);
        holder.bind(item, mSelectedPosition, position);
    }

    @Override
    public long getItemId(int position) {
        return mNavigationItemList.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mNavigationItemList != null && mNavigationItemList.size() > 0) {
            return mNavigationItemList.size();
        } else {
            return 0;
        }
    }

    public void setData(ArrayList<Item> list) {
        if (list == null) list = new ArrayList<>();
        mNavigationItemList.clear();
        this.mNavigationItemList = list;
        notifyDataSetChanged();
    }

    public void select(int position) {
        if (position >= 0 && position < getItemCount()) {
            mSelectedPosition = position;
            notifyDataSetChanged();
        } else {
            mSelectedPosition = NO_SELECTED;
            notifyDataSetChanged();
        }
    }

    public void clearSelection() {
        select(NO_SELECTED);
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public class NavigationViewHolder extends RecyclerView.ViewHolder {

        LinearLayout navItemContainer;
        LinearLayout mExpandedHeader;
        ImageView navItemIcon;
        TextView navItemTitle;
        TextView navItemSubtitle;
        TextView navItemLabel;
        ImageView arrow;
        View view;

        OnItemClickListener mListener;

        public NavigationViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.mListener = listener;

            navItemContainer = itemView.findViewById(R.id.container_item);
            mExpandedHeader = itemView.findViewById(R.id.expanded_header);
            navItemIcon = itemView.findViewById(R.id.nav_icon);
            navItemTitle = itemView.findViewById(R.id.nav_title);
            navItemSubtitle = itemView.findViewById(R.id.nav_subtitle);
            navItemLabel = itemView.findViewById(R.id.nav_label);
            arrow = itemView.findViewById(R.id.nav_arrow);
            view = itemView.findViewById(R.id.line_divisor);

        }

        public void bind(final Item item, final int selectedPosition, int position) {
            final int colorNormal = itemView.getContext().getResources().getColor(R.color.drawer_text_selector);
            final int colorChecked = itemView.getContext().getResources().getColor(R.color.colorPrimary);

            Drawable drawable = item.getIcon();

            if (item.isEnabled()) {
                itemView.setEnabled(true);
                if (selectedPosition == getAdapterPosition()) {
                    mListener.onItemClick(mExpandedHeader, item, getAdapterPosition(), false);
                    navItemContainer.setBackgroundColor(itemView.getContext().getResources()
                            .getColor(R.color.control_highlight_dark));
                    itemView.setBackground(itemView.getContext().getResources()
                            .getDrawable(R.drawable.selector_item_checkable_check));
                    if (drawable != null) {
                        navItemIcon.setVisibility(View.VISIBLE);
                        navItemIcon.setImageDrawable(drawable);
                        /*navItemIcon.setImageDrawable(
                                TintUtils.createTintedDrawable(item.getIcon().mutate(), colorChecked));*/
                    } else {
                        navItemIcon.setVisibility(View.GONE);
                    }
                    navItemTitle.setTextColor(colorChecked);
                    item.setChecked(true);
                } else {
                    navItemContainer.setBackgroundColor(itemView.getContext().getResources()
                            .getColor(android.R.color.transparent));
                    itemView.setBackground(itemView.getContext().getResources()
                            .getDrawable(R.drawable.selector_item_checkable_normal));
                    if (drawable != null) {
                        navItemIcon.setVisibility(View.VISIBLE);
                        navItemIcon.setImageDrawable(drawable);
                        /*navItemIcon.setImageDrawable(
                                TintUtils.createTintedDrawable(item.getIcon().mutate(), colorNormal));*/
                    } else {
                        navItemIcon.setVisibility(View.GONE);
                    }
                    navItemTitle.setTextColor(colorNormal);
                    item.setChecked(false);
                }

                itemView.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                notifyItemChanged(selectedPosition);
                                mSelectedPosition = getAdapterPosition();
                                notifyItemChanged(getAdapterPosition());
                            }
                        });

                itemView.setOnLongClickListener(
                        new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                return mListener.onItemClick(
                                        mExpandedHeader, item, getAdapterPosition(), true);
                            }
                        });
            }

            navItemTitle.setText(item.getTitle());

            if (item.getTitle().equals("Solicitud de permiso")) {
                view.setVisibility(View.VISIBLE);
            } else if (item.getTitle().equals("Salir")) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }

//            if (!TextUtils.isEmpty(item.getSubtitle())) {
//                navItemSubtitle.setVisibility(View.VISIBLE);
//                navItemSubtitle.setText(item.getSubtitle());
//            } else {
//                navItemSubtitle.setText(null);
//                navItemSubtitle.setVisibility(View.GONE);
//            }

            if (!TextUtils.isEmpty(item.getLabel())) {
                navItemLabel.setVisibility(View.VISIBLE);
                navItemLabel.setText(item.getLabel());
            } else {
                navItemLabel.setText(null);
                navItemLabel.setVisibility(View.GONE);
            }

            final Drawable drawableArrowUp = itemView.getContext().getResources().getDrawable(R.drawable.ic_arrow_up);
            final Drawable drawableArrowDown =
                    itemView.getContext().getResources().getDrawable(R.drawable.ic_arrow_down);

            if (item.isExpanded()) {
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        arrow.setImageDrawable(
                                TintUtils.createTintedDrawable(drawableArrowUp.mutate(), colorNormal));
                        navItemTitle.setTextColor(colorChecked);
                    }
                };

                arrow.setImageDrawable(
                        TintUtils.createTintedDrawable(drawableArrowDown.mutate(), colorNormal));
                mExpandedHeader.setOnClickListener(onClickListener);
                arrow.setOnClickListener(onClickListener);
            } else {
                arrow.setImageDrawable(null);
                arrow.setVisibility(View.GONE);
            }
            Log.d(TAG, String.format(Locale.getDefault(),
                    "Navigation Drawer %d (%s) is complete!", position, item.getTitle()));
        }
    }

    public static class Item {
        private int id;
        private String title;
        private String subtitle;
        private String label;
        private Drawable icon;
        private boolean visible;
        private boolean enabled;
        private boolean isChecked;
        private boolean isExpanded;
        private ArrayList<Item> itemsExpandedList;

        public Item() {
        }

        public Item(
                int id,
                String title,
                String subtitle,
                String label,
                Drawable icon,
                boolean visible,
                boolean enabled,
                boolean isExpanded) {
            this.id = id;
            this.title = title;
            this.subtitle = subtitle;
            this.label = label;
            this.icon = icon;
            this.visible = visible;
            this.enabled = enabled;
            this.isExpanded = isExpanded;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public boolean isExpanded() {
            return isExpanded;
        }

        public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }

        public ArrayList<Item> getItemsExpandedList() {
            return itemsExpandedList;
        }

        public void setItemsExpandedList(ArrayList<Item> itemsExpandedList) {
            this.itemsExpandedList = itemsExpandedList;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", label='" + label + '\'' +
                    ", icon=" + icon +
                    ", visible=" + visible +
                    ", enabled=" + enabled +
                    ", isChecked=" + isChecked +
                    ", isExpanded=" + isExpanded +
                    ", itemsExpandedList=" + itemsExpandedList +
                    '}';
        }
    }

    public interface OnItemClickListener {
        boolean onItemClick(View view, Item item, int position, boolean longPress);

        boolean onExpanded(View view, Item item, int position, boolean longPress);
    }
}