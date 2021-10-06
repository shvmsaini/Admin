package com.agrobuy.app.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.agrobuy.app.admin.databinding.LoginLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    public LoginLayoutBinding loginLayout;
    private FirebaseUser currUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginLayout = LoginLayoutBinding.inflate(getLayoutInflater());
        setContentView(loginLayout.getRoot());

        mAuth = FirebaseAuth.getInstance(); // get firebase auth instance

        currUser = FirebaseAuth.getInstance().getCurrentUser();
        checkUser();
        if(currUser!=null){
            Intent i = new Intent(this,AdminHomeActivity.class);
            startActivity(i);
            finish();
        }


        // Login Button
        loginLayout.loginButton.setOnClickListener(view -> {
            String email = loginLayout.emailForLogin.getText().toString();
            String pass = loginLayout.passwordForLogin.getText().toString();
            if (isValidEmail(email) && !pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Wait...", Toast.LENGTH_SHORT).show();
                //checking if email is admin email
                if(!bin2hex(email.toLowerCase().getBytes()).equals(getResources().getString(R.string.admin_email))){
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                // if true then try signing in
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this, task -> {
                            if (!task.isSuccessful()) {
                                // If sign in fails, display a message to the user.
                                Log.w(LoginActivity.class.getName(), "signInWithEmail:failure", task.getException());
                                String exceptionName;
                                if( task.getException()!=null) {
                                    exceptionName = task.getException().getClass().getSimpleName();
                                    if (exceptionName.equals(FirebaseAuthInvalidUserException.class.getSimpleName())) {
                                        Toast.makeText(LoginActivity.this, "Cannot find user", Toast.LENGTH_SHORT).show();
                                        return;
                                    } else if (exceptionName.equals(FirebaseAuthInvalidCredentialsException.class.getSimpleName())) {
                                        Toast.makeText(LoginActivity.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                Toast.makeText(LoginActivity.this, "Authentication failed ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(LoginActivity.class.getName(), "signInWithEmail:success");
                                Intent i = new Intent(this,AdminHomeActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });
            } else {
                if (!isValidEmail(email)) {
                    loginLayout.emailForLogin.requestFocus();
                    Toast.makeText(LoginActivity.this, "Enter a valid email",
                            Toast.LENGTH_SHORT).show();
                } else {
                    loginLayout.passwordForLogin.requestFocus();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    static String bin2hex(byte[] data) {
        StringBuilder hex = new StringBuilder(data.length * 2);
        for (byte b : data)
            hex.append(String.format("%02x", b & 0xFF));
        return hex.toString();
    }
    public void checkUser(){
        if(currUser!=null)
        currUser.reload().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (currUser == null) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(this, "please re-login", Toast.LENGTH_SHORT).show();
                }
                else Log.d("UserID", currUser.getUid());
            } else Log.d("UserID", "task failed");
        });

    }
}
