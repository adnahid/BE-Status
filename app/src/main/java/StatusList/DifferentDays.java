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

public class DifferentDays extends AppCompatActivity {

    RecyclerView differentRecyclerView;
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_different_days);
        differentRecyclerView = findViewById(R.id.differentRecyclerView);
        toolbar = findViewById(R.id.toolbar);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DifferentDays.this, MainActivity.class);
                startActivity(intent);
            }
        });

        differentTable();
        finalArrayListTable();


        DifferentAdapter differentAdapter = new DifferentAdapter();
        differentRecyclerView.setAdapter(differentAdapter);
        differentRecyclerView.setLayoutManager(new LinearLayoutManager(DifferentDays.this));

    }

    private class DifferentAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(DifferentDays.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(DifferentDays.this, "ca-app-pub-8411075266548653/2415594861")
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
    private void differentTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes"," _ღ༉۵\uD83E\uDD8B২১ শে ফেব্রুয়ারিকে ঘিরেই বাংলার স্বাধীনতা আন্দোলনের সূচনা ঘটে__\uD83C\uDF3A’༉༎");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","এবং শোষণ ও পরাধীনতার শৃংখল থেকে মুক্ত হয় এদেশ ও জাতি__ღ༉۵\uD83E\uDD8B");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","__ღ༉۵\uD83E\uDD8Bমনে পড়ে ৫২ এর কথা__\uD83C\uDF3A’༉༎মনে পড়ে একুশে ফেব্রুয়ারির কথা__\uD83C\uDF3A’༉༎যখন হারিয়েছি আমার ভাইদের__\uD83C\uDF3A’༉༎\n" +
                "দিয়েছি রক্ত ভাষার জন্য__ღ༉۵\uD83E\uDD8B");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "╰┛চেতনার পথে দ্বিধাহীন অভিযাত্রী বেশে বাঙালিকে সর্বদা চলার প্রেরণা জোগায় একুশ─༅༎•\uD83C\uDF3A\uD83C\uDF38༅༎•─");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "╰┛আজ বাংলাদেশের পাশাপাশি ইউনেস্কোর ১৯৫টি সদস্য এবং ১০টি সহযোগী সদস্য রাষ্ট্র পালন করবে আমাদের একুশকে─༅༎•\uD83C\uDF3A\uD83C\uDF38༅༎•─");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","__\uD83C\uDF3A’༉༎দোয়ারে বসে মা আবার ধান ভাঙ্গে, বিন্নি ধানের খৈ ভাজে__\uD83C\uDF3A’༉༎খোকা তার কখন আসে কখন আসে।__ღ༉۵\uD83E\uDD8B");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার মাঝেই স্বপ্নের শুরু, তোমার মাঝেই শেষ। তবুও ভাললাগা-ভালোবাসাময় তুমি, আমার বাংলাদেশ!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","১৬ ডিসেম্বর তুমি নব্য বাংলাদেশের  মহা বিজয়ের মহা উল্লাস। তুমি বাংলাদেশ, \n" +
                "তুমি বিধবা মায়ের বন্দী দশা থেকে মুক্তির নিঃশ্বাস।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","প্রথম বাংলাদেশ আমার, শেষ বাংলাদেশ। জীবন বাংলাদেশ আমার, মরণ বাংলাদেশ…।” \n" +
                "সকলকে মহান বিজয় দিবসের শুভেচ্ছা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আপনার সম্মান তখন বাড়বে। যখন বিদেশে গিয়ে আপনি নিজের দেশের সম্মান বাড়াতে পারবে। আর গর্বিতভাবে বলতে পারবেন, আমি বাংলাদেশী।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","বিজয়ের হাসির পিছনে রয়েছে লাখ লাখ শহীদের রক্ত,\n" +
                "মহান বিজয় দিবসের শুভেচ্ছা জানাচ্ছি সকলকে!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-মহাত্মা গান্ধী।");
        hashMap.put("texDes", "সহিংসতা দ্বারা বিজয় অর্জন ক্ষনিকের জন্য হলেও পরাজয়ের সমতুল্য");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— বঙ্গবন্ধু শেখ মুজিবুর রহমান।");
        hashMap.put("texDes","এবারের সংগ্রাম আমাদের মুক্তির সংগ্রাম এবারের সংগ্রাম আমাদের স্বাধীনতার সংগ্রাম!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— টোবা বিটা।");
        hashMap.put("texDes","পরাজয়ের বিরুদ্ধে জয় রক্ষার। জন্য বিজয়ী আইন তৈরি করেন!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সান তজু.");
        hashMap.put("texDes","বিজয়ী যোদ্ধারা প্রথমে জিতে তারপর যুদ্ধ করে।পরাজিত যোদ্ধারা প্রথমে যুদ্ধ করে তার তারপর জয়ের চেষ্টা করে!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জীবন যদি রামধনু হয়,\n" +
                "তবে তুমি হলে তার রঙের বাহার।\n" +
                "জীবনে যদি নামে আঁধার,\n" +
                "তুমি হয়ে ওঠো তার আশার আলো।\n" +
                "নারী দিবসের অনেক অনেক শুভেচ্ছা!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমাদের সব স্বপ্ন সফল হোক,\n" +
                "ইচ্ছা গুলো হোক পূরণ।\n" +
                "তোমরা হয়ে উঠো পাহাড় প্রমান উঁচু।\n" +
                "হ্যাপি ওমেনস ডে!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মহানবী হজরত মুহম্মদ (স.)");
        hashMap.put("texDes","তোমাদের জন্য মায়ের পায়ের নিচেই রয়েছে তোমাদের জান্নাত!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— আব্রাহাম লিংকন");
        hashMap.put("texDes","যার মা আছে সে কখনই গরীব নয়!! ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— আব্রাহাম লিঙ্কন");
        hashMap.put("texDes","আমি যা হয়েছি বা ভবিষ্যতে যা হতে চাই তার সব কিছুর জন্য আমি আমার মায়ের কাছে ঋণী!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— –মোহাম্মদ আলী");
        hashMap.put("texDes","“এটি নিশ্চিতকরণের পুনরাবৃত্তি যা বিশ্বাসের দিকে নিয়ে যায়। এবং একবার সেই বিশ্বাস গভীর প্রত্যয়ে পরিণত হলে, জিনিসগুলি ঘটতে শুরু করে।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মাইকেল রাত্নাডিপাক");
        hashMap.put("texDes", "পৃথিবীতে একটি মেয়েকে তার বাবার চেয়ে কেউ বেশি ভালোবাসতে পারবে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— দিমিত্রি থে স্টোনহার্ট");
        hashMap.put("texDes","একজন বাবা বলে না যে সে তোমাকে ভালোবাসে বরং তিনি দেখিয়ে দেন যে তিনি তোমাকে ভালোবাসে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","”বাবা নামটা উচ্চারিত হওয়ার সঙ্গে সঙ্গে যে কোন বয়সী সন্তানের,\n" +
                "হ্রদয়ে শ্রদ্ধা কৃতজ্ঞতা আর ভালোবাসার এক অনুভব জাগে ।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—  হুমায়ূন আহমেদ");
        hashMap.put("texDes","আমি আমার নিজের দেশ নিয়ে অসম্ভব রকম আশাবাদী৷ আমাকে যদি একশোবার জন্মাবার সুযোগ,\n" +
                "দেয়া হয় আমি একশোবার এই দেশেই জন্মাতে চাইব৷ এই দেশের বৃষ্টিতে ভিজতে চাইব৷,\n" +
                " এই দেশের বাঁশবাগানে জোছনা দেখতে চাইব!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","যদি দেশের জন্য তোমার ভেতরে আবেগ না থাকে তাহলে তোমার শরীরে রক্ত না জল বইছে!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— জীবনানন্দ দাশ");
        hashMap.put("texDes","বাংলার মুখ আমি দেখিয়াছি, তাই আমি পৃথিবীর রূপ খুঁজিতে যাই না আর!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— রুদ্র মুহাম্মদ শহীদুল্লাহ");
        hashMap.put("texDes","যে মাঠ থেকে এসেছিল স্বাধীনতার ডাক, সেই মাঠে আজ বসে নেশার হাট!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মার্ক টোয়েন");
        hashMap.put("texDes","ভালো বই, ভালো বন্ধু, একটি শান্ত বিবেক, একটা আদর্শ জীবন ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","মন হল হাজার দুয়ারি ঘর। যারা বই পড়ে না তাদের কাছে সেই বেশীরভাগ ঘরগুলো অপ্রবিষ্টই থেকে যায়।“বই হচ্ছে মস্তিষ্কের সন্তান।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— জনাথন সুইফট");
        hashMap.put("texDes","বই হচ্ছে মস্তিষ্কের সন্তান।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","—ডক্টর মুহম্মদ শহীদুল্লাহ");
        hashMap.put("texDes","”জীবনে তিনটি জিনিসের প্রয়োজন- বই, বই এবং বই।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— টুপার");
        hashMap.put("texDes","”একটি ভালো বই হলো বর্তমান ও চিরকালের জন্য সবচেয়ে উৎকৃষ্ট বন্ধু।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— হুমায়ূন আহমেদ");
        hashMap.put("texDes","বইমেলা হলো সাংস্কৃতিক বাজার যেখানে আমাদের মানুষের গল্প কণ্ঠস্বর খুঁজে পায় এবং বিশ্বের সাথে অনুরণিত হয়। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— মুহাম্মদ ইউনুস");
        hashMap.put("texDes","বইমেলার প্রাণবন্ত বাজারে, গল্পগুলি মুদ্রা হয়ে ওঠে যা প্রজন্ম অতিক্রম করে, আমাদের সাহিত্যের উত্তরাধিকারকে সমৃদ্ধ করে। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","একটি বইমেলা আত্মার জন্য একটি তীর্থযাত্রা, যেখানে পাঠকরা লিখিত ধনগুলির মধ্যে আবিষ্কারের যাত্রা শুরু করে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","হে মহান শহীদ বুদ্ধিজীবী । আমরা কোনদিন তোমাদের আত্মত্যাগ ভুলবো না। \n" +
                "তোমরা যুগান্তরে রয়ে যাবে সকলের হৃদয়ে ।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","শহীদ বুদ্ধিজীবী দিবসে তাদের আত্মার মাগফেরাত কামনা করছি । \n" +
                "এবং বুদ্ধিজীবীদের প্রেরণায় উজ্জীবিত হওয়ার শপথ করছি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ক্ষমতার পালা বদলে মুক্তিযুদ্ধের ইতিহাস পরিবর্তন হয়েছে ।\n" +
                "শহীদ বুদ্ধিজীবীরা থাকলে আমরা মুক্তিযুদ্ধের প্রকৃত ইতিহাস পেতাম!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","দল-মত ,ধর্ম-বর্ণ নির্বিশেষে সকলেরই শহীদ বুদ্ধিজীবীদের প্রতি গভীর শ্রদ্ধা এবং ভালবাসা জানানো উচিত!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "“একটি বৃক্ষ রোপণ করে আপনি একটি জীবন বাঁচাতে পারেন,\n" +
                "পরিবেশ সংরক্ষণ করতে পারেন এবং আপনার পৃথিবীর উপাস্যতা প্রদর্শন করতে পারেন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“বৃক্ষরোপণ একটি সুন্দর পরিবেশের সৃষ্টি করে যা আপনার সাথে একটি সম্পর্ক স্থাপন করে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“ইসলামে বৃক্ষরোপণ একটি পূর্বাচার প্রক্রিয়া যা মানবিক ও প্রকৃতির সম্পর্ক স্থাপনে মানা হয়।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "“বৃক্ষরোপণ করে মুসলিম ব্যক্তিগণ আল্লাহর নিকট দান করেছে পরিবেশ সংরক্ষণ ও প্রকৃতির জন্য যা \n" +
                "আল্লাহর রাজপথ পালন করার প্রতীক।”");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes", "“একটি মুসলিম যখন একটি বৃক্ষ রোপণ করে, তখন তিনি সদায় কর্মসূচির সাথে আল্লাহর রাজপথ পালন করে এবং জন্মভূমির সংরক্ষণ করে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“বৃক্ষরোপণ করে আপনি আল্লাহর প্রকৃতিতে আপনার অংশ যোগ করেন এবং সম্পূর্ণ সৃষ্টিতে,\n" +
                "আল্লাহর কারিগর হওয়ার মাধ্যমে আপনার বাড়িকে পরিবেশগত পরিমিতি দিয়ে থাকেন।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","ফুলের কাজ হল সুবাস দেওয়া, বৃষ্টি করবে আজ আমাদের মনচুরী, খুশি গুলো আজ হাসাবে আমাদের।\n" +
                "তোমাকে জানাবো আজ ঈদ মোবারকের ফুলঝুরি। ঈদ মোবারক।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","খুশির হাওয়া আজ লাগছে মনে, নাচবো আমরা আজ প্রতিক্ষণে। সাজাবো মোদের নতুন করে, \n" +
                "ভাসবো আমরা সুখের ঘোরে। ঈদের এই আনন্দ থাকুক তোমার সারাটি জীবন ধরে। \n" +
                "ঈদ মোবারক শুভেচ্ছা নিও।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","দূর আকাশ থেকে পড়ে গেছে ওই চাঁদের নজর। সেই চাঁদটি আমাদের জানিয়ে দিলো সেই আনন্দের ঈদের খবর। \n" +
                "ঈদের খুশি আজ আকাশ বাতাস জুড়ে, আগলে রাখ সেই আনন্দের মুহূর্ত গুলো তোমার বাহুডোরে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","দূর আকাশের ঐ চাঁদ উঠেছে, ফুলের বাগানে নতুন ফুল ফুটেছে। তোমরা কি শুনছো, \n" +
                "যদি দেখবে তবে আয়। নতুন চাঁদের আলো আজ আমরা মাখবো সবাই মিলেমিশে আমাদের গায়। ঈদ মোবারক।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","বন্ধু ঈদের শুভেচ্ছা তোমাকে কোন চিঠির মাধ্যমে নয়, কোন ফুল দিয়ে নয়, কোনো কার্ড দিয়ে নয়।\n" +
                "বরং ঈদের শুভেচ্ছা কে জানিয়ে দিলাম এসএমএস এর মাধ্যমে। পবিত্র ঈদের শুভেচ্ছা ও ভালবাসা নিও।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","বুকে আছে প্রেম মনে আছে আশা এই থেকে গড়েছি প্রেম ভালোবাসা সেই ভালোবাসা হোক ঈদের মত পবিত্র আর সুন্দর ঈদ মোবারক!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","কষ্টের আড়ালে লুকিয়ে থাকে সুখের রাশি রাশি। জীবনে আসুক যত দুঃখ বেদনা তবুও আমি তোকে অনেক ভালোবাসি। মুছে ফেলো তোমার অতীত জীবনের যত আছে দুঃখের স্মৃতি। সে কারণেই আমি তোমাকে জানাতে এসেছি ঈদ মোবারক এর শুভেচ্ছা প্রীতি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","মিষ্টি সকালের স্নিগ্ধ সুবাসের বাতাস। তুমি হলে শীতল ভোরের শিশির ভেজা দূর্বা ঘাস। তুমি হলে রাতের অন্ধকারের দূর আকাশের ওই চাঁদ। তুমি হলে অপেক্ষার প্রহর গোনা স্বপ্নের সেই প্রভাত। তাই তোমাকে জানাচ্ছি ঈদ মোবারক এর শুভেচ্ছা ও প্রীতি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","এক বছর পরে এল\n" +
                "ফিরে আজকের এই দিন।\n" +
                "তাই তো তোকে জানাই\n" +
                "শুভ জন্মদিন।\n" +
                "ভালো থাকিস।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","জন্মদিনের প্রতিটি মুহূর্ত\n" +
                "তোমার সুন্দর ভাবে কাটুক।\n" +
                "শুভ জন্মদিন");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আরও একটি বছর এসে গেলো,\n" +
                "বেড়ে যাবে আরও একটি মোমবাতি।\n" +
                "কালও ছিলাম আজও আছি,\n" +
                "তোমার জন্মদিনের সাথি।\n" +
                "❦~শুভ জন্মদিন~❦”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“আজকের এই বিশেষ দিনে\n" +
                "হয়ে ওঠো আরো নবীন,\n" +
                "ভালবেসে জানাই তোমায়ে\n" +
                "❦~শুভ জন্মদিন~❦”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","আসুক ফিরে এমন দিন,\n" +
                "হয়ে উঠুক তোমার জীবন রঙিন।\n" +
                "শুভ জন্মদিনের অনেক শুভেচ্ছা");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","তোমার বিশেষ দিনে,\n" +
                "কামনা করি তোমার জীবন\n" +
                "আনন্দে ভরে উঠুক,\n" +
                "সুখে থাকো সারাজীবন।\n" +
                "শুভ জন্মদিন");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“অতীতের সব দুঃখজনক\n" +
                "ঘটনাকে ভুলে যাও…\n" +
                "মন দাও বর্তমানের দিকে…\n" +
                "অনেক অনেক খুশির জোয়ার\n" +
                "আসুক তোমার জীবন জুড়ে…\n" +
                "শুভ জন্মদিন”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“ফুলের হাসিতে প্রাণের খুশিতে\n" +
                "সোনালী রোদ্দুর ঘাসের বুকেতে\n" +
                "করেছে ভুবন রঙ্গীন\n" +
                "তোমাকে জানায় হৃদয় থেকে\n" +
                "❦~শুভ জন্মদিন~❦”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“স্বপ্ন গুলো সত্যি হোক\n" +
                "সকল আশা পুরন হোক\n" +
                "দুঃখ গুলো দূরে যাক,\n" +
                "সুখে জীবনটা যাক ভরে।\n" +
                "জীবনটা হোক ধন্য\n" +
                "শুভ কামনা তোমার জন্য।\n" +
                "❦~শুভ জন্মদিন~❦”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— সংগৃহীত");
        hashMap.put("texDes","“তোর জন্য ভালোবাসার লক্ষ..\n" +
                "গোলাপ জুঁই..\n" +
                "হাজার লোকের ভিড়ে\n" +
                "আমার থাকবি হৃদয়ে তুই।\n" +
                "❦~শুভ জন্মদিন প্রিয়~❦”");
        arrayList.add(hashMap);
        //63




    }
}