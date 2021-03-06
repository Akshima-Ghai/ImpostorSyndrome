package com.example.impostorsyndrome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText etName,etEmail,etPwd,etCnfPwd;
    Button btnSignup;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.signup);

        etName = findViewById(R.id.et_name);
        etEmail= findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_pwd);
        etCnfPwd = findViewById(R.id.et_cnf_pwd);
        btnSignup = findViewById(R.id.btn_signup);
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPwd.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    etEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    etPwd.setError("Password is required");
                    return;
                }
                if (password.length()<6){
                    etPwd.setError("Password must be greater than 6 characters");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignUp.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(SignUp.this,"Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    });
};
}
