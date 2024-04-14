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

import com.example.statusapplication.DetailsActivity;
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

public class Love extends AppCompatActivity {

    RecyclerView beautifulRecyclerView;

    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.love);
        beautifulRecyclerView = findViewById(R.id.beautifulRecyclerView);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });



        beautifulTable();
        finalArrayListTable();


        BeautiAdapter ba = new BeautiAdapter();
        beautifulRecyclerView.setAdapter(ba);
        beautifulRecyclerView.setLayoutManager(new LinearLayoutManager(Love.this));


    }

    private class BeautiAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(Love.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(Love.this, "ca-app-pub-3940256099942544/2247696110")
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
    private void beautifulTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Estee Lauder");
        hashMap.put("texDes","It is impossible to love and to be wise\n" +
                "- একই সাথে প্রেম করা ও জ্ঞানী হওয়া অসম্ভব।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- William Wordsworth");
        hashMap.put("texDes","The child is father of the man \n" +
                "– ঘুমিয়ে আছে শিশুর পিতা সব শিশুরেই অন্তরে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Anne Frank");
        hashMap.put("texDes","“Whoever is happy will make others happy too.” \n" +
                "“যে সুখী সে অন্যকেও সুখী করবে” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Herman Melville");
        hashMap.put("texDes","“It is better to fail in originality than to succeed in imitation.” \n" +
                "“অনুকরণে সফল হওয়ার চেয়ে মৌলিকতায় ব্যর্থ হওয়া ভাল।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Confucius");
        hashMap.put("texDes","“Everything has beauty, but not everyone sees it.”\n" +
                "“সব কিছুরই সৌন্দর্য আছে তবে সবাই তা দেখে না।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Loretta Young");
        hashMap.put("texDes", "“Love isn’t something you find. Love is something that finds you.”\n" +
                "“ভালবাসা এমন কিছু নয় যা আপনি খুঁজে পান। ভালবাসা এমন একটি বিষয় যা আপনাকে খুঁজে পায়।”");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Redwan Masood");
        hashMap.put("texDes","“The Love in which there is a fear of losing\n" +
                "And they both cry thinking about it\n" +
                "That love is real love.”\n" +
                "“যে ভালোবাসার মাঝে হারানোর ভয় থাকে\n" +
                "আর সে কথা ভেবে দুজনেই কাঁদে\n" +
                "সে ভালোবাসা হচ্ছে প্রকৃত ভালোবাসা”।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes", "“You know you're in love when you can't fall asleep because reality is finally better than your dreams.”\n" +
                "\"আপনি কখন ভালোবাসেন তা বুঝতে পারেন যখন রাত্রিতে ঘুমাতে পারেন না কারণ বাস্তবতা স্বপ্নের চেয়ে অবশেষে ভালো হয়েছে।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","― Dr. Seuss");
        hashMap.put("texDes","“You know you're in love when you can't fall asleep because reality is finally better than your dreams.”\n" +
                "\"আপনি কখন ভালোবাসেন তা বুঝতে পারেন যখন রাত্রিতে ঘুমাতে পারেন না কারণ বাস্তবতা স্বপ্নের চেয়ে অবশেষে ভালো হয়েছে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","― Gayle Forman");
        hashMap.put("texDes","“I'm not sure this is a world I belong in anymore. I'm not sure that I want to wake up.”\n" +
                "\"আমি নিশ্চিত নই আমি আর এই পৃথিবীতে অংশীদার হতে চাই। আমি নিশ্চিত নই যে আমি আর উঠতে চাই।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","“Love is not about how many days, but how much love you bring to each day.”\n" +
                "\"ভালোবাসা কত দিনের জন্য নয়, তবে আপনি প্রতিদিন কতটা ভালবাসা আনেন তা নিয়ে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","“Forever is a long time, but I wouldn’t mind spending it by your side.”\n" +
                "\"চিরকালের জন্য একটি দীর্ঘ সময়, তবে আমি এটি আপনার পাশে ব্যয় করতে আপত্তি করব না।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes", "“Every time I see you, I fall in love all over again.”\n" +
                "\"যতবার আমি তোমাকে দেখি, আমি আবার প্রেমে পড়ে যাই।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","“True love does not have a happy ending, because true love never ends.”\n" +
                "\"সত্যিকারের ভালোবাসার কোনো সুখের সমাপ্তি হয় না, কারণ সত্যিকারের ভালোবাসা কখনো শেষ হয় না।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes", "“Real love stories never have endings.”\n" +
                "\"সত্যিকারের প্রেমের গল্পের শেষ নেই।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," - William Faulkner.");
        hashMap.put("texDes","\"You don't love because, you love despite; not for the virtues, but despite the faults.\"\n" +
                "\"তুমি ভালোবাসো না কারণ, তবুও ভালোবাসো; গুণের জন্য নয়, দোষ থাকা সত্ত্বেও।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Virginia Woolf.");
        hashMap.put("texDes","\"Just in case you ever foolishly forget; I'm never not thinking of you.\" \n" +
                "\"যদি তুমি কখনো বোকামি করে ভুলে যাও; আমি কখনই তোমার কথা ভাবছি না।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Mark Twain");
        hashMap.put("texDes","“To get the full value of a joy you must have somebody to divide it with.” \n" +
                "\"একটি আনন্দের পূর্ণ মূল্য পেতে আপনার অবশ্যই এটিকে ভাগ করার জন্য কাউকে থাকতে হবে।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Oscar Wilde");
        hashMap.put("texDes","“Who, being loved, is poor?” \n" +
                "\"কে, পছন্দ হচ্ছে, দরিদ্র?\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Henry David Thoreau");
        hashMap.put("texDes","“There is no remedy for love but to love more.” \n" +
                "\"ভালোবাসার কোন প্রতিকার নেই কিন্তু আরো ভালবাসার জন্য।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Oscar Wilde");
        hashMap.put("texDes","“The heart was made to be broken.” \n" +
                "\"হৃদয় ভাঙার জন্য তৈরি করা হয়েছিল.\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Tennyson");
        hashMap.put("texDes","“‘Tis better to have loved and lost than never to have loved at all.”\n" +
                "\"'কখনো প্রেম না করার চেয়ে ভালোবেসে যাওয়া এবং হারিয়ে যাওয়া ভালো।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Honore de Balzac");
        hashMap.put("texDes","“A woman knows the face of the man she loves as a sailor knows the open sea.”\n" +
                "\"একজন মহিলা তার ভালবাসার মানুষটির মুখ চেনেন যেমন একজন নাবিক খোলা সমুদ্রকে জানেন।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Victor Hugo");
        hashMap.put("texDes","”Life is the flower for which love is the honey.”\n" +
                "\"জীবন হল সেই ফুল যার জন্য ভালবাসা হল মধু।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Henry Miller");
        hashMap.put("texDes","“The one thing we can never get enough of is love. And the one thing we never give enough of is love.”\n" +
                "\"একটি জিনিস যা আমরা কখনই যথেষ্ট পেতে পারি না তা হল ভালবাসা। এবং একটি জিনিস যা আমরা কখনই যথেষ্ট দেই না তা হল ভালবাসা।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","G. K. Chesterton");
        hashMap.put("texDes","“The way to love anything is to realize that it may be lost.”\n" +
                "\"যেকোনো কিছুকে ভালোবাসার উপায় হল এটি হারিয়ে যেতে পারে তা উপলব্ধি করা।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Stendhal");
        hashMap.put("texDes","“The richest love is that which submits to the arbitration of time.”\n" +
                "\"সবচেয়ে ধনী ভালোবাসা সেটাই যা সময়ের সালিশের কাছে নতি স্বীকার করে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Henry David Thoreau");
        hashMap.put("texDes","“A very small degree of hope is sufficient to cause the birth of love.”\n" +
                "\"ভালোবাসার জন্মের জন্য খুব সামান্য পরিমাণ আশাই যথেষ্ট।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Leo Tolstoy");
        hashMap.put("texDes","“The supreme happiness in life is the conviction that we are loved.”\n" +
                "\"জীবনের সর্বোচ্চ সুখ হল এই প্রত্যয় যে আমরা ভালবাসি।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Francois Mauriac");
        hashMap.put("texDes","“To love someone is to see a miracle invisible to others.”\n" +
                "\"কাউকে ভালবাসা মানে অন্যের কাছে অদৃশ্য অলৌকিক ঘটনা দেখা।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Your smile makes me smile.\n" +
                "তোমার হাসি আমাকে হাসায়।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Only for you, I know the true meaning of love.\n" +
                "শুধু তোমার জন্য, আমি জানি ভালোবাসার প্রকৃত অর্থ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","One thing I’ve tried but can’t hide, is my love for you!\n" +
                "একটা জিনিস আমি চেষ্টা করেছি কিন্তু লুকাতে পারিনি, তা হল তোমার প্রতি আমার ভালোবাসা!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","If I only knew how much love would cost me, I’d never love anyone.\n" +
                "যদি আমি জানতাম যে ভালবাসার জন্য আমার কত দাম হবে, আমি কখনই কাউকে ভালবাসতাম না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","I climb into the dark for you, Are you waiting in the stars for me?\n" +
                "আমি তোমার জন্য অন্ধকারে আরোহণ, তুমি কি আমার জন্য তারায় অপেক্ষা কর?");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","To fall in love is awfully simple, but to fall out of love is simply awful.\n" +
                "প্রেমে পড়া খুবই সহজ, কিন্তু প্রেমে পড়াটা খুবই ভয়ঙ্কর।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","forget me.\n" +
                "আমাকে ভুলে যাও");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Ever has it been that love knows not its own depth until the hour of separation.\n" +
                "কখনও এমন হয়েছে যে প্রেম বিচ্ছেদের সময় পর্যন্ত নিজের গভীরতা জানে না।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Life doesn’t like drama. So, don’t be over-emotional.\n" +
                "জীবন নাটক পছন্দ করে না। সুতরাং, অতিরিক্ত আবেগপ্রবণ হবেন না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Just when you thought everything would be perfect, everything falls apart.\n" +
                "আপনি যখন ভেবেছিলেন সবকিছু নিখুঁত হবে, তখনই সবকিছু ভেঙ্গে পড়ে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Beth Revis");
        hashMap.put("texDes","“And in her smile I see something more beautiful than the stars.” \n" +
                "\"এবং তার হাসিতে আমি তারার চেয়েও সুন্দর কিছু দেখতে পাই।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Agatha Christie");
        hashMap.put("texDes","“It is a curious thought, but it is only when you see people looking ridiculous that you realize just how much you love them.”\n" +
                "\"এটি একটি কৌতূহলী চিন্তা, কিন্তু আপনি যখন লোকেদের হাস্যকর দেখতে দেখেন তখনই আপনি বুঝতে পারেন যে আপনি তাদের কতটা ভালোবাসেন।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Loretta Young");
        hashMap.put("texDes","“Love isn’t something you find. Love is something that finds you.” \n" +
                "\"ভালবাসা এমন কিছু নয় যা আপনি খুঁজে পান। ভালবাসা এমন কিছু যা আপনাকে খুঁজে পায়।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","I smile whenever I think about you\n" +
                "তোমার কথা মনে হলেই হাসি");
        arrayList.add(hashMap);

    }
}