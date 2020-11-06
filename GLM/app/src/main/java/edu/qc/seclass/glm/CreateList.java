package edu.qc.seclass.glm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CreateList extends AppCompatActivity {
    private Button button;
    private ArrayList<String> itemTypes = new ArrayList<String>();
    private ArrayList<Object> items = new ArrayList<Object>();
    private ArrayList<String> displayRow;
    public static DatabaseHandler dbHandler;
    private String searchString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = new DatabaseHandler(getApplicationContext());
        setContentView(R.layout.item_list_activity);

        // the view refresher
        Intent intent = getIntent();
        final int userListId = intent.getExtras().getInt("userListId");
        final ItemListFagmentFragment itemListFragment = ItemListFagmentFragment.newInstance(userListId);
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.listItemFragment, itemListFragment);
            fragmentTransaction.commit();
        }catch (Exception e) {
            System.out.println(e);
        }



        // setting up the search for the edit text

        final EditText searchField = (EditText)findViewById(R.id.search_item_edittext);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                searchString = s.toString();
                if(s == null || searchString.equals("")){
                    View addButtonView = findViewById(R.id.CreateItemButton);
                    addButtonView.setVisibility(addButtonView.INVISIBLE);
                }
                final ItemListFagmentFragment itemListFragment = ItemListFagmentFragment.newSearchInstance(userListId, searchString);
                try {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.listItemFragment, itemListFragment);
                    fragmentTransaction.commit();


                }catch (Exception e) {
                    System.out.println(e);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

    }//onCreate


    public void showdialogtocreateItem(View view) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(CreateList.this);

        View myview = getLayoutInflater().inflate(R.layout.create_new_item_dialog, null);
        final EditText itemNameInput = (EditText) myview.findViewById(R.id.create_item_name);
        final EditText itemTypeInput = (EditText) myview.findViewById(R.id.create_item_type);
        Button cancelCreateItem = (Button) myview.findViewById(R.id.cancel_item_create);
        Button createItem = (Button) myview.findViewById(R.id.create_new_item);

        alert.setView(myview);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        itemNameInput.setText(searchString);
        cancelCreateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        createItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName =  itemNameInput.getText().toString();
                String itemType = itemTypeInput.getText().toString();

                System.out.println("The item name " + itemName);
                System.out.println("The item type " + itemType);
                if(itemName.length()>40 || itemName.length()==0){
                    Toast.makeText(CreateList.this, "Invalid Item Name: Try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(itemType.length()>40 || itemType.length()==0){
                    Toast.makeText(CreateList.this, "Invalid Item Type: Try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                long status = dbHandler.insertItemInDb(itemName, itemType);
//                long status = dbHandler.insertUserList(name);

                if(status != -1){
//                    dbUserItems = dbHandler.getAllItems();
//                    alist.add(name);
                    // now to refresh the fragment
//                    list.setAdapter(adapter);
                    System.out.println("Item Insertion successful!");
                    Intent intent = getIntent();
                    final int userListId = intent.getExtras().getInt("userListId");
                    final ItemListFagmentFragment itemListFragment = ItemListFagmentFragment.newInstance(userListId);
                    try {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.listItemFragment, itemListFragment);
                        fragmentTransaction.commit();
                    }catch (Exception e) {
                        System.out.println(e);
                    }
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(CreateList.this, "Failed to Update New Item", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        alertDialog.show();
    }
}
