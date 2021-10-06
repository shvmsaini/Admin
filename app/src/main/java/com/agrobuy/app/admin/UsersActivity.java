package com.agrobuy.app.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.agrobuy.app.admin.adapters.UserDisplayAdapter;
import com.agrobuy.app.admin.dataclasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersActivity extends AppCompatActivity {
    public List<User> userList = new ArrayList<>();
    RecyclerView recyclerView;
    UserDisplayAdapter adapter;
    TextView emptyView;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        emptyView = findViewById(R.id.empty_view);

        adapter = new UserDisplayAdapter(this, userList,item -> {
            new AlertDialog.Builder(this)
                    .setTitle("Add")
                    .setMessage("Config user")
                    .setPositiveButton("Add Buyer", (dialog, which) -> {
                        Intent i = new Intent(this, AddBuyerActivity.class);
//                        i.putExtra("user_email", item.getUserEmail());
                        i.putExtra("user_ID", item.getUser_ID());
                        startActivity(i);
                    })
                    .setNegativeButton("Add Delivery Partner", (dialog,which)->{
                        Intent i = new Intent(this, AddDeliveryPartnerActivity.class);
//                        i.putExtra("user_email", item.getUserEmail());
                        i.putExtra("user_ID", item.getUser_ID());
                        startActivity(i);
                    })
                    .setCancelable(true)
                    .setIcon(android.R.drawable.ic_menu_add)
                    .show();

        });
        recyclerView = findViewById(R.id.recyclerView);
        emptyView = findViewById(R.id.empty_view);
        emptyView.setText(R.string.loading);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable divider = ContextCompat.getDrawable(recyclerView.getContext(),R.drawable.divider);
        if(divider!=null){
            itemDecorator.setDrawable(divider);
            recyclerView.addItemDecoration(itemDecorator);
        }
        recyclerView.setAdapter(adapter);



        //  getting users from database
        database.getReference("users").get()
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful()){
                        emptyView.setText(R.string.no_user_found);
                        Log.d(UsersActivity.class.getName(), ": Error getting Users data");
                        Toast.makeText(this, "Error getting Users data", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        DataSnapshot snapshot = task.getResult();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Map<String,Object> map = (Map<String, Object>) postSnapshot.getValue();
                            User item;
                            if(map ==null){
                                emptyView.setText(R.string.no_user_found);
                                return;
                            }
                            item = new User(postSnapshot.getKey(), String.valueOf(map.get("email")));
                            Log.d(UsersActivity.class.getName(),"item added: " + item);
                            userList.add(item);
                            adapter.notifyItemInserted(userList.size()-1);
                        }
                        if (userList.size()>0) findViewById(R.id.empty_view).setVisibility(View.GONE);
                        else {
                            findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
                            emptyView.setText(R.string.no_user_found);
                        }

                    }
                });




    }
}