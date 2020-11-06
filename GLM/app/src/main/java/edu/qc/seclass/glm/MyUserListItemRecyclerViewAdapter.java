package edu.qc.seclass.glm;

import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link UserListItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyUserListItemRecyclerViewAdapter extends RecyclerView.Adapter<MyUserListItemRecyclerViewAdapter.ViewHolder> {

    private final List<UserListItem> mValues;
    private Context context;

    public MyUserListItemRecyclerViewAdapter(List<UserListItem> items, Context c) {
        mValues = items;
        context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item_fragment_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mQuantity.setText(mValues.get(position).getItemQuantity());
        holder.mIsChecked.setChecked(mValues.get(position).getIsChecked() == 1);
        holder.mItemName.setText(mValues.get(position).getItemName());

        holder.mIsChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler dbhandler = DatabaseHandler.getInstance(context);
                int value = holder.mIsChecked.isChecked() ? 1 : 0;
                holder.mIsChecked.setChecked(value == 1);
                dbhandler.toggleCheckBoxinUserList(holder.mItem.getUserListItemsID(),value );
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mQuantity;
        public final CheckBox mIsChecked;
        public final TextView mItemName;
        public UserListItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mQuantity = (TextView) view.findViewById(R.id.item_quantity);
            mIsChecked = (CheckBox) view.findViewById(R.id.user_item_row_checkbox);
            mItemName = (CheckBox) view.findViewById(R.id.user_item_row_checkbox);
        }

    }
}