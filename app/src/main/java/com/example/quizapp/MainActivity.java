package com.example.quizapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigationView;
    private FrameLayout main_frame;

    //Method created to control change from one bottom navigation others
    // the method is initiated from onCreate   method which is overridable.
    private BottomNavigationView.OnItemSelectedListener OnItemSelectedListener
            = item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    setFragment(new CategoryFragment());
                    return true;
                } else if (itemId == R.id.nav_leaderboard) {
                    setFragment(new LeaderboardFragment());
                    return true;
                } else {
                    setFragment(new AccountFragment());
                    return true;
                }

            };

    //method that runs first upon the initiation of the activity.
    // onCreate method is called when the activity is first created.
    // It is the place where you initialize your activity's components and set up the event listeners.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        main_frame = findViewById(R.id.main_layout);

        bottomNavigationView.setOnItemSelectedListener(OnItemSelectedListener);

//        bottomNavigationView.setOnItemReselectedListener(item -> {
//            // Handle reselection of items here
//            int itemId = item.getItemId();
//            if (itemId == R.id.nav_home) {
//                // Handle home navigation
//            } else if (itemId == R.id.nav_account) {// Handle search navigation
//            } else if (itemId == R.id.nav_leaderboard) {// Handle profile navigation
//            }
//        });

        //this is drawer layout which will be initiated by ActionBarDrawerToggle
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close); // three line for drawing purpose
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setFragment(new CategoryFragment());
    }

    private void setFragment(Fragment fragment) {
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(main_frame.getId(), fragment);
      transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        return false;
    }
}