package com.aosproject.imagemarket.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.aosproject.imagemarket.Fragment.CartFragment;
import com.aosproject.imagemarket.Fragment.HomeFragment;
import com.aosproject.imagemarket.Fragment.ProfileFragment;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.aosproject.imagemarket.R.id.item_fragment1;
import static com.aosproject.imagemarket.R.id.item_fragment2;
import static com.aosproject.imagemarket.R.id.item_fragment3;

public class MainActivity extends AppCompatActivity {

    private int fromImageDetailViewToCartPage;
    private int fromImgListToProfilePage;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        fromImageDetailViewToCartPage = intent.getIntExtra("cart", 0);
        fromImgListToProfilePage = intent.getIntExtra("imgList", 0);


        bottomNavigationView = findViewById(R.id.tabar_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                BottomNavigate(item.getItemId());
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.item_fragment1);

        if(fromImageDetailViewToCartPage == 3){
            bottomNavigationView.setSelectedItemId(R.id.item_fragment3);
        }else if(fromImgListToProfilePage == 4){
            bottomNavigationView.setSelectedItemId(R.id.item_fragment4);
        }else{
            bottomNavigationView.setSelectedItemId(R.id.item_fragment1);
        }

    }


    private void BottomNavigate(int id) {
        String tag = String.valueOf(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if(currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            if (id == item_fragment1){
                fragment = new HomeFragment();
            } else if (id==item_fragment2){
                fragment = new SearchFragment();
            } else if (id==item_fragment3){
                fragment = new CartFragment();
            } else {
                fragment = new ProfileFragment();
            }
            fragmentTransaction.add(R.id.content_view_frame, fragment, tag);
        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();

    }

    private long pressedTime;
    @Override
    public void onBackPressed() {

        if(pressedTime == 0) {
            Toast.makeText(MainActivity.this,"한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        } else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if(seconds > 2000) {
                pressedTime = 0;
            } else {
                finish();
            }
        }
        finish();
    }
}