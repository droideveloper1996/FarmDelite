package numberfact.codeham.com.farmdelite;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Abhishek on 29/10/2017.
 */

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartHolder> {

    private Context mContext;
    ArrayList<CartItem> cartItem;

    public CartRecyclerAdapter(Context mContext, ArrayList<CartItem> cartItem) {
        this.mContext = mContext;
        this.cartItem = cartItem;

    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_list_item_layout, parent, false);
        CartHolder viewHolder = new CartHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CartHolder holder, int position) {
        holder.mPrice.setText(cartItem.get(position).getPrice());
        holder.mTitleView.setText(cartItem.get(position).getName());
        holder.mSeller.setText("FarmDeLite");
        holder.strike.setText(cartItem.get(position).getMrp());
        holder.strike.setPaintFlags(holder.strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        Picasso.with(mContext).load(cartItem.get(position).getProduct_url()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return cartItem.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTitleView;
        TextView mSeller;
        TextView mPrice;
        TextView strike;

        public CartHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.product_image_view);
            mSeller = itemView.findViewById(R.id.seller_name);
            mTitleView = itemView.findViewById(R.id.main_title);
            mPrice = itemView.findViewById(R.id.price);
            strike = itemView.findViewById(R.id.strike);
        }
    }
}
