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

public class LifeOriented extends AppCompatActivity {

    RecyclerView lifeOrientedRecyclerView;
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_life_oriented);
        lifeOrientedRecyclerView = findViewById(R.id.lifeOrientedRecyclerView);
        toolbar = findViewById(R.id.toolbar);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LifeOriented.this, MainActivity.class);
                startActivity(intent);
            }
        });

        lifeOrientedTable();
        finalArrayListTable();


        LifeOrientedAdapter lifeOrientedAdapter = new LifeOrientedAdapter();
        lifeOrientedRecyclerView.setAdapter(lifeOrientedAdapter);
        lifeOrientedRecyclerView.setLayoutManager(new LinearLayoutManager(LifeOriented.this));

    }

    private class LifeOrientedAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(LifeOriented.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(LifeOriented.this, "ca-app-pub-3940256099942544/2247696110")
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
    private void lifeOrientedTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– শোলম আইএলচেম");
        hashMap.put("texDes","“জীবন জ্ঞানী মানুষের স্বপ্ন, বোকা লোকদের জন্য খেলা, ধনীদের জন্য কৌতুক, দরিদ্রের জন্য বিয়োগান্তক নাটক।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– নেলসন ম্যান্ডেলা");
        hashMap.put("texDes","“জীবনযাপনের সর্বাধিক গৌরব পোরে যাওয়ার মধ্যে নয়, কিন্তু প্রত্যেকবার পোরে যাওয়ার পরেও উঠে দাঁড়ানোতে রয়েছে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মার্গারেট লরেন্স");
        hashMap.put("texDes","“আমাদের জীবনে যা আছে তা নয় তবে আমাদের সাথে যারা আছে তারা গুরুত্বপূর্ণ।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– নেভাল রবিকান্ত");
        hashMap.put("texDes","“জীবনের তিনটি জিনিস – আপনার স্বাস্থ্য, আপনার লক্ষ্য এবং আপনি যাদের পছন্দ করেন। এটাই যথেষ্ট!”");
        arrayList.add(hashMap);

//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– স্টিফেন হকিং ");
        hashMap.put("texDes","“জীবন মজার না হলে করুণ হয়ে উঠত।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– স্টিভ জব্স ");
        hashMap.put("texDes", "“আপনার সময় সীমিত, সুতরাং অন্য কারও জীবন-যাপন করতে যেয়ে ওটাকে ব্যয় করবেন না।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– ক্যারল বার্নেট");
        hashMap.put("texDes","“শুধুমাত্র আমি আমার জীবন পরিবর্তন করতে পারি। আমার পক্ষে কেউ এটি করতে পারে না।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মার্ক টয়েন ");
        hashMap.put("texDes","“ভাল বন্ধু, ভাল বই এবং একটি ঘুমন্ত বিবেক: এটি আদর্শ জীবন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– আলবার্ট আইনস্টাইন ");
        hashMap.put("texDes","“জীবন একটি সাইকেল চালানোর মত। আপনার ভারসাম্য বজায় রাখতে আপনাকে অবশ্যই চলতে থাকতে হবে।”");
        arrayList.add(hashMap);



//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মায়া অ্যাঞ্জেলু");
        hashMap.put("texDes","“আপনি জীবনে অনেক পরাজয়ের মুখোমুখি হবেন, তবে নিজেকে কখনও পরাজিত হতে দেবেন না।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– ম্যাক্সিম লাগসে");
        hashMap.put("texDes","“জীবন একটি পর্বত। আপনার লক্ষ্য আপনার পথটি সন্ধান করা, শীর্ষে পৌঁছানো নয়।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– হেনরি ফোর্ড");
        hashMap.put("texDes","“সফল জীবনের পুরো গোপনীয় বিষয় হল একজনের ভবিতব্য কী করা উচিত তা খুঁজে বের করা এবং তারপরে এটি করা।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– আর্নেস্ট হেমিংওয়ে");
        hashMap.put("texDes","“প্রথমে জীবন সম্পর্কে লেখার জন্য আপনাকে অবশ্যই এটি বাঁচতে হবে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মে ওয়েস্ট");
        hashMap.put("texDes","“তুমি এক বারই বাঁচবে কিন্তু যদি ঠিকভাবে বাচোঁ, এক বারই যথেষ্ট।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মাইকেল জর্ডন");
        hashMap.put("texDes","“আমি আমার জীবনে বারবার ব্যর্থ হয়েছি এবং সে কারণেই আমি সফল হই।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মার্টিন লুথার কিং, জেআর.");
        hashMap.put("texDes","“জীবনের সবচেয়ে অবিরাম এবং জরুরী প্রশ্নটি, আপনি অন্যের জন্য কী করছেন?”");
        arrayList.add(hashMap);

//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মরিস ওয়েস্ট");
        hashMap.put("texDes","“আপনি যদি ঝড়ের জন্য অপেক্ষা করে আপনার পুরো জীবন ব্যয় করেন তবে আপনি কখনই রোদ উপভোগ করবেন না।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– সোরেন কিয়েরকেগার্ড");
        hashMap.put("texDes","“জীবন সমস্যা সমাধানের নয়, অভিজ্ঞতার বাস্তবতা।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– আব্রাহাম লিংকন ");
        hashMap.put("texDes","“শেষ পর্যন্ত, আপনার জীবনের কয়েক বছর নয়। আপনার বছরে জীবন কতটা সেটাই গুরুত্বপূর্ণ।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– হেলেন কেলের");
        hashMap.put("texDes","“জীবন হয় একটি সাহসী দু: সাহসিক কাজ বা কিছুই না।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– কেভিন ক্রুস");
        hashMap.put("texDes","“জীবন একটি প্রভাব তৈরি করা, আয় করা না।”");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– আনাইস নিন");
        hashMap.put("texDes","“একজনের সাহসের অনুপাতে জীবন সঙ্কুচিত বা প্রসারিত হয়।”");
        arrayList.add(hashMap);

//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– ফ্রাঙ্ক লয়েড রাইট");
        hashMap.put("texDes","“আমি যত বেশি বেঁচে থাকি ততই সুন্দর জীবন হয়ে ওঠে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– ডেভিড ডি নোটারিস");
        hashMap.put("texDes", "“আপনি জানেন, জীবন একটি প্রতিধ্বনি; আমরা যা দিই তা পাই।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– বুদ্ধ");
        hashMap.put("texDes","“আপনি যেখানে থাকুন; অন্যথায় আপনি আপনার জীবন হারিয়ে ফেলবেন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– অ্যাস্টন কুচার");
        hashMap.put("texDes","“জীবন যা দেয় তার জন্য নিষ্পত্তি করবেন না; জীবনকে আরও উন্নত করুন এবং কিছু তৈরি করুন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– ফরেস্ট গাম্প");
        hashMap.put("texDes","“আমার মা সর্বদা বলতেন, জীবন চকোলেটের বক্সের মতো। আপনি কী পেতে যাবেন তা আপনি কখনই জানেন না।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– অস্কার ওয়াইল্ড");
        hashMap.put("texDes","“জীবন কখনই নায্য হয় না এবং সম্ভবত আমাদের বেশিরভাগের পক্ষে এটি ভাল জিনিস যা এটি তা নয়।”");
        arrayList.add(hashMap);

//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মাদার টেরেসা ");
        hashMap.put("texDes", "“এই জীবনে আমরা দুর্দান্ত কিছু করতে পারি না। আমরা কেবল মহান ভালবাসা দিয়ে ছোট ছোট জিনিসই করতে পারি।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– লিলিয়ান ডিকসন");
        hashMap.put("texDes","“জীবন মুদ্রার মতো। আপনি এটি আপনার যে কোনও উপায়ে ব্যয় করতে পারেন তবে আপনি কেবল এটি একবার ব্যয় করতে পারবেন।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– জেনিফার অ্যানিস্টন");
        hashMap.put("texDes","“জীবনে কোন অনুশোচনা নেই, কেবল পাঠ।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– রিক ওয়ারেন");
        hashMap.put("texDes","“আপনার জীবনের সাথে আপনি তিনটি জিনিস করতে পারেন: আপনি এটি নষ্ট করতে পারেন, আপনি এটি ব্যয় করতে পারেন, বা আপনি এটি বিনিয়োগ করতে পারেন। আপনার জীবনের সর্বোত্তম ব্যবহার হল এটিকে এমন কিছুতে বিনিয়োগ করা যা পৃথিবীতে আপনার সময়ের চেয়ে দীর্ঘস্থায়ী হয়।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– আলবার্ট আইনস্টাইন ");
        hashMap.put("texDes","“অন্যের জন্য বেঁচে থাকা জীবনই সার্থক জীবন।”");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– ড্রু ব্যারিমোর");
        hashMap.put("texDes","“জীবনটি খুব আকর্ষণীয়  শেষ অবধি, আপনার সবচেয়ে বড় যন্ত্রণার মধ্যে কিছু আপনার সবচেয়ে বড় শক্তি হয়ে ওঠে।”");
        arrayList.add(hashMap);

//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– জন ওয়েইন");
        hashMap.put("texDes","“জীবন কঠিন, আপনি যখন বোকা হন তখন তা আরও কঠিন হয়।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– ওয়ার্ডসওয়ার্থ ");
        hashMap.put("texDes","“একজন ভাল মানুষের জীবনের সেরা অংশটি হল তার সামান্য নামহীন, দয়ালু এবং প্রেমের অবিচ্ছিন্ন আচরণ।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– স্বামী বিবেকানন্দ ");
        hashMap.put("texDes","“একটি ধারণা গ্রহণ করুন। সেই ধারণাটিকে আপনার জীবন তৈরি করুন – এটি ভাবুন, এটির স্বপ্ন দেখুন, সেই ধারণার উপরে বাস করুন। মস্তিষ্ক, পেশী, স্নায়ু, আপনার দেহের প্রতিটি অঙ্গকে সেই ধারণায় পূর্ণ হতে দিন এবং প্রতিটি অন্যান্য ধারণাটি একা ছেড়ে দিন। এটিই সাফল্যের পথ।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– আমার খায়্যাম ");
        hashMap.put("texDes","“এই মুহুর্তের জন্য আনন্দিত হন। এই মুহুর্তটি আপনার জীবন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– কনফুসিয়াস ");
        hashMap.put("texDes","“জীবন সত্যই সহজ, তবে আমরা এটিকে জটিল করার জন্য জোর দিয়েদি।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– দীপক চোপড়া ");
        hashMap.put("texDes","“জীবনের স্বাস্থ্যকর প্রতিক্রিয়া হল আনন্দ।”");
        arrayList.add(hashMap);

//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– জন লেনন");
        hashMap.put("texDes","“জীবন, আপনি যখন অন্য পরিকল্পনা তৈরি করতে ব্যস্ত হন তখন ঘটে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– অপরাহ উইনফ্রে ");
        hashMap.put("texDes","“আপনি জীবনে যা আছে তা যদি দেখেন তবে আপনার কাছে সবসময় বেশি থাকবে। আপনার জীবনে যা নেই তা যদি আপনি তাকান তবে আপনার কখনই পর্যাপ্ত পরিমাণ থাকবে না।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– জেমস এম. ব্যারি");
        hashMap.put("texDes","“জীবন নম্রতার দীর্ঘ পাঠ।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– সক্রেটিস ");
        hashMap.put("texDes","“একটি অপরীক্ষিত জীবন, যাপন করার যোগ্য নয়।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– সন্দীপ মহেশ্বরী");
        hashMap.put("texDes","“যে মুহুর্তে আপনি নিজের মূল্য দেওয়া শুরু করবেন, বিশ্ব আপনাকে মূল্য দিতে শুরু করবে।”");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– রবার্ট ফ্রস্ট ");
        hashMap.put("texDes","“তিন কথায় আমি জীবন সম্পর্কে যা শিখেছি তার সবগুলি সংক্ষেপে বলতে পারি: এটি চলতে থাকে”");
        arrayList.add(hashMap);

//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– ভিক্টর হুগো ");
        hashMap.put("texDes","“জীবন এমন একটি ফুল যার ভালোবাসা হল মধু।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– চার্লস স্বীন্ডল");
        hashMap.put("texDes","“আমার কাছে যা ঘটে তা জীবনেই ১০% এবং আমি এর প্রতি কীভাবে প্রতিক্রিয়া করি তা ৯০%।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– জন ম্যাকলিড");
        hashMap.put("texDes","“আমি বিশ্বাস করি যে জীবনের কোনো জিনিসই গুরুত্বহীন নয়, প্রতিটি মুহুর্তই একটি আরম্ভ হতে পারে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– বিবেক বিন্দ্রা");
        hashMap.put("texDes","“আপনি যদি সেরা হওয়ার চেষ্টা করেন তবে আপনি এক নম্বরে থাকবেন, যদি আপনি অনন্য হওয়ার চেষ্টা করেন তবে আপনিই একমাত্র হবেন!”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মায়া অ্যাঞ্জেলু");
        hashMap.put("texDes","“জীবনে আমার লক্ষ্য কেবল বেঁচে থাকার নয়, বরং সাফল্য অর্জনের জন্য; এবং কিছু আবেগ, কিছু মমতা, কিছু হাস্যরস এবং কিছু স্টাইল দিয়ে এটি করা।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– জন এফ. কেনেডি");
        hashMap.put("texDes","“জীবন কখনও সহজ হয় না। সেখানে কাজ করার এবং বাধ্যবাধকতাগুলি পূরণ করতে হয় – সত্যের, ন্যায়বিচারের এবং স্বাধীনতার দায়বদ্ধতাগুলি।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– জোয়ান রিভার্স ");
        hashMap.put("texDes","“আমি যখন যা জীবনে ঘটে তা উপভোগ করি। এটি ভাল জিনিস বা খারাপ জিনিস কি না তা আমি পাত্তা দিই না। এর অর্থ আপনি জীবিত আছেন।”");
        arrayList.add(hashMap);

//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“বৃষ্টির ঝাপটা শেখায় যে জীবনের সবচেয়ে সুন্দর মুহূর্তগুলোকে বন্দী করা যায় না, শুধু উপভোগ করা যায়।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আমি জানি আমি অজানার পথে যাচ্ছি… কিন্তু আমি মনের মধ্যে পথ উপভোগ করতে চারি নি”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“যদিও আয়নার দাম হীরার চেয়েও কম, সবাই হীরার গয়না পরে আয়নাই খোঁজে।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“হৃদয়ের দরজা সেখানেই খোলে যেখানে অনুভূতিগুলো এক হয়ে দাঁড়ায়, ছেড়ে দেওয়া সহজ, ধরে রাখাই কঠিন”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“জীবনে কখনো কিছু খারাপ লাগলেও শান্ত থাকুন কারণ কান্নার পরে হাসির মজাই আলাদা!”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“যেকোন বইয়ে উত্তর খুঁজুন না কেন, জীবন প্রতিদিনই বিষয়ের বাইরে প্রশ্ন করবে!!”");
        arrayList.add(hashMap);

//

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "“একটা কথা মনে রাখবেন, জীবনের চারদিনের মধ্যে মাত্র দুই দিন বেঁচে থাকলেও তা পরিপূর্ণভাবে বাঁচুন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“জীবনের প্রতিটি সকাল শুরু হয় নতুন নিয়ম দিয়ে এবং প্রতিটি সন্ধ্যা শেষ হয় নতুন অভিজ্ঞতা নিয়ে।“");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আপনার মুঠোয় বন্ধ থাকা সুখ ভাগ করুন, আপনার হোক বা অন্যের… হাতের তালু একদিন খালি হয়েই যাবে“");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আপনার মুঠোয় বন্ধ থাকা সুখ ভাগ করুন, আপনার হোক বা অন্যের… হাতের তালু একদিন খালি হয়েই যাবে“");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "“জীবনে লিফট ভাগ্যের সমান কিন্তু সিঁড়ি পরিশ্রমের সমান। লিফট মাঝে মাঝে আটকে যেতে পারে কিন্তু সিঁড়ি আপনাকে সবসময় উপরে নিয়ে যাবে“");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“ভালো জীবন-যাপনের জন্য এটা মেনে নিতে হবে যে আমাদের যা আছে সেটাই সবচেয়ে সেরা।“");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“একটি সফল জীবনের দুটি মন্ত্র – ধৈর্যের সাথে তাড়াহুড়ো এবং শান্ত হয়ে সাথে রাগ \uD83D\uDE42 “");
        arrayList.add(hashMap);

