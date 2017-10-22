package numberfact.codeham.com.farmdelite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import static numberfact.codeham.com.farmdelite.R.id.navigationView;

public class MainActivity extends AppCompatActivity {
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView mNavigationView;
    TabLayout tabLayout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new HomeFragmemt()).commit();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(navigationView);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.True, R.string.False);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        //    ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        // tabLayout = (TabLayout) findViewById(R.id.tabs);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (id) {
                    case R.id.action_home:
                        fragmentTransaction.replace(R.id.fragment_container, new HomeFragmemt()).commit();
                        break;
                    case R.id.action_account:
                        fragmentTransaction.replace(R.id.fragment_container, new AccountFragment()).commit();
                        break;
                    case R.id.action_cart:
                        fragmentTransaction.replace(R.id.fragment_container, new CartFragment()).commit();
                        break;
                    case R.id.action_wish:
                        fragmentTransaction.replace(R.id.fragment_container, new FavouriteFragment()).commit();
                        break;
                    case R.id.action_search:
                        fragmentTransaction.replace(R.id.fragment_container, new SearchFragment()).commit();
                        break;
                }
                return true;
            }
        });


//        tabLayout.setupWithViewPager(viewPager);
        // setupTabIcon();

        setupNAviationView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflator = getMenuInflater();
        menuInflator.inflate(R.menu.main_menu, menu);
        MenuItem menuItem=menu.findItem(R.id.action_searchbar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        switch (id) {
            case R.id.action_notification:
                Toast.makeText(getApplicationContext(), "Clicked Notification", Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);

    }


    void setupNAviationView() {
        mNavigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_reset:
                        // startActivity(new Intent(MainActivity.this, InitializeActivity.class));
                        break;
                    case R.id.action_control_center:
                        // startActivity(new Intent(MainActivity.this, AddProductActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    private void setupTabIcon() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_store_black_24dp);
     /*   tabLayout.getTabAt(1).setIcon(R.drawable.yellow);
        tabLayout.getTabAt(2).setIcon(R.drawable.lock);
*/

    }

}
