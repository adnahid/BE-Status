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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.delower.bestatus.DetailsActivity;
import com.delower.bestatus.MainActivity;
import com.delower.bestatus.R;
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

public class Fail extends AppCompatActivity {

    RecyclerView failRecyclerView;
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fail);
        failRecyclerView = findViewById(R.id.failRecyclerView);
        toolbar = findViewById(R.id.toolbar);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fail.this, MainActivity.class);
                startActivity(intent);
            }
        });

        failTable();
        finalArrayListTable();


        FailAdapter failAdapter = new FailAdapter();
        failRecyclerView.setAdapter(failAdapter);
        failRecyclerView.setLayoutManager(new LinearLayoutManager(Fail.this));

    }

    private class FailAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(Fail.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(Fail.this, "ca-app-pub-3940256099942544/2247696110")
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
    private void failTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ব্যর্থতা মানেই শেষ নয়, এটি পুনরায় চেষ্টা করার একটি সুযোগ।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ব্যর্থতা হলো সফলতার একটি অস্তিত্বশূন্য মুহূর্ত, তার অতীতে চলতে দিন না।\"");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"জীবনে ব্যর্থতা হলো একটি শেখা, যা আমাদেরকে আগামীকালের জন্য উদাহরণ হিসেবে ব্যবহার করতে শেখা যায়।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সফলতা কেবল পথের একটি দিক, ব্যর্থতা তার প্রতিষ্ঠান তৈরি করে এবং আমাদেরকে সঠিক দিকে নিয়ে যায়।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ব্যর্থতা তখন হয় যখন আমরা কিছু শেখি, আর তখনই আসল সফলতা শুরু হয়।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "\"জীবনে অনেক সময় ব্যর্থতা হলো একটি গুরুত্বপূর্ণ অধ্যায়ের শুরু, নতুন একটি কাহিনির আদলে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সফলতা প্রাপ্ত করার জন্য ব্যর্থতা হতে হবে, এটি একটি অমিল পথের অংশ।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ব্যর্থতা শেষ নয়, এটি আমাদের কেবল সঠিক পথে নিয়ে যায়।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","একজন মানুষ এই মুহূর্তে কতটা উপরে আছে, তা দিয়ে সাফল্য মাপা উচিত নয়। একদম নিচে পড়ে যাওয়ার পর সে নিজেকে কতটা ওপরে তুলতে পারে – সেটাই আসল কথা।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "ব্যর্থতার ছাই থেকে সাফল্যের প্রাসাদ নিমান করো। হতাশা আর ব্যর্থতা , এই দুটো জিনিস ই হলো সাফল্যের প্রাসাদের দুই মূল ভিত্তিপ্রস্তর।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সমালোচনা করার মতন তোমার যদি কেউ না থাকে, তাহলে জানবে তোমার সফল হওয়ার সম্ভাবনা নেই বললেই চলে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","অতীতের সাফল্য অনেকসময় ভবিষ্যতের ব্যর্থতার দিকে নিয়ে যায়। তবে যদি প্রতিটি ব্যর্থতা থেকে শিক্ষা নেওয়া যায়, তবে দিন শেষে সেই মানুষ টি একজন সফল মানুষ হিসেবে প্রতিপন্ন হবেই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","পৃথিবীতে অনেক মানুষ ব্যর্থ হয়েছে শুধু হার মেনে নেয়ার দরুণ ।এমন অনেক মানুষ আছে যারা হার মেনে নেওয়ার সময়ে বুঝতেও পারেনি তারা বিজয় পতাকার কতটা কাছাকাছি পৌঁছে গিয়েছিল।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","পৃথিবীতে সেইসব মানুষ জীবনে এমন কিছু গুরুত্বপূর্ণ সম্মান অর্জন করেছে , যারা সব আশার আলো নিভে যাওয়ার পরও চেষ্টা চালিয়ে গেছে, ব্যর্থতা তাঁদের গতিকে কখনও স্তিমিত করে দেয়নি ।");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ব্যর্থতা প্রকৃতপক্ষে নতুন করে শুরু করার একটা সুবর্ণ সুযোগ, যা পরোক্ষভাবে মানুষকে বলে দেয় যে আগেরবারের থেকে এবারে তাকে কিঞ্চিৎ বেশি বুদ্ধি খাটাতে হবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","প্রকৃতপক্ষে মানুষ কখনও ব্যর্থ হয় না, সে কেবল একটা পর্যায়ে এসে হার মেনে নেয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","যতক্ষণ না একজন মানুষ হার মানছে সে কখনই ব্যর্থ হতে পারেনা।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সাফল্যের অর্থই হল নিজের উ\u200Cৎসাহ না হারিয়ে একটার পর একটা ব্যর্থতাকে অতিক্রম করে যাওয়া।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনে কিছু থেকে ব্যর্থ হয়ে যাওয়া মানে সমগ্র জীবন ব্যর্থ হয়ে যাওয়া নয়, এই ব্যর্থতা পরোক্ষভাবে শিক্ষা দেয় যে, “তুমি আরও ভালো কিছুর দিকে এগিয়ে যাওয়ার পথে আছো”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","মানুষের জন্ম হয় সাফল্যের সুখ লাভ করার জন্য ব্যর্থতার জালে নিমজ্জিত হওয়ার জন্য নয়।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সাফল্যকে কখনই আপনার মাথায় চড়তে দেবেন না; ব্যর্থতা যেন কখনও আপনার মনে না বাসা বাঁধে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনি একবার ব্যর্থ হয়েছেন বলেই এর অর্থ এই নয় যে আপনি সব কিছুতে ব্যর্থ হবেন। চেষ্টা চালিয়ে যান এবং নিজের ইচ্ছাশক্তিকে ধরে রাখুন সর্বদা। নিজেকে বিশ্বাস করুন, কারণ আপনি যদি তা না করেন, তবে কে করবে?");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "সর্বদা সাফল্যের পথ কে ধরেই ব্যর্থতাকে অতিক্রম করতে হয়।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","একজন সফল ব্যক্তি ব্যর্থতার আশঙ্কা করেন না কারণ তিনি জানেন যে ব্যর্থতা থেকেই শিক্ষা নিয়ে প্রকৃত অর্থে বেড়ে ওঠা যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সমস্ত লোকেরা তাদের জীবনে কিছু নির্দিষ্ট ক্ষেত্রে ব্যর্থ হয়, একমাত্র যে বিষয়টিকে তাদের আলাদা করে তোলে তা হল তারা কীভাবে এই ব্যর্থতার মধ্যে থেকেও উঠে দাঁড়িয়েছে অথবা কীভাবে তারা আবার ব্যর্থ হতে বেছে নেয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ব্যর্থতা তখন হয় যখন আমরা কিছু নতুন চেষ্টা করি, আর তখন সফলতা হতে বাধা আসেনা।\"");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "সমস্ত লোকেরা তাদের জীবনে কিছু নির্দিষ্ট ক্ষেত্রে ব্যর্থ হয়, একমাত্র যে বিষয়টিকে তাদের আলাদা করে তোলে তা হল তারা কীভাবে এই ব্যর্থতার মধ্যে থেকেও উঠে দাঁড়িয়েছে অথবা কীভাবে তারা আবার ব্যর্থ হতে বেছে নেয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ব্যর্থতা তখন হয় যখন আমরা কিছু নতুন চেষ্টা করি, আর তখন সফলতা হতে বাধা আসেনা।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ব্যর্থতা হলো আমাদের যাত্রার একটি অবিচ্ছিন্ন অংশ, যা আমাদেরকে আগামী পথের জন্য প্রস্তুত করে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ব্যর্থতা হলো আমাদের যাত্রার একটি অবিচ্ছিন্ন অংশ, যা আমাদেরকে আগামী পথের জন্য প্রস্তুত করে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"জীবনের পথে যখন আপনি একটি দরিদ্র পথে চলছেন, তখন সতর্ক থাকুন, কারণ তাতে আপনি যে কোনও নতুন পথে পাওয়া সম্ভাবনা রয়েছে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সমস্যা হলো একটি পূর্ববর্তী শক্তি, আপনি যদি তার জোর দেওয়া দিতে না চান, তবে এটি আপনার কাছে অস্তিত্ব পায়নি।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ব্যর্থতা একটি পথে, তাতে অসংখ্য অন্য পথ খোলতে সাহায্য করতে পারে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সফল হওয়া যদি কোনো মানুষের দৃঢ় সংকল্প হয় তবে ব্যর্থতা কখনই তাকে ছাপিয়ে যেতে পারবে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ব্যর্থতা থেকেও আফসোসের ভীতি অনেক বেশি ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ব্যর্থতা থেকেও আফসোসের ভীতি অনেক বেশি ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সাফল্যকে ব্যর্থতা থেকে আলাদা করার একটিমাত্র পথ হ’ল একটি শেষ প্রচেষ্টা। আরও একবার চেষ্টা করুন ; আপনি ভাগ্যবান হবেন।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","একটি মানুষ জীবনে কখনো ব্যর্থ না হয়ে বেঁচে থাকতে পারে না। তাই জীবনে কখনো ব্যর্থ হলে নিরাশ হতে নেই ।");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","কেউ নিয়ম অনুসরণ করে হাঁটতে শেখেনি । একজন মানুষ নিজের কর্মের দ্বারাই অভিজ্ঞতা সঞ্চয় করে এবং বারেবারে পড়ে গিয়েই শিখতে পারে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সাফল্য বা ব্যর্থতা মানসিক সামর্থ্যের চেয়ে মানসিক মনোভাব দ্বারা বেশি সংঘটিত হয়ে থাকে ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ব্যর্থতাকে ভয় করবেন না – ব্যর্থতা নয়, মানুষের লক্ষ্যের পথটি নিম্ন হওয়া ই হ’ল অপরাধ। নিজের সবরকম প্রচেষ্টা দিয়ে ব্যর্থ হলেও তা গৌরবময়।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“ব্যর্থ হওয়ার নানা উপায় আছে, কিন্তু সফল হওয়ার উপায় একটাই ”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "“সফল হওয়ার উপায় কী জানি না, কিন্তু ব্যর্থ হওয়ার চাবিকাঠি হচ্ছে সবাইকে খুশি করার চেষ্টা করা ”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","” আরম্ভ হয় না কিছু — সমস্তের তবু শেষ হয় —\n" +
                "কীট যে ব্যর্থতা জানে পৃথিবীর ধুলো মাটি ঘাসে\n" +
                "তারও বড় ব্যর্থতার সাথে রোজ হয় পরিচয়!\n" +
                "যা হয়েছে শেষ হয়; শেষ হয় কোনোদিন যা হবার নয়! ”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ব্যর্থতা একটি পথ, নয় শেষ হওয়ার দিকে একটি নতুন শুরু।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনে অপেক্ষা করার সময় নয়, বরং আপনি কী করতে চান তা চিন্তা করার সময়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ব্যর্থতা মানেই আবার চেষ্টা করার সুযোগ পাওয়া, নয় আসলে হারানো মানেই সম্মান হারানো।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Robert Kiyosaki");
        hashMap.put("texDes","\"ব্যর্থতা মানেই আপনি কিছু চেষ্টা করছেন, আপনি ভুল হয়েছেন না।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Tony Robbins");
        hashMap.put("texDes","\"সফলতা সর্বদা একটি পথের মধ্যে অবস্থিত হয় না, সমস্ত পথেই তাকে অনুসরণ করতে হয়।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Winston Churchill");
        hashMap.put("texDes","\"ব্যর্থতা আপনাকে আপনার লক্ষ্যে পৌঁছানোর একটি অনুভূতি দেয়, কারণ এটি যে কোনও অবস্থানে অবস্থিত থাকতে পারে না।\" ");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Albert Einstein");
        hashMap.put("texDes","\"সফলতা হতে গেলে আপনাকে প্রথমে ব্যর্থ হতে হবে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সেক্সপিয়ার");
        hashMap.put("texDes","“জ্ঞানী লোকেরা কখনোই পরাজয়ের পর অলসভাবে বসে থাকে না – খুশির সঙ্গে চেষ্টা করে ক্ষতিটা পুষিয়ে নেওয়ার জন্য’’");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— নেলসন ম্যান্ডেলা");
        hashMap.put("texDes","“দক্ষতা তৈরি হয় অভিজ্ঞতা থেকে আর অভিজ্ঞতা আসে ব্যর্থতা থেকেই, তাই ব্যর্থতা খারাপ কিছু নয়। এটা সাফল্যের প্রথম ধাপ”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— নেপোলিয়ন হিল");
        hashMap.put("texDes","“ব্যর্থতা হল আপনাকে মহান দায়িত্বের জন্য প্রস্তুত করার জন্য প্রকৃতির পরিকল্পনা” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“দ্রুত পরিবর্তনশীল বিশ্বে ব্যর্থ হওয়ার একটি মাত্র উপায় আছে আর তা হল ঝুঁকি না নেওয়া”।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আমি ব্যর্থ হইনি ;আমি এমনকিছু হাজারো কাজ খুঁজে পেয়েছিলাম যেগুলো আমার ক্ষেত্রে সফল হয়নি”।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সেক্সপিয়ার");
        hashMap.put("texDes", "“জ্ঞানী লোকেরা কখনোই পরাজয়ের পর অলসভাবে বসে থাকে না – খুশির সঙ্গে চেষ্টা করে ক্ষতিটা পুষিয়ে নেওয়ার জন্য’’");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— নেলসন ম্যান্ডেলা");
        hashMap.put("texDes","“দক্ষতা তৈরি হয় অভিজ্ঞতা থেকে আর অভিজ্ঞতা আসে ব্যর্থতা থেকেই, তাই ব্যর্থতা খারাপ কিছু নয়। এটা সাফল্যের প্রথম ধাপ”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— নেপোলিয়ন হিল");
        hashMap.put("texDes","“ব্যর্থতা হল আপনাকে মহান দায়িত্বের জন্য প্রস্তুত করার জন্য প্রকৃতির পরিকল্পনা”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— আলবার্ট আইনস্টাইন");
        hashMap.put("texDes", "“যে ব্যক্তি কখনো ভুল করেনি সে কখনো নতুন কিছু করার চেষ্টা করেনি”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—  এ পি জে আব্দুল কালাম  ");
        hashMap.put("texDes","”ব্যর্থতা নামক রোগের সব থেকে ভালো ওষুধ হল আত্মবিশ্বাস আর কঠোর পরিশ্রম, এটা আপনাকে একজন সফল মানুষ গড়ে তুলতে সাহায্য করবে’’");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ইনভাজি");
        hashMap.put("texDes","”জীবনের পরস্পরবিরোধী ঐক্য বা দ্বৈততা বোঝার চেষ্টা করুন। সাফল্যের কোন অর্থ নেই, যদি ব্যর্থতা বিদ্যমান না থাকে”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— কলিন পাওয়েল");
        hashMap.put("texDes","”সাফল্য হল পরিপূর্ণতা, কঠোর পরিশ্রম, ব্যর্থতা থেকে শিক্ষা, আনুগত্য এবং অধ্যবসায়ের ফলাফল”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ব্যর্থতাগুলি আমাদের অর্জনের একমাত্র পথ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ব্যর্থতা যা আপনাকে সাফল্যের সঠিক দৃষ্টিভঙ্গি দেয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— জেনা শোভাল্টার");
        hashMap.put("texDes","“ত্যাগ করাই ব্যর্থ হওয়ার একমাত্র নিশ্চিত উপায়”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","—  ইনভাজি");
        hashMap.put("texDes", "“ব্যর্থতা সাফল্যের জন্য প্রয়োজনীয় আপনার শেখার বক্ররেখাকে প্রসারিত করে।”");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— বেঞ্জামিন ডিজরালি");
        hashMap.put("texDes","“আমার সমস্ত সাফল্য আমার ব্যর্থতার উপর নির্মিত হয়েছে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— টারিন রোজ");
        hashMap.put("texDes", "“ব্যর্থতার চেয়ে অনুশোচনার ভয় বেশি।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ব্রেন ব্রাউন");
        hashMap.put("texDes","“ব্যর্থতা ছাড়া কোন নতুনত্ব ও সৃষ্টিশীলতা নেই।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—এডউইন ল্যান্ড");
        hashMap.put("texDes", "“সৃজনশীলতার একটি অপরিহার্য দিক হল ব্যর্থ হতে ভয় না পাওয়া।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ব্যর্থ প্রাণের আবর্জনা পুড়িয়ে ফেলে আগুন জ্বালো।\n" +
                "একলা রাতের অন্ধকারে আমি চাই পথের আলো ॥\n" +
                "দুন্দুভিতে হল রে কার আঘাত শুরু,\n" +
                "বুকের মধ্যে উঠল বেজে গুরুগুরু–\n" +
                "পালায় ছুটে সুপ্তিরাতের স্বপ্নে-দেখা মন্দ ভালো ॥");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\n" +
                "আপন ভেবে যারে আমি দিয়েছিলাম মন\n" +
                "পরের মত চেয়ে আছে সে আমার আপনজন\n" +
                "হেরে গেলাম শেষে দেখি আমার কাছে আমি\n" +
                "নাম নেব না আমি\n" +
                "জেনো, আজ থেকে আর ভালোবাসার নাম নেব না আমি।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Bertrand Russell");
        hashMap.put("texDes","\"প্রতিবাদ হলেও, এটি অবশ্যই সম্পর্কে রয়েছে না। তার প্রভাব কেবল আপনি নির্ধারণ করতে দিতে পারেন।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Zig Ziglar");
        hashMap.put("texDes","\"যত্র ব্যর্থতা অবস্থিত তার জন্য পথ তৈরি করতে হয়, তারপর চলতে হয়।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Thomas Edison");
        hashMap.put("texDes","\"সফলতা হওয়ার জন্য আপনার মনে রাখতে হবে, প্রতিটি ব্যর্থতা একটি নতুন সুযোগ এবং আগামীর উপায়ে প্রস্তুতির একটি অংশ।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"জীবনে ব্যর্থতা আসতে হয়, কিন্তু সেটা তোমাকে বিপদে নিয়ে যাচ্ছে না, তোমাকে তোমার লক্ষ্যে নিয়ে যাচ্ছে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ব্যর্থতা শেখা হলো একটি সহজ শব্দে যাত্রা শুরু করা, অনেক কঠিন শব্দে সফলতা পৌঁছানো।\"\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ব্যর্থতা শেখা হলো একটি সহজ শব্দে যাত্রা শুরু করা, অনেক কঠিন শব্দে সফলতা পৌঁছানো।\"\n");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"জীবন একটি পাঠাগার, ব্যর্থতা হলে একটি পৃষ্ঠা মোছে দাও এবং আগামী পৃষ্ঠায় সফলতা লেখতে থাক।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ব্যর্থতার আশঙ্কা নিয়ে কখনও সফল হওয়া যায় না, কারণ ব্যর্থতা থেকে শিক্ষা নিয়েই প্রকৃত অর্থে জীবনে এগিয়ে যাওয়া যায়।  ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ওগ ম্যান্ডিয়ন");
        hashMap.put("texDes","“ব্যর্থতা কখনই আমাকে অতিক্রম করবে না যদি আমার সফল হওয়ার সংকল্প যথেষ্ট দৃঢ় হয়।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— উইনস্টন চার্চিল");
        hashMap.put("texDes","“উদ্দীপনা না হারিয়ে ব্যর্থতা থেকে ব্যর্থতার দিকে যাওয়াই সাফল্য।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— অপূর্ব দুবে");
        hashMap.put("texDes","“একমাত্র জিনিস যা সাফল্যকে ব্যর্থতা থেকে আলাদা করে তা হল একটি শেষ প্রচেষ্টা। আরও একবার চেষ্টা করুন এবং আপনি ভাগ্যবান হবেন”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— উইনস্টন চার্চিল");
        hashMap.put("texDes", "“সাফল্য চূড়ান্ত নয়, ব্যর্থতা মারাত্মক নয়: এটি চালিয়ে যাওয়ার সাহসই গুরুত্বপূর্ণ”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মোরিহেই উয়েশিবা");
        hashMap.put("texDes","”ব্যর্থতাই সাফল্যের চাবিকাঠি, কারণ প্রতিটি ভুলই আমাদের কিছু না কিছু শেখায়।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ওয়াল্টার স্কট");
        hashMap.put("texDes","“সাফল্য বা ব্যর্থতা মানসিক ক্ষমতার চেয়ে মানসিক মনোভাবের কারণে বেশি হয়।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ব্যর্থতা এবং সফলতা দুটি একে অপরের সঙ্গে জড়িত। ব্যর্থ না হলে জীবনে সফল হতে পারবে না আবার সফল মানুষই ব্যর্থতার সম্মুখীন হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("cat","স্ট্যাটাস");
        hashMap.put("texTitle","— ট্রুম্যান ক্যাপোট ");
        hashMap.put("texDes", "“ব্যর্থতা হল সেই মশলা যা সর্বদা সাফল্যের স্বাদ দেয়।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সর্বদা নিয়ম অনুসরণ করে কেউ হাঁটতে শেখেনি। একজন মানুষ তার নিজের কর্মের দ্বারাই অভিজ্ঞতা সঞ্চয় করে এবং ব্যর্থতার পরেই শিখতে পারে।");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনের অনেক ব্যর্থতায় অনেক মানুষ যারা হাল ছেড়ে দেওয়ার সময় বুঝতে পারেনি যে তারা সাফল্যের কত কাছাকাছি ছিল।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "আমরা যখন আমাদের কর্তব্য – কর্মে অবহেলা দেখিয়ে কোন দায়িত্বকে নিষ্ঠার সঙ্গে গ্রহন করিনা, তখনই অকৃতকার্যতা আসে।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","কর্মক্ষেত্রে ব্যর্থতা নিয়ে ভাবা অর্থহীন, যা সঠিক তাই করে যাওয়াই বুদ্ধিমানের কাজ। ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— কলিন পাওয়েল");
        hashMap.put("texDes","”সাফল্যের কোন রহস্য নেই। এটি প্রস্তুতি, কঠোর পরিশ্রম এবং ব্যর্থতা থেকে শেখার ফলাফল”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— বেনামী");
        hashMap.put("texDes","“সফলতাকে কখনই আপনার মাথায় আসতে দেবেন না, ব্যর্থতাকে আপনার হৃদয়ে প্রবেশ করতে দেবেন না” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","লড়তে শেখো ব্যর্থতার কিছু গল্প জেনে। সাফল্যের দিকে পা বাড়াও অসাফল্যের বাঁধাকে মেনে।  ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— হেনরি ফোর্ড");
        hashMap.put("texDes","“ব্যর্থতা মানে আবার শুরু করার সুযোগ, এবার আরও বুদ্ধিমত্তার সাথে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— এলোইস রিস্তাদ");
        hashMap.put("texDes", "“যখন আমরা নিজেদেরকে ব্যর্থ হওয়ার অনুমতি দিই, আমরা একই সময়ে নিজেদেরকে শ্রেষ্ঠত্বের অনুমতি দিই”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— অস্কার ওয়াইল্ড");
        hashMap.put("texDes","“আকাঙ্ক্ষা হল ব্যর্থতার শেষ আশ্রয়।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভালোবাসায় যে বিনিময়ে কিছু আশা করে, কখনোই তা সত্যিকারের ভালোবাসা হয় না, এই ধরনের ভালোবাসা অবশ্যই একদিন ব্যর্থ হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— কোকো চ্যানেল");
        hashMap.put("texDes"," বেশিরভাগ সফলতা তাদের দ্বারা অর্জিত হয়, যারা জানেন না যে ব্যর্থতা অনিবার্য।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— নেলসন ম্যান্ডেলা");
        hashMap.put("texDes", "দক্ষতা তৈরি হয় অভিজ্ঞতা থেকে আর অভিজ্ঞতা আসে ব্যর্থতা থেকেই, তাই ব্যর্থতা খারাপ কিছু নয় । এটা সাফল্যের প্রথম ধাপ ।");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—  বিল গেটস");
        hashMap.put("texDes","সফলতা উদযাপন করা খুবই ভালো, তবে ব্যর্থতার ধাপগুলোতে মনোযোগ দেওয়া আরও গুরুত্বপূর্ণ ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মাইকেল জর্ডন");
        hashMap.put("texDes","আমি ব্যর্থতা গ্রহণ করতে পারি, প্রত্যেকেই কিছু না কিছু ব্যর্থ হয়। কিন্তু আমি চেষ্টা না করে গ্রহণ করতে পারি না।");
        arrayList.add(hashMap);










    }


}