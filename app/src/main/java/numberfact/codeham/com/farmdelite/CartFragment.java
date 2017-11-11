package numberfact.codeham.com.farmdelite;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import numberfact.codeham.com.farmdelite.CartRecyclerAdapter.OnItemClickListener;

/**
 * Created by Abhishek on 21/10/2017.
 */

public class CartFragment extends Fragment implements OnItemClickListener {

    String refId;
    ;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String mCurrentUser;
    private Context mContext;
    DatabaseReference databaseProduct;
    RecyclerView cart_recycler;
    ArrayList<CartItem> cartItemArrayList;
    CartRecyclerAdapter cartRecyclerAdapter;
    ProgressBar progressBar;
    DatabaseReference wishListDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cart_fragment, container, false);
        cart_recycler = v.findViewById(R.id.recyclerview_cart);
        cartItemArrayList = new ArrayList<>();
        cart_recycler.setHasFixedSize(true);
        cart_recycler.setLayoutManager(new LinearLayoutManager(mContext));
        mCurrentUser = "Guest";
        progressBar = (ProgressBar) v.findViewById(R.id.product_progress);

        mContext = getContext();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mCurrentUser = mAuth.getCurrentUser().getUid();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child(ConstantUtils.CART).child(mCurrentUser);
        wishListDatabase = FirebaseDatabase.getInstance().getReference().child(ConstantUtils.WISH).child(mCurrentUser);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cartItemArrayList.clear();


                if (dataSnapshot != null) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String product_id = dataSnapshot1.getKey();
                        Log.d("CartFragment", product_id);
                        databaseProduct = FirebaseDatabase.getInstance().getReference().child(ConstantUtils.PRODUCTS).child(product_id);
                        databaseProduct.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot2) {
                                if (dataSnapshot2 != null) {


                                    Log.d("Cart", dataSnapshot2.toString());

                                    String name = dataSnapshot2.child(ConstantUtils.NAME).getValue().toString();
                                    String mrp = dataSnapshot2.child(ConstantUtils.MRP).getValue().toString();
                                    String price = dataSnapshot2.child(ConstantUtils.PRICE).getValue().toString();
                                    String brand = dataSnapshot2.child(ConstantUtils.BRAND).getValue().toString();
                                    String detail = dataSnapshot2.child(ConstantUtils.DETAIL).getValue().toString();
                                    String product_url = dataSnapshot2.child(ConstantUtils.PRODUCT_URL).getValue().toString();
                                    String Stock = dataSnapshot2.child(ConstantUtils.STOCK).getValue().toString();
                                    Log.d("CartFragment", name + mrp + price + brand + detail + Stock + product_url);
                                    cartItemArrayList.add(new CartItem(brand, detail, mrp, name, price, product_url, Stock));
                                    cartRecyclerAdapter = new CartRecyclerAdapter(mContext, cartItemArrayList, CartFragment.this);

                                    cart_recycler.setAdapter(cartRecyclerAdapter);
                                    cartRecyclerAdapter.notifyDataSetChanged();
                                    if (cartItemArrayList.size() > 0) {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                } else {
                                    Toast.makeText(mContext, "Product Not Found", Toast.LENGTH_LONG).show();
                                }
                                cartRecyclerAdapter.notifyDataSetChanged();


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        Log.d("CartFragment", dataSnapshot1.toString());


                    }


                    Toast.makeText(mContext,
                            "Data Received From Firebase", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getContext(),
                            "Oops! No Data Received From FarmDelite Server", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }


    @Override
    public void onRemoveClick(View view, final int item) {
        Toast.makeText(mContext, Integer.toString(item), Toast.LENGTH_SHORT).show();
        if (mAuth != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        int c = 1;
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                            String id = dataSnapshot1.getKey();
                            Log.d("CartFragment1", id);
                            ++c;
                            if (c == item) {
                                Log.d("CartFragment1", Integer.toString(c));

                                databaseReference.child(id).removeValue()   ;
                                refId = id;
                                break;
                            }
                        }


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }


}


