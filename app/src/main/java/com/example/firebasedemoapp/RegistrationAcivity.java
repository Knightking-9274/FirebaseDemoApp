package com.example.firebasedemoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationAcivity extends AppCompatActivity {
    private TextInputEditText userNameEdt,pwdEdt,cnfPwdEdt;
    private Button btnRegister;
    private ProgressBar prgRegister;
    private TextView txtViewREgister;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_acivity);

        userNameEdt = findViewById(R.id.edtUserName);
        pwdEdt = findViewById(R.id.edtPassword);
        cnfPwdEdt = findViewById(R.id.edtConPassword);
        btnRegister = findViewById(R.id.btnRegister);
        prgRegister = findViewById(R.id.prgRegistration);
        mAuth = FirebaseAuth.getInstance();
        txtViewREgister = findViewById(R.id.txtAlreadyUser);

        txtViewREgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationAcivity.this,LogInActivity.class);
                startActivity(intent);

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prgRegister.setVisibility(View.VISIBLE);
                String userName = userNameEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();
                String cnfPwd = cnfPwdEdt.getText().toString();

                if(!pwd.equals(cnfPwd)){
                    Toast.makeText(RegistrationAcivity.this, "Please chkeck both Passwords!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnfPwd)){
                    Toast.makeText(RegistrationAcivity.this, "Please fill credentials! ", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.createUserWithEmailAndPassword(userName,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                prgRegister.setVisibility(View.GONE);
                                Toast.makeText(RegistrationAcivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationAcivity.this,LogInActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else{
                                prgRegister.setVisibility(View.GONE);
                                Toast.makeText(RegistrationAcivity.this,"Failed to Register!",Toast.LENGTH_SHORT).show();

                            }


                        }
                    });


                }

            }
        });
    }
}