package numberfact.codeham.com.farmdelite;

import android.Manifest.permission;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SignupActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 4001;
    private EditText firstname;
    private EditText lastname;
    private EditText mobile;
    private EditText email;
    private EditText password;
    private EditText country_code;
    private LinearLayout getStarted;
    private ImageView set_profile_picture;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    String uid = null;
    private StorageReference mStorageRef;
    TextView already_have_account;
    Uri selectedImage = null;
    Uri downloadedUri = null;
    String text;
    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstname = (EditText) findViewById(R.id.first_name);
        lastname = (EditText) findViewById(R.id.last_name);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        getStarted = (LinearLayout) findViewById(R.id.getStarted);
        already_have_account = (TextView) findViewById(R.id.already_have_account);
        set_profile_picture = (ImageView) findViewById(R.id.set_profile_picture);
        getPermision();
        mStorageRef = FirebaseStorage.getInstance().getReference().child("profile_pic");
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {
                    makeIntent();
                }
            }
        };


        getStarted.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setTitle("Registering");
                progressDialog.setMessage("Please wait while we register ....");

                registerUser(firstname.getText().toString(), lastname.getText().toString(),
                        email.getText().toString(), password.getText().toString(),
                        mobile.getText().toString());
            }
        });

        already_have_account.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        set_profile_picture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                getProfileImageFromGalley();

            }
        });
    }

    private void getProfileImageFromGalley() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/jpeg");
// intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(intent, 45);

    }

    private void registerUser(final String first, final String last, final String email, final String password, final String mobile) {
        otp = "";
        Random random = new Random();
        for (int i = 0; i <= 5; i++) {
            otp = otp + random.nextInt(10);
        }
        ConstantUtils constantUtils = new ConstantUtils(this);
        constantUtils.createUrl("Your One Time Password is \n" + otp, mobile);

        if (TextUtils.isEmpty(first) || TextUtils.isEmpty(last) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(mobile)
                ) {
            Toast.makeText(SignupActivity.this, "Please Check Fields", Toast.LENGTH_SHORT).show();
        } else {
            final View v = getLayoutInflater().inflate(R.layout.dialogue_builder, null);
            final EditText otpEditText = (EditText) v.findViewById(R.id.password_otp);
            final AlertDialog.Builder
                    builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
            builder.setView(v);
            builder.setCancelable(false);
            builder.show();

            v.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    text = otpEditText.getText().toString();
                    if (!text.equals(otp)) {
                        Toast.makeText(getApplicationContext(), "Wrong OTP. Please Re-Enter", Toast.LENGTH_LONG).show();

                        return;

                    } else {
                        progressDialog.show();
                        mAuth.createUserWithEmailAndPassword(email, password).

                                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull final Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            if (mAuth.getCurrentUser() != null) {
                                                uid = mAuth.getCurrentUser().getUid();
                                            }
                                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                                            Map<String, String> map = new HashMap<String, String>();
                                            map.put(ConstantUtils.REGISTERED_FIRST_NAME, first);
                                            map.put(ConstantUtils.REGISTERED_LAST_NAME, last);
                                            map.put(ConstantUtils.REGISTERED_EMAIL_ADDRESS, email);
                                            map.put(ConstantUtils.REGISTERED_MOBILE_NUMBER, mobile);

                                            if (downloadedUri == null) {
                                                map.put(ConstantUtils.PROFILE_PIC_URL, "default_avatar");
                                            } else {
                                                map.put(ConstantUtils.PROFILE_PIC_URL, downloadedUri.toString());

                                            }
                                            map.put(ConstantUtils.USER_STATUS, "Hey there! I’m Using DarkChat Messenger :)");

                                            databaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        progressDialog.hide();
                                                        makeIntent();
                                                    }
                                                }
                                            });


                                        }
                                    }
                                });
                    }
                }
            });

            v.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
                                                               @Override
                                                               public void onClick(View view) {


                                                               }
                                                           }
            );


        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(authStateListener);
    }

    public void makeIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 45 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Bitmap bitmap = null;
            selectedImage = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            set_profile_picture.setImageBitmap(bitmap);

        }
    }

    void getPermision() {
        if (ContextCompat.checkSelfPermission(SignupActivity.this,
                permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            ActivityCompat.requestPermissions(SignupActivity.this,
                    new String[]{permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
        }


    }

}