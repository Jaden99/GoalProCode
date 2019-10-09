package app.teamxtreme.com.goalpro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference ref = database.getReference();
    private ProgressDialog bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        bar=new ProgressDialog(this);

        Button btn =  findViewById(R.id.btnRegister);
        btn.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {


                bar.setMessage("Please wait...");
                bar.show();
                EditText editEmail, editPassword;
                editEmail =  findViewById(R.id.registerEmail);
                editPassword =  findViewById(R.id.registerPassword);

                // get text from EditText name view
                String email = editEmail.getText().toString();
                // get text from EditText password view
                String password = editPassword.getText().toString();
                registerUser(email, password);
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(Register.this, "User Registered",
                                    Toast.LENGTH_SHORT).show();
                            bar.dismiss();
                            openLogin();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "Registration failed",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });


    }



    public void openLogin()
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}