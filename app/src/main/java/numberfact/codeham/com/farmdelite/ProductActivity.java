package numberfact.codeham.com.farmdelite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {

    ImageView itemImage;
    TextView itemName;
    TextView itemPrice;
    TextView itemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        itemDetail = (TextView) findViewById(R.id.itemDescription);
        itemName = (TextView) findViewById(R.id.item_name);
        itemImage = (ImageView) findViewById(R.id.product_image);
        itemPrice = (TextView) findViewById(R.id.itemPrice);

        Intent intent = getIntent();

        if (intent != null) {
            Bundle bundle = intent.getExtras();

            Picasso.with(this).load(bundle.getString(ConstantUtils.PRODUCT_URL)).into(itemImage);
            itemName.setText(bundle.getString(ConstantUtils.PRODUCT_NAME_KEY));
            itemPrice.setText(bundle.getString(ConstantUtils.PRODUCT_PRICE_KEY));
            itemDetail.setText(bundle.getString(ConstantUtils.PRODUCT_DETAIL_KEY));
            Toast.makeText(this,bundle.getString(ConstantUtils.PRODUCT_ID),Toast.LENGTH_LONG).show();

        }
    }
}
