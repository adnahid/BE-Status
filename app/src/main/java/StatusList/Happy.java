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

public class Happy extends AppCompatActivity {

    RecyclerView happyRecyclerView;
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_happy);
        happyRecyclerView = findViewById(R.id.happyRecyclerView);
        toolbar = findViewById(R.id.toolbar);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Happy.this, MainActivity.class);
                startActivity(intent);
            }
        });

        happyTable();
        finalArrayListTable();

        HappyAdapter happyAdapter = new HappyAdapter();
        happyRecyclerView.setAdapter(happyAdapter);
        happyRecyclerView.setLayoutManager(new LinearLayoutManager(Happy.this));

    }

    private class HappyAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(Happy.this, "কপি", Toast.LENGTH_SHORT).show();
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
                AdLoader adLoader = new AdLoader.Builder(Happy.this, "ca-app-pub-3940256099942544/2247696110")
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
    private void happyTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখ হলে সব অসুখ ছুটে যায়।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"জীবনটি হোক সুখের রংবৈচিত্রে চলা।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"আমাদের জীবনটির সত্য মূল্য হলো সুখ।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes",  "স্বাস্থ্যের চেয়ে বড় সম্পদ এবং অল্পেতেই তুষ্টি পাওয়ার চাইতে বড় সুখ আর কিছু নেই ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখ পাওয়ার জন্য আমাদের কাছে অনেক ছোট ছোট মৌলিক উপায় রয়েছে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সুখ একটি অমূল্য সম্পত্তি, যা ধন-সম্পত্তির মধ্যে পাওয়া যায় না। সত্যিই সুখ হলো মনের সুখ, যা আমাদের জীবনের অমূল্য অংশ। বিখ্যাত লোকদের প্রশংসা করা ছাড়াও, তাদের অনুসরণ করা সুখ আমাদের জীবনে আনতে পারে। অসহায় দরিদ্রদের যে ভালবাসে, সেই তো জীবনে প্রকৃত সুখী মানুষ। সুখ হলো মানুষের জীবনের সবচেয়ে বড় পাওয়া, যা সবার ভাগ্যেই থাকে না !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","দুঃখ কষ্ট শার্টের ধুলাবালির মতো ঝেড়ে ফেলে দিতে পারলেই আপনি সুখী মানুষ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes",  "জ্ঞানী লোকেরা কখনই সুখের সন্ধান করে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনি যদি অন্যের সুখের কারণ হতে পারেন, তবে আপনি নিজেও সুখী হবে । অন্যের দুঃখের কারণ হলে দুঃখও দল বেঁধে আপনার জীবনে আসবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "মানুষ যতটুকু সুখী হতে চায়, সে ততটুকুই সুখী হতে পারে। সুখের কোনোও পরিসীমা হয় না, আমরা ইচ্ছে করলেই সুখকে আকাশ অভিসারী করে তুলতে পারি ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনে সুখের তীব্র আকাঙ্ক্ষাই হল তারুণ্য ধরে রাখার মূল রহস্য।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সুখ কখনই ভবিষ্যতের জন্য জমিয়ে রেখে দেওয়ার মত বিষয় নয়, বরং এটি হল বর্তমানে অনুভব করার সুযোগ।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","কোনো মানুষের জীবনে সুখের সবচেয়ে বড় শত্রু হল তার সরলতা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি সর্বদাই নিজেকে সুখী ভাবি, কারণ আমি কখনও কারো কাছে কোনো প্রত্যাশা রাখি না, কারো কাছে কিছু প্রত্যাশা করাটাই বেশির ভাগ সময় দুঃখের কারণ হয়ে দাঁড়ায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","এই পৃথিবীতে একজন সুখী মানুষ সাদা কাকের মতই দুর্লভ ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","দুনিয়াতে তারাই সুখী হয় যারা কোনো প্রতিবাদ ছাড়া নিন্দা শোনে এবং সেই অনুসারে নিজেদের সংশোধন করতে পারে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","পৃথিবীতে সুখ এবং দুঃখ সব সময় সমান সমান থাকবে। কেউ একসময় চরম আনন্দ পেলে, তাকে চরম দুঃখও পেতেই হবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes",  "সবাই তো সুখী হতে চাই , তবু কেউ সুখী হয়, কেউ হয়না। জানিনা বলে যা লোকে সত্যি কিনা? কপালে সবার নাকি সুখ সয় না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— বার্নার্ড ডি ফন্টেনেল");
        hashMap.put("texDes","“অত্যধিক সুখ আশা করা সুখের একটি বড় বাধা।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— নেভাল রবিকান্ত");
        hashMap.put("texDes"," “সুখ শান্তি থেকে আসে। শান্তি উদাসীনতা থেকে আসে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মহাত্মা গান্ধী");
        hashMap.put("texDes","“সুখ তখনই হয় যখন আপনি যা ভাবেন, আপনি কী বলেন এবং যা করেন তা সামঞ্জস্যভাবে হয়।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— স্টিভ মারাবোলি");
        hashMap.put("texDes","“কাঁদুন। ক্ষমা করুন। শিখুন। এগিয়ে যান। আপনার অশ্রুগুলিকে আপনার ভবিষ্যতের সুখের বীজকে জল দিতে দিন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— জোসেফ অ্যাডিসন");
        hashMap.put("texDes","“এই জীবনে সুখের জন্য তিনটি দুর্দান্ত প্রয়োজনীয়তা হল কিছু করা, কিছু ভালবাসা এবং কিছু আশা করা।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– চার্লস ডিকেন্স");
        hashMap.put("texDes","“সুখ একটি উপহার এবং কৌশলটি হল এটি প্রত্যাশা করা নয়, এটি যখন আসে তখন এতে আনন্দিত হওয়া।”");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– ফ্র্যাঙ্ক টাইগার");
        hashMap.put("texDes","“আপনি কি চান তা করছেন তা হল স্বাধীনতা। আপনি যা করেন তা পছন্দ করা হলো সুখ।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– উইলিয়াম জেমস");
        hashMap.put("texDes","“ক্রিয়া হয়তো সর্বদা সুখ বয়ে আনতে পারে না, তবে কর্ম ছাড়া সুখ নেই।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– ওয়াল্ট হুইটম্যান ");
        hashMap.put("texDes", "“সুখ, অন্য জায়গায় নয় তবে এই জায়গা… অন্য এক ঘন্টার জন্য নয়, এই ঘন্টাটি।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– থমাস জেফারসন");
        hashMap.put("texDes","“আমাদের সর্বাধিক সুখ জীবনের এমন পরিস্থিতির উপর নির্ভর করে না যে সুযোগ আমাদেরকে দিয়েছে, তবে সর্বদা একটি ভাল বিবেক, সুস্বাস্থ্য, পেশা এবং সমস্ত ন্যায্য অনুসরণে স্বাধীনতার ফলাফল।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মার্কাস অরেলিয়াস");
        hashMap.put("texDes","“আপনার জীবনের সুখ আপনার চিন্তার মানের উপর নির্ভর করে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– ডেনিস ওয়েটলি");
        hashMap.put("texDes","“সুখ ভ্রমণ করা যায় না, মালিকানাধীন, উপার্জন, পরা বা খাওয়া যায় না। সুখ হল প্রতি মিনিটে প্রেম, অনুগ্রহ এবং কৃতজ্ঞতার সাথে বেঁচে থাকার আধ্যাত্মিক অভিজ্ঞতা।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– দিয়েগো ভাল");
        hashMap.put("texDes","“আমি মনে করি জীবনের মূল চাবিকাঠি কেবল একটি সুখী ব্যক্তি হওয়া, এবং সুখ আপনাকে সাফল্য এনে দেবে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– আর্ল নাইটিংগেল");
        hashMap.put("texDes","“আপনার জীবনের প্রতিটি মিনিট উপভোগ করতে শিখুন। এখন খুশি হন। ভবিষ্যতে আপনাকে খুশী করার জন্য নিজের বাইরে কোনও কিছুর জন্য অপেক্ষা করবেন না।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– ফ্রেয়া স্টার্ক");
        hashMap.put("texDes","“আমরা যে জিনিসগুলিতে বিশ্বাস করি সেগুলি আমাদের কাজগুলির থেকে পৃথক হলে কোনও সুখ থাকতে পারে না।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– ফ্র্যাঙ্কলিন ডি রুজভেল্ট");
        hashMap.put("texDes","“সুখ অর্জনের আনন্দ এবং সৃজনশীল প্রচেষ্টার রোমাঞ্চে নিহিত।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– জেমস ল্যান্ডাল বাসফোর্ড");
        hashMap.put("texDes","“আসল সুখ অস্থায়ী উপভোগ নয়, তবে ভবিষ্যতের সাথে এমনভাবে জড়িত যে এটি চিরকাল আশীর্বাদ করে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"প্রতিটি সুখের ক্ষণই জীবনটির অমূল্য ক্ষণ।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– গুইলুম অ্যাপোলিনায়ার");
        hashMap.put("texDes","“এখন এবং তারপরে আমাদের সুখের সাধনাতে বিরতি দেওয়া এবং কেবল খুশি হওয়া ভাল।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– অনার ডি বালজ্যাক");
        hashMap.put("texDes",  "“সমস্ত সুখ সাহস এবং কাজের উপর নির্ভর করে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– গ্রেটা ব্রুকার পামার");
        hashMap.put("texDes","“সুখ অন্য কাউকে খুশি করার প্রচেষ্টার একটি উপ-পণ্য।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– দালাই লামা");
        hashMap.put("texDes", "“সুখ তৈরির মতো কিছু নয়। এটা আপনার নিজের কর্ম থেকে আসে।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– উইলিয়াম মরিস");
        hashMap.put("texDes", "“সুখের আসল রহস্যটি দৈনন্দিন জীবনের সমস্ত বিবরণে সত্যিকারের আগ্রহ গ্রহণের মধ্যে রয়েছে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মাইল্ডার্ড বারথেল");
        hashMap.put("texDes","সুখ একটি সচেতন পছন্দ, স্বয়ংক্রিয় প্রতিক্রিয়া নয়।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– আইন র্যান্ড");
        hashMap.put("texDes","“নিজেকে মূল্য দিতে শিখুন যার অর্থ: আপনার সুখের জন্য লড়াই করুন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– লেসলি কারন");
        hashMap.put("texDes","“দুর্দান্ত সুখ পেতে হলে আপনাকে প্রচন্ড ব্যথা এবং অসুখী হতে হবে – অন্যথায় আপনি যখন খুশি হবেন তখন কীভাবে জানবেন?”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– রবার্ট এ হেইনলাইন");
        hashMap.put("texDes","“প্রেম সেই শর্তটি যেখানে অন্য ব্যক্তির সুখ আপনার নিজের জন্য অপরিহার্য।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– চীনা প্রবাদ");
        hashMap.put("texDes","“আপনি যদি এক ঘন্টা সুখ চান তবে একটি ঝাঁকুনি নিন। আপনি যদি এক দিনের জন্য সুখ চান, মাছ ধরতে যান। আপনি যদি এক বছরের জন্য সুখ চান, একটি ভাগ্যের উত্তরাধিকারী হন। যদি আপনি আজীবন সুখ চান তবে অন্য কারও সাহায্য করুন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখ পেতে হলে প্রথমে অপেক্ষা ভাঙতে হয়।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"শান্তির মাঝেই সবচেয়ে বড় সুখ অবস্থান করে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখ পেতে ব্যক্তির হৃদয়কে খোলা রাখা গুরুত্বপূর্ণ।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখের খোঁজে যাওয়া হলে, তা আপনার আত্মা থেকেই শুরু হতে হবে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Albert Einstein");
        hashMap.put("texDes","আপনি যদি সুখী জীবনযাপন করতে চান তবে এটিকে একটি লক্ষ্যের সাথে বেঁধে রাখুন, মানুষ বা জিনিসের সাথে নয়!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Woody Allen");
        hashMap.put("texDes","খুশি হওয়ার প্রতিভা হল, তোমার যা নেই তার পরিবর্তে যা আছে, তার প্রশংসা করা!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Maharishi Mahesh Yogi");
        hashMap.put("texDes","সুখ একটি ফুলের সুবাসের মতো বিকিরণ করে এবং সমস্ত ভালো জিনিসকে আপনার দিকে টানে !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Denis Waitley");
        hashMap.put("texDes","সুখ হল ভালবাসা, করুণা এবং কৃতজ্ঞতার সাথে প্রতি মিনিটে বেঁচে থাকার আধ্যাত্মিক অভিজ্ঞতা !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","George Sand");
        hashMap.put("texDes","এই জীবনে একটাই সুখ, ভালোবাসা !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Maharishi Mahesh Yogi");
        hashMap.put("texDes","সুখ একটি ফুলের সুবাসের মতো বিকিরণ করে এবং সমস্ত ভালো জিনিসকে আপনার দিকে টানে !!");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Buddha");
        hashMap.put("texDes","একটি মোমবাতি থেকে হাজার হাজার মোমবাতি জ্বালানো যায়, এবং মোমবাতির জীবন ছোট হবে না। সুখ ভাগাভাগি করলে কখনো কমে না !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সুখ কখনও কিনতে পাওয়া যায় না বা কারো থেকে তা ধার নেয়া যায় না। সুখ হল মানুষের ভিতরের একটি ব্যাপার, সুখী হতে গেলে বেশী কিছু না, শুধু একটা সুখী মন হলেই চলে ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "সুখ সুখ করে কেঁদোনা আর, যতই কাঁদিবে ততোই বাড়িবে হৃদয় ভার।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","এই দুনিয়াতো সুখেরই দুনিয়া, কিন্তু সেই সুখ কে সবাই খুঁজে নিতে জানেনা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সুখের জন্যই বাঁচে কেউ, আবার কেউ কেউ সুখের লোভেই মরে যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সুখ? সে তো এক অলীক বস্তু।তার দেখা কি-এতো সহজে আর মেলে? সবাই তো আছে সুখের খোঁজে, নিজের সকল কাজ ফেলে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সুখ, নিতান্তই আপেক্ষিক একটি ব্যাপার। কারো কাছে যে ব্যাপারটা সুখের, অন্য কারো কাছে সেটাই আবার দুঃখের হতে পারে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তুমি যদি দুঃখের আগুনে না পোড়ো; তবে সুখও খুঁজে পাবে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তারাই এই দুনিয়ার সবচেয়ে বেশি সুখী মানুষ হয়, যারা অল্পতেই নিজের সুখ খুঁজে নেয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Dodinsky");
        hashMap.put("texDes","সুখী হওয়ার চাবিকাঠি হল আপনার কাছে কী গ্রহণ করবেন এবং কী ছেড়ে দেবেন তা বেছে নেওয়ার ক্ষমতা রয়েছে আপনার হাতে !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","দুটি জিনিস আমাদের সুখ থেকে বাধা দেয়; অতীতে বাস করা এবং অন্যদের পর্যবেক্ষণ করা !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Dalai Lama ");
        hashMap.put("texDes","সুখ হল স্বাস্থ্যের সর্বোচ্চ রূপ !!");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সুখ কোন গন্তব্য নয়, সুখ একটি যাত্রা। তাই যাত্রার প্রতিটি পদক্ষেপ উপভোগ করুন!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সুখ হল আপনার যা আছে তাতে সন্তুষ্ট থাকা এবং চিন্তামুক্ত জীবনযাপন করা !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","কৃতিত্বের উত্তেজনা এবং সৃজনশীল প্রচেষ্টার রোমাঞ্চের মধ্যেই প্রকৃত সুখ নিহিত !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনি জীবনে কতটা সুখী সেটা গুরুত্বপূর্ণ নয়, বরং আপনার কারণে কতজন খুশি তা গুরুত্বপূর্ণ!!");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","গল্প উপন্যাসে থাকা নায়ক-নায়িকাদের সুখ-দুঃখ নিয়ে যারা কাতর হয়ে পরে, তারা সাধারণত নিজেদের সুখ দুঃখের ব্যাপারে অনেকটাই উদাসীন হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখের গোধূলি পাওয়ার জন্য আমাদের সাথে থাকা গুরুত্বপূর্ণ।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখ একটি আত্মিক অবস্থা, তার জন্য মানব হৃদয়টি সর্বোত্তম রকমে তৈরি।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখের পথে চলতে হলে হাসতে শেখা গুরুত্বপূর্ণ।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"জীবনটি একটি উপহার, সুখ এবং প্রতিকূলতা এর মধ্যে স্তর রখতে হবে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখের শেখা হলে জীবন হয়ে উঠে একটি সুপ্রাণ যাত্রা।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখের দিকে তাকার জন্য আমাদের চোখের দৃষ্টি পরিবর্তন করতে হবে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখ হলে জীবন একটি মিষ্টি স্বপ্ন।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Helen Keller");
        hashMap.put("texDes","সুখের একটি দরজা বন্ধ হলে আরেকটি খুলে যায়; কিন্তু প্রায়ই আমরা বন্ধ দরজার দিকে তাকিয়ে থাকি, কিন্তু আমাদের জন্য যেটি খোলা হয়েছে তা আমরা দেখতে পাই না !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সুখ মাইল দূরে নয়; এটা আপনার পাশে আছে, এটা উপলব্ধি করার জন্য আপনাকে আঁকড়ে আছে!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","দুঃখের ভাগীদার কেউ হতে চায় না, তবে সুখের ভাগীদার হতে চায় !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","কারণ ছাড়া হাসি প্রকৃত সুখের আবেগপূর্ণ অভিব্যক্তি !!");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Thucydides ");
        hashMap.put("texDes", "সুখের রহস্য হল স্বাধীনতা আর স্বাধীনতার রহস্য হল সাহস !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Sydney J. Harris");
        hashMap.put("texDes","সুখ একটি দিক, স্থান নয় !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Lucius Annaeus Seneca");
        hashMap.put("texDes","প্রকৃত সুখ হল ভবিষ্যত নিয়ে চিন্তা ছাড়াই বর্তমানকে উপভোগ করা !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখের অপেক্ষা অসুখ বেশি সহজ, কিন্তু সুখ হতে হলে কঠিনতা মোকাবিলা করতে হবে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখ আসে তখন, যখন আমরা আত্মা সাক্ষাত্কার করতে পারি।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখ হলে সবকিছু সুন্দর হয়ে যায়, আত্মা স্বচ্ছ হয়ে উঠে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখের রহস্য হলো, তা অন্যের সাথে ভাগ করলে বাড়ে এবং আত্মা সন্তুষ্ট হলে বৃদ্ধি পায়।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখের রহস্য হলো, এটি অন্যের সাথে ভাগ করতে পারলেই বাড়তি হয়।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Rita Mae Brown");
        hashMap.put("texDes","সুখের চাবিকাঠিগুলির মধ্যে একটি হল খারাপ স্মৃতি!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","Agnes Repplier");
        hashMap.put("texDes", "নিজের মধ্যে সুখ খুঁজে পাওয়া সহজ নয় আবার অন্য কোথাও পাওয়াও সম্ভব নয় !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Thomas Merton");
        hashMap.put("texDes","সুখ তীব্রতার বিষয় নয় বরং ভারসাম্য, শৃঙ্খলা, ছন্দ এবং সাদৃশ্যের বিষয় !!");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","George Orwell");
        hashMap.put("texDes","সুখ কেবল গ্রহণের মধ্যেই থাকতে পারে !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Ben Jonson");
        hashMap.put("texDes","প্রকৃত সুখ বন্ধুদের ভিড়ে নয়, বরং মূল্য এবং পছন্দের মধ্যে থাকে !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Benjamin Disraeli");
        hashMap.put("texDes","কর্ম সবসময় সুখ আনতে পারে না; কিন্তু কর্ম ছাড়া সুখ নেই !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখ পেতে আমাদের বিশ্বাস করতে হবে আত্মা এবং প্রকৃতির মধ্যে একটি অদৃশ্য সংবাদ।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes",  "\"সুখ পেতে হলে আমাদের চেষ্টা করতে হবে অত্যন্ত সহজ ও ছোট বিষয়গুলির জন্য।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সুখের অন্বেষণ দুঃখের অন্যতম কারণ!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনার জীবন নিয়ে সন্তুষ্ট থাকুন কারণ সন্তুষ্টির মধ্যেই সুখ রয়েছে!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনে সত্যিকারের সুখী হতে চাইলে অন্তর থেকে ঘৃণাকে দূর করুন এবং মন থেকে দুশ্চিন্তা দূর করুন, অন্যের প্রতি প্রত্যাশা কম রাখুন এবং সহজ জীবনযাপন করুন !!");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"সুখের একটি প্রধান উৎস হলো আত্মা সন্তুষ্টি।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Thomas Merton");
        hashMap.put("texDes","উচ্চাকাঙ্ক্ষা শেষ হলে সুখ শুরু হয়!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— Dalai Lama");
        hashMap.put("texDes",  "সুখ তৈরি করার জিনিস নয়, এটি এটা আপনার নিজের কর্ম থেকে আসে !!\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Frank McCourt");
        hashMap.put("texDes","সুখ স্মরণ করা কঠিন। এটা শুধু একটি আভা !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— William E. Gladstone");
        hashMap.put("texDes","আপনার যা আছে তা নিয়ে খুশি হন, আপনাকে সুখের সন্ধান করতে হবে না !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনে সত্যিকারের সুখী হতে চাইলে অন্তর থেকে ঘৃণাকে দূর করুন এবং মন থেকে দুশ্চিন্তা দূর করুন, অন্যের প্রতি প্রত্যাশা কম রাখুন এবং সহজ জীবনযাপন করুন !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Albert kamu");
        hashMap.put("texDes","আপনি যদি সুখী হতে চান তবে অন্যের উপর নির্ভর করা বন্ধ করুন !! ");
        arrayList.add(hashMap);











    }
}