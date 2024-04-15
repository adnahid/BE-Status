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

public class Human extends AppCompatActivity {

    RecyclerView humanRecyclerView;
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_human);
        humanRecyclerView = findViewById(R.id.humanRecyclerView);
        toolbar = findViewById(R.id.toolbar);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Human.this, MainActivity.class);
                startActivity(intent);
            }
        });


        alonTable();
        finalArrayListTable();


        HumanAdapter humanAdapter = new HumanAdapter();
        humanRecyclerView.setAdapter(humanAdapter);
        humanRecyclerView.setLayoutManager(new LinearLayoutManager(Human.this));

    }

    private class HumanAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(Human.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(Human.this, "ca-app-pub-3940256099942544/2247696110")
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
    private void alonTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- চার্লস চাপলিন");
        hashMap.put("texDes","“মানবতা মানব হওয়ার মতো একটি সূত্র।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– নেলসন ম্যান্ডেলা");
        hashMap.put("texDes","“মানবতার জন্য একটি মুক্তি চায় না বরং একটি মানবতার জন্য মুক্তি চায়।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– আলবার্ট আইনস্টাইন");
        hashMap.put("texDes","“যদি তুমি মানুষকে মানুষত্বের মতো ব্যবহার না করতে পারো, তবে বিপদ থেকে রক্ষা করা বিপদ নয়।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– রলফ ওয়ালডো এমারসন");
        hashMap.put("texDes","“মানবতা হলো তোমার সম্পূর্ণ ব্যক্তিত্বকে একটি আদর্শ উচ্চতায় এনে সেটা সবার সঙ্গে ভাগ করার ক্ষমতা।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– কার্ল জঙ্গ");
        hashMap.put("texDes","“মানবতা হলো সবার সঙ্গে সহজলভ্য একটি ব্যক্তিগত বৈশিষ্ট্য, স্বাভাবিকভাবে সমস্ত মানুষের সঙ্গে মিলিত একটি বান্ধব বন্ধন।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– নেলসন ম্যান্ডেলা");
        hashMap.put("texDes","“মানুষকে সাহায্য করার সবচেয়ে সুন্দর ও আদর্শ পদ্ধতি হল তাদের শিক্ষাদানের মাধ্যমে শক্তিশালী হতে সাহায্য করা।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– ফ্রেড রজার্স");
        hashMap.put("texDes","“সেই সময়টা আসছে যখন সবাই বাড়ি ফিরে যাচ্ছে এবং তুমি কাউকেই সাহায্য করতে না পারলে সেই সময়টা তোমার বাস্তবিক পরীক্ষা হবে।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","আশপাশের মানুষেরা ভাল কিনা জানার আগে,\n" +
                "নিজের মনের ভেতর পাপ আছে কিনা,\n" +
                "নিজে ভাল কিনা,সেটা আগে নিজে জেনে নাও।\n" +
                "এই ভাবেই যদি আমরা নিজেদের সুন্দর করে গড়ে তুলতে পারি,\n" +
                "তবে একটা সুন্দর সমাজ পাবো।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– সুজীন পাকরাজ");
        hashMap.put("texDes","“একজন মানুষের সাহায্য করার সময় তার নগদ প্রয়োজন হতে পারে না, সমস্যা সমাধানে তার আত্মবিশ্বাসের প্রয়োজন হয়।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","“যদি তুমি কাউকে ভালোবাসো এবং তার কাছে আরও ভাল হতে চাও, তাহলে তাকে একটি কাজ শিখিয়ে দাও, যাতে সে আরো স্বয়ংসমর্পণ করতে পারে।” –");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- রবীন্দ্রনাথ ঠাকুর");
        hashMap.put("texDes","\"মানুষ মানুষের জন্য একটি বিশ্বাসযোগ্য মিত্র।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- মহাত্মা গান্ধী");
        hashMap.put("texDes","\"সত্য, অহিংসা এবং প্রণয় দ্বারা একে অপরকে বাঁচানো যায়।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- নেলসন ম্যান্ডেলা");
        hashMap.put("texDes","\"আমরা আলাদা আলাদা ভাষায় বলতে পারি, কিন্তু সবাই একই ভাষায় মানবিকতা বোঝতে পারবে তাই আমরা সবাই এক হতে চাই।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- এমার্সন");
        hashMap.put("texDes","\"মানব অধিকার এবং সমান অধিকার প্রতি আমাদের আবেগ অনবরত।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","সফলতা পেতে গেলে যেমন নিরন্তর পরিশ্রমের প্রয়োজন, তেমনি একটি সুন্দর সমাজ গড়তে হলে প্রয়োজন মানবতা।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","কারোর কষ্ট দেখে আপনার মন যদি অস্থির হয়ে ওঠে, তাহলে বুঝবেন মানবতা এখনো আপনার মধ্যে বেঁচে আছে….!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","একজন মানুষের মনুষ্যত্ব তখনই শেষ হয়ে যায় যখন সে অন্যকে দুঃখী দেখে নিজে খুশী হতে শুরু করে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes", "যে মানুষ হিসেবে তার দায়িত্ব পালন করে,,, তার সাথে লাখো মানুষের আশীর্বাদ থাকে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- আলবার্ট আইনস্টাইন");
        hashMap.put("texDes","\"একজন ব্যক্তি যখন অপরকে সহানুভূতি দেয়, সে একটি মানবিক অবস্থানে পৌঁছে যায়।\" ");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- হেলেন কেলার");
        hashMap.put("texDes","\"মানুষের জীবনটি একটি সংঘর্ষ, একটি উত্সাহের সফর।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- উইলিয়াম জেমস");
        hashMap.put("texDes","\"আমাদের অবস্থানটি হৃদয় না, আমাদের চেষ্টার ফল।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- জন পল সার্টর");
        hashMap.put("texDes","\"সকল মানববন্ধন একটি আত্মতা।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","দেশের পরিবেশ একটা ব্লাড ব্যাঙ্কের মতো হওয়া উচিত। যেখানে কোনো জাত নেই, ধর্ম নেই, শুধু মানুষ আর মানবতা..!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","যেদিন পৃথিবী থেকে মানবতা এবং মনুষ্যত্ব হারিয়ে যাবে, সেদিন থেকে পৃথিবীর ধ্বংসের সূচনা হবে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","যে ব্যক্তি ধনী হওয়া সত্ত্বেও অভাবীকে দেওয়ার জন্য পকেট থেকে টাকা বের করে না, সে মানবতার দিক থেকে দরিদ্র।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-মহাত্মা গান্ধী");
        hashMap.put("texDes","\"আইতহাসিক সত্যের জন্য একটি অপরাধ করা হতে পারে, তবে তা অবশ্যই এমন একটি অপরাধ হতে হবে, যাতে আমরা জীবন বৃদ্ধি পাই।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- স্বামী বিবেকানন্দ");
        hashMap.put("texDes","\"যে দেশটি ভূগোলে একটি ছোট দেশ হোক, কিন্তু যেখানে মানবতা হবে, তার জন্য সেটি মহাদেশ।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- রবীন্দ্রনাথ ঠাকুর");
        hashMap.put("texDes","\"একজন ব্যক্তির প্রতি আমাদের উদ্দীপনা এবং স্নেহ তার মানবিকতা এবং সামাজিক সংবাদের জন্য প্রধান উৎস।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- আব্দুল কালাম");
        hashMap.put("texDes","\"একটি মহান জনগণ একটি মহান সমাজ তৈরি করতে পারে না যদি তারা মানবিকতা এবং নৈতিকতা পূর্ণ না থাকে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- মাদার টেরেসা");
        hashMap.put("texDes","\"আমরা সবাই বৃদ্ধি চাই, তাই আমাদের একবার দেখতে হবে আপনার পাশে একজন অসহায় ব্যক্তির কথা বলতে হলে বা সেই অসহায় ব্যক্তির মাধ্যমে আমাদের কথা বলতে হলে।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","মানুষ যখন তার মূল্যের চেয়ে বেশি,, উপার্জন করতে শুরু করে, তখন সে মানবতার উপলব্ধি ভুলে যেতে শুরু করে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","মনুষ্যত্বহীন মানুষকে মানবতার পাঠ দিতে হবে। তবেই মনুষ্যত্বের জয় হবে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","যে ব্যক্তির অন্তরে স্বার্থপরতা জন্ম নেয়,, তার জীবন থেকে মানবতা শেষ হয়ে যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","মানুষ প্রতিটি ঘরে জন্মায়, কিন্তু মনুষ্যত্ব জন্মায় মাত্র কয়েকটি ঘরে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- রবীন্দ্রনাথ ঠাকুর!");
        hashMap.put("texDes","যে ব্যক্তি তার ভালো গুণের প্রশংসা করছে, তাকে জিজ্ঞাসা করুন সে মানুষ হিসেবে আজ পর্যন্ত কী করেছে?");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","\"আমাদের জীবনে প্রতি প্রতিরূপে বিচিত্রতা এবং সহানুভূতির সহাজ সিদ্ধান্ত থাকতে হবে।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","\"যে ব্যক্তি নিজেকে এবং আরেকজনকে সহানুভূতিতে মিলাতে পারে, তার হৃদয়ে আজাদি এবং সত্যের আলো থাকতে পারে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- কাজী নজরুল ইসলাম");
        hashMap.put("texDes","\"আমি আমার দেশকে ভালোবাসি, তাই আমি আমার দেশবাসীদের দু: খীয়ে দেখতে চাইনা।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","\"মানুষ হওয়ার মাধ্যমেই আসল মানবিকতা প্রকাশ পাওয়া সম্ভব।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes", "বর্তমানে আমদের মধ্যে প্রায় সকলেই সামাজিক, কিন্তু তাও যেন আমাদের আশেপাশে সামাজিকতার বড় অভাব। ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","সমাজকল্যাণ হল এমন এক ক্ষেত্র, যেখানে রাষ্ট্রসমাজ এবং ব্যক্তি প্রত্যেকের ক্ষেত্রেই সুনির্দিষ্ট ভূমিকা ও দায়িত্ব রয়েছে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- মাইকেল টিল");
        hashMap.put("texDes","ইহাই মানবতা যা আমাদের অ্যাধাত্মিক বানায় এবং আত্মা সেটাই যা আমাদের মানুষ বানায়।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- রোনাল্ড রিয়াগান!");
        hashMap.put("texDes","আমরা হয়তো সকলকেই সাহায্য করতে পারব না, তবে আমরা কাউকে না কাউকে সাহায্য করতে পারব। আর এটাই হলো মানবতা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-কন্সট্যান্টিন সিওলকভস্কি");
        hashMap.put("texDes","পৃথিবীকে মানবতার দোলনা বলা হয়, তবে মানুষ সারাজীবন এই দোলনায় থাকতে পারে না।কেননা একটা সময় তার ভিতর মানবতার অভাব দেখা যায়।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সুজি কাসেম");
        hashMap.put("texDes","মানবতা হারিয়ে যাচ্ছে কেননা মানুষ তার জীবনযাত্রায় বিবেককে কম্পাস হিসাবে ব্যবহার করতে ভুলে যাচ্ছে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("cat","স্ট্যাটাস");
        hashMap.put("texTitle","— লিও টলস্টয়");
        hashMap.put("texDes",  "জীবনের মূল মানেটা হলো নিজের সব কিছু দিয়ে মানবতার সেবা করা।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- আন্না ফ্রাংক!");
        hashMap.put("texDes","মানবতার প্রধান শিক্ষা এটাই যে দানের মাধ্যমে মানুষ কখনোই ফকির হয়ে যায় না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — আব্দুল সাত্তার ইধি");
        hashMap.put("texDes","মানবতার চেয়ে বড় কোনো ধর্মের নাম আমার জানা নেই। কেননা ধর্মের মূল বিষয়টা এখানেই শুরু।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- ইউয়ানশিখা");
        hashMap.put("texDes","যখন মানবতা হারিয়ে যাবে তখন আমরা নিজেদের মানুষ হিসাবে পরিচয় দেয়ার কোনো অধিকারই রাখবো না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- আলবার্ট আইনস্টাইন");
        hashMap.put("texDes","আমি মানবতাকে ভালোবাসি কিন্তু মানুষকে ঘৃণা করি।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— লিও টলস্টয়");
        hashMap.put("texDes","ভালোবাসাই হলো একমাত্র উপায় যার মাধ্যমে মানবতাকে সব ধরনের রোগ থেকে বাচানো সম্ভব।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মহাত্মা গান্ধী");
        hashMap.put("texDes","আপনার কখনোই মানবতার উপর থেকে বিশ্বাস হারানো উচিত নয়। কারণ পুরো মানবজাতি হলো এক সমুদ্রের মতো এখানে দু এক ফোটা নোংরা থাকলে কিন্তু পুরো সাগরটা দূষিত হয় না।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","সমাজে ধনী, গরীব, ভালো, খারাপ সকল প্রকারের মানুষই থাকে। সেক্ষেত্রে নিজেদের বুঝে চলতে হয় যে কোন মানুষ কেমন হতে পারে, সেজন্যই সামাজিকতা জরুরী।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes", "একজন সৎ পথগামী, সৃজনশীল এবং প্রগতিশীল চলচ্চিত্র নির্মাতা সঠিক বিষয়ে ভিত্তি করে রচিত সিনেমাকে সমাজ বিপ্লবের হাতিয়ার রূপে ব্যবহার করার ক্ষমতা রাখেন।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","স্বামী বিবেকানন্দ");
        hashMap.put("texDes","\"একজন জীবন্ত ব্যক্তি একটি সমৃদ্ধ সমাজ সৃষ্টি করতে সক্ষম হতে পারে এবং তার সবচেয়ে মূল্যবোধ মানুষের সহানুভূতি এবং ন্যায়ের উপর ভিত্তি করতে হবে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","স্বামী বিবেকানন্দ");
        hashMap.put("texDes","\"আত্মসমর্পণ এবং অন্যের সেবা হলো সত্যিক মানবিকতার চেতনা।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","সমাজের সারমর্ম হল শান্তি স্থাপন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","“সামাজিক হতে চাইলে সবার প্রথমে নিজেকে সমাজ সেবায় নিযুক্ত করতে হবে।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","–লেনিন");
        hashMap.put("texDes","“সমাজতন্ত্রই শােষিত নির্যাতিত জনগণের মুক্তির একমাত্র পথ।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","–জন রলস");
        hashMap.put("texDes","“জনসাধারণের ন্যায়বিচার দ্বারা নিয়ন্ত্রিত একটি সমাজ সহজাতভাবে স্থিতিশীল।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– আলবার্ট আইনস্টাইন");
        hashMap.put("texDes","“মানব সমাজে যা কিছু মূল্যবান তা নির্ভর করে ব্যক্তির প্রদত্ত বিকাশের সুযোগের উপর।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– আলবার্ট আইনস্টাইন");
        hashMap.put("texDes","“মানব সমাজে যা কিছু মূল্যবান তা নির্ভর করে ব্যক্তির প্রদত্ত বিকাশের সুযোগের উপর।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes", "“সমাজে মানুষ কখনই একাকী বাঁচতে পারে না। সমাজে থাকতে গেলে আমাদের সকলেরই উচিৎ একসাথে মিলে মিশে থাকা।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– অ্যাঞ্জেলা ডেভিস");
        hashMap.put("texDes","“আমাদের মনকে মুক্ত করার পাশাপাশি আমাদের সমাজকেও মুক্ত করতে হবে।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– জর্জ বার্নার্ড শ়়");
        hashMap.put("texDes", "“নিজেদের সংস্কার করার আগে আমাদের অবশ্যই সমাজ সংস্কার করতে হবে।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– রালফ নাদের");
        hashMap.put("texDes","“যে সমাজে ন্যায়বিচার বেশি, সেই সমাজে দাতব্যের প্রয়োজন হয় না।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","“যেসব মানুষের অর্থ বেশি, এই সমাজে তাদেরই মূল্য সবচেয়ে বেশি।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","– আলেকজান্ডার হ্যামিল্টন");
        hashMap.put("texDes", "“আমি মনে করি সমাজের প্রথম কর্তব্য ন্যায়বিচার।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- মহাত্মা গান্ধী");
        hashMap.put("texDes","ফোটা নোংরা থাকলে কিন্তু পুরো সাগরটা দূষিত হয় না। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— আলবার্ট আইনস্টাইন");
        hashMap.put("texDes","আমি মানবতাকে ভালোবাসি কিন্তু মানুষকে ঘৃণা করি।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— রোনাল্ড রিয়াগান");
        hashMap.put("texDes","আমরা হয়তো সকলকেই সাহায্য করতে পারব না, তবে আমরা কাউকে না কাউকে সাহায্য করতে পারব। আর এটাই হলো মানবতা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—মাইকেল টিল");
        hashMap.put("texDes","ইহাই মানবতা যা আমাদের অ্যাধাত্মিক বানায় এবং আত্মা সেটাই যা আমাদের মানুষ বানায়। ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— লিও টলস্ট");
        hashMap.put("texDes","ভালোবাসাই হলো একমাত্র উপায় যার মাধ্যমে মানবতাকে সব ধরনের রোগ থেকে বাচানো সম্ভব। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","একটি সমাজে বাস করতে হলে সকলের সাথে মিলে মিশে থাকতে হয়, সমাজের মধ্যে থেকে কেউ একাকী বা আলাদা থাকতে পারে না।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","সমাজে বসবাসকারী জনগণের “নীতিবোধ” কিংবা আরো স্পষ্ট করে বলতে গেলে চারিত্রিক মূল্যবোধই হল সমাজ সংগঠনের প্রধান শক্তি। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","সমাজসেবা করার কথা হয়তো সকলেই ভাবে, কিন্তু সকলে করে না বা করতে পারেনা। তবে কেউ সমাজ সেবা করতে ইচ্ছুক হোক তা প্রথমে নিজ গৃহ থেকেই শুরু করতে হবে, তাহলে সামগ্রিকভাবে দেশের কল্যাণ সম্ভব।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","যেকোনো ক্ষেত্রেই একটি সফল সামাজিকতার কৌশল ব্যক্তিগত স্বাধীনতার জন্যই অপ্রত্যাশিত উপায় খুঁজে বের করে থাকে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes", "কোনো অসভ্য সমাজে থেকে স্বাধীনভাবে বাস করার চাইতে একটি সভ্য সমাজে থেকে শৃঙ্খলিত ভাবে বাস করা অনেক ভালো");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- মহাত্মা গান্ধী");
        hashMap.put("texDes","\"মানুষ মানুষের জন্য একে অপরকে ভালোবাসতে হয়।\" ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- মার্টিন লুথার কিং জুনিয়র");
        hashMap.put("texDes","\"সমাজের সুধারের জন্য সমাজের মূখ্য অংশই আমাদের মানবিকতার মধ্যে লুকিয়ে থাকা একটি ভালোবাসা।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- নেলসন ম্যান্ডেলা");
        hashMap.put("texDes","\"আমাদের সকলের মাঝে একই মানবিক অধিকার এবং মূল্যবোধ থাকতে হবে।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- অ্যালবার্ট আইনস্টাইন");
        hashMap.put("texDes","\"ব্যক্তিগত উন্নতি এবং সমাজের পূর্ণাঙ্গ উন্নতির জন্য সৃষ্টি করা হয়েছে মানবিক ভাবে।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– নেলসন ম্যান্ডেলা");
        hashMap.put("texDes","“মানবতা হলো সর্বোচ্চ মানবিক মানবিকতা” -“মানবতার উক্তি, সমানতা এবং বৈচিত্র্য সম্পর্কে জন্মগ্রহণ করে এবং তাদের ভাইত্ব এবং বন্ধুত্বের ভালোবাসা সম্পর্কে একটি কর্তব্যবিমূঢ় হতে হবে।” ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– হেলেন কেলার");
        hashMap.put("texDes","“যে মানুষ একটি অন্ধকার করে অন্য মানুষের দিকে দেখে, সে আলোর কিছু দেখতে পারবে না।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– দলাই লামা");
        hashMap.put("texDes","“মানুষ হতে সমস্ত মানুষের প্রতি সদয় ও দয়ালু হতে হবে।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– কার্ল পপার");
        hashMap.put("texDes","“একটি আদর্শ মানবিক সমাজ যা কিছু বিশেষ ব্যক্তিদের দ্বারা নির্মিত নয়, কিন্তু সবার মধ্যে অবস্থান করে এবং সবার জন্য সুখ এবং স্বাধীনতা বঞ্চিত করে না।” ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","“সব মানুষ মুক্ত এবং সমান জন্মে উঠেছে এবং তাদের মনের চিন্তা এবং ধর্মের স্বাধীনতার দ্বারা তাদের কোনও একতা বিচ্ছিন্ন করা উচিত নয়।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মার্টিন লুথার কিং জুনিয়র");
        hashMap.put("texDes","“মানবতার আদর্শ একটি সমান জগত যেখানে সকল মানুষ সমান অধিকার এবং সমান মর্যাদা পেয়ে থাকে।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মাদাম কুরি");
        hashMap.put("texDes","“যদি তুমি সত্যিকারের মানুষ হতে চাও, তাহলে তুমি অন্যের দুঃখটা বুঝতে পারবে এবং তাদের উপকারের জন্য কাজ করতে পারবে।” ");
        arrayList.add(hashMap);


    }
}