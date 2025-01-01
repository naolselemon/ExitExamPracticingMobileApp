package com.example.quizapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.TextView;

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

import java.util.Objects;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigationView;
    private FrameLayout main_frame;
    private TextView drawerProfileName, getDrawerProfileText;
    private NavigationView navigationView;


    //Method created to control change from one bottom navigation others
    // the method is initiated from onCreate   method which is overridable.
    private BottomNavigationView.OnItemSelectedListener OnItemSelectedListener
            = item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    setFragment(new CategoryFragment());
                    return true;
//                } else if (itemId == R.id.nav_leaderboard) {
//                    setFragment(new LeaderboardFragment());
//                    return true;
                } else {
                   setFragment(new AccountFragment());
                    return true;
                }

            };
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if(item.getItemId() ==  R.id.nav_home) {
                setFragment(new CategoryFragment());
            }
//            else if (item.getItemId() == R.id.nav_leaderboard){
//
//            setFragment(new LeaderboardFragment());
//        }
           else if(item.getItemId() == R.id.nav_account){
            setFragment(new AccountFragment());
        }

        // Close the drawer after selecting an item
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawers();
        }

        return true; // Indicate that the item selection is handled
    }


    //method that runs first upon the initiation of the activity.
    // onCreate method is called when the activity is first created.
    // It is the place where you initialize your activity's components and set up the event listeners.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Categories");

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        main_frame = findViewById(R.id.main_layout);

        bottomNavigationView.setOnItemSelectedListener(OnItemSelectedListener);

        //this is drawer layout which will be initiated by ActionBarDrawerToggle
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close); // three line for drawing purpose
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerProfileName = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_name);
        getDrawerProfileText = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_text_image);

        String name = DBQuery.myProfile.getName();
        drawerProfileName.setText(name);
        getDrawerProfileText.setText(name.toUpperCase().substring(0,1));

        setFragment(new CategoryFragment());
    }

    private void setFragment(Fragment fragment) {
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(main_frame.getId(), fragment);
      transaction.commit();
    }

}