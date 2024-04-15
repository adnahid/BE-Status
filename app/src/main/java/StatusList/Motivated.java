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

public class Motivated extends AppCompatActivity {

    RecyclerView motivatedRecyclerView;
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_motivated);
        motivatedRecyclerView = findViewById(R.id.motivatedRecyclerView);
         toolbar = findViewById(R.id.toolbar);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Motivated.this, MainActivity.class);
                startActivity(intent);
            }
        });

        motivatedTable();
        finalArrayListTable();


        MotivatedAdapter motivatedAdapter = new MotivatedAdapter();
        motivatedRecyclerView.setAdapter(motivatedAdapter);
        motivatedRecyclerView.setLayoutManager(new LinearLayoutManager(Motivated.this));

    }

    private class MotivatedAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(Motivated.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(Motivated.this, "ca-app-pub-3940256099942544/2247696110")
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
    private void motivatedTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— আল কোরআন");
        hashMap.put("texDes","যে ব্যক্তি নিজের ভাগ্য পরিবর্তনের চেষ্টা করে না, আমিও তাকে ভাগ্য পরিবর্তনে কোন সাহায্য করি না। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","স্বপ্ন সেটা নয় যেটা মানুষ ঘুমিয়ে ঘুমিয়ে দেখে, স্বপ্ন সেটাই যেটা পূরণের প্রত্যাশায় মানুষকে ঘুমাতে দেয় না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবন হল 10% যা আপনার সাথে ঘটে এবং 90% আপনি এতে কীভাবে প্রতিক্রিয়া করেন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সাফল্য একটি যাত্রা একটি গন্তব্য।!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনার লক্ষ্য অর্জনের মাধ্যমে আপনি কী পান তা আপনার লক্ষ্য অর্জনের মাধ্যমে আপনি কী হয়ে উঠলেন তা ততটা গুরুত্বপূর্ণ নয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","এই কাজটা না হওয়া পর্যন্ত এইটা সবসময় অসম্ভব বলে মনে হয়।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— আলবার্ট আইনস্টাইন");
        hashMap.put("texDes","“সফল মানুষ হওয়ার চেষ্টা করার থেকে বরং মূল্যবোধ সম্পন্ন মানুষ হওয়ার চেষ্টা করো।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","সাফল্য হল নিজেকে পছন্দ করা, আপনি যা করেন তা পছন্দ করা এবং আপনি কীভাবে এটি করেন তা পছন্দ করা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","চাঁদের লক্ষ্য। আপনি যদি মিস করেন তবে আপনি একটি তারকাকে আঘাত করতে পারেন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনি যদি বড় কিছু করতে না পারেন তবে ছোট কাজগুলিকে দুর্দান্ত উপায়ে করুন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি কখনো সাফল্যের স্বপ্ন দেখিনি। আমি এটার জন্য কাজ করেছি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— রবীন্দ্রনাথ ঠাকুর");
        hashMap.put("texDes","“জীবন থেকে সূর্য চলে যাওয়ার জন্য আপনি যদি কেঁদে ফেলেন, তাহলে আপনার অশ্রুগুলি আপনাকে তারাগুলি দেখতে বাধা দেবে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "ধৈর্য্য হলো এমন এক শক্তি,\n" +
                "যার মাধ্যমে জীবনের সব কঠিন\n" +
                "বাধা কাটিয়ে ওঠা সম্ভব হয়!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমি বুঝতে পেরেছিলাম যে আমাকে নিখুঁত হতে হবে না। আমাকে যা করতে হবে তা হল দেখানো এবং আমার জীবনের অগোছালো, অপূর্ণ এবং সুন্দর যাত্রা উপভোগ করা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আমরা যদি আমাদের সন্তানদের স্ব-প্রীতি দেই, তাহলে তারা জীবন তাদের সামনে যা কিছু রাখে তা মোকাবেলা করতে সক্ষম হবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","এই মুহুর্তের জন্য খুশি থাকুন। আপনার জীবনের এই মুহূর্ত");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes",  "জীবনে অসাধারন কারো পিছনে ছুটে,\n" +
                "সময় নষ্ট করো না।\n" +
                "সাধারণ কাওকে সময় দাও,\n" +
                "অনেক মূল্য পাবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Vince Lombardi");
        hashMap.put("texDes","\"আপনি যেভাবে ভাবতে পারেন, আপনি সেভাবে হয়ে যাবেন।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Les Brown");
        hashMap.put("texDes","\"আপনি যদি একটি লক্ষ্যে পৌঁছতে চান, তাদের সহযোগিতা নিন, নয়, এটি আবশ্যক।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Oprah Winfrey");
        hashMap.put("texDes","\"যারা আপনাকে সহানুভূতি দেয়, তারা প্রয়াস করতে দিন না; তারা প্রতি সময় আপনার কাছে থাকতে দিন।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Oprah Winfrey");
        hashMap.put("texDes","\"যারা আপনাকে সহানুভূতি দেয়, তারা প্রয়াস করতে দিন না; তারা প্রতি সময় আপনার কাছে থাকতে দিন।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Magic Johnson");
        hashMap.put("texDes","\"আপনি যদি অন্যের সাথে তুলনা করতে চান, তারা আপনার সাথে বেশি করে কঠোরভাবে প্রয়াস করতে বলুক।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Maya Angelou");
        hashMap.put("texDes","\"অসম্ভাব্য জীবনটি একটি সম্ভাব্য স্বপ্নের সাথে পূর্ণ হয়ে যায়।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"প্রতিটি নিউ ডেই একটি নিউ চ্যাপ্টার।\" ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Albert Einstein");
        hashMap.put("texDes","\"সমস্যার সম্মুখে আসুন, অন্যথায় আপনি কোনও সমস্যার সমাধান করতে পারবেন না।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"ভবিষ্যতের জন্য আপনি যা করতে চান, তা আজকে শেখা শুরু করুন।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"আপনি যদি ভুল একটি পথে যান, তবে সময়ে সময়ে একটি নতুন পথ নিন।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Karen Lamb");
        hashMap.put("texDes","\"যতটুকু আপনি প্রয়াস করছেন, ততটুকু আপনি পারবেন।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Channing Pollock");
        hashMap.put("texDes","\"সঠিক পথে এগিয়ে যাওয়ার জন্য প্রতিটি হার একটি নতুন আসন।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","\"আপনি যত্রা যাচ্ছেন, তার গুরুত্ব এটা নয়, বরং তার সাথে কি শেখা যাচ্ছেন।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Elbert Hubbard");
        hashMap.put("texDes","\"আপনি যত্ন নেয়ার সময়, আপনি তার মানব হোন না, বরং এটি আপনার উচ্চ আদর্শ হোন।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Zig Ziglar");
        hashMap.put("texDes","\"আপনি একটি লক্ষ্য নির্ধারণ করতে পারলে, আপনি হোক তা বা না, তা বুঝে নেবেন।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Collier C. Robert");
        hashMap.put("texDes","\"প্রতিটি সফলতা একটি কিছু ছোট প্রয়াসের যোগফল।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনি যদি আপনার হৃদয়ে আনন্দ বহন করেন তবে আপনি যে কোনও মুহুর্তে নিরাময় করতে পা");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","এমন কিছুর জন্য আফসোস করবেন না যা আপনাকে হাসিয়েছে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","অন্ধকার অন্ধকার দূর করতে পারে না: কেবল আলোই তা করতে পারে। ঘৃণা ঘৃণাকে তাড়িয়ে দিতে পারে না: শুধুমাত্র ভালবাসা তা করতে পারে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ক্লাইভ জেমস");
        hashMap.put("texDes","বিখ্যাত না হয়ে জীবন কাটালেও সুন্দর জীবন কাটানো সম্ভব। কিন্তু জীবনের মত জীবন না কাটিয়ে বিখ্যাত হওয়া কখনও সুন্দর জীবন হতে পারে না!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ওয়াল্ট ডিজনি");
        hashMap.put("texDes","যদি স্বপ্ন দেখতে পারো, তবে তা বাস্তবায়নও করতে পারবে");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— হেনরি জেমস");
        hashMap.put("texDes","একজন মানুষ অন্য একজন মানুষের নামে তোমার কাছে কিছু বললে তাতে কান দিও না। সবকিছু নিজের হাতে যাচাই করো।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— নেলসন ম্যান্ডেলা");
        hashMap.put("texDes","সম্পন্ন করার আগে সবকিছুই অসম্ভব মনে হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— হযরত আলী (রা)");
        hashMap.put("texDes","জীবন হোক কর্মময়, নিরন্তর ছুটে চলা। চিরকাল বিশ্রাম নেয়ার জন্য তো কবর পড়েই আছে !");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— তুরস্কের বিখ্যাত প্রবাদ ");
        hashMap.put("texDes","সৌন্দর্য একদিন তোমাকে ছেড়ে যাবে, কিন্তু জ্ঞান চিরদিন- তোমার সাথে থাকবে !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— অস্ট্রিয়ান প্রবাদ");
        hashMap.put("texDes","অন্ধরা দেখতে না পেলেও আলো আলোই থাকে, সে অন্ধকার হয়ে যায় না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মার্কাস ইলেরিয়াস");
        hashMap.put("texDes","যদি সুখী হতে চাও, তবে এমন একটি লক্ষ্য ঠিক করো, যা তোমার বুদ্ধি আর শক্তিকে জাগ্রত করে, এবং তোমার মাঝে আশা সুখী জীবনের জন্য- খুব অল্প কিছুর প্রয়োজন। এটা তোমার মধ্যেই আছে, এটা তোমার ভাবনার ধরন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— এডিথ ওয়ারটন");
        hashMap.put("texDes","আলো ছড়ানোর দু’টি উপায় আছে। এক – নিজে মোমবাতি হয়ে জ্বলো, দুই – আয়নার মত আলোকে প্রতিফলিত করো।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— জর্জ ওয়াশিংটন");
        hashMap.put("texDes","খারাপ মানুষের সঙ্গের চেয়ে একা থাকাও অনেক ভালো!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— জার্মান প্রবাদ");
        hashMap.put("texDes","চোখ নিজেকে বিশ্বাস করে, কান বিশ্বাস করে অন্যকে!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ড্যানিশ প্রবাদ");
        hashMap.put("texDes","পানির গভীরতা নাকের কাছে উঠে আসার আগেই সাঁতার শিখে নাও।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— প্রাচীন গ্রীক প্রবাদ");
        hashMap.put("texDes","ভালো মানুষ খুব ধীরে ‘না’বলে। বুদ্ধিমান মানুষ চট করে ‘না’বলতে পারে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— কনরাড হিলটন");
        hashMap.put("texDes","সফল মানুষেরা কাজ করে যায়। তারা ভুল করে, ভুল শোধরায় – কিন্তু কখনও হাল ছাড়ে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— জে আর আর টলকিন");
        hashMap.put("texDes","যারা নতুন কিছু খোঁজে না, একদিন তাদেরও কেউ খুঁজবে না !");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— থমাস কার্লাইল");
        hashMap.put("texDes","সব ধরনের অনিশ্চয়তা, হতাশা আর বাধা সত্ত্বেও নিজের সবটুকু দিয়ে সফল হওয়ার চেষ্টাই শক্তিমান মানুষকে দুর্বলদের থেকে আলাদা করে!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","— পর্তুগীজ প্রবাদ");
        hashMap.put("texDes", "শুধু কথা দিয়ে চুলায় রুটি ওঠানো যায় না!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— বিখ্যাত ড্যানিশ প্রবাদ");
        hashMap.put("texDes","প্রশ্ন করতে যে লজ্জা পায়, সে শিখতে পারে না.");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle", "_ জার্মান প্রবাদ ");
        hashMap.put("texDes","অকর্মার কাছেও মাঝে মাঝে সৌভাগ্য আসে, কিন্তু কখনওই বেশিক্ষণ থাকে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— থমাস জেফারসন ");
        hashMap.put("texDes","সততা হল জ্ঞানী হওয়ার বইয়ের ১ম অধ্যায়!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মিল্টন বার্লে");
        hashMap.put("texDes","সুযোগ যদি তোমার দরজায় কড়া না নাড়ে, তবে নতুন একটি দরজা বানাও !");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— প্লেটো");
        hashMap.put("texDes","বুদ্ধিমানেরা তখন কথা বলে যখন তাদের কিছু বলার থাকে। বোকারা কথা বলে কারণ তারা ভাবে তাদের কথা বলতে হবে !");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— নেপোলিয়ন হিল");
        hashMap.put("texDes","যদি খুব ভালো কিছু করতে না পারো, তবে ছোট ছোট কাজ- খুব ভালো করে করো।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— প্লুতার্ক");
        hashMap.put("texDes","আমরা ভেতর থেকে যেভাবে বদলাই, সে অনুযায়ীই আমাদের বাইরের বাস্তবতা বদলে যায়।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— জার্মান প্রবাদ");
        hashMap.put("texDes","খারাপ হওয়ার জন্য ভালো কাজ না করে হাত গুটিয়ে বসে থাকাই যথেষ্ঠ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ড্যানিশ প্রবাদ");
        hashMap.put("texDes","নেকড়ের পালের সাথে বসবাস করো, তুমি বিড়াল হলেও- একদিন গর্জন করতে শিখবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— আর্নেস্ট হেমিং");
        hashMap.put("texDes","মানুষ পরাজয়ের জন্য সৃষ্টি হয়নি। তাকে হয়তো ধ্বংস করা যায়, কিন্তু হারানো যায় না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— এডওয়ার্ড এভরিট হ্যালি");
        hashMap.put("texDes","একসাথে হওয়া মানে শুরু, একসাথে থাকা মানে উন্নতি। দীর্ঘ সময় একসাথে চলা মানে সাফল্য।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— তুর্কী প্রবাদ");
        hashMap.put("texDes","কাককে মুখে তুলে খাওয়াতে গেলে, সে তোমার চোখ উপড়ে খাবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মার্ক টোয়েন");
        hashMap.put("texDes","জীবনে উন্নতি করার গোপন সূত্র হল কাজ শুরু করা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— অস্ট্রিয়ান প্রবাদ");
        hashMap.put("texDes","তুমি যদি টাকা ধার করো, তবে তুমি ব্যাংকের কাছে দায়বদ্ধ। আর যদি টাকার মালিক হও, তাহলে ব্যাংক তোমার কাছে দায়বদ্ধ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ড. এপিজে আব্দুল কালাম");
        hashMap.put("texDes","চলুন আজকের দিনটাকে আমরা উৎসর্গ করি, যাতে আমাদের সন্তানরা কালকের দিনটাকে উপভোগ করতে পারে !");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— রাশিয়ান প্রবাদ");
        hashMap.put("texDes","অতীত নিয়ে সবসময়ে পড়ে থাকলে তোমার এক চোখ অন্ধ,\n" +
                "অতীতকে ভুলে গেলে তোমার দুই চোখই অন্ধ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","বেন্জামিন ফ্র্যাঙ্কলিন");
        hashMap.put("texDes","বললে আমি ভুলে যাব। শেখালে মনে রাখব। সাথে নিলে আমি শিখব !");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— আল হাদিস");
        hashMap.put("texDes","অপব্যয়কারী শয়তানের ভাই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— – মার্ক টোয়েন");
        hashMap.put("texDes","”জীবনে সফল হতে চাইলে দু’টি জিনিস প্রয়োজন: জেদ আর আত্মবিশ্বাস”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— পাবলো পিকাসো");
        hashMap.put("texDes","কাজ শুরু করাই হলো সাফল্যের মূল চাবিকাঠি।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— আলবার্ট আইনস্টাইন");
        hashMap.put("texDes","” সফল হওয়ার চেষ্টা করার বদলে দক্ষ হওয়ার চেষ্টা করো। সাফল্য এমনিই আসবে।“");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“জেতার আসল মজা তো তখনই, যখন সবাই তোমার হারের অপেক্ষায় থাকে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মাওলানা জালাউদ্দিন রুমি।");
        hashMap.put("texDes","কখনো ভেঙে পড়ো না। পৃথিবীর যা কিছু হারিয়ে যায়, অন্য কোন রূপে সেটি ঠিকই আবার ফিরে আসে জীবনে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— লরেন্স জে পিটার।");
        hashMap.put("texDes","বাস্তবতা এমন লোকদের জন্য যারা ড্রাগের মুখামুখি হতে পারে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— আবুল ফজল।");
        hashMap.put("texDes", "অনেক কিছু ফিরে আসে, ফিরিয়ে আনা যায়, কিন্তু সময়কে ফিরিয়ে আনা যায় না");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— -হুমায়ূন আহমেদ।");
        hashMap.put("texDes",  "মধ্যবিত্ত পরিবারের মানুষগুলোই ধরণীর আসল রূপ দেখতে পায়");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ওয়েইন ডায়ার");
        hashMap.put("texDes","আমাদের উদ্দেশ্য আমাদের বাস্তবতা তৈরি করে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— রেদোয়ান মাসুদ");
        hashMap.put("texDes","পৃথিবীতে কঠিন বাস্তবের মধ্যে একটি বাস্তব হলো- মানুষ যখন সাফল্যের দ্বারপ্রান্তে এসে পৌঁছায় আর তখনই তার প্রিয় মানুষটি হারিয়ে যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—  জালাল উদ্দিন রুমি");
        hashMap.put("texDes","তোমার জন্ম হয়েছে পাখা নিয়ে, উড়ার ক্ষমতা তোমার আছে। তারপরও খোঁড়া হয়ে আছো কেন ?");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes"," শুধু দাঁড়িয়ে নদী দেখতে থাকলে আপনি কোনদিনও সেই নদী পার করতে পারবেন না, পার করতে হলে আপনাকে অবশ্যই সঠিক পদক্ষেপ নিতে হবে !");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ঈশ্বরচন্দ্র বিদ্যা সাগর");
        hashMap.put("texDes","ধনী আর গরীবের মধ্যে পার্থক্য একটাই – ধনীরা খাবার হজম করার জন্য দৌড়ায় ,আর গরীবেরা খাবার জোগাড়ের জন্য দৌড়ে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," – অজানা");
        hashMap.put("texDes","এই পৃথিবীতে সবচেয়ে সস্তা হলো পরামর্শ …!\n" +
                "একজনের কাছে চাইলে দশ জন দিয়ে দেয়।\n" +
                "আর পৃথিবীতে দামি জিনিস হলো সাহায্য ..!\n" +
                "দশজনের কাছে চাইলে – হয়তো একজনের কাছে পাওয়া যেতে পারে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—এ পি জে আব্দুল কালাম");
        hashMap.put("texDes","আমরা সবাই যোগ্য ব্যক্তি খোঁজার চেষ্টা করি। কিন্তু আমরা কেউ যোগ্য হওয়ার চেষ্টা করি না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— নেলসন মেন্ডেলা");
        hashMap.put("texDes","জীবনে যদি কিছু করে দেখতে চাও – তাহলে একলা কি ভাবে লড়তে হয় ,তা প্রথমে শিখে নাও।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— কাজী নজরুল ইসলাম");
        hashMap.put("texDes","বিশ্বাস করুন, আমি কবি হতে আসিনি, আমি নেতা হতে আসিনি। আমি প্রেম দিতে এসে ছিলাম, প্রেম পেতে এসে ছিলাম। সে প্রেম পেলাম না বলে, আমি প্রেমহীন নীরস পৃথিবী থেকে। নীরব অভিমানে চিরদিনের জন্য বিদায় নিলাম।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— হজরত আলী");
        hashMap.put("texDes","কেউ তোমাকে মূল্য দিক আর না দিক -তুমি সৎ কাজ করে যাও। কারণ – এর প্রতিদান মানুষ হয়তো নাও দিতে পারে কিন্তু আল্লাহ দিবেন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","–এ পি জে আব্দুল কালাম");
        hashMap.put("texDes","জীবনে যায় হোক কখনো হাসতে ভুলে যেওনা। কারণ –হাসিটা তোমার ভিতরে শক্তি ও সাহস যোগাবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— খেলিস।");
        hashMap.put("texDes","সবচেয়ে কঠিন কাজ হচ্ছে নিজেকে চেনা। আর সবচেয়ে সহজ কাজ হচ্ছে অন্যদেরকে উপদেশ দেওয়া।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," –প্লেটো");
        hashMap.put("texDes","কথা -বার্তায় ক্রোধের পরিমাণ -খাবারের লবনের মতো হাওয়া উচিত। পরিমান মতো হলে রুচিকর ,আর অপরিমিত হলে ক্ষতিকর।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— হযরত ওমর");
        hashMap.put("texDes",  "যে তোমার সামনে ভুল ধরিয়ে দেয় ,সেই তোমার প্রকৃত বন্ধু। আর যে তোমার সামনে প্রশংসা করে সেই শত্রূ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— বিল গেটস");
        hashMap.put("texDes", "আপনি যদি গরীব হয়ে জন্ম নেন তাহলে এটা আপনার দোষ নয়, কিন্তু যদি গরীব থেকেই মারা যান তবে সেটা আপনার দোষ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— জন লিভগেট");
        hashMap.put("texDes","যে নদীর গভীরতা বেশি, তার বয়ে যাওয়ার শব্দ কম।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— টম হপকিন্স");
        hashMap.put("texDes","যতবার আমি ব্যর্থ হই এবং চেষ্টা চালিয়ে যাই তার উপর সরাসরি নির্ভর করে আমি কতবার সফল হতে পারব।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ইমাম গাজ্জালী (রঃ)");
        hashMap.put("texDes"," আপনার জন্য যা নির্ধারণ করা হয়েছে, তা যদি দুই পর্বতের নিচে ও থাকে তবু ও তা আপনার কাছে পৌঁছে যাবে। আর আপনার জন্য যা নির্ধারণ করা হয় নি, তা যদি দুই ঠোঁটের মাঝে ও থাকে তবু ও তা আপনার কাছে পৌঁছবে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— অ্যানোনিমাস");
        hashMap.put("texDes","ব্যর্থ হওয়া মানে হেরে যাওয়া নয়, ব্যর্থতা নতুন করে আবার শুরু করার প্রেরণা। হাল ছেড়ে দেওয়া মানেই হেরে যাওয়া।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— অ্যানোনিমাস");
        hashMap.put("texDes","দুঃসময়ের অন্ধকার কখনো কখনো আমাদের জীবনের সবচেয়ে উজ্জ্বল মুহূর্তটির দ্বার খুলে দেয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— রেদোয়ান মাসুদ");
        hashMap.put("texDes","জীবন চলার পথে বাঁধা আসতেই পারে তাই বলে থেমে যাওয়ার কোনো অবকাশ নেই। যেখানে বাঁধা আসবে সেখান থেকেই আবার শুরু করতে হবে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মাইকেল জর্ডান");
        hashMap.put("texDes"," আমি ব্যর্থতা কে মেনে নিতে পারি কিন্তু আমি চেষ্টা না করাকে মেনে নিতে পারিনা।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সুরা আলে ইমরান, আয়াত ১৪৩)");
        hashMap.put("texDes","“আল্লাহর পথে দু: খ ও প্রস্তুতি থাকতে পারে। কিন্তু ধৈর্যশীল থাকুন, কোনো সময়েই আল্লাহর নিকট সকলের সময়ে ভাল হয়।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সুরা তাওবা, আয়াত ১১৫)");
        hashMap.put("texDes","“আল্লাহ তোমার সঙ্গে আছে, আর তিনি সব কিছু জানেন।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সুরা ফুরকান, আয়াত ৬)");
        hashMap.put("texDes","“প্রতিবাদের প্রতি সাব্বক প্রতিবাদ দিন, যেন তাদের পর আপনার মধ্যে স্থান পাওয়া যায়।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সহীহ মুসলিম)");
        hashMap.put("texDes","“কোনো দিন আপনি যেখানে থাকুন, সেখানে এমন পানি প্রস্তুত করা যাবে যেটি তিনি পরিমাণের দু: খ ব্যক্ত করার জন্য সর্বোপরি অনুমোদন করেন।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— হযরত আলী (রাঃ)");
        hashMap.put("texDes","তোমার যা ভালো লাগে তাই জগৎকে দান কর, বিনিময়ে তুমিও অনেক ভালো জিনিস লাভ করবে। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবনে দুটি দুঃখ আছে। একটি হল তোমার ইচ্ছা অপূর্ণ থাকা, অন্যটি হল ইচ্ছা পূর্ণ হলে আরেকটির প্রত্যাশা করা।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— রেদোয়ান মাসুদ");
        hashMap.put("texDes","মানুষের মন যেদিনই ক্লান্ত হয় সেদিনই তার মৃত্যু হয়। ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনার দর্শন ও স্বপ্নকে নিজের সন্তানের মত লালন করুন কারণ এগুলোই আপনার চূড়ান্ত অর্জনের প্রতিচিত্র হয়ে উঠবে। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— হার্ভি ম্যাকে");
        hashMap.put("texDes","একজন গড়পড়তার মানুষ কথা বলে। একজন ভাল মানুষ ব্যাখ্যা করে। একজন উর্ধ্বতন মানুষ কাজ করে দেখায়। একজন সেরা মানুষ অন্যদেরকে প্রেরণা যোগায় যাতে তারা নিজেরাই কাজকে নিজের মত করে দেখতে পারে। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— চার্লি চ্যাপলিন");
        hashMap.put("texDes", "এই বিশ্বে স্থায়ী কিছুই না, এমনকি আমাদের সমস্যাগুলোও না। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— পেলে");
        hashMap.put("texDes","সহজে জেতার আনন্দ কোথায়? বাধা যত বিশাল, বিজয়ের আনন্দও ততোই বাঁধভাঙ্গা! ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— টনি রবিনস");
        hashMap.put("texDes","আপনি যে নতুন একটি কাজ হাতে নিয়েছেন তার উপর ভিত্তি করেই সত্যিকারের সিদ্ধান্তের মাপকাঠি ধরা হয়। যদি হাতে কোন কাজ না থাকে, তাহলে আপনি এখনও আপনার সত্যিকারের সিদ্ধান্ত গ্রহণ করতে পারেননি।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— জোসেফ ক্যাম্পবেল");
        hashMap.put("texDes"," আমাদের জন্য যে জীবন অপেক্ষা করছে তা পেতে আমাদের পরিকল্পনা করা জীবন ছেড়ে দিতে ইচ্ছুক হতে হবে। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ইমাম গাজ্জালী");
        hashMap.put("texDes","আপনার জন্য যা নির্ধারণ করা হয়েছে, তা যদি দুই পর্বতের নিচে ও থাকে তবু ও তা আপনার কাছে পৌঁছে যাবে। আর আপনার জন্য যা নির্ধারণ করা হয় নি, তা যদি দুই ঠোঁটের মাঝে ও থাকে তবু ও তা আপনার কাছে পৌঁছবে না!");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— হযরত আলী (রাঃ)");
        hashMap.put("texDes","ঘুমিয়েই কি কেটে যাবে একটি জীবন? জীবন হোক কর্মচাঞ্চল্যে ভরপুর, ছুটে চলার নিরন্তর অনুপ্রেরণা। বিশ্রাম নেওয়ার জন্য কবরের জীবন চিরকাল পড়ে রয়েছেই। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— এ পি জে আব্দুল কালাম");
        hashMap.put("texDes","সমাপ্তি মানেই শেষ নয়। ‘END’ শব্দটির মানে হচ্ছে ‘Effort Never Dies’ অর্থাৎ ‘প্রচেষ্টার মৃত্যু নেই’। ");
        arrayList.add(hashMap);






    }
}