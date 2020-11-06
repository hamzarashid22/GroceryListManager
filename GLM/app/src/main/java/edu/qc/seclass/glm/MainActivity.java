package edu.qc.seclass.glm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText text;
    private ListView list;
    private ArrayList<UserList> dbUserLists;
    private  ArrayList<String> alist;
    private MyCustomAdapter adapter;
    private static Context staticContext;
    public  static DatabaseHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DatabaseHandler(getApplicationContext());
        dbUserLists = dbHandler.getAllUser();
        alist = new ArrayList<String>(convertUserListArrayList(dbUserLists));
        adapter = new MyCustomAdapter(alist,this);
        list = (ListView) findViewById(R.id.grocerylist);
        list.setAdapter(adapter);
    }

    private ArrayList<String> convertUserListArrayList(ArrayList<UserList> userLists){
        ArrayList<String> items = new ArrayList<String>();

        for(int i =0 ; i < userLists.size(); i++){
            items.add(userLists.get(i).getName());
        }
        return items;
    }

    private UserList findUserList(String name, ArrayList<UserList> userLists) {
        for (int i = 0; i < userLists.size(); i++) {
            UserList userList = userLists.get(i);

            if(userList.getName().equals(name)){
                return userList;
            }
        }
        return null;
    }

    public void showdialogtocreatelist(View view) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

        View myview = getLayoutInflater().inflate(R.layout.createlistdialog, null);
        final EditText listinput = (EditText) myview.findViewById(R.id.newlistinput);
        Button cancel = (Button) myview.findViewById(R.id.cancelcreatelist);
        Button create = (Button) myview.findViewById(R.id.createlist);

        alert.setView(myview);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = listinput.getText().toString();
                if(name.length()>40 || name.length()==0){
                    Toast.makeText(MainActivity.this, "List Name Invalid: Try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                long status = dbHandler.insertUserList(name);

                if(status != -1){
                    dbUserLists = dbHandler.getAllUser();
                    alist.add(name);
                    list.setAdapter(adapter);
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(MainActivity.this, "Failed to Update User List", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        alertDialog.show();
    }

     class MyCustomAdapter extends BaseAdapter implements ListAdapter {
        private ArrayList<String> list = new ArrayList<String>();
        private Context context;


        public MyCustomAdapter(ArrayList<String> list, Context context) {
            this.list = list;
            this.context = context;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int pos) {
            return list.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return 0;
            //just return 0 if your list items do not have an Id variable.
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {

            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listviewlayout, null);
            }

            //Handle TextView and display string from your list
            TextView listItemText = (TextView) view.findViewById(R.id.list_item_string);
            listItemText.setText(list.get(position));

            //Handle buttons and add onClickListeners
            TextView kobe = (TextView) view.findViewById(R.id.list_item_string);
            Button addBtn = (Button) view.findViewById(R.id.add_btn);


            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showpopmenu(v, position);


                    notifyDataSetChanged();
                }


            });

            // when you click the textview it goes inside the new list
            kobe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // changed it to go to user list class
                    Intent intent = new Intent(parent.getContext(), UserListActivity.class);
                    int targetId = dbUserLists.get(position).getId();
                    intent.putExtra("userListId",targetId );
                    parent.getContext().startActivity(intent);
                    notifyDataSetChanged();
                }
            });

            return view;
        }

         void showpopmenu(final View v, final int number) {
            final PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
            popupMenu.inflate(R.menu.listoptions);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                int kobe = number;


                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.delete:
                            UserList targetUserList = dbUserLists.get(kobe);
                            long status = dbHandler.deleteUserListByUserListId(targetUserList.getId());
                            if(status != -1){
                                list.remove(kobe);
                                notifyDataSetChanged();
                                return true;
                            }else{
                                Toast.makeText(MainActivity.this, "User List Failed to delete", Toast.LENGTH_SHORT).show();
                            }


                        case R.id.rename:

                            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            View drake = getLayoutInflater().inflate(R.layout.renamelist, null,false);
                            Button cancelrename = (Button) drake.findViewById(R.id.cancelrename);
                            Button rename = (Button) drake.findViewById(R.id.renamelist);
                            final EditText renametext = (EditText)  drake.findViewById(R.id.renamelistinput);

                            alert.setView(drake);


                            final AlertDialog dialog = alert.create();
                            dialog.setCanceledOnTouchOutside(true);

                            cancelrename.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog.dismiss();
                                    return;


                                }



                            });
                            rename.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String name = renametext.getText().toString();
                                    UserList targetUserList = dbUserLists.get(kobe) ;
                                    long status = dbHandler.updateUserListName(targetUserList.getId() , name);
                                    if(status != -1){
                                        dbUserLists.get(kobe).setName(name);
                                        list.set(kobe,name);
                                        notifyDataSetChanged();
                                        dialog.dismiss();
                                    }else{
                                        Toast.makeText(MainActivity.this, "User List Failed to rename", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                            dialog.show();
                            return true;

                        default:
                            return false;
                    }

                }

            });

            popupMenu.show();


        }


    }
}




