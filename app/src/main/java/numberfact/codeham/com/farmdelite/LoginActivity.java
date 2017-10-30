package numberfact.codeham.com.farmdelite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button button;
    private TextView create_new_account;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progressDialogue;
    private DatabaseReference FirebasedatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.login);
        progressDialogue = new ProgressDialog(this);
        create_new_account = (TextView) findViewById(R.id.create_new_account);
        mAuth = FirebaseAuth.getInstance();
        FirebasedatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        authStateListener = new AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                }
            }
        };
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login(email.getText().toString(), password.getText().toString());
            }
        });

        create_new_account.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                  startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    private void login(String s, String s1) {
        if (!TextUtils.isEmpty(s) || !TextUtils.isEmpty(s1)) {
            progressDialogue.setTitle("Logging in");
            progressDialogue.setMessage("Please wait...");
            progressDialogue.setCanceledOnTouchOutside(false);
            progressDialogue.show();
            mAuth.signInWithEmailAndPassword(s, s1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    if (authResult.getUser() != null) {
                        makeIntent();
                        progressDialogue.hide();
                    }
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Please Check fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeIntent() {

        String token = FirebaseInstanceId.getInstance().getToken();
        FirebasedatabaseReference.child(mAuth.getCurrentUser().getUid()).child("token_id").setValue(token).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(authStateListener);
    }
}
