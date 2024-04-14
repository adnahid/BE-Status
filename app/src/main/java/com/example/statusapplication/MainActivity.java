package com.example.statusapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import StatusList.Alone;
import StatusList.Attitude;
import StatusList.DifferentDays;
import StatusList.Educational;
import StatusList.Emotional;
import StatusList.Fail;
import StatusList.Happy;
import StatusList.Human;
import StatusList.Islamic;
import StatusList.LifeOriented;
import StatusList.Lovely;
import StatusList.Motivated;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar Toolbar;
    NavigationView navigationView;


     TabLayout tabLayout;
     ViewPager viewPager;

    TabItem tabsItem1st,tabsItem2nd;
    pageAdapter vpAdpter;

    public static final String Url = "https://play.google.com/store/apps/details?id=";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(Toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabsItem1st = findViewById(R.id.tabsItem1st);
        tabsItem2nd = findViewById(R.id.tabsItem2nd);





        vpAdpter = new pageAdapter (getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,tabLayout.getTabCount());
        viewPager.setAdapter(vpAdpter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,Toolbar,R.string.drawerClose,R.string.drawerOPen);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

/*
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout,new FragmentLayout());
        fragmentTransaction.commit();

 */

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId()==R.id.home){
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);

                } else if (item.getItemId()==R.id.share){
                    //Toast.makeText(MainActivity.this, "Offer", Toast.LENGTH_SHORT).show();
                    //shareMethod
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Share Other");
                    intent.putExtra(Intent.EXTRA_TEXT,"This is an Nicely apps for android user........."+Url+getPackageName());
                    startActivity(Intent.createChooser(intent,"Share App Vai "));
                    drawerLayout.closeDrawer(GravityCompat.START);


                }else if (item.getItemId()==R.id.rate){
                    //Toast.makeText(MainActivity.this, "Offer", Toast.LENGTH_SHORT).show();
                    //ReviewMethod/code
                    try {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(Url+getPackageName()));
                        intent.setPackage(getPackageName());
                        startActivity(intent);

                    }catch (ActivityNotFoundException e){

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(Url+getPackageName()));
                        startActivity(intent);
                    }

                    drawerLayout.closeDrawer(GravityCompat.START);


                }else if (item.getItemId()==R.id.policy){

                    WebViewActivity.url="https://sites.google.com/view/bestatus";
                    Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                    startActivity(intent);

                    Toast.makeText(MainActivity.this, "Offer", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else if (item.getItemId()==R.id.about){
                    Intent intent = new Intent(MainActivity.this,AboutPage.class);
                    startActivity(intent);

                    //Toast.makeText(MainActivity.this, "Offer", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }



                return true;
            }
        });















    }
}