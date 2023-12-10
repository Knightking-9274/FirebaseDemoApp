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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private TextInputEditText userNameEdt,pwdEdt;
    private Button btnLogIN;
    private ProgressBar prgLogIn;
    private TextView txtNewUser;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        userNameEdt = findViewById(R.id.edtUserName);
        pwdEdt = findViewById(R.id.edtPassword);
        prgLogIn = findViewById(R.id.prgLogIn);
        txtNewUser = findViewById(R.id.txtNewUser);
        mAuth = FirebaseAuth.getInstance();
        btnLogIN = findViewById(R.id.btnLogIn);

        btnLogIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prgLogIn.setVisibility(View.VISIBLE);
                String userName = userNameEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();

                if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd)){

                    Toast.makeText(LogInActivity.this, "Please enter correct credentials!", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(userName,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                prgLogIn.setVisibility(View.GONE);
                                Toast.makeText(LogInActivity.this, "LogIn Successful!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LogInActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else{
                                prgLogIn.setVisibility(View.GONE);
                                Toast.makeText(LogInActivity.this, "Failed to LogIn!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        txtNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this,RegistrationAcivity.class);
                startActivity(intent);


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(LogInActivity.this,RegistrationAcivity.class);
            startActivity(intent);
            this.finish();

        }    }
}