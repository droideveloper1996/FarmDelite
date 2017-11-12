package numberfact.codeham.com.farmdelite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static numberfact.codeham.com.farmdelite.ConstantUtils.MESSAGE;
import static numberfact.codeham.com.farmdelite.ConstantUtils.MY_SENDER_ID1;
import static numberfact.codeham.com.farmdelite.ConstantUtils.MY_USER;
import static numberfact.codeham.com.farmdelite.ConstantUtils.My_PASS;
import static numberfact.codeham.com.farmdelite.ConstantUtils.PASSWORD;
import static numberfact.codeham.com.farmdelite.ConstantUtils.SENDER;
import static numberfact.codeham.com.farmdelite.ConstantUtils.SMS_ALERT_BASE_URL;
import static numberfact.codeham.com.farmdelite.ConstantUtils.TO;
import static numberfact.codeham.com.farmdelite.ConstantUtils.USERNAME;

public class BuyNowActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference addressDatabaseReference;
    private DatabaseReference productsDatabasereference;
    private DatabaseReference wishListDatabaseReference;
    private DatabaseReference userDatabaseReference;
    String FirstName, LastName, mobile, email;
    TextView customerName, emailAdd, mobile_number;
    TextView checkout;
    TextView FinalPrice;
    Bundle bn;
    ProgressBar progressBar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Placing Your Order");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressBar = (ProgressBar) findViewById(R.id.buy_now_progressBar);
        if (getIntent() != null) {
            bn = getIntent().getExtras();
            Toast.makeText(getApplicationContext(), bn.getString(ConstantUtils.PRODUCT_NAME_KEY), Toast.LENGTH_LONG).show();

            View toolbar_view = findViewById(R.id.include_layout);
            Toolbar toolbar = (Toolbar) toolbar_view.findViewById(R.id.toolbar22);
            setSupportActionBar(toolbar);
            FinalPrice = (TextView) findViewById(R.id.finalPrice);
            final PrefManager prefManager = new PrefManager(this);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            customerName = (TextView) findViewById(R.id.customer_name);
            emailAdd = (TextView) findViewById(R.id.email);
            mobile_number = (TextView) findViewById(R.id.mobile_number);
            checkout = (TextView) findViewById(R.id.checkout);
            mAuth = FirebaseAuth.getInstance();
            if (mAuth.getCurrentUser() == null) {
                startActivity(new Intent(BuyNowActivity.this, LoginActivity.class));

            } else {
                String Uid = mAuth.getCurrentUser().getUid();
                userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);

                addressDatabaseReference = FirebaseDatabase.getInstance().getReference().child(ConstantUtils.ADDRESS).child(Uid);

                //  addressDatabaseReference= FirebaseDatabase.getInstance().getReference().child(Uid).push().
                addressDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {


                            userDatabaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot != null) {

                                        Log.i("Buy", dataSnapshot.toString());
                                        UserDetail userDetail = dataSnapshot.getValue(UserDetail.class);
                                        email = userDetail.getEmail();
                                        FirstName = userDetail.getFirst_name();
                                        LastName = userDetail.getLast_name();
                                        mobile = userDetail.getMobile();

                                        Log.i("Buy", email + FirstName + LastName + mobile);

                                    }
                                    customerName.setText(FirstName + " " + LastName);
                                    emailAdd.setText(email);
                                    mobile_number.setText(mobile);
                                    prefManager.setEmailAddress(email);
                                    prefManager.setMobileNumber(mobile);
                                    prefManager.setUserName(FirstName + " " + LastName);
                                    FinalPrice.setText(bn.getString(ConstantUtils.PRODUCT_PRICE_KEY));
                                    progressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            Toast.makeText(getApplicationContext(), "No Address Found", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Some Error Occured", Toast.LENGTH_LONG).show();

                        }

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
            final ConstantUtils constantUtils = new ConstantUtils(this);
            checkout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog.show();
                    sendToAdmin(
                            bn.get(ConstantUtils.PRODUCT_NAME_KEY)+
                            " Of " + " Amount Rs."+
                            bn.get(ConstantUtils.PRODUCT_PRICE_KEY));

                    sendToUser(bn.get(ConstantUtils.PRODUCT_NAME_KEY).toString() + " " + " for Amount Rs." + bn.get(ConstantUtils.PRODUCT_PRICE_KEY) + " "+" from "+
                    FirstName+" "+LastName);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            finish();
                        }
                    }, 3000);

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Something Went wrong ", Toast.LENGTH_LONG).show();
        }
    }

    public void sendToAdmin(String mess) {

        String message = "Dear! " + FirstName + " " + LastName +
                ",Thank You for Placing Your Order for " + (mess) +
                " is received successfully. Thank You For Using FarmDelite ";
        Uri Uri = android.net.Uri.parse(SMS_ALERT_BASE_URL).buildUpon().appendQueryParameter(USERNAME, MY_USER)
                .appendQueryParameter(PASSWORD, My_PASS)
                .appendQueryParameter(SENDER, MY_SENDER_ID1)
                .appendQueryParameter(MESSAGE, message)
                .appendQueryParameter(TO, mobile).build();
        String Url = Uri.toString();
        Log.i("MessageString", Url);

        NetworkUtils networkUtils = new NetworkUtils(this);
        networkUtils.fetchResponse(Url);
    }

    public void sendToUser(String msg) {
        String message = "Dear Administrator, You have a new Order for " + (msg) + " Kindly Deliver ASAP";
        Uri Uri = android.net.Uri.parse(SMS_ALERT_BASE_URL).buildUpon().appendQueryParameter(USERNAME, MY_USER)
                .appendQueryParameter(PASSWORD, My_PASS)
                .appendQueryParameter(SENDER, MY_SENDER_ID1)
                .appendQueryParameter(MESSAGE, message)
                .appendQueryParameter(TO, "7007013757,9559814196,9559814061").build();
        String Url = Uri.toString();
        Log.i("MessageString", Url);

        NetworkUtils networkUtils = new NetworkUtils(this);
        networkUtils.fetchResponse(Url);
    }

}
