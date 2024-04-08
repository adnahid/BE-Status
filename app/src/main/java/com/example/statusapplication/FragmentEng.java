package com.example.statusapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import EnglishStatusActivity.Love;
import EnglishStatusActivity.Broken;
import EnglishStatusActivity.InspiredEng;
import EnglishStatusActivity.Lost;
import EnglishStatusActivity.Other;


public class FragmentEng extends Fragment {






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View engView = inflater.inflate(R.layout.fragment_eng, container, false);
        AdView adView = engView.findViewById(R.id.adView);
        CardView beautiful = engView.findViewById(R.id.beautiful);
        CardView broken = engView.findViewById(R.id.broken);
        CardView inspire = engView.findViewById(R.id.inspire);
        CardView lost = engView.findViewById(R.id.lost);
        CardView struggle = engView.findViewById(R.id.struggle);
        ImageSlider engslider = engView.findViewById(R.id.engslider);

        ImageView InspirationImage = engView.findViewById(R.id.InspirationImage);
        ImageView brokenImage = engView.findViewById(R.id.brokenImage);
        ImageView loveImage = engView.findViewById(R.id.loveImage);
        ImageView lostImage = engView.findViewById(R.id.lostImage);
        ImageView otherImage = engView.findViewById(R.id.otherImage);

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);



        String Broken = "https://cdn-icons-png.flaticon.com/128/9497/9497849.png";
        String Love = "https://cdn-icons-png.flaticon.com/128/13333/13333082.png";
        String Lost = "https://cdn-icons-png.flaticon.com/128/9924/9924444.png";
        String Inspiration =  "https://cdn-icons-png.flaticon.com/128/2196/2196164.png";
        String Struggle = "https://cdn-icons-png.flaticon.com/128/2405/2405308.png";


        Picasso.get().load(Broken).placeholder(R.drawable.message).into(brokenImage);
        Picasso.get().load(Love).placeholder(R.drawable.message).into(loveImage);
        Picasso.get().load(Lost).placeholder(R.drawable.message).into(lostImage);
        Picasso.get().load(Inspiration).placeholder(R.drawable.message).into(InspirationImage);
        Picasso.get().load(Struggle).placeholder(R.drawable.message).into(otherImage);

        //Image Slider
        List<SlideModel> slideModelList = new ArrayList<>();
        String url0 = "https://th.bing.com/th/id/OIP.8c5gcDQpDsiRD140Pdak6AHaHe?rs=1&pid=ImgDetMain";
        String url1 = "https://th.bing.com/th/id/OIP._kZALwekG6vyAvR7Ed8m9wHaHa?w=640&h=640&rs=1&pid=ImgDetMain";
        slideModelList.add(new SlideModel(url0,"title", ScaleTypes.FIT));
        slideModelList.add(new SlideModel(url1,"title2", ScaleTypes.FIT));
        engslider.setImageList(slideModelList);

        //onClick
        beautiful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Love.class);
                startActivity(intent);
            }
        });

        broken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Broken.class);
                startActivity(intent);
            }
        });

        inspire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InspiredEng.class);
                startActivity(intent);
            }
        });


        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Lost.class);
                startActivity(intent);
            }
        });

        struggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Other.class);
                startActivity(intent);
            }
        });









        return engView;
    }
}