//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– হুমায়ূন আহমেদ");
        hashMap.put("texDes","জীবন সহজ নয়, জটিলও নয়, জীবন জীবনের মতো। আমরাই একে সহজ করি জটিল করি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- এইচ আর এস");
        hashMap.put("texDes","মানুষের জীবনে দুইটা সময় থাকে, একটা হচ্ছে মূল্যবান আরেকটা হচ্ছে মূল্যহীন। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— হুমায়ুন আহমেদ");
        hashMap.put("texDes","বইয়ের পাতা মানুষকে যা শিক্ষা দেয়, জীবনের পাতা তার থেকে অনেক বেশি, কিছু শিখিয়ে দিয়ে যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– স্টিফেন হকিং");
        hashMap.put("texDes","জীবনে যার কাছে থেকে তুমি ভালোবাসা পাবে তাকে তুমি ছুড়ে ফেল না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ব্রায়ান ডাইসন");
        hashMap.put("texDes","স্বপ্নপূরণই জীবনের একমাত্র লক্ষ্য নয়, তাই বলে স্বপ্নকে ত্যাগ করে নয় , তাকে সঙ্গে নিয়ে চলো , স্বপ্ন ছাড়া জীবন অর্থহীন ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— চার্লস সুইন্ডল");
        hashMap.put("texDes","জীবনের দশ শতাংশ যা আপনার সাথে ঘটে এবং নব্বই শতাংশ আপনি কীভাবে প্রতিক্রিয়া করেন।");
        arrayList.add(hashMap);

