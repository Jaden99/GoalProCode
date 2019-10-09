package app.teamxtreme.com.goalpro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editEmail, editPassword;
    private ProgressDialog bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bar=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        editEmail  =  findViewById(R.id.loginEmail);
        editPassword =  findViewById(R.id.loginPassword);

        Button btn= findViewById(R.id.btnLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.setMessage("Please wait...");
                bar.show();
                String email = editEmail.getText().toString(); // get text from EditText email view
                String password = editPassword.getText().toString();// get text from EditText password view
                if (validate(email, password)) {
                    //Sign up or login User
                    signIn(email,password);

                }

            }
        });

        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener(){


            public void onClick(View v)
            {
                openRegister();//calls method to open activity
            }

        });
    }

    //_________________________code attribution_______________________
// The following method was taken from FOSSASIA
//Author :Shailesh351
//Link : https://blog.fossasia.org/email-and-password-validation-in-open-event-android/
    private boolean validate(String email, String password) {

        // Reset errors.
        editEmail.setError(null);
        editPassword.setError(null);

        if (TextUtils.isEmpty(email)) {
            editEmail.setError("Email is required");
            return false;
        }


        if (TextUtils.isEmpty(password)) {
            editPassword.setError("Password is required");
            return false;
        }


        return true;
    }
//_______________________end_______________________________________

    //_________________Open new activities__________________________
    public void openRegister()
    {
        //opens new activity
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    //____________________________________________________________
    public void signIn(final String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Successfully signed in with: " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();

                            bar.dismiss();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            bar.dismiss();
                        }

                        // ...
                    }
                });
    }
}

