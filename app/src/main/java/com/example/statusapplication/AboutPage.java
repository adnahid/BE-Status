package com.example.statusapplication;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.squareup.picasso.Picasso;

public class AboutPage extends AppCompatActivity {

    AdView aboutAdView;

    ImageView phoneImage,gmailImage,locationImage,instituteImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_page);
        aboutAdView = findViewById(R.id.aboutAdView);
        gmailImage = findViewById(R.id.gmailImage);
        locationImage = findViewById(R.id.locationImage);
        phoneImage = findViewById(R.id.phoneImage);
        instituteImage = findViewById(R.id.instituteImage);

        MobileAds.initialize(AboutPage.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        aboutAdView.loadAd(adRequest);


        String phone = "https://cdn-icons-png.flaticon.com/128/5968/5968534.png";
        String gmail = "https://cdn-icons-png.flaticon.com/128/3059/3059502.png";
        String location = "https://cdn-icons-png.flaticon.com/128/535/535188.png";
        String institute =  "https://cdn-icons-png.flaticon.com/128/5636/5636522.png";

        Picasso.get().load(phone).placeholder(R.drawable.message).into(phoneImage);
        Picasso.get().load(gmail).placeholder(R.drawable.message).into(gmailImage);
        Picasso.get().load(location).placeholder(R.drawable.message).into(locationImage);
        Picasso.get().load(institute).placeholder(R.drawable.message).into(instituteImage);



    }
}