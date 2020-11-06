package edu.qc.seclass.glm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class UserListActivity extends AppCompatActivity {
    private int userListId;
    private Button clearListButton;

    public  static DatabaseHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = new DatabaseHandler(getApplicationContext());
        setContentView(R.layout.inside_list);
        clearListButton = (Button) findViewById(R.id.clearalllists);
        clearListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(UserListActivity.this);
                View myview = getLayoutInflater().inflate(R.layout.alertbox,null);
                Button nope = (Button) myview.findViewById(R.id.No);
                Button yes = (Button) myview.findViewById(R.id.Yes);
                alert.setView(myview);
                final AlertDialog dialog = alert.create();
                dialog.setCanceledOnTouchOutside(false);

                nope.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent intent = getIntent();
                        int userListId = intent.getExtras().getInt("userListId");
                        boolean deleteStatus = dbHandler.deleteAllcheckeditems(userListId);
                        if(deleteStatus){
                            refreshUserListFragment();
                        }
                        dialog.dismiss();
                    }
                });
            dialog.show();
            }

        });
    }
    private void refreshUserListFragment(){
        Intent intent = getIntent();
        int userListId = intent.getExtras().getInt("userListId");
        UserListItemFragment userListItemFragment = UserListItemFragment.newInstance(userListId);
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.userListFragment, userListItemFragment);
            fragmentTransaction.commit();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        refreshUserListFragment();
    }

    public void openItemList(View view){
        Intent intent = new Intent(UserListActivity.this, CreateList.class);
        Bundle bundle = getIntent().getExtras();
        userListId =  bundle.getInt("userListId");
        intent.putExtra("userListId", userListId);
        startActivity(intent);
    }


}
