package edu.qc.seclass.glm;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 */
public class ItemListFagmentFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private int userListId;
    private String searchString;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemListFagmentFragment() {
    }

    @SuppressWarnings("unused")
    public static ItemListFagmentFragment newInstance(int id) {
        int columnCount = 1;
        ItemListFagmentFragment fragment = new ItemListFagmentFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt("userListId", id);
        fragment.setArguments(args);
        return fragment;
    }

    public static ItemListFagmentFragment newSearchInstance(int id, String searchString){
        int columnCount = 1;
        ItemListFagmentFragment fragment = new ItemListFagmentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt("userListId", id);
        args.putString("searchString", searchString);
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<Item> getSearchItemList(Context context, String searchString){
        DatabaseHandler dbHelper = DatabaseHandler.getInstance(context);
        ArrayList<Item> items = new ArrayList<>(dbHelper.getAllItems(null));
        ArrayList<Item> searchItems = new ArrayList<>();
        // add the matching items to the list
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getItemName().equals(searchString)){
                searchItems.add(items.get(i));
            }
        }
        return searchItems;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            userListId =  getArguments().getInt("userListId");
            searchString =  getArguments().getString("searchString");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

//            System.out.println(searchString);
            ArrayList<Item> items = getItemList(getActivity().getApplicationContext(), searchString);
            recyclerView.setAdapter(new MyItemListFagmentRecyclerViewAdapter(items,userListId, getActivity().getApplicationContext()));
        }
        return view;
    }

    // empty string or no
    private ArrayList<Item> getItemList(Context context, String searchString){

        DatabaseHandler dbHelper = DatabaseHandler.getInstance(context);
        ArrayList<Item> returnedItems= new ArrayList<>(dbHelper.getAllItems(searchString));

        if(searchString!= null && searchString.length() > 0){
           // show the add items button if the the arraylist is empty
            View addButtonView = getActivity().findViewById(R.id.CreateItemButton);
            if(returnedItems.size() == 0){
                addButtonView.setVisibility(addButtonView.VISIBLE);
            }else{
                addButtonView.setVisibility(addButtonView.INVISIBLE);
            }
        }
        return returnedItems;
    }
}