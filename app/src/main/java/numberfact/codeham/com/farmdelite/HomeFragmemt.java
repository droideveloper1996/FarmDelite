package numberfact.codeham.com.farmdelite;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Abhishek on 21/10/2017.
 */

public class HomeFragmemt extends Fragment {

    ViewPager viewPager;

    LinearLayout sliderLayout;
    private int dotsCounts;
    private ImageView dots[];
    private Context mCtx;
    private Activity activity;
    public static final String URLSS = "http://www.codeham.com/slider/";
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<CartItem> cartItems;
    HomeProductAdapter homeProductAdapter;
    RecyclerView recycler_view;

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        mCtx = getContext();
        activity = getActivity();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(ConstantUtils.PRODUCTS);

        cartItems = new ArrayList<>();

        recycler_view = v.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycler_view.setHasFixedSize(true);
      /*  = new HomeProductAdapter(mCtx, cartItems);


        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager();*/


        viewPager = v.findViewById(R.id.viewPager);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new

                GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));

        recyclerView.setAdapter(new

                HomeAdapter(mCtx));

        viewPager.setPageTransformer(false, new

                PageTransformer() {
                    @Override
                    public void transformPage(View view, float position) {
                        view.setTranslationX(view.getWidth() * -position);

                        if (position <= -1.0F || position >= 1.0F) {
                            view.setAlpha(0.0F);
                        } else if (position == 0.0F) {
                            view.setAlpha(1.0F);
                        } else {
                            // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                            view.setAlpha(1.0F - Math.abs(position));
                        }
                    }
                });
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());
        viewPager.setAdapter(viewPagerAdapter);
        sliderLayout = (LinearLayout) v.findViewById(R.id.sliderDots);
        dotsCounts = viewPagerAdapter.getCount();
        dots = new ImageView[dotsCounts];
        for (
                int i = 0;
                i < dotsCounts; i++)

        {
            try {
                dots[i] = new ImageView(getContext());
//                dots[i].setImageDrawable(getContext().getDrawable(R.drawable.inactive_dots));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 0, 8, 0);
                sliderLayout.addView(dots[i], params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //   dots[0].setImageDrawable(getContext().getDrawable(R.drawable.active_dot));
        Timer timer = new Timer();
        viewPager.addOnPageChangeListener(new

                                                  OnPageChangeListener() {
                                                      @Override
                                                      public void onPageScrolled(int position, float positionOffset,
                                                                                 int positionOffsetPixels) {

                                                      }

                                                      @RequiresApi(api = VERSION_CODES.LOLLIPOP)
                                                      @Override
                                                      public void onPageSelected(int position) {
                                                          for (int i = 0; i < dotsCounts; i++) {

                                                              try {
//                                                                  dots[i].setImageDrawable(getContext().getDrawable(R.drawable.inactive_dots));
                                                              } catch (Exception e) {
                                                                  e.printStackTrace();
                                                              }
                                                          }

                                                          try {
                                                              // dots[position].setImageDrawable(getContext().getDrawable(R.drawable.active_dot));

                                                          } catch (Exception e) {
                                                              e.printStackTrace();
                                                          }
                                                      }


                                                      @Override
                                                      public void onPageScrollStateChanged(int state) {

                                                      }
                                                  });
        timer.scheduleAtFixedRate(new

                                          TimerTask() {
                                              @Override
                                              public void run() {

                                                  activity.runOnUiThread(new Runnable() {
                                                      @Override
                                                      public void run() {
                                                          if (viewPager.getCurrentItem() == 0) {
                                                              viewPager.setCurrentItem(1);
                                                          } else if (viewPager.getCurrentItem() == 1) {
                                                              viewPager.setCurrentItem(2);
                                                          } else if (viewPager.getCurrentItem() == 2) {
                                                              viewPager.setCurrentItem(3);

                                                          } else if (viewPager.getCurrentItem() == 3) {
                                                              viewPager.setCurrentItem(4);
                                                          } else if (viewPager.getCurrentItem() == 4) {
                                                              viewPager.setCurrentItem(0);
                                                          }

                                                      }
                                                  });
                                              }
                                          }, 2000, 4000);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {


                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Log.d("TAG", dataSnapshot1.toString());
                        String name = dataSnapshot1.child(ConstantUtils.NAME).getValue().toString();
                        String mrp = dataSnapshot1.child(ConstantUtils.MRP).getValue().toString();
                        String price = dataSnapshot1.child(ConstantUtils.PRICE).getValue().toString();
                        String brand = dataSnapshot1.child(ConstantUtils.BRAND).getValue().toString();
                        String detail = dataSnapshot1.child(ConstantUtils.DETAIL).getValue().toString();
                        String product_url = dataSnapshot1.child(ConstantUtils.PRODUCT_URL).getValue().toString();
                        String Stock = dataSnapshot1.child(ConstantUtils.STOCK).getValue().toString();
                        Log.d("CartFragment", name + mrp + price + brand + detail + Stock + product_url);

                        cartItems.add(new CartItem(brand, detail, mrp, name, price, product_url, Stock));
                        homeProductAdapter = new HomeProductAdapter(mCtx, cartItems);
                        recycler_view.setAdapter(homeProductAdapter);

                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return v;
    }


}
