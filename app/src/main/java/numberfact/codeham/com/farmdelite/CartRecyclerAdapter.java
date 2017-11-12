package numberfact.codeham.com.farmdelite;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Abhishek on 29/10/2017.
 */

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartHolder> {

    private Context mContext;
    ArrayList<CartItem> cartItem;
    ArrayList<String> productIds;
    private final OnItemClickListener listener;
    DatabaseReference CartdatabaseReference;

    public CartRecyclerAdapter(Context mContext, ArrayList<String> productIds, ArrayList<CartItem> cartItem, OnItemClickListener listener) {
        this.mContext = mContext;
        this.cartItem = cartItem;
        this.listener = listener;
        this.productIds = productIds;
    }

    public interface OnItemClickListener {
        void onRemoveClick(View view, int item);


    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_list_item_layout, parent, false);
        CartHolder viewHolder = new CartHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CartHolder holder, final int position) {
        holder.mPrice.setText(cartItem.get(position).getPrice());
        holder.mTitleView.setText(cartItem.get(position).getName());
        holder.mSeller.setText("FarmDeLite");
        holder.strike.setText(cartItem.get(position).getMrp());
        holder.strike.setPaintFlags(holder.strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        Picasso.with(mContext).load(cartItem.get(position).getProduct_url()).into(holder.mImageView);
        holder.remove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                removeItem(position);

            }
        });


    }

    private void removeItem(int position) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            CartdatabaseReference = FirebaseDatabase.getInstance().getReference().child(ConstantUtils.CART)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(productIds.get(position));
            CartdatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot!=null)
                    {
                        dataSnapshot.getRef().setValue(null);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cartItem.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageView;
        TextView mTitleView;
        TextView mSeller;
        TextView mPrice;
        TextView strike;
        Button wish_btn;
        Button remove;

        public CartHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.product_image_view);
            mSeller = itemView.findViewById(R.id.seller_name);
            mTitleView = itemView.findViewById(R.id.main_title);
            mPrice = itemView.findViewById(R.id.price);
            strike = itemView.findViewById(R.id.strike);
            wish_btn = itemView.findViewById(R.id.move_to_wishlist);
            remove = itemView.findViewById(R.id.remove);
//              itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            listener.onRemoveClick(view.findViewById(R.id.remove), clickedPosition);


        }
    }
}
