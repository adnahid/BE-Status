package StatusList;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statusapplication.DetailsActivity;
import com.example.statusapplication.MainActivity;
import com.example.statusapplication.R;
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

public class Lovely extends AppCompatActivity {

    RecyclerView lovelyRecyclerView;
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lovely);
        lovelyRecyclerView = findViewById(R.id.lovelyRecyclerView);
        toolbar = findViewById(R.id.toolbar);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lovely.this, MainActivity.class);
                startActivity(intent);
            }
        });

        lovelyTable();
        finalArrayListTable();


        LovelyAdapter lovelyAdapter = new LovelyAdapter();
        lovelyRecyclerView.setAdapter(lovelyAdapter);
        lovelyRecyclerView.setLayoutManager(new LinearLayoutManager(Lovely.this));

    }

    private class LovelyAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(Lovely.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(Lovely.this, "ca-app-pub-3940256099942544/2247696110")
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

            if ( x>1 && x%50==0){
                hashMap = new HashMap<>();
                hashMap.put("itemType","NativeAd");
                finalArrayList.add(hashMap);
            }

            hashMap = arrayList.get(x);
            finalArrayList.add(hashMap);
        }

    }


    // detailsTable
    private void lovelyTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"জীবন হচ্ছে একটি রহস্য, যা ভালোবাসার সমাধান খুঁজছে, তবে আমি এখনো খোঁজতে আছি।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— ( টমাস ফুলার)");
        hashMap.put("texDes",  "ভালোবাসা পাওয়ার চাইতে ভালোবাসা দেওয়াতেই বেশী আনন্দ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ভালোবাসা এমন একটি অদূরত্ব, যাতে আমি অবশ্যই থাকতে পারি, কিন্তু তোমার কাছে নয়।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ❦~হাবিবুর রাহমান সোহেল~❦");
        hashMap.put("texDes","অন্যের জন্যে নিজেকে বাঁচিয়ে রেখে নিজেকে নিজে তিলে তিলে শেষ করার নামই হলো ভালোবাসা ।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ( রেদোয়ান মাসুদ )");
        hashMap.put("texDes","ভালোবাসা হলো দুটি হৃদয়ের সমন্বয়, যেখানে একটি ছাড়া অন্যটি অচল।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (টেনিসন )");
        hashMap.put("texDes","ভালোবাসা যা দেয়, তার চেয়ে বেশি কেড়ে নেয় ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (রেদোয়ান মাসুদ )");
        hashMap.put("texDes","যে ভালোবাসার মাঝে না পাওয়ার ভয় থাকে, আর সেই কথা মনে করে দু’জনেই কাদে, সে ভালোবাসা হচ্ছে প্রকৃত ভালবাসা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সমরেশ মজুমদার )");
        hashMap.put("texDes","ছেলেরা ভালোবাসার অভিনয় করতে করতে যে কখন সত্যি সত্যি ভালোবেসে ফেলে তারা তা নিজেও জানেনামেয়েরা সত্যিকার ভালো বাসতে বাসতে যে কখন অভিনয় শুরু করে দেয় তারা তা নিজেও জানেনা।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ( দস্তয়েভস্কি )");
        hashMap.put("texDes","কেউ ভালোবাসা পেলে এমন কি সুখ ছাড়াও সে বাঁচতে পারে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (হুমায়ূন আহমেদ )");
        hashMap.put("texDes","পৃথিবীতে অনেক ধরনের অত্যাচার আছে ভালোবাসার অত্যাচার হচ্ছে সবচেয়ে ভয়ানক অত্যাচারএ অত্যাচারের বিরুদ্ধে কখনো কিছু বলা যায় না, শুধু সহ্য করে নিতে হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (কনফুসিয়াস )");
        hashMap.put("texDes","কোন কিছুকে ভালোবাসা হলো সেটি বেঁচে থাক তা চাওয়া।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (টমাস ফুলার)");
        hashMap.put("texDes","ভালোবাসা পাওয়ার চাইতে ভালোবাসা দেওয়াতেই বেশী আনন্দ।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (এলিজাবেথ বাওয়েন )");
        hashMap.put("texDes","যখন আপনি কাউকে ভালোবাসেন তখন আপনার জমিয়ে রাখা সব ইচ্ছে গুলো বেরিয়ে আসতে থাকে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (হাবিবুর রাহমান সোহেল )");
        hashMap.put("texDes","অন্যের জন্যে নিজেকে বাঁচিয়ে রেখে নিজেকে নিজে তিলে তিলে শেষ করার নামই হলো ভালোবাসা ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (হ্যাভনক এলিস )");
        hashMap.put("texDes","ভালোবাসা যখন অবদমিত হয়, তার জায়গা দখল করে ঘৃণা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","—  (হুমায়ূন আহমেদ )");
        hashMap.put("texDes",  "বাস্তবতা এতই কঠিন যে কখনও কখনও বুকের ভেতর গড়ে তোলা বিন্দু বিন্দু ভালোবাসাও অসহায় হয়ে পড়ে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (কীটস্ )");
        hashMap.put("texDes","যে ভালোবাসা পেলো না, যে কাউকে ভালোবাসতে পারলো না, সংসারে তার মতো হতভাগা কেউ নেই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (ডেভিড রস)");
        hashMap.put("texDes","ভালোবাসা এবং যত্ন দিয়ে মরুভূমিতেও ফুল ফোটানো যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (বসন্ত বাউরি )");
        hashMap.put("texDes","কেউ যদি তোমার ভালোবাসার মূল্য না বুঝে তবে নিজেকে নিঃস্ব ভেব না জীবনটা এত তুচ্ছ না।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি তোমার জন্য একটি পূর্ণ গল্প, আবারও লেখতে পারতাম না।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "তোমার সাথে থাকা মনে হয় যেন একটি সুন্দর স্নানের মতো, এবং তা হয়তো কখনোই শেষ হবে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার বিনায়কতা আমার জীবনে একটি অসীম বৃদ্ধির অভিজ্ঞান হিসেবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসা হলো একটি সহজ কাজ, যা আমি তোমার সাথে করতে চাই সব সময়।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— লিও টলস্টয়");
        hashMap.put("texDes","যখন তুমি কাউকে ভালোবাসো তখন তুমি পুরো মানুষটাকেই ভালবাসো ঠিক সে যেমন তেমনভবে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ❦~রেদোয়ান মাসুদ ~❦");
        hashMap.put("texDes","ভালোবাসা হলো দুটি হৃদয়ের সমন্বয়, যেখানে একটি ছাড়া অন্যটি অচল।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ❦~রেদোয়ান মাসুদ~❦");
        hashMap.put("texDes","ভালবাসা আর ভাল লাগা এক জিনিস না ভাল সবারই লাগে কিন্তু ভালবাসতে ক’জন পারে?");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ❦~রেদোয়ান মাসুদ ~❦");
        hashMap.put("texDes","যে ভালোবাসার মাঝে না পাওয়ার ভয় থাকে, আর সেই কথা মনে করে দু’জনেই কাদে, সে ভালোবাসা হচ্ছে প্রকৃত ভালবাসা।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ❦~হৃষিকেশ অনন্ত~❦");
        hashMap.put("texDes","যে ভালোবাসতে জানে , ভালোবাসি বলতে জানে না, তার মতো অভাগা এ পৃথিবীতে নেই।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার সঙ্গে থাকা হলো একটি অসীম গল্প, যা প্রতি দিন নতুন পৃষ্ঠা যোগ হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার সঙ্গে থাকা সময়টি হলো একটি সবুজ মেদের এলাকা, যেখানে শান্তি এবং সৌন্দর্যের অবস্থান।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসা হলো একটি নীল আকাশের মতো, সোজা এবং অসীম।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার মনে রয়েছে একটি মাধুর্যপূর্ণ গান, যা আমি প্রতি দিন গাই।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমার জীবন হলো একটি মজাদার গল্প, এবং তুমি তার সবচেয়ে ভালোবাসা প্রতিষ্ঠান।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "তোমার হাসি হলো আমার জীবনের সবচেয়ে মধুর সুর।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসা হলো একটি মহাকাব্য, এবং তুমি হওয়া সুবাধু প্রসবের প্রতীক।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "আমি তোমার সঙ্গে থাকা মনে হয় একটি শোভন সাঁজও, যা সব সময় মধুর এবং চমকপূর্ণ থাকে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার সাথে থাকা মনে হয় যেন একটি বিশেষ কানাডার গল্প, যেখানে প্রতিটি সময় আগের চেয়েও সুন্দর।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি তোমার হাতে একটি মহাকাব্য লেখতে চাই, তাতে প্রতি কথা তোমার সাথে ভাগ করতে চাই।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসা হলো একটি আভূতপূর্ণ প্রয়াস, এবং তুমি আমার জন্য তার সবচেয়ে ভালো ফলগুলি দিচ্ছ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার সাথে থাকা হলো একটি স্বপ্ন, যেখানে সব কিছু সম্ভব এবং সব সময় ভালোবাসা সামগ্রী রয়েছে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি তোমার জন্য একটি চিরকাল প্রতিশ্রুতি করছি, এবং তুমি তার সবচেয়ে মূল্যবান রত্ন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ❦~হুমায়ূন আহমেদ ~❦");
        hashMap.put("texDes", "বাস্তবতা এতই কঠিন যে কখনও কখনও বুকের ভেতর গড়ে তোলা বিন্দু বিন্দু ভালোবাসাও অসহায় হয়ে পড়ে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ❦~রেদোয়ান মাসুদ~❦");
        hashMap.put("texDes","প্রেম হলো মরণব্যাধির চেয়েও ভয়ানক রোগকারণ মরণব্যাধি মানুষকে একবারে শেষ করে দেয়আর প্রেম রোগ অনেককে সারা জীবন তিলে তিলে ক্ষয় করে মারে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ❦~সমরেশ মজুমদার ~❦");
        hashMap.put("texDes","ছেলেরা ভালোবাসার অভিনয় করতে করতে যে কখন সত্যি সত্যি ভালোবেসে ফেলে তারা তা নিজেও জানেনামেয়েরা সত্যিকার ভালো বাসতে বাসতে যে কখন অভিনয় শুরু করে দেয় তারা তা নিজেও জানেনা।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ❦~মুঃ ইসহাক কোরেশী~❦");
        hashMap.put("texDes","পাখিরা বাসা বাধে লতা পাতা দিয়ে, আর মানুষ বাধে ভালবাসা দিয়ে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসার কোন রঙ নেই, তবুও এটি অনেক রঙিন! ভালোবাসার কোন মুখ নেই, তবুও এটি অনেক সুন্দর!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসা নামক জিনিসটা এমনই! ভালোবাসার মানুষটা ছেড়ে চলে গেলেও ভালোবাসা শেষ হয়না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","হাত ধরে কিছুক্ষণ চলার নাম ভালোবাসা নয়।। ছায়া হয়ে সারাজীবন পাশে থাকার নামই ভালোবাসা।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "যদি তোমার ভালোবাসা এই জীবনে আমার থাকে, তাহলে সময়ের শেষ পর্যন্ত এটাই যথেষ্ট হবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি জানি আমি তোমার প্রেমে পড়েছি কারণ আমার বাস্তব শেষ পর্যন্ত আমার স্বপ্নের চেয়ে ভালো।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "একবার ভালোবেসে দেখো! ওই মানুষটি ছাড়া আর কাউকে ভালো লাগবে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," -বঙ্কিমচন্দ্র চট্টোপাধ্যায়");
        hashMap.put("texDes",  "“যাকে ভালোবাসো তাকে চোখের আড়াল করোনা”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-কাজী নজরুল ইসলাম");
        hashMap.put("texDes","\n" +
                "” তুমি সুন্দর তাই চেয়ে থাকি প্রিয় ,\n" +
                "সে কি মোর অপরাধ ?\n" +
                "চাঁদেরে হেরিয়া কাঁদে চকোরিনী,\n" +
                "বলে না তো কিছু চাঁদ ”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনের সবচেয়ে বড়ো পাওয়া হচ্ছে, এমন একজনকে পাওয়া, যে আপনার সব দোষ-ত্রুটি দুর্বলতা গুলো জানে এবং তারপরও আপনাকে ভালোবাসে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সত্যিকারের ভালোবাসার জন্য সুন্দর চেহারা বা অঢেল টাকার প্রয়োজন হয় না, এর জন্য একটি সুন্দর আর পবিত্র মনই যথেষ্ঠ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","কাউকে থেকে যাওয়ার জন্য কখনো অনুরোধ করতে নেই, কারণ যে সত্যি ভালোবাসে সে কখনো ছেড়ে যায় না।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনে সুখী হওয়ার জন্য পুরো পৃথিবীর দরকার হয় না। শুধু একজন মনের মতো মানুষ হলেই হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— জীবনানন্দ দাশ");
        hashMap.put("texDes","”তবু তোমাকে ভালোবেসে\n" +
                "মুহূর্তের মধ্যে ফিরে এসে\n" +
                "বুঝেছি অকূলে জেগে রয়\n" +
                "ঘড়ির সময়ে আর মহাকালে\n" +
                "যেখানেই রাখি এ হৃদয় ”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ফ্রাংকলিন পিজোনস");
        hashMap.put("texDes","ভালোবাসা হলো সেটাই যা জীবন নামক যাত্রাকে অর্থবহ করে তোলে!!");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— পাবলো নেরুদা");
        hashMap.put("texDes","ভালোবাসা অল্প কয়েক দিনের জন্য হলেও ভুলে যাওয়া সময় সাপেক্ষ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("cat","স্ট্যাটাস ");
        hashMap.put("texTitle","—  হারুকি মুরাকামি");
        hashMap.put("texDes",  "যদি তুমি আমাকে মনে রাখো তবে অন্য কেউ ভুলে গেলেও আমার কিছু যায় আসে না");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— নিকোলাস স্পার্কস");
        hashMap.put("texDes", "আমার বাবা আমাকে বলেছিল, যখন তুমি প্রথমবার ভালোবাসায় পড়বে এটা তোমাকে চিরদিনের জন্য পাল্টায়ে দিবে");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— জন আপডিক");
        hashMap.put("texDes","আমরা তখনই জীবন্ত থাকি যখন আমরা ভালোবাসার মধ্যে থাকি");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "ভালোবাসা যদি সত্যি হয় তা কখনোই শেষ হবার নয়");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— লুসিলি বেল");
        hashMap.put("texDes","পৃথিবীতে কোনো কিছু করতে হলে অবশ্যই নিজেকে আগে ভালোবাসতে হবে");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "ভালোবাসা হলো একটি রহস্য, যা আমি তোমার সাথে শেখার চেষ্টা করছি চিরকাল ধরে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি একটি অচ্ছা গল্প চাচ্ছি, আর তুমি সেই গল্পের অমূল্য চরণ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসা হলো একটি সহজ হাসি, যা তোমার দিকে পৌঁছাতে চেষ্টা করে সব সময়।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার বিনায়কতা হলো আমার জীবনের শব্দগুলি, যা বিরক্তিতে এক পথে রূপান্তর হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","এমন কাউকে ভালোবেস না যে তোমাকে সাধারণ এর মতো দেখে!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—  পাওলো কোয়েলহো");
        hashMap.put("texDes","যখন আমরা ভালোবাসি তখন আমরা আরও ভালো হওয়ার চেষ্টা করি আর এই চেষ্টার সময়ই আমাদের চারপাশ ভালো হয়ে যায়!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","প্রকৃত ভালোবাসাই কেবল তোমার আত্মাকে জাগ্রত করতে সক্ষম");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— রেদোয়ান মাসুদ");
        hashMap.put("texDes","“পৃথিবীতে সবচেয়ে বড় কষ্ট হলো এক তরফা ভালবাসা আর তার চেয়ে বড় কষ্ট হলো আপনি তাকে ভালোবাসতেন সে জানত, এখনও ভালোবাসেন কিন্তু সে জানে না ”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("cat","বাণী");
        hashMap.put("texTitle","-রেদোয়ান মাসুদ");
        hashMap.put("texDes",  "যে চোখ সৌন্দর্যের উপর দৃষ্টিপাত হয় প্রতিনিয়ত,\n" +
                "সে চোখে ভালোবাসা নয় উপভোগ থাকে অবিরত!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবন তার সাথেই\n" +
                "কাটানো প্রয়োজন,\n" +
                "যার চেহারার থেকে মন\n" +
                "অধিক সুন্দর!!");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তুমি আমার জীবনের গান,\n" +
                "আমার প্রানের পাখি!\n" +
                "ইচ্ছে করে মনের খাঁচায়,\n" +
                "লুকিয়ে তোমায় রাখি।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার আমার ভুল গুলো,\n" +
                "উড়ে যাক ফানুস হয়ে!\n" +
                "তুমি থেকে যাও শুধু,\n" +
                "আমার মনের মানুষ হয়ে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার ভিতরে হারিয়ে যাই,\n" +
                "তোমার মাঝে আমার খুঁজি!\n" +
                "প্রেম বলতে আজও আমি,\n" +
                "শুধুমাত্র তোমায় বুঝি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("cat","উক্তি");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসার পাল তুলে,\n" +
                "চলো মোরা ভেসে যাই!\n" +
                "অচিন দেশে বাঁধবো বাঁসা,\n" +
                "যে দেশে আর কেউ নাই!");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভুল করে ভুল হয়ে গেলে,\n" +
                "একটু তুমি মানিয়ে নিও!\n" +
                "অভিমানের প্রাচীর ভেঙে,\n" +
                "তোমার কাছে যেতে দিও।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","চুপ থেকে কথা হোক আজ,\n" +
                "হাতে হাত রেখে!\n" +
                "নীরবতা ভাষা খুঁজে পাক,\n" +
                "শুধু তোরই চোখে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসা হলো স্বাভাবিক হৃদয়ে ঘেরা একটি গোধূলি, যা আমি তোমার জন্য সব সময় ভাসি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসা হলো স্বাভাবিক হৃদয়ে ঘেরা একটি গোধূলি, যা আমি তোমার জন্য সব সময় ভাসি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার সাথে থাকা মনে হয় যেন একটি সুখোদ সফর, যা কখনোই শেষ হতে চায় না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি তোমার সাথে হৃদয়ের একটি সুখদ সাথে থাকতে চাই, এবং তোমার সাথে থাকা হলো আমার জীবনের সবচেয়ে সুন্দর অধ্যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার বিনায়কতা আমার জীবনের সবচেয়ে মূল্যবান সামগ্রী, যা আমি খোলে রাখতে চাই এবং সব সময় সোজা রাখতে চাই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","দিন কেটে যায় মেঘের ভেলায়,\n" +
                "রাত যে কাটে চাঁদে!\n" +
                "তোমায় ছাড়া বিষন্ন মন,\n" +
                "সারাক্ষনই কাঁদে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","কাছে এসো প্রিয়,\n" +
                "যেও না দূরে চলে!\n" +
                "এ প্রাণ রবে না আর,\n" +
                "তোমাকে হারালে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তুমি ছাড়া শূন্য সবই,\n" +
                "কিছু আর ভালো লাগেনা!\n" +
                "কবে আসবে প্রিয় তুমি,\n" +
                "আমার মন তো আর মানে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জোৎস্না রাতে একা বসে,\n" +
                "তোমার কথা ভাবি!\n" +
                "এই হৃদয়ের আঙ্গিনাতে,\n" +
                "তোমার ছবি আকি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","বৃষ্টি হলে খবর দিও,\n" +
                "হাঁটবো দুজন একটি ছাতায়!\n" +
                "তোমার আমার প্রেমের কথা,\n" +
                "লিখে রেখো ডাইরির পাতায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভোরের বৃষ্টির হিমেল হাওয়ায়,\n" +
                "ঘুমন্ত তোমার হাতের স্পর্শ!\n" +
                "মুছে দেবে একলা রাতের,\n" +
                "জমে থাকা অভিমানের গল্প।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি হাঁটতে চাই তোমার সাথে,\n" +
                "শুরু থেকে এই পথের শেষে!\n" +
                "হঠাৎ থমকে দিয়ে বলতে চাই,\n" +
                "ধন্য তোমায় ভালোবেসে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার ক্লান্ত মনের বেহাতে,\n" +
                "আমি তোমার প্রেমের রাগিনী!\n" +
                "তুমি যে গো মরীচিকা শুধু,\n" +
                "তুমি মোর প্রেমের কাহিনী।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তুমি আমার প্রথম প্রেম,\n" +
                "প্রথম ভালোবাসা!\n" +
                "তুমি আমার জীবনের,\n" +
                "বেঁচে থাকার আশা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\n" +
                "হাজার জনম চাইনা আমি,\n" +
                "একটা জনম চাই!\n" +
                "সেই জনমে আমি যেন,\n" +
                "শুধু তোমায় পাই!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\n" +
                "ঘর সাজাবো আলো দিয়ে,\n" +
                "মন সাজাবো প্রেম দিয়ে,\n" +
                "চোখ সাজাবো স্বপ্ন দিয়ে,\n" +
                "আর তোমায় সাজাবো,\n" +
                "শুধু আমার ভালোবাসা দিয়ে!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","একটা আঁকাশে অনেক তাঁরা ।\n" +
                "একটা জীবনে দূঃখ ভরা ।\n" +
                "অনেক রকম প্রেমের ভুল ।\n" +
                "ভুলের জন্য জীবন দিবো ।\n" +
                "তবুও আমি তোমারই রবো ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","রাজার আছে অনেক ধন ।\n" +
                "আমারআছে একটি মন ।\n" +
                "পাখির আছে ছোট্ট বাসা ।\n" +
                "আমার মনে একটি আশা ।\n" +
                "তোমায় ভালোবাসা ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি তোমার সঙ্গে থাকা মনে হয় একটি শোভন কাব্য, যেখানে সব কিছু আরামদায়ক এবং ভালোবাসা দিয়ে ভরপুর।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসা হলো একটি সত্যিই সোজা কথা, যা অসীম ভালোবাসা সামগ্রীর মধ্যে অবস্থান পায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার সঙ্গে থাকা হলো একটি মহামান্য কাব্য, এবং তোমার সঙ্গে থাকা হলো একটি অমূল্য চরণ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি তোমার সঙ্গে থাকতে পারতাম না, তবে এখন তোমার সঙ্গে থাকতে চাই চিরকালের জন্য।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার সাথে থাকা মনে হয় একটি ভালোবাসা যাত্রা, এবং তোমার প্রতি পয়স্য হয় একটি শব্দও।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes",  "ভালোবাসা হলো একটি সুন্দর সফর, এবং তুমি হওয়া একটি সমৃদ্ধ গোলপ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","স্বপ্ন দিয়ে আঁকি আমি,\n" +
                "সুখের সীমানা ।\n" +
                "হৃদয় দিয়ে খুজি আমি,\n" +
                "মনের ঠিকানা ।\n" +
                "ছায়ার মত থাকবো আমি,\n" +
                "শুধু তার পাশে,\n" +
                "যদি বলে সে আমায় সত্যি ভালবাসে॥");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সুন্দর রাত তার চেয়ে সুন্দর তুমি,\n" +
                "মনের দরজা খুলে দেখ তোমার\n" +
                "অপেক্ষায় দাড়িয়ে আছি আমি।\n" +
                "দু’হাত বাড়ালাম আমি তোমার তরে,\n" +
                "তুমি কী নিবে আমায় ভালবেসে আপন করে?");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ফুল লাল পাতা সবুজ,\n" +
                "মন কেন এতো অবুজ ।\n" +
                "কথা কম কাজ বেশি,\n" +
                "মন চায় তোমার কাছে আসি ।\n" +
                "মেঘ চায় বৃষ্টি,\n" +
                "চাঁদ চায় নিশি,\n" +
                "মন বলে আমি তোমায়\n" +
                "অনেক ভালোবাসি ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","একদিন দুজনে হাঁটব\n" +
                "আবার উড়বে তোমার চুল,\n" +
                "একদিন শূন্য বাতাস\n" +
                "ছুয়ে যাবে কৃষ্ণচুড়ার ফুল ।।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "আমি তোমার জন্য একটি চিরকাল প্রতিশ্রুতি করছি, এবং তুমি তার সবচেয়ে মূল্যবান রত্ন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার সঙ্গে থাকা হলো একটি সুখবর, যা সব সময় মনে রয়েছে এবং ভালোবাসা দিয়ে পূর্ণ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসা হলো একটি সোজা কথা, এবং তুমি তার সবচেয়ে মূল্যবান কথার মাধ্যমে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার সাথে থাকা হলো একটি মহাকাব্য, যেখানে সব কিছু আশার সাথে ভরপুর।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসা হলো একটি অদ্ভুত মিষ্টি স্বভাব, এবং তুমি সেই মিষ্টির প্রতি আমার প্রণাম।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি তোমার সাথে থাকা মনে হয় একটি ভালোবাসা সাগর, এবং তুমি তার অমূল্য রত্ন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি তোমার সঙ্গে থাকার মধ্যে তোমার আমার জন্য একটি অমূল্য বাণী।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার সাথে থাকা হলো একটি সুখদ সাফারি, যেখানে হতে থাকব তুমি এবং আমি।");
        arrayList.add(hashMap);






    }

}