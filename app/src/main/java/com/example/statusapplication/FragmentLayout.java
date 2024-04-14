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
import com.squareup.picasso.Picasso;

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


public class FragmentLayout extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_layout, container, false);


        CardView islamicStatus = view.findViewById(R.id.islamicStatus);
        CardView lifeOrientedStatus = view.findViewById(R.id.lifeOrientedStatus);
        CardView emotionStatus = view.findViewById(R.id.emotionStatus);
        CardView alon = view.findViewById(R.id.alon);
        CardView happy = view.findViewById(R.id.happy);
        CardView lovely = view.findViewById(R.id.lovely);
        CardView attitude = view.findViewById(R.id.attitude);
        CardView fail = view.findViewById(R.id.fail);
        CardView education = view.findViewById(R.id.education);
        CardView days = view.findViewById(R.id.days);
        CardView hsStatus = view.findViewById(R.id.hsStatus);
        CardView motivated = view.findViewById(R.id.motivated);
        CardView sadStatus = view.findViewById(R.id.sadStatus);

        ImageView islamicImage = view.findViewById(R.id.islamicImage);
        ImageView lifeImage = view.findViewById(R.id.lifeImage);
        ImageView SadImage = view.findViewById(R.id.SadImage);
        ImageView emotionImage = view.findViewById(R.id.emotionImage);
        ImageView alonImage = view.findViewById(R.id.alonImage);
        ImageView motivationImage = view.findViewById(R.id.motivationImage);

        ImageView happyImage = view.findViewById(R.id.happyImage);
        ImageView loveImage = view.findViewById(R.id.loveImage);
        ImageView attitudeImage = view.findViewById(R.id.attitudeImage);
        ImageView failImage = view.findViewById(R.id.failImage);
        ImageView educationImage = view.findViewById(R.id.educationImage);
        ImageView dayImage = view.findViewById(R.id.dayImage);
        ImageView humanImage = view.findViewById(R.id.humanImage);
       ImageSlider image_slider = view.findViewById(R.id.image_slider);

        String Happy = "https://cdn-icons-png.flaticon.com/128/6976/6976288.png";
        String love =  "https://cdn-icons-png.flaticon.com/128/2448/2448392.png";
        String stringAttitude = "https://cdn-icons-png.flaticon.com/128/2593/2593795.png";
        String stringFail = "https://cdn-icons-png.flaticon.com/128/10651/10651902.png";
        String stringEducation = "https://cdn-icons-png.flaticon.com/128/3197/3197967.png";
        String day =  "https://cdn-icons-png.flaticon.com/128/2994/2994311.png";
        String human = "https://cdn-icons-png.flaticon.com/128/7829/7829198.png";
        String Alon = "https://cdn-icons-png.flaticon.com/128/6553/6553790.png";
        String Islamic = "https://cdn-icons-png.flaticon.com/128/14181/14181526.png";
        String Sad = "https://cdn-icons-png.flaticon.com/128/14359/14359907.png";
        String Emotion =  "https://cdn-icons-png.flaticon.com/128/7494/7494888.png";
        String life = "https://cdn-icons-png.flaticon.com/128/10184/10184706.png";
        String Motivation =  "https://cdn-icons-png.flaticon.com/128/10618/10618087.png";


        Picasso.get().load(Happy).placeholder(R.drawable.message).into(happyImage);
        Picasso.get().load(love).placeholder(R.drawable.message).into(loveImage);
        Picasso.get().load(stringAttitude).placeholder(R.drawable.message).into(attitudeImage);
        Picasso.get().load(stringFail).placeholder(R.drawable.message).into(failImage);
        Picasso.get().load(stringEducation).placeholder(R.drawable.message).into(educationImage);
        Picasso.get().load(day).placeholder(R.drawable.message).into(dayImage);
        Picasso.get().load(human).placeholder(R.drawable.message).into(humanImage);

        Picasso.get().load(Islamic).placeholder(R.drawable.message).into(islamicImage);
        Picasso.get().load(life).placeholder(R.drawable.message).into(lifeImage);
        Picasso.get().load(Sad).placeholder(R.drawable.message).into(SadImage);
        Picasso.get().load(Emotion).placeholder(R.drawable.message).into(emotionImage);
        Picasso.get().load(Alon).placeholder(R.drawable.message).into(alonImage);
        Picasso.get().load(Motivation).placeholder(R.drawable.message).into(motivationImage);


        //Image Slider
        String url = "https://i.postimg.cc/L4PhmPbL/Yellow-White-Minimalist-Quote-Reminder-Facebook-Post.png";
        String url1st = "https://i.pinimg.com/originals/29/0f/1b/290f1bd21caf451f479d1d5748abb536.jpg";
        String url2nd = "https://i.pinimg.com/736x/2f/cb/80/2fcb80c870189aea42860cd966f79a30.jpg";
        String url3rd = "https://i.pinimg.com/originals/f5/a6/96/f5a696841c554f2272e5cf394a5054a9.jpg";
        String url4th = "https://i.pinimg.com/originals/d5/ac/0a/d5ac0a5c97b318e70dc4416a8e0ccdae.jpg";
        String url5th = "https://th.bing.com/th/id/R.891c9d9b03f1a9e0a6357edd4a3fb84e?rik=Lr7dJ4MwTA86lg&pid=ImgRaw&r=0";
        String url6th = "https://i.pinimg.com/736x/bb/b8/af/bbb8aff784130f9a32516910f301535d.jpg";
        String url7th = "https://th.bing.com/th/id/OIP.PhAFtFOEudjCUrpj1PfnEwAAAA?rs=1&pid=ImgDetMain";

        List<SlideModel> list = new ArrayList<>();
        list.add(new SlideModel(url, ScaleTypes.FIT));
        list.add(new SlideModel(url1st, ScaleTypes.FIT));
        list.add(new SlideModel(url2nd, ScaleTypes.FIT));
        list.add(new SlideModel(url3rd, ScaleTypes.FIT));
        list.add(new SlideModel(url4th, ScaleTypes.FIT));
        list.add(new SlideModel(url5th, ScaleTypes.FIT));
        list.add(new SlideModel(url6th, ScaleTypes.FIT));
        list.add(new SlideModel(url7th, ScaleTypes.FIT));
        image_slider.setImageList(list);



        islamicStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Islamic.class);
                startActivity(intent);
            }
        });

        lifeOrientedStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LifeOriented.class);
                startActivity(intent);
            }
        });

        emotionStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Emotional.class);
                startActivity(intent);
            }
        });

        alon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Alone.class);
                startActivity(intent);
            }
        });

        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Happy.class);
                startActivity(intent);
            }
        });

        lovely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Lovely.class);
                startActivity(intent);
            }
        });

        attitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Attitude.class);
                startActivity(intent);
            }
        });

        fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Fail.class);
                startActivity(intent);
            }
        });

        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Educational.class);
                startActivity(intent);
            }
        });

        days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DifferentDays.class);
                startActivity(intent);
            }
        });

        hsStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Human.class);
                startActivity(intent);
            }
        });


        motivated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Motivated.class);
                startActivity(intent);
            }
        });

        sadStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SadActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }
}