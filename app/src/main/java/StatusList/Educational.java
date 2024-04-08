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

public class Educational extends AppCompatActivity {

    RecyclerView educationRecyclerView;
    ImageView educationImageBack;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_educational);
        educationRecyclerView = findViewById(R.id.educationRecyclerView);
        educationImageBack = findViewById(R.id.educationImageBack);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        educationImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Educational.this, MainActivity.class);
                startActivity(intent);
            }
        });

        educationTable();
        finalArrayListTable();


        EducationalAdapter educationalAdapter = new EducationalAdapter();
        educationRecyclerView.setAdapter(educationalAdapter);
        educationRecyclerView.setLayoutManager(new LinearLayoutManager(Educational.this));

    }

    private class EducationalAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(Educational.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(Educational.this, "ca-app-pub-3940256099942544/2247696110")
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
    private void educationTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-আল কুরআন।");
        hashMap.put("texDes","পড় তোমার প্রভুর নামে, যিনি তোমাকে সৃষ্টি করেছেন।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","আজ তুমি যেখানে আছো,\n" +
                "সেটা তোমার অতীতের কর্মফল।\n" +
                "কিন্তু কাল তুমি যেখানে পৌঁছাবে,\n" +
                "সেটা তোমার আজকের কর্মফল।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","একজন শিক্ষার্থী হতে মানবতা ও পৃথিবীর প্রতি দায়িত্ববোধ করুন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","\"শিক্ষা একটি পূর্ণব্যক্তি তৈরি করে, একজন চিন্তাশীল, একজন সমৃদ্ধি সৃষ্টিকারী।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","\"শেখা হলো আমাদের মনোবল এবং আত্মবিশ্বাসের চোখ খোলা রাখার একটি উপায়।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","\"শিক্ষা তখনই পূর্ণ হয়, যখন আমরা শেখার প্রক্রিয়ায় রুচি নিয়ে থাকি।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","\"জ্ঞানের সফল অনুভূতি হলো একটি সহজ উদ্দীপনা, এবং সততা একটি সংশ্লিষ্ট যাত্রা।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","\"শিক্ষার মাধ্যমে আমরা নতুন দৃষ্টিকোণ অর্জন করতে পারি এবং বিশ্ববিদ্যালয়ের বাইরে পৃথিবীর শিক্ষার বিস্তারিত অধ্যয়ন করতে পারি।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","\"শিক্ষা নতুন হয়ে উঠার সুযোগ প্রদান করে, এবং আপনি শেখা এবং উন্নত করার সময় অমূল্য অভিজ্ঞান অর্জন করতে পারেন।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","\"শিক্ষা আপনাকে আপনার লক্ষ্যে পৌঁছানোর দিকে নেতৃত্ব করতে সাহায্য করে এবং আপনি আপনার স্বপ্ন অনুসরণ করতে অনুমতি দেয়।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","\"একজন শিক্ষার্থী হওয়া হলো আত্মনির্ভরশীলতা এবং বৈচিত্র্য সহিত আবিষ্কৃতির পথে চলা।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","\"জ্ঞান একটি শক্তিশালী সরঞ্জাম, এবং শিক্ষার মাধ্যমে আপনি এই সরঞ্জামটি ব্যবহার করতে শেখতে পারেন।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","\"শিক্ষা হলো আত্মা ও মানবিক সমৃদ্ধির উদ্দীপনা, এবং এটি পৃথিবীকে একটি উজ্জ্বল ভবিষ্যতে পথ দেখায়।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","জ্ঞানী হও,\n" +
                "তবে অহংকারী হয়ো না।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—জন এ শেড");
        hashMap.put("texDes","আগুন দিয়ে যেমন লোহা চেনা যায়\n" +
                "তেমনি মেধা দিয়ে মানুষ চেনা যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—লর্ড হ্যলি ফক্স!");
        hashMap.put("texDes","সেই সত্যিকারের মানুষ যে\n" +
                "অন্যের দোষত্রুটি নিজেকে\n" +
                "দিয়ে বিবেচনা করতে পারে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—উইলিয়াম ল্যাংলয়েড!");
        hashMap.put("texDes", "যেখানে পরিশ্রম নেই\n" +
                "সেখানে সাফল্যতাও নেই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—স্বামী বিবেকানন্দ!");
        hashMap.put("texDes", "“যে মানুষ বলে তার আর শেখার কিছু নেই,\n" +
                "সে আসলে মরতে বসেছে।\n" +
                "যত দিন বেঁচে আছো শিখতে থাকো।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—স্বামী বিবেকানন্দ!");
        hashMap.put("texDes","“শিক্ষা হচ্ছে মানুষের মধ্যে ইতিমধ্যে থাকা উৎকর্ষের প্রকাশ।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—স্বামী বিবেকানন্দ!");
        hashMap.put("texDes","“অন্য কারোর জন্য অপেক্ষা করো না,\n" +
                "তুমি যা করতে পারো সেটা করো\n" +
                "কিন্তু অন্যের উপর আশা করো না”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—রবীন্দ্রনাথ ঠাকুর");
        hashMap.put("texDes","“মনুষ্যত্বের শিক্ষাটাই চরম শিক্ষা আর সমস্তই তার অধীন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","অন্যের ভুল থেকে শিখুন,\n" +
                "কারণ জীবন এত বড় নয় যে\n" +
                "আপনি নিজে সব ভুল করে শিক্ষা নিবেন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—আলবার্ট আইনস্টাইন");
        hashMap.put("texDes","“স্কুলে যা শেখানো হয়, তার সবটুকুই ভুলে যাবার পর যা থাকে, তাই হলো শিক্ষা।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– নেলসন ম্যান্ডেলা!");
        hashMap.put("texDes","“শিক্ষা হল সবচেয়ে শক্তিশালী অস্ত্র যা বিশ্বকে পরিবর্তন করতে পারে।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- আইন্সটাইন!");
        hashMap.put("texDes","ভুলের মাঝে যে নতুন কিছু শেখা যায় যে তা জানে না সে নতুন কিছু শিখে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– এরিস্টটল");
        hashMap.put("texDes","শিক্ষিত হওয়ার চাইতে সুশিক্ষিত হয়ে গড়ে ওঠাই আসল শিক্ষা। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– এরিস্টটল!");
        hashMap.put("texDes","শিক্ষা অর্জন অনেকটা তেতো তবে এর ফল অনেক মিষ্টি। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– শেক্সপিয়ার");
        hashMap.put("texDes","আপনার জ্ঞানের পরিধির চাইতে কম কথা বলা উচিত বেশি জানার আগ্রহ থাকা উচিত।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– বুদ্ধ");
        hashMap.put("texDes","প্রতিটি ভুল পদক্ষেপ নতুন কিছু শেখার বার্তা দিয়ে যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সক্রেটিস");
        hashMap.put("texDes","অপেক্ষার ফল মিষ্টি হয়, তাড়াহুড়ো করলেই জীবনে হেরে যাওয়ার সম্ভবনা থাকে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","শুধুমাত্র টাকার জন্য শিক্ষিত হওযার চাইতে অশিক্ষিত থাকা ভাল।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","টিয়া পাখির মতো মুখস্ত করে বড় বড় সার্টিফিকেট অর্জন করে বড় বড় চাকরি পাওয়াকে শিক্ষা বলে না, শিক্ষা হচ্ছে সেটা যা একজন মানুষের ভিতরের কুশিক্ষাকে দূর করে সমাজের পরিবর্তনে এগিয়ে আসার উৎসাহ যোগায়।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","কথা বলতে শক্তির প্রয়োজন হয় না,\n" +
                "শক্তির প্রয়োজন হয় চুপ থাকতে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","\"শিক্ষার মাধ্যমে আমরা আপনার মানবতা ও জ্ঞানের সীমা পার করতে পারি।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","\"শিক্ষা হলো আলোর পথে একটি আলোককণ্যা, যা অন্ধকারের ভীতর জ্ঞানের আলো ছুঁড়ে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes","\"আপনি সবসময় শেখা শুরু করতে পারেন, কারণ শেখা আপনাকে আগামীর দিকে নিয়ে যায়।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-নেপোলিয়ন বোনাপার্ট।");
        hashMap.put("texDes","আমাকে শিক্ষিত মা দাও, আমি তোমাকে শিক্ষিত জাতি দিবো।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-আহমদ ছফা।");
        hashMap.put("texDes","বড় বড় নামকরা স্কুলে বাচ্চারা বিদ্যার চাইতে অহংকার টা বেশি শিক্ষা করে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-রেদোয়ান মাসুদ");
        hashMap.put("texDes","সার্টিফিকেট বাড়ছে মানেই এই নয় যে দেশ ভারমুক্ত হচ্ছে, এ দেশে সার্টিফিকেট বাড়ছে মানে শিক্ষিত বেকারের সংখ্যাটাও ভারী হচ্ছে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-শেখ সাদি");
        hashMap.put("texDes","অজ্ঞের পক্ষে নীরবতাই হচ্ছে সবচেয়ে উত্তম পন্থা। এটা যদি সবাই জানত তাহলে কেউ অজ্ঞ হত না।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-শেখ সাদি।");
        hashMap.put("texDes","একজন ঘুমন্ত ব্যক্তি আরেকজন ঘুমন্ত ব্যক্তিকে জাগ্রত করতে পারে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-আল কুরআন।");
        hashMap.put("texDes","ধর্ম ও নৈতিকতার শিক্ষা সন্তানের জন্য সবচেয়ে বড় সম্পদ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– আল্লামা ইকবাল");
        hashMap.put("texDes","প্রতিটি বিদ্যালয়ের শিক্ষকের একজন মানবাত্মা গঠনকারী মিস্ত্রী হওয়া উচিৎ, এতে করেই সেই শিক্ষক একজন উত্তম শিক্ষকে পরিণত হতে পারেন।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– জন পোর্টার");
        hashMap.put("texDes","প্রতিটি শিশুর জীবনে তার একজন উত্তম শিক্ষকের পালনীয় ভূমিকার গুরুত্ব অপরিসীম।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– রেভারথি");
        hashMap.put("texDes","প্রতিটি শিশুর জন্য তার সর্বশ্রেষ্ঠ শিক্ষক হলো তার মা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– এইচ জি অয়েলস");
        hashMap.put("texDes","একজন শিক্ষকের উপরেই তার বিদ্যালয়ের প্রায় সব ছাত্রদের ভবিষ্যতের দায়িত্ব থেকে যায় যা অনেক বড় একটি ব্যাপার, এটিকে কখনোই অবহেলা করা উচিৎ হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– তোফাজ্জল হোসেন");
        hashMap.put("texDes","প্রতিটি মানুষের জীবনেই শিক্ষকের একটি অত্যন্ত বড় প্রভাব রয়েছে যা অনন্তকালে গিয়েও শেষ হয়না।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– মুরাত ইলদান");
        hashMap.put("texDes","প্রত্যেক মানুষের কাছে তার নিজের জীবনের অভিজ্ঞতা যেকোনো শিক্ষক বা স্কুল অপেক্ষা বড়।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes", "একটি জাতিকে নির্ভুলভাবে গড়ে তোলার সবথেকে দক্ষ কারিগর হলেন সাধারণ একজন শিক্ষক মাত্র।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– জ্যাক ওয়েলচ");
        hashMap.put("texDes","আমি শিখেছি ভুলগুলি সাফল্যের মতো প্রায়শই একজন ভাল শিক্ষক হতে পারে!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","–আলবার্ট আইনস্টাইন");
        hashMap.put("texDes","সৃজনশীল প্রকাশ এবং জ্ঞানে আনন্দ জাগ্রত করা শিক্ষকের সর্বোচ্চ শিল্প!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","বিল গেটস");
        hashMap.put("texDes","সাফল্য একটি পরিপূর্ণ শিক্ষক। এটি স্মার্ট মানুষের চিন্তায় তারা কখনো ব্যর্থ হবে না এটি ঢুকিয়ে দেয়। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","–দীপক চোপড়া");
        hashMap.put("texDes","নির্জনতা মহান শিক্ষক, এবং এর পাঠগুলি শিখতে আপনাকে অবশ্যই এতে মনোযোগ দিতে হবে !!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","–মেহমেট মুরাত ইলদান !");
        hashMap.put("texDes","আপনার নিজের অভিজ্ঞতার চেয়ে মূল্যবান কোনও স্কুল বা শিক্ষক নেই। ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","বিল গেটস !!");
        hashMap.put("texDes","প্রযুক্তি কেবল একটি সরঞ্জাম। বাচ্চাদের এক সাথে কাজ করার এবং তাদের অনুপ্রেরণার দিক থেকে শিক্ষক সবচেয়ে গুরুত্বপূর্ণ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— নিকোলাস খালব্রাঁশ");
        hashMap.put("texDes","কোনো প্রত্যয় অর্জন করার জন্য আমাদের ভাবনার প্রয়োজন, ঠিক যেমনি চোখের আলোর প্রয়োজন দেখার জন্য।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ক্রিস্টোফার মর্লি!");
        hashMap.put("texDes","নতুন জানার যেমন যন্ত্রণা আছে, তেমনি আনন্দও আছে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— শেলী");
        hashMap.put("texDes","আমরা যতই অধ্যয়ন করি ততই আমাদের অজ্ঞতাকে আবিষ্কার করি।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes", "এদেশের শিক্ষা থেকে রাজনীতি দূর হয়নি কিন্তু রাজনীতি থেকে শিক্ষাটা দূর হয়ে গেছে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— টেনিসন");
        hashMap.put("texDes","ভালো জেনেও না মানার চেয়ে, না জানা ভালো।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— আল হাদিস!");
        hashMap.put("texDes","জ্ঞানীর নিন্দা, মূর্খের উপাসনা অপেক্ষা শ্রেয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— আল হাদিস!");
        hashMap.put("texDes","বিদ্বানের কলমের কালি শহীদের রক্তের চেয়েও দামি।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সংগৃহীত!");
        hashMap.put("texDes"," শিক্ষার উদ্দেশ্যেগুলোর মধ্যে জ্ঞান অর্জনের চেয়েও মানবিকতা জাগিয়ে তোলা গুরুত্বপূর্ণ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— প্রমথ চৌধুরী!");
        hashMap.put("texDes", "যিনি যথার্থ গুরু তিনি শিষ্যের আত্নাকে উদ্বোধিত করেন এবং তার অন্তর্নিহিত সকল প্রচ্ছন্ন শক্তিকে ব্যাক্ত করে তোলেন। সেই শক্তির বলে শিষ্য নিজের মন নিজে গড়ে তোলে, নিজের অভিমত বিদ্যা নিজে অর্জন করে বিদ্যার সাধনা শিষ্যকে নিজে অর্জন করতে হয়। গুরু উত্তরসাধক মাত্র।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সর্বপল্লী রাধাকৃষ্ণণ");
        hashMap.put("texDes","”সত্যিকারের শিক্ষক তাঁরাই, যাঁরা আমাদের ভাবতে সাহায্য করেন।“");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-আলবার্ট আইনস্টাইন");
        hashMap.put("texDes","”সৃষ্টিশীল প্রকাশ এবং জ্ঞানের মধ্যে আনন্দ জাগ্রত করা হলো শিক্ষকের সর্বপ্রধান শিল্প।“");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– উইলিয়াম গ্লাসার।");
        hashMap.put("texDes","পৃথিবীতে শুধুমাত্র স্কুল আর জেলই এমন দুটি জায়গা যেখানে কাজ করার জন্য সময়কে প্রাধান্য দেওয়া হয়।");
        arrayList.add(hashMap);

        //67




    }
}