package edu.qc.seclass.glm;

import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.qc.seclass.glm.Item;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Item}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemListFagmentRecyclerViewAdapter extends RecyclerView.Adapter<MyItemListFagmentRecyclerViewAdapter.ViewHolder> {

    private final List<Item> mValues;
    private int userListId;
    private Context context;

    public MyItemListFagmentRecyclerViewAdapter(List<Item> items, int id, Context c) {
        mValues = items;
        userListId = id;
        context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getItemName());
        final int itemId = holder.mItem.getItemID();

        String category = holder.mItem.getItemTypeName();
        holder.mCategory.setText(category);

        DatabaseHandler dbHandler = DatabaseHandler.getInstance(context);
        holder.mQuantity.setText(String.valueOf(dbHandler.getQuantityByItem(itemId ,userListId)));

        holder.mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler dbHandler = DatabaseHandler.getInstance(context);
                int quantity =dbHandler.getQuantityByItem(itemId, userListId);
                if(quantity== 0){
                    long status = dbHandler.insertUserListItem(holder.mItem.getItemName(),1,itemId,userListId );
                }else{
                    dbHandler.incrementItemQuantityInUserList(itemId, userListId, quantity);
                }

                holder.mQuantity.setText(String.valueOf(dbHandler.getQuantityByItem(itemId ,userListId)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mQuantity;
        public final Button mAddButton;
        public final TextView mCategory;
        public Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_name_textview);
            mQuantity = (TextView) view.findViewById(R.id.item_quantity);
            mCategory = (TextView) view.findViewById(R.id.item_category_textbox);
            mAddButton = (Button) view.findViewById(R.id.add_item_button);
        }
    }
}