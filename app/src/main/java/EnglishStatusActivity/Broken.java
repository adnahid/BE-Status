package EnglishStatusActivity;

import static android.os.Build.VERSION_CODES;
import static android.os.Build.VERSION_CODES;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.delower.bestatus.DetailsActivity;
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

public class Broken extends AppCompatActivity {
    RecyclerView brokenRecyclerView;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_broken);
        brokenRecyclerView = findViewById(R.id.brokenRecyclerView);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });



        brokenTable();
        finalArrayListTable();


        BrokenAdapter brokenAdapter = new BrokenAdapter();
        brokenRecyclerView.setAdapter(brokenAdapter);
        brokenRecyclerView.setLayoutManager(new LinearLayoutManager(Broken.this));

    }

    private class BrokenAdapter extends RecyclerView.Adapter{

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
                View view1 = layoutInflater.inflate(R.layout.engitemlayout, parent, false);
                return new readHolder(view1);
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
                            Toast.makeText(Broken.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(Broken.this, "ca-app-pub-8411075266548653/2415594861")
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
    private void brokenTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- George Eliot");
        hashMap.put("texDes","No man cen be wise on empty stomach\n" +
                "- ক্ষুধার্ত পেটে বুদ্ধিমান থাকা যায়না। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- William Sexpear");
        hashMap.put("texDes","Frailty the name is woman \n" +
                "– নারীর আরেক নাম দুর্বলতা");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Redwan Masood");
        hashMap.put("texDes","“Everyone wants to be happy, but wanting to be happy in some people’s lives is the cause of sadness.”\n" +
                "“সুখে থাকতে সবাই চায়, কিন্তু কিছু মানুষের জীবনে সুখে থাকতে চাওয়াই দুঃখের কারণ হয়ে দাঁড়ায়।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Crying is a way your eyes speak when your mouth can’t explain how broken your heart is. \uD83D\uDE22\uD83D\uDDA4\n" +
                "কাঁদা টা হল আপনার চোখের মাধ্যমে কথা বলা, যখন আপনার মুখ বুঝাতে পারে না যে আপনার হৃদয় কতটা ভেঙ্গেছে। \uD83D\uDE22\uD83D\uDDA4");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","The worst kind of sadness is not being able to explain why\uD83D\uDC94\n" +
                "সবচেয়ে খারাপ ধরণের দু:খ হ'ল কারণ ব্যাখ্যা করতে পারা না\uD83D\uDC94");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Sometimes, breakups are the bitter pills that cure you of toxic relationships\uD83D\uDE22\n" +
                "কখনও, বিচ্ছেদ সেই কাড়া ওষুধ যা আপনাকে বিষাক্ত সম্পর্ক থেকে নিখুঁত করে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Heavy heart, empty soul \uD83D\uDC93\n" +
                "\"ভারী হৃদয়, শূন্য আত্মা \uD83D\uDC93");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","A heart in pieces, a soul in pain.\n" +
                "টুকরো টুকরো হৃদয়, ব্যাথায় আত্মা।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","When happiness feels out of reach.\n" +
                "সুখের অভাবে মন বিচ্ছিন্ন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Lost in a world of gray.\n" +
                "ধুঁইলা বিশ্বে হারিয়ে গেছি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Rainy days match my mood \uD83C\uDF27\uFE0F\n" +
                "বৃষ্টির দিন আমার মনের অবস্থার মতো \uD83C\uDF27\uFE0F");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Grieving the loss of what once was.\n" +
                "যে একসময় ছিল, তার অপেক্ষায় বেদনা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Pain too deep for words.\n" +
                "শব্দের জন্য অত্যন্ত ব্যথা।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Empty spaces, empty heart. \n" +
                "খালি স্থান, খালি হৃদয়।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Everyone goes through phases and all, don’t they?\n" +
                "প্রত্যেকেই পর্যায়ক্রমে এবং সমস্ত কিছুর মধ্য দিয়ে যায়, তাই না?");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Getting flashbacks suck when they’re memories that you’ve always wanted to forget.\n" +
                "ফ্ল্যাশব্যাক পাওয়া খুব কষ্টকর যখন সেগুলি সেই স্মৃতি যা আপনি সবসময় ভুলে যেতে চেয়েছিলেন।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes", "I need a break from my own thoughts.\n" +
                "আমার নিজের চিন্তা থেকে বিরতি দরকার।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Just because I can carry my troubles well doesn’t mean I don’t feel the crushing weight.\n" +
                "আমি আমার কষ্টগুলো ভালোভাবে বহন করতে পারি তার মানে এই নয় যে আমি ক্রাশিং ওয়েট অনুভব করি না।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes", "Let the tears come and water your soul.\n" +
                "অশ্রু আসুক এবং আপনার আত্মা জল.");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- George Eliot");
        hashMap.put("texDes","Bad things will always happen, and there’s absolutely nothing we can do about it.\n" +
                "খারাপ জিনিস সবসময় ঘটবে, এবং এটি সম্পর্কে আমরা কিছুই করতে পারি না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Clouds let rain go when they get too heavy. The same is true for humans.\n" +
                "মেঘ খুব ভারী হয়ে গেলে বৃষ্টি ছেড়ে দেয়। মানুষের ক্ষেত্রেও একই কথা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Crying is not a sign of weakness. Even strong people cry, especially when they’ve been strong for too long.\n" +
                "কান্না দুর্বলতার লক্ষণ নয়। এমনকি শক্তিশালী লোকেরাও কাঁদে, বিশেষ করে যখন তারা খুব বেশি দিন ধরে শক্তিশালী থাকে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Don’t say goodbye. Goodbye is to leave, as to leave is to eventually forget.\n" +
                "বিদায় বলবেন না। বিদায় মানে চলে যাওয়া, যেমন চলে যাওয়া মানে অবশেষে ভুলে যাওয়া।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Even the strongest feelings become void when disregarded and taken for granted.\n" +
                "এমনকি সবচেয়ে শক্তিশালী অনুভূতি শূন্য হয়ে যায় যখন উপেক্ষা করা হয় এবং মঞ্জুর করা হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Every person has a secret sorrow that the world doesn’t know about.\n" +
                "প্রত্যেক ব্যক্তির একটি গোপন দুঃখ আছে যা বিশ্ব জানে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Redwan Masood");
        hashMap.put("texDes","Feeling too much always ends in feeling nothing.\n" +
                "অত্যধিক অনুভূতি সবসময় কিছুই অনুভব করার মধ্যে শেষ হয়।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Redwan Masood");
        hashMap.put("texDes","For the most part, it is by losing what we love that we learn how to appreciate them.\n" +
                "বেশিরভাগ ক্ষেত্রে, আমরা যা ভালোবাসি তা হারানোর মাধ্যমে আমরা তাদের প্রশংসা করতে শিখি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Happiness makes one enjoy the music. Sadness, on the other hand, makes one understand the lyrics.\n" +
                "সুখ একজনকে সঙ্গীত উপভোগ করে। অন্যদিকে, দুঃখ একজনকে গানের কথা বুঝতে সাহায্য করে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Happiness would be meaningless if sadness didn’t exist.\n" +
                "দুঃখের অস্তিত্ব না থাকলে সুখ অর্থহীন হবে।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Love is priceless, yet we often pay heavily for it.\n" +
                "ভালবাসা অমূল্য, তবুও আমরা প্রায়শই এর জন্য ভারী মূল্য দিতে পারি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Our eyes can speak. It is almost always futile to try and hide our feelings.\n" +
                "আমাদের চোখ কথা বলতে পারে। আমাদের অনুভূতি লুকানোর চেষ্টা করা প্রায় সবসময়ই বৃথা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Sadness is like an ocean. Frequently, we drown. But sometimes, we are forced to swim.\n" +
                "দুঃখ একটা সাগরের মত। প্রায়শই, আমরা ডুবে যাই। কিন্তু কখনও কখনও, আমরা সাঁতার কাটা বাধ্য হয়.");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Someone who overthinks is someone who overloves.\n" +
                "Sure, closing your eyes will shut away the things that you don’t want to see. But closing your heart doesn’t mean you won’t feel anything anymore.\n" +
                "অবশ্যই, আপনার চোখ বন্ধ করা জিনিসগুলিকে বন্ধ করে দেবে যা আপনি দেখতে চান না। তবে আপনার হৃদয় বন্ধ করার অর্থ এই নয় যে আপনি আর কিছুই অনুভব করবেন না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- George Eliot");
        hashMap.put("texDes","Tears are words that the heart can’t say.\n" +
                "অশ্রু এমন শব্দ যা হৃদয় বলতে পারে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Ultimately, we can only blame ourselves.\n" +
                "শেষ পর্যন্ত, আমরা কেবল নিজেদের দোষ দিতে পারি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-  Redwan Masood");
        hashMap.put("texDes","We distance ourselves to keep the sadness away, but it also prevents the happiness from coming in.\n" +
                "আমরা দুঃখকে দূরে রাখার জন্য নিজেদেরকে দূরে রাখি, কিন্তু এটি সুখকে আসতে বাধা দেয়।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-Unknow");
        hashMap.put("texDes","Never love with all your heart, it only ends in aching.");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Broken heart will turn into a stronger one within hope.");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","I look to the past, where fragments of forgotten moments illuminate my present.");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Through my sadness, my art truly comes alive.");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","I find my solace in dark hues and tear-stained canvases.");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","In our shared sadness, we find understanding and compassion.");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Love when you’re ready,\n" +
                "not when you are alone.");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes", "\"I don't know when I got so busy that I stop giving time to my love.\"");
        arrayList.add(hashMap);

    }
}