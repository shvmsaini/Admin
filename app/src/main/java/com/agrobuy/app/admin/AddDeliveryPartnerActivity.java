package com.agrobuy.app.admin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.agrobuy.app.admin.databinding.AddDeliveryPartnerBinding;
import com.google.android.material.snackbar.Snackbar;
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

public class AddDeliveryPartnerActivity extends AppCompatActivity {
    private static final String TAG = AddDeliveryPartnerActivity.class.getName();
    public AddDeliveryPartnerBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddDeliveryPartnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        uid = getIntent().getStringExtra("user_ID");

        //
        binding.topAppBar.setNavigationOnClickListener(v-> onBackPressed());
        //
        binding.picChooseButton.setOnClickListener(v->{
            try {
                mGetContent.launch("image/*");
            } catch (android.content.ActivityNotFoundException ex) {
                // Potentially direct the user to the Market with a Dialog
                Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
            }
        });
        //
        binding.add.setOnClickListener(v->{
            Toast.makeText(this, "Wait...", Toast.LENGTH_LONG+2000).show();
            DatabaseReference ref;

            //check if pic has not been chosen
            if(binding.partnerImg.getText().toString().length()==0){
                binding.partnerImg.requestFocus();
                Toast.makeText(this, "choose a pic", Toast.LENGTH_SHORT).show();
                return;
            }
            //check if buyer ID exists already
            if(auth.getCurrentUser()!=null){
                ref = database.getReference("delivery_partners" +"/" + uid + "/" + binding.partnerId.getText().toString());
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()){
                            // uploading pic
                            FirebaseUser currUser = auth.getCurrentUser();
                            StorageReference storageRef;
                            if(currUser!=null){
                                storageRef = FirebaseStorage.getInstance().getReference(
                                        uid + "/" + "delivery_partners" + "/" +  binding.partnerId.getText().toString());
                                Uri uri = Uri.parse(binding.partnerImg.getText().toString());
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
                                                Toast.makeText(getApplicationContext(), "Partner successfully added", Toast.LENGTH_SHORT)
                                                        .show();
                                            }

                                        });
                                    }

                                });
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Partner ID already exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });

    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> binding.partnerImg.setText(uri.toString()));

    private Map<String, Object> makeMap(Uri picUrl) {
        Map<String, Object> item = new HashMap<>();
        item.put("name",binding.partnerName.getText().toString());
        item.put("pic",picUrl.toString());
        return item;
    }
}