//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- উইনস্টন এস চার্চিল");
        hashMap.put("texDes","সাফল্য চূড়ান্ত নয়; ব্যর্থতা মারাত্মক নয়: এটি চালিয়ে যাওয়ার সাহসই গুরুত্বপূর্ণ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," – চার্লি চ্যাপলিন");
        hashMap.put("texDes"," “জীবন হতে পারে চমৎকার, যদি আপনি একে ভয় না পান। এজন্য প্রয়োজন সাহস, কল্পনা শক্তি ও অল্প কিছু টাকাকড়ি।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– স্টিফেন হকিং");
        hashMap.put("texDes"," “জীবন যেমনই কঠিন হোক না কেন, অবশ্যই এমন কিছু আছে যা তুমি করতে পারবে এবং সে কাজে তুমি সফল হবে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– চার্লি চ্যাপলিন");
        hashMap.put("texDes"," “জীবন বিস্ময়কর হতে পারে, যদি মানুষ একা আপনাকে ছেড়ে দেয়।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," – হুমায়ূন আহমেদ");
        hashMap.put("texDes","“জীবনে কিছু কিছু প্রশ্ন থাকে যার উত্তর কখনও মেলে না, কিছু কিছু ভুল থাকে যা শোধরানো যায় না, আর কিছু কিছু কষ্ট থাকে যা কাউকে বলা যায় না।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," – স্যামুয়েল জনসন");
        hashMap.put("texDes"," “জীবন ক্ষণস্থায়ী, কাজেই উপার্জনের পাশাপাশি তা ভোগ করে যাওয়া উচিত।”");
        arrayList.add(hashMap);

//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– হুইটিয়ার");
        hashMap.put("texDes","“জীবনের প্রতিটি সিঁড়িতে পা রেখে ওপরে ওঠা উচিত। ডিঙ্গিয়ে উঠলে পড়ে যাবার সম্ভাবনা বেশি।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– এডওয়ার্ড ইয়ং");
        hashMap.put("texDes"," “দুঃখ-কষ্ট নিয়েই মানুষের জীবন, কিন্তু দুঃখের পর সুখ আসবে, এটাই ধ্রুব সত্য।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“জীবনটা তখনই সুন্দর হয় যখন একটা সুন্দর মনের মানুষ জীবন সঙ্গী হয়।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","নদীগুলোতে স্রোত থাকে, তাই নদী বেগবান হয়। একইভাবে আমাদের জীবনে দন্দ্ব আছে বলেই জীবন বৈচিত্রময়।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তারা কি ভাববে? পৃথিবী কি ভাববে? কেউ যদি এর ঊর্ধ্বে উঠে ভাবতে পারে, তাহলে জীবন হয়ে উঠবে শান্তির অপর নাম!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনি যদি জীবনে সম্মান পেতে চান, তাহলে সবার আগে আপনাকে অন্যকে সম্মান করতে শিখতে হবে!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনার ভেতরের শিশু-সুলভতা কে সবসময় বাঁচিয়ে রাখুন! কারণ বেশি বোঝাপড়া জীবনকে বিরক্তিকর করে তোলে।");
        arrayList.add(hashMap);
//

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes"," ধীরে ধীরে প্রতিটি ইচ্ছা পূরণ হতে শুরু করলে, জীবনকে সুন্দর মনে হয়!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমাদের জীবনকে জয় করতে শিখতে হবে! কারণ একদিন আমরা.. মৃত্যুর কাছে পরাজিত হবো..!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes"," জীবনে ভালো থেকো, কিন্তু প্রমাণ করার চেষ্টা করো না!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— রাবীন্দ্রনাথ ঠাকুর ");
        hashMap.put("texDes","যত্ন নেওয়া যায় না বলে কোনও কাজ আগে করা হয় না। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— হেলেন কেলার ");
        hashMap.put("texDes","জীবনে একটি নতুন দৃষ্টিভঙ্গি পেতে আপনাকে আপনার শীর্ষস্থান থেকে উঠতে হতে পারে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— লেন মিরেন ");
        hashMap.put("texDes","জীবন তোমাকে তোমার পর্যাপ্ত আপাততা প্রদান করবে, যতটুকু তুমি তার দিকে গুরুত্ব দেবে। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— স্টিভ জবস");
        hashMap.put("texDes","আপনি যদি আপনার কাজকর্ম প্রেম করেন, তাহলে আপনি যে কোনও মুল্য অদান করতে প্রস্তুত হবেন।");
        arrayList.add(hashMap);

//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— হেলেন কেলার");
        hashMap.put("texDes","আপনি আপনার সার্কাস্টিক অবস্থায় বিজয়ী হতে পারবেন না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মহাত্মা গান্ধী");
        hashMap.put("texDes","আপনি নিজেকে পরিবর্তন হতে দেখতে চান, তাহলে দুয়ারের দিকে দেখবেন, কারণ আপনি যা দেখতে চান, তা আপনি হতে দেবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","পরিবর্তন নতুন সূর্যাস্তের মতো, আসে এবং সম্পূর্ণ দৃশ্যটি পরিবর্তন করে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবন পরিবর্তনের অবসান নেই, শুধু আমরা সম্পর্কিত সাহস সংগ্রহণ করতে হবে।");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-ডেমিক্রিটাস");
        hashMap.put("texDes","অপরের দোষ অপেক্ষা নিজের দোষ যাচাই করা উত্তম। ");
        arrayList.add(hashMap);

//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মুহাম্মদ আলী ক্লে");
        hashMap.put("texDes"," প্রশিক্ষণের প্রতিটি মিনিটকেই আমি ঘৃণা করেছি, কিন্তু নিজেকে বলেছি, ‘এখন কষ্ট করো’ সারাজীবন চ্যাম্পিয়ন হয়ে কাটাতে পারবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– এ. পি. জে. আবুল কালাম");
        hashMap.put("texDes","স্বপ্ন সেটা নয়, যেটা মানুষ ঘুমিয়ে ঘুমিয়ে দেখে স্বপ্ন সেটাই যেটা পূরণের প্রত্যাশা মানুষকে ঘুমাতে দেয় না। ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সন্দীপ মহেশ্বরী");
        hashMap.put("texDes","যেই কর্ম আপনাকে ভিতর থেকে শক্তিশালী করে তোলে তা হল একটা ভালো কর্ম। কিন্তু যেই কর্ম আপনাকে ভিতর থেকে দুর্বল করে তোলে সেটা একটা খারাপ কর্ম।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- বায়রন");
        hashMap.put("texDes","সেই যথার্থ মানুষ যে জীবনের পরিবর্তন দেখেছে এবং পরিবর্তনের সাথে নিজেও পরিবর্তিত হয়েছে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","ফিলিপ স্ট্যানহোপ");
        hashMap.put("texDes","সময়ের সত্যিকার মূল্য দাও। প্রতিটি মূহুর্তকে দখল করো, উপভোগ করো। আলস্য করো না। যে কাজ আজ করতে পারো, তা কালকের জন্য ফেলে রেখো না।");
        arrayList.add(hashMap);


//
        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","জোহান গথে");
        hashMap.put("texDes","তোমার স্বপ্ন যেটাই হোক না কেন, কাজ শুরু করো। একাগ্র ভাবে কাজ করার মাঝে দারুন এক জাদুকরী শক্তি আছে।");
        arrayList.add(hashMap);


    }
}