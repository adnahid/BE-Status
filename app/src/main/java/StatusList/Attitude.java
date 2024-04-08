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

public class Attitude extends AppCompatActivity {

    RecyclerView attitudeRecyclerView;
    ImageView attitudeImageBack;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attitude);
        attitudeRecyclerView = findViewById(R.id.attitudeRecyclerView);
        attitudeImageBack = findViewById(R.id.attitudeImageBack);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        attitudeImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Attitude.this, MainActivity.class);
                startActivity(intent);
            }
        });

        attitudeTable();
        finalArrayListTable();


        AttitudeAdapter attitudeAdapter = new AttitudeAdapter();
        attitudeRecyclerView.setAdapter(attitudeAdapter);
        attitudeRecyclerView.setLayoutManager(new LinearLayoutManager(Attitude.this));

    }

    private class AttitudeAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(Attitude.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(Attitude.this, "ca-app-pub-3940256099942544/2247696110")
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
    private void attitudeTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আমি কতদিন বেঁচে থাকবো তাতে আমার কিছু যায় আসে না। যতদিন পারব বাঁচবো কিন্তু আমি আমার আত্মসম্মান নিয়ে বাঁচব।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“নিজের কথা শুনি তাই এখনো দাঁড়িয়ে আছি। অন্য কারুর শুনলে তো কখনই ভেঙে পড়তাম।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "“মানছি যে আমার মধ্যে রাজার মতো কিছুই নেই, কিন্তু জেনে রাখবে যে, কোনো রাজার আমার মতো হওয়ার ক্ষমতা নেই \uD83D\uDE0E”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আমি বদলায়নি। এখন শুধু চুপ থাকতে ভালোবাসি!”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“ভয় অস্ত্র থেকে নয় মস্তিষ্ক থেকে বৃদ্ধি পায়, আর মস্তিষ্ক আমার ছোটবেলা থেকেই খারাপ \uD83D\uDE01”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আমি খারাপ তাই তো আমি বেঁচে আছি। ভালো হলে এই দুনিয়া আমায় বাঁচতে দিতো না।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“সাহস থেকে হারবে কিন্তু সাহস নয় কারণ চক্রভিউ আমাদের নিজেদের লোকেরাই গঠন করে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“যে মন থেকে ভেঙে পরে তাকে কেবল বন্ধুরাই বাঁচাতে পারে। আত্মীয়রা কেবল ব্যাবহার বাঁচাতে চায়!”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“পিঠের পিছনে আঘাত তারাই করে যাদের সমান হওয়ার ক্ষমতা নেই।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“জীবনের আসল মজা আসে যখন শত্রুও আপনার সাথে হাত মেলাতে মরিয়া যায়। ✌”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "“সেই গন্তব্যগুলো ভাঙতে হবে, যারা তাদের উচ্চতায় গর্বিত .. !!”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“ওটা তে কেবল সময় যা পাল্টে যাবে। আজ তোমার কাল আমার হয়ে যাবে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“সঠিক সময় আমার শক্তি সবাইকে দেখিয়ে দেব, কয়েকটা পুকুর নিজেকে সাগর ভাবতে শিখেছে…!”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“সুখ আপনার Attitude এর ওপর নির্ভর করে, আপনার কাছে কি আছে তার ওপর নয়।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“সাফল্যের জন্য, এটিটিউড দক্ষতার মতোই গুরুত্বপূর্ণ।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনটি হলো একটি প্রতিযোগিতা, আমি শীর্ষে থাকতে চাই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","মৃত্যু স্থানে আমার নাম এসেছে, কারণ আমি হার মানিনি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি হৃদয় দিয়ে হাসি, কিন্তু মাথা উচু রাখি সব সময়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","স্বপ্ন দেখার সময় আমি আঁধার দেখি না, আমি এককভাবে স্টার দেখি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","স্বপ্ন দেখার সময় আমি আঁধার দেখি না, আমি এককভাবে স্টার দেখি।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","যারা আমার সাথে হাসে, তারা বোঝে আমি কখনোই একক নই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আমাকে বিচার করার আগে নিশ্চিত করো যে তুমি নিখুঁত কি না।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আমি যেমন আছি তেমন ভাবেই নিয়ে নাও বা আমি যেভাবে যাচ্ছি আমাকে দেখতে থাকো \uD83E\uDD14\uD83D\uDE11।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আমি যখন তোমাকে উপেক্ষা করি তখন দয়া করে আমাকে বাধা দেবে না।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“যখন আপনি একজন বোকা ব্যক্তির সাথে কথোপকথন করেন তখন নীরবতা হল সর্বোত্তম প্রতিক্রিয়া।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“যদি আমার সফল হওয়ার দৃঢ় সংকল্প যথেষ্ট দৃঢ় হয় তাহলে ব্যর্থতা কখনোই আমার সামনে পেরে উঠতে পারবে না।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“সবকিছুতে দুর্দান্ত দেখতে আপনার মস্তিষ্ককে প্রশিক্ষণ দিন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“নেতিবাচক মনোভাব থেকে কোন ইতিবাচক ফলাফল আসতে পারে না। সর্বদা ইতিবাচক চিন্তা করুন এবং সর্বদা ইতিবাচকভাবে জীবনযাপন করুন।”\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "“ইতিবাচক প্রত্যাশা একটি উচ্চতর ব্যক্তিত্বের চিহ্ন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আমি তাদের, আমার পিছনে কথা বলতে শুনছি, যাই হোক অন্তত একটি কারণ খুঁজে পেলাম যে তারা কেন আমার পিছনে এখনো আমার পিছনে রয়েছে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "“সিংহ শান্ত হয়ে গেলেও জঙ্গল কুকুরের হয় না!”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“জীবনে সাফল্যের জন্য আপনার দুটি জিনিস দরকার ! অজ্ঞতা এবং ! আত্মবিশ্বাস");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "“আমি প্রকাশ করার জন্যে জন্মেছি, কাউকে প্রভাবিত করার জন্যে নয়!”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আমি লোকের সাথে খারাপ ব্যবহার করি না, আমি তাদের সাথে তাদের মতোই ব্যবহার করি।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "“জীবনে ঝড় আস্তে যেতে থাকে, কিন্তু আমার মনের ঝড়কে কি করে থামাবো তাই ভাবতে পারছি না!”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“কাউকে পছন্দ করতে এক মিনিট সময় লাগে, কাউকে ভালোবাসতে এক ঘন্টা, তবে কাউকে ভুলে যেতে জীবনকাল লাগে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","এটি এমন একটি ব্যক্তিত্ব যা আপনি পরিচালনা করতে পারবেন না!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","কোন ব্যাখ্যা প্রয়োজন। আমি জানি আমি সঠিক!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি সর্বোচ্চ উচ্চতায় উঠতে পারি কারণ আমার উড়ার সাহস আছে !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ভাল যথেষ্ট ভাল না। আমি আরো প্রাপ্য এবং এটি মহানতা !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনি যদি তাদের বোঝাতে না পারেন তবে তাদের বিভ্রান্ত করুন!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","একটি ভাল মনোভাব চয়ন করুন এবং সবকিছু জায়গায় পড়ে যাবে!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমার সাফল্য আমার শত্রুদের কাছে আমার প্রতিশোধ!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি নিজেকে অন্যের সাথে তুলনা করি না। আমি জানি আমি আমার নিজের উপায়ে সেরা!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনি আমার চিন্তার মাত্রা বুঝতে না পারলে আমি দুঃখিত!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","এমন শিল্প হও যা কেউ বোঝে না!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমার পিছনে যারা আমাকে নিয়ে গপ্পো করে তারা কোথায় জানেন? আমার পিছনে পিছনে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি হতে পারি এমন কেউ না থাকলে কোন প্রতিযোগিতা কিভাবে হতে পারে?");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","কোনো অ্যালার্ম ঘড়ির প্রয়োজন নেই। আমার আবেগ আমাকে জাগিয়ে তোলে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ক্লাসিক হওয়ার জন্য আমার বয়সের দরকার নেই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনার নিজের সম্ভাবনা নিয়ে আবিষ্ট হন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনের রঙ বদলাতে চাও, বদলাও। কিন্তু মনে রেখ জীবন বদলাতে পারবে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","বাহানা বানিয়ে লাভ নেই থাকার হলে থাকিস নাহলে ফুট নিজের রাস্তা দেখিস !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","খুশির খোঁজ করা বন্ধ করে দিয়েছি ,\n" +
                "কারণ খুশি খুঁজতে খুঁজতে নিজের\n" +
                "বর্তমান কে হারিয়ে ফেলছি দিন দিন!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","নিজের অন্দর মহলের সুন্দরতা খুবই\n" +
                "গুরুত্বপূর্ণ ভূমিকা পরিবহন করে\n" +
                "নিজেকে সুন্দর করে তুলতে!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সব নিয়ন্ত্রণকে গুড়িয়ে দাও , দাঁড়িয়ে থাকো\n" +
                "সবার থেকে পৃথক ভাবে , নিজের মস্তিষ্কের নয়\n" +
                "শুধু গুরুত্ব দাও নিজের মনকে!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আবেগগুলো অসম্পূর্ণই থাক\n" +
                "কারণ আমি মধ্যবিত্ত!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সারাদিন সবাই ভালো থাকার অভিনয় করে যায়\n" +
                "মাঝরাতে সবাই অভিনয়ের কাছে হেরে যায় ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "জীবনের বেশিরভাগ সময়টাই নিজেকে বুঝতেই ব্যয় করে ফেলি, আশপাশটা আর ভালো করে বুঝে উঠাই হয় না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","স্বপ্ন দেখার আগে অবশ্যই কাঙ্খিত বস্তুর জন্য প্রয়াস করতে হবে, কারণ এটি স্বপ্ন সফল করার প্রারম্ভিক ধাপ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনে সব কিছু সুন্দর নয়, কিন্তু নিরন্তন প্রয়াসের মাধ্যমে আপনি সেই সব কিছু থেকে সুন্দরতা বের করতে পারেন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সাময়িক সমস্যার কারণে কখনোই আপনি নিজেকে হারিয়ে ফেলবেন না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনে সফল হতে চাইলে খালি আলোচনা না করে আপনার কাজে দৃঢ় হোন।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","প্রত্যেক কষ্টের পেছনে রয়েছে সুন্দর কিছু , যা আপনি দেখতে পাচ্ছেন না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনি ব্যর্থ হয়েছেন? আরেকটি সুযোগের অপেক্ষায় থাকুন, কারণ সবসময় আপনি ব্যর্থ হবেন না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ব্যক্তিগত বৃদ্ধি আসে প্রত্যাশা থেকে, কিন্তু সমষ্টিগত বৃদ্ধি আসে উচ্চ ভাবনা থেকে।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","যেমন প্রত্যেকটি দুঃখে একটি দ্বার বন্ধ হয়, আবার একই সময়ে একটি দ্বার খুলে যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "সব সময় শিখতে হয়, এমনকি আমি যদি জীবনের বৃদ্ধি করতে চাই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি আমার মুখ দিয়ে বলি না, আমি আমার কাজে দেখাই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","যারা আমার পেছনে চলে, তারা জানে আমি কখনো থামি না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি কেউ থেকে কিছু চুরি করিনি, আমি নিজেই আমার দুর্বলতা ঠিক করতে চাই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি অপূর্ণ নই, আমি একটি অভিজ্ঞান যা অবিরাম চলছে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আজগের আনন্দ নিন কারণ গতকাল চলে গেছে এবং আগামী কাল নাও আস্তে পারে!”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সঠিক সময়ে, সঠিক স্থানে, সঠিক বাক্য প্রয়োগ করা খুব গুরুত্বপূর্ণ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনার বিচারে সঠিক মনে হলে কোন দ্বিধা ছাড়া প্রতিবাদ করতে পারেন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সবসময় বেশি ভাবি, কারণ আমি বেশি হাসি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "আমি ভুলে যাই না, শুধু অভিজ্ঞান করি এবং এগিয়ে যাই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "ভুলে যাওয়ার কোন সুযোগ নেই, কারণ ভুলে যাওয়া তাকে আমি অভ্যন্তরীণ অভিজ্ঞান হিসেবে ধরতে পারি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি মুক্তি চাই না, আমি সত্যি চাই, কারণ সত্যি মুক্তির সাথে যুক্ত আছে।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি সবাইকে ভালোবাসতে পারি, কারন আমি নিজেকে ভালোবাসি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি প্রতিনিয়ত আমার স্বপ্ন পূরণের পথে অগ্রসর হচ্ছি ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "ব্যক্তিগত বৃদ্ধির সমস্ত উপকরণ ও শক্তি আমার মধ্যে রয়েছে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি পরিবর্তনের মধ্যে আনন্দ পাই, কারণ সেটি অবশ্যই জীবনের একটি অংশ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি নিজের ভুল বুঝতে পারি, স্বীকার ও করি এবং ওই ভুল থেকে শিক্ষা গ্রহণ করি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","কখনো কখনো প্রত্যাশা ও পরিশ্রম অনুসারে ফল পাই না, তাই বলে পরিশ্রমের খামতি রাখি না, দ্বিগু উৎসাহে আবার ঝাঁপিয়ে পড়ি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনে চলার পথে সঞ্চিত অভিজ্ঞতাকে উপহার বলে মনে করি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি যে কিছু চাই, তা সত্যই দারুণ ও মূল্যবান।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি স্বপ্নের পিছনে ধাওয়া করি না, আমি নিজেই স্বপ্ন সৃষ্টি করি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি কারোর সাফল্যে ঈর্ষান্বিত হইনা, আমি আমার নিজের পথে আনতে ভালোবাসি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনে প্রয়োজনের তুলনায় অধিক যত্ন করা বড় ভুল, আমি প্রচুর যত্ন নেয়া অর্থ বুঝি না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি ভূতুড়ে নয়, তাই যারা ভূতুড়ের মধ্যে ভীষণ ভীতি পায়, তারা আমার সাথে ছবি তোলে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি অজানা দিকে পথ চলি, কারণ পূর্বের কোনও মানুষ তার পথ নিয়ে বাধ্য হয়নি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "ভুল করার অনুমতি নেই, কারণ আমি আগেই সঠিক ছিলাম।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সমৃদ্ধির মাধ্যমে আমি কারোকে মিথ্যা বোলি না, তার মধ্যে আমি ভালো থাকতে চাই।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি অনেক কিছু বুঝি, কিন্তু আমি অনেক বুঝতে চাই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","মৃত্যু এসে গেলে, আমি একটি সাহসীর মত সম্মানে মাথা উঁচু করে চলে যেতে চাই ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি আমার স্বপ্নের অভিমুখে রয়েছি, এখন আরো এগিয়ে যেতে হবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","যদি কখনো কারোর সঙ্গে বিরোধ হয় তাহলে সব সময় আমি আমার নিজের কাজের পর্যালোচনা করি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি মুখে যা সবাইকে করতে বলছি, সেই কাজ করে দেখানোর দায়বদ্ধতা আমার।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","নিজেকে প্রমাণ করার জন্য কাউকে ছোট করার দরকার নেই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি যে সদর্থক চিন্তাগুলি করি, সেগুলি সত্য করার প্রতিশ্রুতি দিতে দ্বিধাগ্রস্ত হই না।");
        arrayList.add(hashMap);





    }
}