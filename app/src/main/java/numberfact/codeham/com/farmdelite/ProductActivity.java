package numberfact.codeham.com.farmdelite;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {

    ImageView itemImage;
    TextView itemName;
    TextView itemPrice;
    TextView itemDetail;
    FirebaseAuth mAuth;
    Button wish;
    Button cart;
    Button share;
    Button buy;
    DatabaseReference wishdatabaseReference;
    DatabaseReference cartDatabaseReference;
    String productIds;
    Vibrator vibrator;
    Bundle mainBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        itemDetail = (TextView) findViewById(R.id.itemDescription);
        itemName = (TextView) findViewById(R.id.item_name);
        itemImage = (ImageView) findViewById(R.id.product_image);
        itemPrice = (TextView) findViewById(R.id.itemPrice);
        wish = (Button) findViewById(R.id.wishlist);
        cart = (Button) findViewById(R.id.cart);
        share = (Button) findViewById(R.id.share);
        buy = (Button) findViewById(R.id.buy_now);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        Intent intent = getIntent();
        mainBundle=intent.getExtras();
        mAuth = FirebaseAuth.getInstance();
        mainBundle = new Bundle();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(ProductActivity.this, LoginActivity.class));

        } else {

            String authKey = mAuth.getCurrentUser().getUid();
            wishdatabaseReference = FirebaseDatabase.getInstance().getReference().child(ConstantUtils.WISH).child(authKey);
            cartDatabaseReference = FirebaseDatabase.getInstance().getReference().child(ConstantUtils.CART).child(authKey);

            if (intent != null) {
                Bundle bundle = intent.getExtras();
                mainBundle = bundle;
                productIds = bundle.getString(ConstantUtils.PRODUCT_ID);
                Picasso.with(this).load(bundle.getString(ConstantUtils.PRODUCT_URL)).into(itemImage);
                itemName.setText(bundle.getString(ConstantUtils.PRODUCT_NAME_KEY));
                itemPrice.setText(bundle.getString(ConstantUtils.PRODUCT_PRICE_KEY));
                itemDetail.setText(bundle.getString(ConstantUtils.PRODUCT_DETAIL_KEY));

            }

            wish.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {


                    wishdatabaseReference.child(productIds).child("quantity").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                vibrator.vibrate(500);
                                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            });

            cart.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View view) {

                    cartDatabaseReference.child(productIds).child("quantity").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                vibrator.vibrate(500);
                                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();

                }

            });

            buy.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(ProductActivity.this, BuyNowActivity.class);
                    intent.putExtras(mainBundle);

                    startActivity(intent);
                }
            });
        }
    }

}
