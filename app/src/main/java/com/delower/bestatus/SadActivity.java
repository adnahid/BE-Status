package com.delower.bestatus;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.statusapplication.R;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SadActivity extends AppCompatActivity {


    RecyclerView sadRecyclerView;
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sad);
        sadRecyclerView = findViewById(R.id.sadRecyclerView);
        toolbar = findViewById(R.id.toolbar);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SadActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        sabTable();
        finalArrayListTable();


        RAdapter rAdapter = new RAdapter();
        sadRecyclerView.setAdapter(rAdapter);
        sadRecyclerView.setLayoutManager(new LinearLayoutManager(SadActivity.this));

    }

    private class RAdapter extends RecyclerView.Adapter{

        int Native = 0;
        int Read = 1;


        private class readHolder extends RecyclerView.ViewHolder{
            TextView textDes;
            Button buttonTextTitle;

            FloatingActionButton Copy,Share;
            public readHolder(@NonNull View itemView) {
                super(itemView);
                textDes = itemView.findViewById(R.id.textDes);
                buttonTextTitle = itemView.findViewById(R.id.buttonTextTitle);
                Copy = itemView.findViewById(R.id.Copy);
                Share = itemView.findViewById(R.id.Share);


            }
        }

        private class nativeHolder extends RecyclerView.ViewHolder{


            TemplateView my_template;
            public nativeHolder(@NonNull View itemView) {
                super(itemView);
                my_template = itemView.findViewById(R.id.my_template);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();

            if (viewType == Read) {
                View view = layoutInflater.inflate(R.layout.itemlayout, parent, false);
                return new readHolder(view);
            }
            View view = layoutInflater.inflate(R.layout.native_item, parent, false);
            return new nativeHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (getItemViewType(position)==Read){
                readHolder readHolder = (readHolder) holder;
                hashMap = finalArrayList.get(position);
                String texTitle  = hashMap.get("texTitle");
                String texDes = hashMap.get("texDes");




                Random rnd = new Random();
                int color = Color.argb(220, rnd.nextInt(220), rnd.nextInt(220), rnd.nextInt(220));
                readHolder.buttonTextTitle.setBackgroundColor(color);




                readHolder.buttonTextTitle.setText(texTitle);
                readHolder.textDes.setText(texDes);

                readHolder.Copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (readHolder.textDes!=null){

                            ClipboardManager clipboard = (ClipboardManager) getSystemService(DetailsActivity.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("copy",readHolder.textDes.getText().toString());
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(SadActivity.this, "কপি", Toast.LENGTH_SHORT).show();
                        }

                    }
                });



                readHolder.Share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT,readHolder.textDes.getText().toString());
                        intent.setType("text/plain");
                        intent = Intent.createChooser(intent,"share via");
                        startActivity(intent);


                    }
                });




            }else {
                nativeHolder nativeHolder = (nativeHolder) holder;

                AdLoader adLoader = new AdLoader.Builder(SadActivity.this, "ca-app-pub-8411075266548653/2415594861")
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                nativeHolder.my_template.setNativeAd(nativeAd);
                            }
                        })
                        .build();

                adLoader.loadAd(new AdRequest.Builder().build());
            }


        }

        @Override
        public int getItemCount() {
            return finalArrayList.size();
        }


        @Override
        public int getItemViewType(int position) {
            hashMap = finalArrayList.get(position);
            String itemType = hashMap.get("itemType");
            if (itemType.contains("read")) return Read;
            else
                return Native;

        }

    }
    private void finalArrayListTable(){

        finalArrayList = new ArrayList<>();

        for ( int x=0; x<arrayList.size(); x++){

            if ( x>1 && x%30==0){
                hashMap = new HashMap<>();
                hashMap.put("itemType","NativeAd");
                finalArrayList.add(hashMap);
            }

            hashMap = arrayList.get(x);
            finalArrayList.add(hashMap);
        }

    }

    // detailsTable
    private void sabTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes",  "কিছু ঘুম শান্তির, কিছু ঘুম না চাইতেই। কিছু ঘুম নিদ্রার, আবার কিছু ঘুম মৃত্যু অজান্তেই!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes",  "যখনই আমি কাউকে বিশ্বাস করতে শুরু করি, তখন সবচেয়ে বেশি আঘাত পাই।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি সেই মুহূর্তকে ঘৃণা করি যখন হঠাৎ আমার রাগ কান্নায় পরিণত হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","অবহেলা হচ্ছে সবচেয়ে খারাপ অনুভূতি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি কখনো একা বোধ করি না কারণ একাকীত্ব সবসময় আমার সাথে থাকে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","অতিরিক্ত ঘনিষ্ঠতা এখন সম্পর্কগুলো নষ্ট করে, কম আলাপ থাকলেই বোধহয় সম্পর্ক বেঁচে থাকে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","অবহেলা বিচ্ছেদের প্রথম সিগন্যাল, আর দুর্ব্যবহার সেটার অন্তিম ফলাফল।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমার নীরবতা অনেক কথা বলেছিল কিন্তু তুমি বুঝতে পারোনি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"আমি একলা থাকতে চাই না, তবে আমি আমার জন্য একলা হতে চাই না।\"\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"আমার হৃদয়ে একটি অনুভূতি আছে, একটি অনুভূতি যে আমি কখনোই হারাতে পারতে পারি না।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "\"ভালোবাসা তখনই ক্ষতিকর, যখন আপনি এটি অবহেলায় ভালোবাসার চেয়ে অধিক হয়।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes",  "\"অস্থির সময়ের জন্য আমি বোঝার চেষ্টা করছি, কিন্তু মনটি স্থির করতে পারছে না।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","শব্দ ছাড়া কান্নাগুলো খুব কষ্টের হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ঘুম জীবনের সেরা জিনিস, যা এলে সব ভুলিয়ে দেয় আর না এলে সব মনে করিয়ে দেয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সবচেয়ে বেদনাদায়ক স্মৃতি যখন আমি চলে গেলাম এবং তুমি আমাকে একবারও আটকালে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"আমার সবচেয়ে ভালো সময় তার সাথে ছিল, যেখানে তিনি আমার থাকার জন্য হৃদয় খোলে দিতেন।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"কিছু সময়, সময় কোণও না দিয়ে চলে যায়, এবং আমি তার পিছনে থাকি, ভোরের মতো আজকেই চোখের জলে ভিজে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","মানুষ সবচেয়ে বেশি অসহায় তখন, যখন মুখটা ভাসে কিন্তু মানুষটা আর থাকে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সম্পর্ক ফুরিয়ে যায় তখন, যখন মরে যায় মায়া।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "স্মৃতিগুলো বেইমান নয়, মানুষ চলে গেলেও ওরা থেকে যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","যদি কোন মানুষ তোমাকে চায়, কিছুই তাকে দূরে রাখতে পারে না। যদি সে তোমাকে না চায়, কিছুই তাকে ধরে পারে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সবাই জানে যে কিছু ভুল হয়েছে। কিন্তু কেউ জানে না কি হচ্ছে!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তুমি হারিয়ে গেলে ঠিকই তোমায় খুঁজে নিতাম। কিন্তু, তুমিতো বদলে গেছো।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ওরা ভীষণ রকমের স্বার্থপর হয়, যারা প্রয়োজনের তাগিদে প্রিয়জন পরিবর্তন করে!!");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","মায়া বড়ই অদ্ভুত না দেয় ভুলতে না দেয় ভালো থাকতে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমার আমি কখন তোমাতে হারিয়ে গেছি বুঝতেই পারিনি। তোমাতে বিভোর হয়ে আমি নিজেকে কখন বিলীন করে দিয়েছি, সেটাও বুঝতে পারিনি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমার ক্ষত বিক্ষত হৃদয় নিয়ে, তোমার সামনে দাঁড়াবার সাহস নেই। তোমার ভালোবাসা চাওয়ার অধিকারও নেই। শুধু দূর থেকে বলতে চাই, ভালো থেকো।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","বাবার হাত ছেড়ে যেদিন থেকে চলতে শিখেছি, সেদিন থেকেই বুঝেছি বাস্তবতা কল্পনার চেয়েও কঠিন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","মধ্যবিত্তের স্বপ্ন দেখা বারণ।কারো কাছে কোন আবদার করা বারণ।মধ্যবিত্তের কাঁদতে মানা।প্রয়োজনে হাত পাততে মানা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","যতোটা যত্ন করে মা-বাবা সন্তানকে বড় করে, ততোটা যত্ন করে যদি সন্তানের মনের খবর রাখতো! হতাশায় পড়ে হয়তো কাউকেই আত্মহত্যা কিংবা মাদকের পথ বেছে নিতে হতোনা।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","রাতটা যেন আমার ক্ষতগুলোর মতোই গভীর। শুধু পার্থক্য এটাই রাতের কাছে চাঁদ আছে। কিন্তু আমার কাছে কিছুই নেই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমার ব্যর্থতা নিয়ে আমি অনেক ভালো আছি। সবার তিরস্কার নিয়েও আমি বেচে তো আছি!");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes",  "যে বার বার হোচট খেয়েও উঠে দাড়াতে জানে, চলার ভয়টা সে কখনোই পায়না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসার মানুষ ছেড়ে চলে গেলে, তখন সুন্দর পৃথিবীকেও অসুন্দর মনে হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসাটা এতটা কষ্টের, যে এটি প্রকাশ করাটা কষ্টের, আবার ভূলে যাওয়াটাও কষ্টের।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","হয়ত একদিন স্বপ্নগুলো পূরন হবে কিন্তু আমি নিজ চোখে দেখতে পারবো না।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","স্বার্থের দুনিয়ায় যাকে ভাববে প্রিয়জন দিন শেষে সে বুঝিয়ে দিবে তুমি ছিলে তার প্রয়োজন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","প্রিয়জন বা প্রিয় জিনিস দুটোই বেশি দিন টিকে না হয়তো বদলে যায় নয়তো হারিয়ে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ডিপ্রেশন শুধু প্রেমের কারনেই হয় না পরিবারের চিন্তা, একাকিত্ব, ভবিষ্যৎ ভাবনা নিয়েও ডিপ্রেশন হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","অভিমান ভেঙে কথা বলে নিও..!!\n" +
                "আজ আছি কাল না ও থাকতে পারি..!!");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\n" +
                "বিচ্ছেদ জানে না, \n" +
                "ভালোবাসা কতটা বাকী..!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\n" +
                "নিজের সাথে যুদ্ধ করে নিজের সাথেই হার,\n" +
                "বৃষ্টি মেখে কান্না ঢাকি কারন বাঁচাটা দরকার..!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ছেলেদের জীবনটা বড়ো জটিল সারাজীবন পরিবারের দায়িত্ব ঘাড়ে নিয়ে চলতে হয়, কিন্তু তাদের মনখারাপ গুলো কেউ দেখতে পায় না !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ছেলেদের মন খারাপের অধিকার নেই, কান্নার অধিকার নেই। কারণ ছেলেরা যদি কান্না করে, তাহলে সবাই বলবে নাকামো করছে!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তুমি সবার প্রিয় কখনো হতে পারবে না!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","অনেক সময় কান্নাও এতটা কষ্ট প্রকাশ করতে পারে না, যতটা হাসি লুকিয়ে থাকে!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তার প্রতিটি স্মৃতি ঠাণ্ডা বাতাসের মতো আঘাত করে এবং সেই মুহূর্তে আমাকে নিথর করে দেয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","মনে রাখবেন, যখন এটি ব্যথা শুরু করে, জীবন আপনাকে কিছু শেখানোর চেষ্টা করছে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ভালোবাসার শব্দটি অনেকের জন্য কেবল একটি শব্দ, কিন্তু আমার জন্য এটি একটি");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ভালোবাসার আবেগ অনেকেই বুঝতে পারে, কিন্তু তার বেশিরভাগ মৌলিক ভাবে অজানা থাকে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"অনেক সময়, বুকে কিছু কাছে আসতে দেওয়ার জন্য অনেক বড় করে, তবে মনে থাকতে পারে না।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সত্য হল যে তারা আপনার কাছে আসে যখন আপনি প্রস্তুত নন, এবং যখন আপনার সবচেয়ে বেশি প্রয়োজন হয় তখন তারা চলে যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তিনি একজনের দ্বারা ভেঙে পড়েছেন এবং এটি তাকে সকলকে ঘৃণা করে তোলে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমার মন তোমাকে আমার মাথা থেকে বের করে আনার চেষ্টা করছে কিন্তু আমার হৃদয় তুমি যে সব কথা বলেছ তার প্রতিটা কথাই ধরে আছে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সবাই জানে যে তারা ইতিমধ্যে অন্য কারো জন্য পড়ে গেছে, আমরা তাদের জন্য অনুভব করি!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","দুঃখের বিষয়, বিশ্বাস ভালোবাসার চেয়ে বেশি বিপজ্জনক হতে পারে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","এই উজ্জ্বল হাসির পিছনে রয়েছে অন্ধকার রহস্য যা আপনি কখনই বুঝতে পারবেন না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ভালোবাসা একটি অদূরত্ব, একটি ব্যক্তিত্ব এবং একটি আবেগপূর্ণ গল্প।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"আমার মন বলতে চায়, কিন্তু তার কথা কেউ শোনতে চায় না।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সময় যত্নশীল ছিল, কিন্তু এখন হৃদয় যত্নশীল হয়েছে, তা মনে পড়তে চলেছে।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"জীবন একটি পথ হয়ে যাচ্ছে, এবং তার মধ্যে আমাদের সবচেয়ে ভালো এবং খারাপ মুহূর্তগুলি রয়েছে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"আমি চিরকাল হারিয়েছি, কিন্তু তোমার একমাত্র সঙ্গে পাওয়া এটি হলো সবচেয়ে বড় লাভ।\"");
        arrayList.add(hashMap);








    }
}