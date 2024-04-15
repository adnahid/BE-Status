package EnglishStatusActivity;

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

public class Other extends AppCompatActivity {
    RecyclerView struggleRecyclerView;

    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_struggle);
        struggleRecyclerView = findViewById(R.id.struggleRecyclerView);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });



        struggleTable();
        finalArrayListTable();


        StruggleAdapter struggleAdapter = new StruggleAdapter();
        struggleRecyclerView.setAdapter(struggleAdapter);
        struggleRecyclerView.setLayoutManager(new LinearLayoutManager(Other.this));

    }

    private class StruggleAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(Other.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(Other.this, "ca-app-pub-3940256099942544/2247696110")
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

            if ( x>1 && x%40==0){
                hashMap = new HashMap<>();
                hashMap.put("itemType","NativeAd");
                finalArrayList.add(hashMap);
            }

            hashMap = arrayList.get(x);
            finalArrayList.add(hashMap);
        }

    }


    // detailsTable
    private void struggleTable(){

        arrayList = new ArrayList<>();


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","John Milton");
        hashMap.put("texDes","Childhood shows the man \n" +
                "– শিশুকালেই মানুষের প্রতিচ্ছবি বোঝা যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- John Keats");
        hashMap.put("texDes", "Beauty is truth, truth is beauty \n" +
                "– সৌন্দর্যই সত্য, সত্যই সুন্দর।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Heraclitus");
        hashMap.put("texDes","“There is nothing permanent except change.”\n" +
                "“পরিবর্তন ছাড়া স্থায়ী কিছুই নেই।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Socrates");
        hashMap.put("texDes","“The only true wisdom is in knowing you know nothing.” \n" +
                "“একমাত্র সত্য জ্ঞান আপনি কিছুই জানেন না তা জানার মধ্যে রয়েছে।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Nelson Mandela");
        hashMap.put("texDes","“Education is the most powerful weapon which you can use to change the world.”\n" +
                "“শিক্ষা হল সবচেয়ে শক্তিশালী অস্ত্র যা আপনি বিশ্বকে পরিবর্তন করতে ব্যবহার করতে পারেন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Plato");
        hashMap.put("texDes","“The beginning is the most important part of the work.” \n" +
                "“শুরুটি কাজটির সবচেয়ে গুরুত্বপূর্ণ অংশ।” ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Robert H. Schuller");
        hashMap.put("texDes", "“Problems are not stop signs, they are guidelines.” \n" +
                "“সমস্যাগুলি থামার লক্ষণ নয়, তারা নির্দেশিকা।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Democritus");
        hashMap.put("texDes", "“Happiness resides not in possessions, and not in gold, happiness dwells in the soul.”\n" +
                "“সুখ সম্পদের মধ্যে থাকে না, সোনায় নয়, সুখ আত্মায় বাস করে।”");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Albert Einstein");
        hashMap.put("texDes","“Reality is just an illusion, although it is very permanent.” \n" +
                "“বাস্তবতা নিছক একটি বিভ্রম, যদিও এটি খুব স্থায়ী।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Subhas Chandra Bose");
        hashMap.put("texDes","“Freedom is not given – it is taken.”\n" +
                "“স্বাধীনতা দেওয়া হয় না- নেওয়া হয়।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Ramakrishna");
        hashMap.put("texDes","“When the flower blooms, the bees come uninvited.” \n" +
                "“যখন ফুল ফোটে, মৌমাছিরা অনামন্ত্রণে আসে।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","― Swami Vivekananda");
        hashMap.put("texDes","“In a conflict between the heart and the brain, follow your heart. ”\n" +
                "“হৃদয় এবং মস্তিষ্কের মধ্যে দ্বন্দ্বে, আপনার হৃদয়কে অনুসরণ করুন। “ ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Ramakrishna");
        hashMap.put("texDes","“If you want to go east, don’t go west.” \n" +
                "“পূর্ব দিকে যেতে চাইলে পশ্চিমে যাবেন না। “");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","");
        hashMap.put("texDes", "You have seen pigeons, you have not seen pigeon traps.\n" +
                "ঘুঘু দেখেছো ঘুঘুর ফাঁদ দেখোনি।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","A friend in bad times is a real friend.\n" +
                "অসময়ের বন্ধুই প্রকৃত বন্ধু।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Sometimes I cry to stop the tears from falling down.\n" +
                "মাঝে মাঝে কান্না থামাতে কাঁদি।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Sadness is but a wall between two gardens.\n" +
                "দু:খ দুটো বাগানের মাঝের দেয়াল মাত্র।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Everyone has a story but my story left me.\n" +
                "প্রত্যেকেরই গল্প আছে কিন্তু আমার গল্প আমাকে ছেড়ে গেছে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— John Lennon");
        hashMap.put("texDes","\"Life is what happens when you're busy making other plans.\" \n" +
                "\"জীবন এমন হয় যখন আপনি অন্য পরিকল্পনা করতে ব্যস্ত থাকেন।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Will Smith");
        hashMap.put("texDes","\"Money and success don’t change people; they merely amplify what is already there.\n" +
                "\"অর্থ এবং সাফল্য মানুষকে পরিবর্তন করে না; তারা কেবল সেখানে যা আছে তা বৃদ্ধি করে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," – Steve Jobs");
        hashMap.put("texDes","\"Your time is limited, so don’t waste it living someone else’s life. Don’t be trapped by dogma – which is living with the results of other people’s thinking.\" \n" +
                "\"আপনার সময় সীমিত, তাই অন্য কারো জীবন যাপন করার জন্য এটিকে নষ্ট করবেন না। মতবাদের ফাঁদে পড়বেন না - যা অন্য মানুষের চিন্তার ফলাফল নিয়ে বেঁচে থাকে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Ernest Hemingway");
        hashMap.put("texDes","“In order to write about life first you must live it.”\n" +
                "\"জীবন সম্পর্কে লিখতে হলে প্রথমে আপনাকে এটিকে বাঁচতে হবে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Ashton Kutcher");
        hashMap.put("texDes","\"Don’t settle for what life gives you; make life better and build something.\" \n" +
                "\"জীবন আপনাকে যা দেয় তার জন্য স্থির হবেন না; জীবনকে আরও ভাল করুন এবং কিছু তৈরি করুন।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Anonymous");
        hashMap.put("texDes","\"Sometimes, you need to be alone. Not to be lonely, but to enjoy your free time being yourself.\" \n" +
                "\"কখনও কখনও, আপনাকে একা থাকতে হবে। একাকী হতে নয়, নিজের অবসর সময়টাকে উপভোগ করতে হবে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Johann Wolfgang von Goethe");
        hashMap.put("texDes","\"The soul that sees beauty may sometimes walk alone.\" \n" +
                "\"যে আত্মা সৌন্দর্য দেখে সে কখনও কখনও একা চলতে পারে।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Mahatma Gandhi");
        hashMap.put("texDes","\"It's easy to stand with the crowd. It takes courage to stand alone.\" \n" +
                "\"ভিড়ের সাথে দাঁড়ানো সহজ। একা দাঁড়াতে সাহস লাগে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—Mother Teresa");
        hashMap.put("texDes","“The most terrible poverty is loneliness and the feeling of being unloved.” \n" +
                "\"সবচেয়ে ভয়ানক দারিদ্র্য হল একাকীত্ব এবং ভালোবাসাহীন হওয়ার অনুভূতি।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—Douglas Coupland");
        hashMap.put("texDes","“The time you feel lonely is the time you most need to be by yourself. Life’s cruelest irony.” \n" +
                "“আপনি যে সময় একাকী বোধ করেন সেই সময়টি আপনার নিজের কাছে থাকা সবচেয়ে বেশি প্রয়োজন। জীবনের নিষ্ঠুরতম বিড়ম্বনা।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Jimmy Carter");
        hashMap.put("texDes","“The bond of our common humanity is stronger than the divisiveness of our fears and prejudices.”\n" +
                "\"আমাদের সাধারণ মানবতার বন্ধন আমাদের ভয় এবং কুসংস্কারের বিভক্তির চেয়ে শক্তিশালী।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","–Gary Snyder");
        hashMap.put("texDes","\"Nature is not a place to visit, it is home.\" \n" +
                "\"প্রকৃতি দেখার জায়গা নয়, এটি বাড়ি।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Maya Angelou");
        hashMap.put("texDes", "\"Do the best you can until you know better. Then when you know better, do better.\" \n" +
                "\"আপনি যতক্ষণ না ভাল জানেন ততক্ষণ পর্যন্ত আপনি যথাসাধ্য করুন। তারপর যখন আপনি আরও ভাল জানেন, তখন আরও ভাল করুন।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","― Albert Einstein");
        hashMap.put("texDes","“Try not to become a man of success. Rather become a man of value.”\n" +
                "\"সাফল্যের মানুষ না হওয়ার চেষ্টা করুন। বরং মূল্যবান মানুষ হয়ে উঠুন।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","\"Embrace the beauty of each moment, cherish the blessings of today, and journey forward with a heart full of gratitude. \uD83C\uDF1F \n" +
                "\"প্রতিটি মুহূর্তের সৌন্দর্যকে আলিঙ্গন করুন, আজকের আশীর্বাদগুলিকে লালন করুন, এবং কৃতজ্ঞতায় পূর্ণ হৃদয় নিয়ে এগিয়ে যান৷ ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","\"Life is short, smile while you still have teeth.\"\n" +
                "জীবন ছোট, দাঁত থাকা অবস্থায় হাসুন।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","\"Do what makes your soul shine.\"\n" +
                "\"যা আপনার আত্মাকে উজ্জ্বল করে তা করুন।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","\"In a world where you can be anything, be kind.\"\n" +
                "\"এমন একটি পৃথিবীতে যেখানে আপনি কিছু হতে পারেন, দয়ালু হন।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","\"Happiness is not by chance, but by choice.\"\n" +
                "\"সুখ সুযোগ দ্বারা নয়, কিন্তু পছন্দ দ্বারা।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","\"Every day may not be good, but there's something good in every day.\"\n" +
                "\"প্রতিটি দিন ভালো নাও হতে পারে, কিন্তু প্রতিদিনের মধ্যে ভালো কিছু থাকে।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","\"Dream big, work hard, stay focused, and surround yourself with good people.\"\n" +
                "\"বড় স্বপ্ন দেখুন, কঠোর পরিশ্রম করুন, মনোনিবেশ করুন এবং নিজেকে ভাল লোকেদের সাথে ঘিরে রাখুন।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","\"The only way to do great work is to love what you do.\n" +
                "\"মহান কাজ করার একমাত্র উপায় হল আপনি যা করেন তা ভালবাসা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","\"Surround yourself with those who make you feel like sunshine. \n" +
                "\"নিজেকে তাদের সাথে ঘিরে রাখুন যারা আপনাকে সূর্যের আলোর মতো অনুভব করে৷");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","\"In the end, we only regret the chances we didn't take.\"\n" +
                "\"শেষ পর্যন্ত, আমরা কেবল সেই সুযোগগুলি গ্রহণ না করার জন্য আফসোস করি।\" '");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","\"Happiness is not something ready-made. It comes from your own actions.\" \n" +
                "\"সুখ রেডিমেড কিছু নয়। এটা আপনার নিজের কাজ থেকে আসে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Eleanor Roosevelt");
        hashMap.put("texDes","\"The future belongs to those who believe in the beauty of their dreams.\" \n" +
                "\"ভবিষ্যত তাদেরই যারা তাদের স্বপ্নের সৌন্দর্যে বিশ্বাস করে।\" ");
        arrayList.add(hashMap);

    }
}