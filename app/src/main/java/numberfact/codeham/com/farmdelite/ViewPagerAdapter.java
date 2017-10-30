package numberfact.codeham.com.farmdelite;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Abhishek on 21/10/2017.
 */


public class ViewPagerAdapter extends PagerAdapter {


    public static final String URLSS = "http://www.codeham.com/slider/";

    String images[] = new String[]{URLSS + "image1.jpg", URLSS + "image2.jpg", URLSS + "image3.jpg", URLSS + "image4.jpg", URLSS + "image5.jpg"};
    Context mContext;

    public ViewPagerAdapter(Context mContext) {

        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.list_item, null);
        ImageView imageView = view.findViewById(R.id.list_image);

        Picasso.with(mContext).load(images[position]).into(imageView);


        ViewPager viewPager = (ViewPager) container;
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Clicked", Toast.LENGTH_LONG).show();
            }
        });

        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);

    }
}



