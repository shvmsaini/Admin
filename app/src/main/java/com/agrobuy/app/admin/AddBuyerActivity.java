package com.agrobuy.app.admin;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.agrobuy.app.admin.databinding.AddBuyerBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class AddBuyerActivity extends AppCompatActivity {
    private static final String TAG = AddBuyerActivity.class.getName();
    public AddBuyerBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = AddBuyerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        uid = getIntent().getStringExtra("user_ID");
        binding.topAppBar.setNavigationOnClickListener(v-> onBackPressed());
        binding.picChooseButton.setOnClickListener(v->{
            try {
               mGetContent.launch("image/*");
            } catch (android.content.ActivityNotFoundException ex) {
                // Potentially direct the user to the Market with a Dialog
                Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.add.setOnClickListener(v->{
            Toast.makeText(this, "Wait...", Toast.LENGTH_LONG+2).show();
            DatabaseReference ref;

            //check if pic has not been chosen
            if(binding.buyerImg.getText().toString().length()==0){
                binding.buyerImg.requestFocus();
                Toast.makeText(this, "choose a pic", Toast.LENGTH_SHORT).show();
                return;
            }
            //check if buyer ID exists already
            if(auth.getCurrentUser()!=null){
                ref = database.getReference("buyer_network" +"/" + uid + "/" + binding.buyerId.getText().toString());
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()){
                            // uploading pic
                            FirebaseUser currUser = auth.getCurrentUser();
                            StorageReference storageRef;
                            if(currUser!=null){
                                storageRef = FirebaseStorage.getInstance().getReference(
                                        uid + "/" + "buyer_network" + "/" +  binding.buyerId.getText().toString());
                                Uri uri = Uri.parse(binding.buyerImg.getText().toString());
                                storageRef.putFile(uri).addOnCompleteListener(task ->{
                                    Toast.makeText(getApplicationContext(), "Uploading pic...", Toast.LENGTH_SHORT)
                                            .show();
                                    if(!task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Couldn't upload pic", Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                    else{
                                        Log.d(TAG, ": Pic uploaded");
                                        storageRef.getDownloadUrl().addOnCompleteListener(task1 -> {
                                            if(!task1.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Error, Try again!", Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                            else{
                                                Uri picUri = task1.getResult();
                                                Map<String,Object> map = makeMap(picUri);
                                                ref.updateChildren(map);
                                                Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT)
                                                        .show();
                                            }

                                        });
                                    }

                                });
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Buyer ID already exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
    }

    private Map<String, Object> makeMap(Uri picUrl) {
        Map<String, Object> item = new HashMap<>();
        item.put("buyer_name",binding.buyerName.getText().toString());
        item.put("categories",binding.buyerName.getText().toString());
        item.put("company_phone_number",binding.buyerName.getText().toString());
        item.put("contact_person_email",binding.buyerName.getText().toString());
        item.put("contact_person_name",binding.buyerName.getText().toString());
        item.put("contact_personal_number",binding.buyerName.getText().toString());
        item.put("contact_time_preference", binding.buyerName.getText().toString());
        item.put("date_of_incorporation",binding.buyerName.getText().toString());
        item.put("importer_credit_rating",binding.buyerName.getText().toString());
        item.put("importer_history",binding.buyerName.getText().toString());
        item.put("pic_url",picUrl.toString());
        return item;
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> binding.buyerImg.setText(uri.toString()));
}