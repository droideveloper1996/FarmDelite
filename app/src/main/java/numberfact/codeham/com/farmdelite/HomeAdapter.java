package numberfact.codeham.com.farmdelite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Abhishek on 22/10/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final OnItemClickListener listener;
 //   private final HomeProductClickListener homeProductclickListner;

    interface OnItemClickListener {
        void homeProduct(int position);

    }

    private Context mCtx;
    String arr[] = new String[]{
            "https://lh3.ggpht.com/N95wdMg31rW9uFtmXb7dlMEQqgR8Vt8G58_8G83AeXFD2kmTffp7FDo-65HdF18SXmk=h1080",

            "http://www.crossfitbothell.com/wp-content/uploads/2016/12/eating-chocolate-daily-is-good-for-health980-1456212647_980x457.jpg"
            ,
            "http://btulp.com/wp-content/uploads/2017/03/chocolate-gift-ideas-15.jpg"};

    HomeAdapter(Context mCtx, OnItemClickListener listener) {
        this.mCtx = mCtx;

        this.listener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.list_item1, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(mCtx).load(arr[position]).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arr.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.homeProduct(position);
        }
    }
}
