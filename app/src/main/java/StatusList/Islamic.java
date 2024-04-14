package StatusList;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import androidx.cardview.widget.CardView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Islamic extends AppCompatActivity {

    RecyclerView islamicRecyclerView;
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_islamic);
        islamicRecyclerView = findViewById(R.id.islamicRecyclerView);
        toolbar = findViewById(R.id.toolbar);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Islamic.this, MainActivity.class);
                startActivity(intent);
            }
        });


        islamicTable();
        finalArrayListTable();


        IslamicAdapter islamicAdapter = new IslamicAdapter();
        islamicRecyclerView.setAdapter(islamicAdapter);
        islamicRecyclerView.setLayoutManager(new LinearLayoutManager(Islamic.this));
    }

    private class IslamicAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(Islamic.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(Islamic.this, "ca-app-pub-3940256099942544/2247696110")
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
    private void islamicTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা কাহফঃ৫৪");
        hashMap.put("texDes", "আমি এই কুরআনে মানুষকে বিভিন্ন উপকারের মাধ্যমে সব রকম বিষয়বস্তু বুঝিয়েছি। কিন্তু অধিকাংশ লোকই অস্বীকার না করে থাকেনি। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আর রহমানঃ১-৪");
        hashMap.put("texDes", "আল্লাহ কোরআন শেখানোর উদ্দেশ্যে মানব সৃষ্টি করলেন এবং তাকে ভাব প্রকাশ করা শেখালেন। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সুরা নাহল - ১৬:১২৬");
        hashMap.put("texDes","যদি তোমরা প্রতিশোধ গ্রহণ কর, তবে ঐ পরিমাণ প্রতিশোধ গ্রহণ করবে, যে পরিমাণ তোমাদেরকে কষ্ট দেয়া হয়। যদি সবর কর, তবে তা সবরকারীদের জন্যে উত্তম।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সুরা মা’য়ারিজ - ৭০:৩২-৩৫");
        hashMap.put("texDes","যারা তাদের আমানত ও অঙ্গীকার রক্ষা করে, এবং যারা তাদের সাক্ষ্যদানে সরল-নিষ্ঠাবান, এবং যারা তাদের নামাযে যত্নবান, তারাই জান্নাতে সম্মানিত হবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা তালাক- ৬৫:২-৩");
        hashMap.put("texDes","যারা আল্লাহ-সচেতন থাকে, আল্লাহই তাদের ঝামেলা ও অশান্তি থেকে বেরোনোর পথ করে দেন। আর অপ্রত্যাশিত উৎস থেকে জীবনোপকরণ দান করেন। যে আল্লাহর ওপর নির্ভর করে আল্লাহই তার জন্যে যথেষ্ট।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সুরা আল-মায়িদা-:৫:৯০");
        hashMap.put("texDes","হে মুমিনগণ! মদ,জুয়া,মৃর্তি পূজারী বেদী ভাগ্য নির্ণায়ক তিনি নিঃসন্দেহে শয়তানের কাজ। কাজেই তোমারা তা থেকে দৃরে থাক।তাহলে তোমরা সফল হতে পারবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— –(সহিহ বুখারী) ।");
        hashMap.put("texDes", "যে ব্যক্তি (পাপ ও ভিক্ষা করা হতে) পবিত্র থাকতে চায়, আল্লাহ তাকে প্রতি পবিত্র রাখেন");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সহীহ বুখারী) ।");
        hashMap.put("texDes","বান্দার উপর আল্লাহর অধিকার হলো, তারা কেবল তারই আনুগত্য ও দাসত্ব করবে এবং তার সাথে কোন অংশীদার বানাবে না। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (আহমদ) ।");
        hashMap.put("texDes","তুমি মুমিন হবে তখন, যখন তোমার ভালো কাজ তোমাকে আনন্দ দেবে, আর মন্দ কাজ দেবে মনোকষ্ট। ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (আল হাদিস) ");
        hashMap.put("texDes","যার দুটি দিন একই রকম কাটলো, সে ক্ষতিগ্রস্ত হলো। অর্থাৎ” যে আজকের দিনটিকে গতকালের চেয়ে বেশি কাজে লাগাতে পারলো না , সে উন্নতি করতে পারল না ”।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (বুখারী) ");
        hashMap.put("texDes","অর্ধেকটা খেজুর দান করেও তোমরা নিজেদের বাঁচাতে পারো। যদি তাও না থাকে, তবে সুন্দর করে কথা বলো। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সহীহ মুসলিম) ");
        hashMap.put("texDes"," তোমরা একে অন্যের প্রতি হিংসা করো না, ঘৃণা বিদ্বেষ করোনা এবং একে অপরের থেকে মুখ ফিরিয়ে নিয়োনা। ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (আল হাদিস) ");
        hashMap.put("texDes","এক ব্যক্তি রাসুল সাঃ কে সে বলল, আমাকে এমন কিছু শেখান যাতে আমি সুন্দরভাবে জীবন কাটাতে পারি। কিন্তু এমন কঠিন কিছু নয়, যা আমি ভুলে যেতে পারি। রাসূল সাল্লাল্লাহু ওয়া সাল্লাম বললেনঃ “রাগ করোনা”। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (বুখারী) ।");
        hashMap.put("texDes","সব ধরনের দাগ দূর করার জন্য কিছু না কিছু আছে; মনের দাগ দূর করার জন্য আছে আল্লাহর স্মরণ। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সূরা আল ইমরান ৩:৩৯১)");
        hashMap.put("texDes","এই বিশ্বের বিস্ময় ও সৃষ্টি নিয়ে গভীর চিন্তা ভাবনা কর। ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সূরা বাকারা ২:৪৪)");
        hashMap.put("texDes", "সৎ কার্য নিজে সম্পাদন করার পর অন্যদের করতে বল।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (বুখারী) ");
        hashMap.put("texDes","কোন আমল সমূহ আল্লাহর কাছে সবচেয়ে প্রিয়? যার মাধ্যমে আল্লাহর সৃষ্টিকূল উপকৃত হয়। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (আবু দাউদ, তিরমিজি)");
        hashMap.put("texDes","দয়ালুর প্রতি আল্লাহ ও দয়াশীল হোন। তাই, পৃথিবীর মানুষের প্রতি দয়াশীল হও, তাহলে যিনি আসমানে আছেন – তিনি তোমার প্রতি দয়া দেখাবেন। ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (মুসলিম)");
        hashMap.put("texDes","যখন এমন কাউকে দেখবে যাকে তুমি বেশি সম্পদ দেয়া হয়েছে, (তখন আফসোস করার বদলে) এমন মানুষের দিকে তাকাও যাকে কম দেওয়া হয়েছে। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (বুখারী)");
        hashMap.put("texDes","সত্যিকারের জ্ঞানী কারা? – যারা তাদের জ্ঞানকে বাস্তবে কাজে লাগায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (তিরমিজি)");
        hashMap.put("texDes","তুমি যদি পূর্ণভাবে ভরসা করো যেমনটা করা উচিত, তাহলে তিনি অবশ্যই তোমার সব প্রয়োজন পূরণ করবেন, যেমনটা তিনি পাখিদের জন্য করেন। তারা ক্ষুধার্ত হয়ে বাসা থেকে বের হয়, কিন্তু ভরা পেট নিয়ে নীড়ে ফেরে। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (বুখারী)");
        hashMap.put("texDes", "তোমাদের মধ্যে সর্বোত্তম মানুষ তারাই যাদের আচার আচরণ সবচেয়ে ভালো। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (বুখারী)");
        hashMap.put("texDes", "কোন কাজগুলো সর্বোত্তম? – মানুষের মনে খুশির সৃষ্টি করা, ক্ষুধার্তকে খাবার দেয়া, পঙ্গু ও অসুস্থদের সাহায্য করা, দুঃখীদের দুঃখকে হালকা করা, এবং আহতকে যন্ত্রণা থেকে মুক্ত করা। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (আল হাদিস)");
        hashMap.put("texDes","সমস্ত কাজের ফলাফল নির্ভর করে নিয়তের উপর। আর প্রত্যেক ব্যক্তি যা নিয়ত করেছে, তাই পাবে। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ইবনে মাজাহ");
        hashMap.put("texDes","“তোমাদের মধ্যে সে-ই উত্তম, যে তার পরিবার পরিজনের কাছে উত্তম। ”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সহীহ মুসলিম");
        hashMap.put("texDes","“আল্লাহ ততোক্ষণ বান্দাহর সাহায্য করেন, যতোক্ষণ সে তার ভাইকে সহযোগীতা করে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সহীহ বুখারী");
        hashMap.put("texDes", "“যে পবিত্র থাকতে চায় , তাকে আল্লাহ পবিত্র রাখেন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— বুখারী");
        hashMap.put("texDes","“আল্লাহর পথে একটি সকাল কিংবা একটি সন্ধ্যা ব্যয় করা গোটা পৃথিবী এবং পৃথিবীর সমস্ত সম্পদের চেয়ে উত্তম। ”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— তিরমিযী");
        hashMap.put("texDes","“অত্যাচারী শাসকের সামনে সত্য কথা বলা সবচেয়ে বড় জিহাদ। ”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মিশকাত");
        hashMap.put("texDes","“কুরআনকে আঁকড়ে ধরলে কখনো বিপথগামী হবেনা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","ইমাম আহমাদ ইবনে হাম্বল (রাহিমাহুল্লাহ)");
        hashMap.put("texDes"," জ্ঞানের ভিত্তি হলো মহান আল্লাহর প্রতি ভয় (তাকওয়া)।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— উমর ইবনুল খাত্তাব (রাঃ)");
        hashMap.put("texDes","আল্লাহকে ভয় করো, কারণ যে তাকে ভয় করে সে কখনো একাকীত্ব অনুভব করে নাহ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—  ওমর সুলাইমান");
        hashMap.put("texDes","কোন ব্যক্তির তাকওয়া (আল্লাহভীতি) না থাকলে সে যদি বিবাহিত হয় তবুও দৃষ্টিকে সংযত করে না। তাকওয়াসম্পন্ন মানুষ যদি অবিবাহিতও থাকে, সে তার দৃষ্টিকে সংযত করে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—ইমাম ইবনে তাইমিয়া (রহঃ)");
        hashMap.put("texDes","একজন মানুষের অন্তর যদি রোগগ্রস্ত না হয় তাহলে সে কোনদিন, কোন অবস্থাতেই আল্লাহ সুবহানাহু ওয়া তা’আলা ছাড়া অন্য কাউকে ভয় পাবে না");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","ইমাম ইবনুল কাইয়্যিম (রাহিমাহুল্লাহ)");
        hashMap.put("texDes","আপনি যখন কোন সৃষ্টিকে ভয় করবেন তখন তার থেকে দূরে পালাতে চেষ্টা করবেন। আপনি যখন আল্লাহকে ভয় করবেন তখন তাকে ভালোবাসবেন এবং তার সাথে ঘনিষ্ট হওয়ার চেষ্টা করবেন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—ইমাম শাফেয়ী (রহঃ)");
        hashMap.put("texDes","যাকে আল্লাহভীতি দান করে সম্মানিত করা হয়নি তার আর কোনো সম্মানই নেই।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—ইমাম গাজ্জালী (রহঃ)");
        hashMap.put("texDes","আল্লাহর প্রত্যেকটি ফয়সালাই ন্যায়বিচারের ওপর ভিত্তিশীল। সুতরাং কোন অবস্থাতেই অভিযোগের ভাষা যেন তোমার মুখে উচ্চারিত না হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","উমার বিন আবদুল আজিজ (রাহিমাহুল্লাহ)");
        hashMap.put("texDes","নিজেকে আল্লাহর রাহমাত সমূহের কথা বেশি করে স্মরণ করিয়ে দিন, কেননা যিনি বেশি বেশি স্মরণ করেন তার কৃতজ্ঞতা প্রকাশ করার সম্ভাবনাও বেশি থাকে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ওমর সুলাইমান");
        hashMap.put("texDes","সে-ই প্রকৃত পুরুষ যে আল্লাহর জন্য কাঁদে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— ইমাম আল-কুরতুবী (রহিমাহুল্লাহ)");
        hashMap.put("texDes","কারো সাথে কথোপকথনে বিনয়ী হওয়াটা একজন ব্যক্তির জন্য আবশ্যক।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—বুখারী ও মুসলিম");
        hashMap.put("texDes","“আল্লাহ তাঁর সৃষ্টিকূলকে সৃষ্টি করার পর তাঁর আরশের ওপর লিখেছিলেন: নিশ্চই আমার দয়া আমার ক্রোধকে প্রশমিত করবে”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— বায়হাকী");
        hashMap.put("texDes","“সব সময়ে সত্য বল – এমনকি যদিও তা অন্যদের কাছে কঠিন ও অপছন্দনীয় হয়”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—  মুসলিম");
        hashMap.put("texDes","“দয়া বিশ্বাসীর একটি চিহ্ন; যার দয়া নেই, তার মাঝে বিশ্বাস (ঈমান) নেই”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সহীহ বুখারীঃ ৩২১৫)");
        hashMap.put("texDes", "আমার কথা (অন্যদের কাছে) পৌঁছিয়ে দাও, তা যদি একটি আয়াতও হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সহীহ বুখারি অধ্যায়ঃ ২)");
        hashMap.put("texDes","আনাস (রাঃ) থেকে বর্ণিত, রাসুলুল্লাহ (ﷺ) ইরশাদ করেনঃ তোমাদের কেউ মু’মিন হতে পারবে না, যতক্ষন না আমি তার কাছে তার পিতা, সন্তান ও সব মানুষের চেয়ে বেশী প্রিয় হই। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সহীহ বুখারীঃ ১০৭,)");
        hashMap.put("texDes", "রাসূল (ﷺ) বলেছেনঃ যে ব্যক্তি আমার প্রতি মিথ্যা আরোপ করবে সে জাহান্নামে যাবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (তিরমিযী)");
        hashMap.put("texDes","পিতা-মাতার সন্তুষ্টিতে আল্লাহ্ সন্তুষ্ট আর পিতা-মাতার অসন্তুষ্টিতে আল্লাহ্ অসন্তুষ্ট।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (শুয়াবুল ঈমান)");
        hashMap.put("texDes","\"দুটি অভ্যাস আল্লাহর কাছে খুবই প্রিয়। তা হলোঃ দান ও উদারতা।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— [মুসলিম]");
        hashMap.put("texDes","যে ব্যক্তি নম্রতা থেকে বঞ্চিত হয়েছে, সে কল্যাণ থেকে বঞ্চিত হয়েছে। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (নাসায়ী ৪৭৪৭)");
        hashMap.put("texDes","\"যে মুসলিম কোনো অমুসলিম নাগরিককে অন্যায়ভাবে হত্যা করে, তার জন্য জান্নাত হারাম।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সহীহ বুখারীঃ ৫৪৬)");
        hashMap.put("texDes","যে ব্যক্তি ফজর ও আসরের নামায আদায় করবে সে জান্নাতে যাবে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সহীহ বুখারীঃ ৪১,৬০২০)");
        hashMap.put("texDes","আল্লাহ্ তা’আলার নিকট প্রিয় ঐ আমল যা নিয়মিত করা হয় যদিও তা অল্প হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সহীহ মুসলিমঃ ৪৭৭৯)");
        hashMap.put("texDes","“যে ব্যক্তি মারা গেল অথচ জিহাদ (অন্যায়ের বিরোদ্ধে প্রতিবাদ) করেনি এমনকি জিহাদের আকাঙ্ক্ষাও ব্যক্ত করেনি, সে মুনাফিকের ন্যায় মৃত্যুবরণ করল।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সহীহ বুখারীঃ ৫৫৫৯,৫৫৬০)");
        hashMap.put("texDes", "আত্মীয়ের সাথে ভালো ব্যবহার করলে রিযিক ও হায়াত বৃদ্ধি পায়।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সহীহ মুসলিমঃ ৪৭৭৮)");
        hashMap.put("texDes","যে ব্যক্তি নিষ্ঠার সাথে শহীদি মৃত্যু কামনা করে, আল্লাহ্ তাকে শহীদদের মর্যাদায় পৌছিয়ে দিবেন, যদিও সে তার বিছানায় মৃত্যুবরণ করে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","(সহীহ মুসলিমঃ ৬৩৯২,৬৩০৯)");
        hashMap.put("texDes","যে ব্যক্তি দুনিয়াতে কোন ব্যক্তির দোষ-ত্রুটি গোপন রাখবে কিয়ামতের দিন আল্লাহ্ তা’আলাও তার ত্রুটি গোপন রাখবেন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সহীহ বুখারীঃ ৬৬৯০)");
        hashMap.put("texDes","মানুষের মধ্যে সর্বাপেক্ষা নিকৃষ্ট হল ‘দ্বিমুখী ব্যক্তি’। তারা এদের কাছে বলে এক কথা আর ওদের কাছে বলে আর এক কথা। ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা ইউনুসঃ ৫৭।");
        hashMap.put("texDes", "আল কুরআন অন্তরের ব্যাধির সুস্থতা দান করে। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— [সূরা নাহ্\u200Cল ১৬:১১৯]");
        hashMap.put("texDes", "প্রজ্ঞা ও উত্তম নির্দেশনা দ্বারা আল্লাহ্\u200C তাআলার প্রতি আহ্বাব করা উচিত। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সুরা বাকারা : আয়াত ৮৩)\n");
        hashMap.put("texDes","মানুষের সঙ্গে সুন্দর ও উত্তম তথা প্রাঞ্জল ভাষায় কথা বল।'");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সুরা বনি ইসরাঈল : আয়াত ৩৬)");
        hashMap.put("texDes","'যে বিষয়ে জ্ঞান নেই, সে সম্পর্কে কথা না বলা অর্থাৎ জ্ঞানহীন অসাড় কথা না বলা।'");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সুরা বনি ইসরাঈল : আয়াত ৩৭)");
        hashMap.put("texDes","পৃথিবীতে দম্ভভরে (অহংকারের সঙ্গে) পদচারণা করো না। নিশ্চয় তুমি তো ভূ-পৃষ্ঠকে কখনই বিদীর্ণ করতে পারবে না এবং উচ্চতায় তুমি কখনই পর্বত প্রমাণ হতে পারবে না।' ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সুরা হিজর : আয়াত ৮৮)");
        hashMap.put("texDes","আপনি দৃষ্টি তুলে ওই বস্তুর দিকে (লোভাতুর দৃষ্টিতে) তাকাবেন না, যা আমি তাদের মধ্যে কয়েক প্রকার লোককে ভোগ করার জন্যে দিয়েছি, তাদের জন্য চিন্তিত হবেন না আর ঈমানদারদের জন্য স্বীয় বাহু নত করুন।' ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সুরা হুজুরাত : আয়াত ৯)");
        hashMap.put("texDes","'যদি মুমিনদের দুই দল যুদ্ধে লিপ্ত হয়ে পড়ে, তবে তোমরা তাদের মধ্যে মীমাংসা করে দেবে। অতপর যদি তাদের একদল অপর দলের ওপর চড়াও হয়, তবে তোমরা আক্রমণকারী দলের বিরুদ্ধে যুদ্ধ করবে; যে পর্যন্ত না তারা আল্লাহর নির্দেশের দিকে ফিরে আসে। যদি ফিরে আসে, তবে তোমরা তাদের মধ্যে ন্যায়ানুগ পন্থায় মীমাংসা করে দেবে এবং ইনছাফ করবে। নিশ্চয় আল্লাহ ইনছাফকারীদেরকে পছন্দ করেন।'");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সুরা হুজরাত : আয়াত ১২)");
        hashMap.put("texDes","'মুমিনগণ, তোমরা অনেক ধারণা থেকে বেঁচে থাক। নিশ্চয় কতক ধারণা গোনাহ। আর গোপনীয় বিষয় সন্ধান করো না। তোমাদের কেউ যেন কারও পশ্চাতে নিন্দা না করে। তোমাদের কেউ কি তার মৃত ভাইয়ের গোশত খাওয়াকে পছন্দ করবে? বস্তুত তোমরা তো একে ঘৃণাই কর। আল্লাহকে ভয় কর। নিশ্চয় আল্লাহ তওবাহ কবুলকারী, পরম দয়ালু।'");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সুরা আরাফ : আয়াত ৩১)");
        hashMap.put("texDes","'হে বনি আদম! তোমরা প্রত্যেক নামাজের সময় সাজসজ্জা পরিধান করে নাও, খাও ও পান কর এবং অপব্যয় করো না। তিনি অপব্যয়ীদের পছন্দ করেন না।' ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সুরা রাদ, আয়াত ১১)");
        hashMap.put("texDes","\"আল্লাহ তোমাদের মাঝে কিছু পরিবর্তন করতে চায়, ততক্ষণে যদি তোমরা নিজেদের পরিবর্তন করো।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-বাকারা (২:২০৫)");
        hashMap.put("texDes","আমাদের জীবন এবং মৃত্যুই আল্লাহর হাতে। \"যে কারণে, তোমাদের মৃত্যু হয়েছিল, এবং তিনি তোমাদেরকে জীবিত করেছেন। তাই, তোমরা আত্মার জন্য আদর্শ জীবন প্রয়োজন।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-বাকারা (২:২০৬)");
        hashMap.put("texDes","আল্লাহ জীবনের সাথে যদি তার সহায়তা না দেন, তাহলে কোনও সহায়তা হবে না। \"তোমাদের প্রতি যে সুবিধা করছি, তা হলো তোমার জন্য, আর তোমরা তা দিয়ে মোর কাছে এসোনি।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-বাকারা (২:২০৮)");
        hashMap.put("texDes","আল্লাহ প্রতি সময় আমাদের কাছে হাসির সৃষ্টি হয়ে যাচ্ছে এবং তার কথার মাধ্যমে আমরা আশা পাই। \"যারা আমাদের প্রতি একটি সুন্দর মিথ্যা বলতে চান, আমি তাদের এবং তাদের সাথে একটি দু: খিত বাস্তবতা দেব।\"\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-আনকাবূত (২৯:৬৯)");
        hashMap.put("texDes","আল্লাহর সাথে শ্রদ্ধা এবং তার মাধ্যমে আমরা অবস্থান পাই। \"যারা আমার সাথে প্রতিশ্রদ্ধাশীল, তাদের আমি নিয়ন্ত্রণ করি এবং তাদের প্রতি উন্নত করি।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-ইমরান (৩:২০০)");
        hashMap.put("texDes","আল্লাহর দৃষ্টিতে যারা সবসময় সব কিছুতে সতর্ক থাকে, তাদের জন্য সব কিছু সহজ হয়ে যায়। \"তারা সব কিছুতে ভরসা করতে পারে, সবসময় আমার দৃষ্টিতে থাকতে পারে, আর সব কিছুতে সতর্ক থাকতে পারে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-বাকারা (২:১৫৫)");
        hashMap.put("texDes","কোনও অবস্থায় আল্লাহকে মনোনিবেশ করা ও আসন্ন করা আমাদের জীবনে আশা এবং শান্তি তৈরি করে। \"আমি তোমাদেরকে দুঃখ বা মৌকুফই করি না। আমি তোমাদেরকে পরীক্ষা দিচ্ছি, কেবল তাদেরকে জানার জন্য কেন যে তারা কেমন কাজ করতে পারে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-ইমরান (৩:১৫৫)");
        hashMap.put("texDes", " জীবনের সময়ে যখন আমরা শোক ও দু: খিত, তখনই আল্লাহ হলেন সহায়ক এবং দয়ালু। \"তাদের জন্য, যারা তাদের জীবনে প্রয়াসশীল, শ্রদ্ধাশীল এবং আমার কাছে সাবলানা এবং ক্ষমাশীল হয়।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-বাকারা (২:২৫৫)");
        hashMap.put("texDes", "আল্লাহর সহায়ে সব কিছু সম্ভব, তার অমূল্য রক্ষা এবং সহানুভূতির সাথে। \"আল্লাহ হলেন অমূল্য রক্ষা, তিনি কাউকে বাধ্য করতে পারেন না, তার দেখায় কাউকে ভয় ও অসুস্থ করা যায় না।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-হাশর (৫৯:১৮-১৯)");
        hashMap.put("texDes","জীবনে আল্লাহর পথে সংঘর্ষ করলে তার বৃদ্ধি ও সমৃদ্ধি নিশ্চিত। \"তাদের প্রতি যারা তাদের বৃদ্ধির প্রতি ভক্তি করে, তাদেরকে সংবিদানগত এবং দিব্য বুদ্ধি প্রদান করতে পারেন, এবং যারা তাদের যোগদান করে তাদের জীবনে বৃদ্ধি এবং সমৃদ্ধি আনতে সাহায্য করে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-ইনশিরাহ (৯৪:৫-৬)");
        hashMap.put("texDes","জীবনে কঠিন সময়ে আল্লাহ সর্বসময় সাথে থাকে, তার উদাহরণ হিসেবে আসাতে সতর্ক থাকুন। \"আমি তোমাদের জন্য দু: খ নিয়েছিলাম, তাদের জন্য সৃষ্টি করতে হবে তাদের দৃষ্টিতে একটি মূল্যবান উপকারের জন্য, তাদেরকে জানাতে যে আমি তাদের প্রতি দয়ালু এবং অনুগ্রহশীল একজন রব হয়েছি।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-আলা (৮৭:১৫-১৭)");
        hashMap.put("texDes","আল্লাহর উপর বিশ্বাস এবং তার পথে চলার মাধ্যমে জীবন সমৃদ্ধি প্রাপ্ত হয়। \"তারা যারা আল্লাহর উপর বিশ্বাস করে এবং তার পথে চলে, তাদের জন্য সুখ এবং অমূল্য উপহার আছে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-ইমরান (৩:১৫২) ");
        hashMap.put("texDes","জীবনের যে কোনও সময়ে, যদি আমরা আল্লাহর সাথে থাকি, তাহলে বিজয় অবশ্যই হবে। \"আল্লাহর সাথে থাকতে যে কোনও সময়ে, তাতে আমরা জীবনে সফলতা অর্জন করতে পারি এবং যে কোনও পরিস্থিতিতে শক্তি প্রাপ্ত হতে পারি।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-বাকারা (২:১৫৭)");
        hashMap.put("texDes","আল্লাহর পথে যারা শক্তি দেখায়, তাদের প্রতি তিনি প্রতি সময় সহানুভূতি এবং করুণার চেহারা দেখায়। \"তারা যারা তার পথে জীবন যাত্রা করে, তাদের প্রতি আমি আমার সহানুভূতি এবং করুণার চেহারা দেখাই।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-হাশর (৫৯:২২-২৪)");
        hashMap.put("texDes","আল্লাহর সাথে যোগদান করতে হলে মনোনিবেশ ও আত্মবল দেখাতে হবে। \"তাদের প্রতি, যারা আল্লাহ এবং তার দুটি সাহায্যকে একত্রিত করে দেয়, এবং তাদের জীবনে আমি দয়ালু, করুণাময় এবং তাদের জীবনে সহানুভূতি দেখাই।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা আল-ইনশিরাহ (৯৪:৭-৮)");
        hashMap.put("texDes","জীবনের সবকিছু হলেও একটি কারণের জন্য হয়েছে, তার জন্য শোক না করা এবং আল্লাহর দিকে প্রত্যয়ন করা। \"তার জন্য হলো, যে জীবনে প্রয়াসশীল এবং আল্লাহ দিকে প্রতিষ্ঠান করে, এবং শোক করে না এবং বৃদ্ধি ও সমৃদ্ধির জন্য আমরা তাদের সমর্থন করি।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা লুকমান (সূরা ৩১), আয়াত ৬");
        hashMap.put("texDes","\"মানুষ একটি কথায় বলতে পারে যা তার নিজের ক্ষমতা বা শক্তি নয়, কিন্তু সমস্ত শক্তি মোকাবিলানো প্রতি যোগ্য আল্লাহের।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সূরা বানী ইসরাইল (সূরা ১৭), আয়াত ৩১");
        hashMap.put("texDes","\"অংহকার করো না, কারণ আল্লাহ অংহকারী এবং দাম্ভিকবাদীদের পক্ষ থেকে প্রিষ্টভূমি উঠাতে পারেননি।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সূরা আল-হাশর (সূরা ৫৯), আয়াত ১৮:");
        hashMap.put("texDes","\"তোমরা এবাদদের মধ্যে অংহকার করো না। তোমরা আল্লাহ যদি জানো তার দিকে কোন অংহকার করতে পারতাম না।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","সূরা আল-মুমিনুন (সূরা ২৩), আয়াত ৭৭-৭৮:");
        hashMap.put("texDes","\"তারা তাদের জীবনের অংহকার দেখাচ্ছে এবং অংহকারী স্বভাবকে তারা প্রতিহত করছে।\"\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","(সূরা আল-ইমরান ৩:১৯৯)");
        hashMap.put("texDes","\"আল্লাহ্\u200Cর সহানুবুতির জন্য অবাধ্য হোক তোমাদের জীবন।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("image","https://static.vecteezy.com/system/resources/thumbnails/000/263/243/small_2x/spring-or-summer-cartoon-green-landscape.jpg");
        hashMap.put("cat","বাণী ও উক্তি");
        hashMap.put("texTitle","— (সূরা আল-বাকারা ২:১৫৬)");
        hashMap.put("texDes","\"আমি তোমাদের সাথে আছি, তোমরা যদি আমার পথে আসো।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সূরা বাকারা ২:২০৬)");
        hashMap.put("texDes","\"আমি তোমাদের দোয়া শোনতে পারি এবং সব কিছু জানি।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সূরা আল-আলে ইমরান ৩:১৬৩)");
        hashMap.put("texDes","\"আল্লাহ্\u200Cর পথে মারা যাওয়া মুসলিমের জন্য মৃত্যু নয়, সচ্চারাচ্চিত জীবনের শোক নয়।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সূরা হূদ ১১:১ৡ৬)");
        hashMap.put("texDes","\"আল্লাহ্\u200C তোমাদের জন্য আত্মার সৃষ্টি করেছেন, সেইসব কাজ গুলি করা জন্য যা তোমার উদ্দেশ্য তোমার জীবনের মাধ্যমে।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— (সূরা আল-ইমরান ৩:১৬৪)");
        hashMap.put("texDes","\"তোমরা যদি আমার উপর বিশ্বাস করো, তাহলে আমি তোমাদের সব কিছু উল্লেখযোগ্য করতে পারি।\" ");
        arrayList.add(hashMap);










    }
}