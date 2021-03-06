package numberfact.codeham.com.farmdelite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Abhishek on 29/10/2017.
 */

public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.HomeViewHolder> {

    private Context mContext;
    ArrayList<CartItem> productArrayList;
    private final HomeProductClickListner homeProductAdapter;

    public HomeProductAdapter(Context mContext, ArrayList<CartItem> productArrayList, HomeProductClickListner homeProductAdapter) {
        this.mContext = mContext;
        this.productArrayList = productArrayList;
        this.homeProductAdapter = homeProductAdapter;
    }

    interface HomeProductClickListner {
        void productClick(int position);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_all_product, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        holder.progressBar.setVisibility(View.GONE);
        holder.mProductrice.setText(productArrayList.get(position).getPrice());
        holder.mTitleView.setText(productArrayList.get(position).getName());
        Picasso.with(mContext).load(productArrayList.get(position).getProduct_url()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        ImageView mImageView;
        TextView mTitleView;
        TextView mProductrice;
        ProgressBar progressBar;


        public HomeViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_product);
            mTitleView = itemView.findViewById(R.id.product_name);
            mProductrice = itemView.findViewById(R.id.product_price);
            progressBar = itemView.findViewById(R.id.item_progress);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            homeProductAdapter.productClick(getAdapterPosition());

        }
    }

}
