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

public class Emotional extends AppCompatActivity {

    RecyclerView emotionalRecyclerView;
    ImageView emotionalImageBack;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_emotional);
        emotionalRecyclerView = findViewById(R.id.emotionalRecyclerView);
        emotionalImageBack = findViewById(R.id.emotionalImageBack);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        emotionalImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Emotional.this, MainActivity.class);
                startActivity(intent);
            }
        });

        emotionalTable();
        finalArrayListTable();


        EmotionalAdapter emotionalAdapter = new EmotionalAdapter();
        emotionalRecyclerView.setAdapter(emotionalAdapter);
        emotionalRecyclerView.setLayoutManager(new LinearLayoutManager(Emotional.this));

    }

    private class EmotionalAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(Emotional.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(Emotional.this, "ca-app-pub-3940256099942544/2247696110")
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
    private void emotionalTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","হাসি মুখে কথা বলি,সবার সাথে মিশে চলি, দুঃখ পেলে গোপন রাখি,সবাই ভাবে আমি সুখি, আসলে সুখি আমি নয়,আমার জীবন টাই সুখের অভিনয়।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“ঝরে যাওয়া পাতা জানে।\n" +
                "স্মৃতি নিয়ে বাঁচার মানে।\n" +
                "হয়তো আমি ঝরে যাবো\n" +
                "সময়ের তালে তোমার মনে।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“আবেগ হল মােমবাতি যা\n" +
                "কিছুক্ষণ পর নিভে যায়।\n" +
                "আর বিবেক হল সূর্য যা\n" +
                "কখনো নেভে না।””");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“মধ্যবিত্ত ঘরের ছেলের পকেট\n" +
                "ভর্তি টাকা থাকেনা!\n" +
                "মাথা ভর্তি টেনশন থাকে !”");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“চিৎকার করে কখনও নিজেকে\n" +
                "নির্দোষ প্রমাণ করা যায়না!\n" +
                "মাঝে মাঝে চুপ থাকতে হয় !”");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“যে ধোঁকা দেয়,\n" +
                "সে চালাক হতে পারে!\n" +
                "তবে যে ধোঁকা খায়,\n" +
                "সে বোকা নয়, সে বিশ্বাসী!”");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“চায়ের কাপে ভেজানাে বিস্কুট\n" +
                "একটাই শিক্ষা দেয়। কারাে\n" +
                "প্রতি এতটাও ডুবে যেওনা\n" +
                "যাতে নিজেকেই\n" +
                "ভেংগে পড়তে হয়।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“যেখানে স্বপ্নয় আমাদের\n" +
                "এক করতে পারে না\n" +
                "সেখানে বাস্তবতা তাে নির্মম।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“কার ভিতরে কেমন মানুষ\n" +
                "লুকিয়ে আছে সেটা\n" +
                "শুধু সময় বলে দেয় …”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","\n" +
                "“নিজেকে যদি শক্তিশালী করে\n" +
                "তুলতে চাও।\n" +
                "তাহলে একলা\n" +
                "কিভাবে থাকতে হয়,\n" +
                "তা শিখে নাও।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“আমি সবার মন ভালাে\n" +
                "রাখার চেষ্টা করি, কিন্তু\n" +
                "সবাই ভুলে যায় আমারো\n" +
                "একটা মন আছে।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“চাঁদ তুমি শুনবে কি\n" +
                "আমার মনের কথা ?\n" +
                "সত্যি বলছি আমিও যে\n" +
                "তাোমার মত একা..!”");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“তীরটা যখন বুকে ডুখলো তখন\n" +
                "একটুও কষ্ট পায়নি,\n" +
                "যখন দেখলামলা মানুষটা\n" +
                "আমারি পরিচিত তখনই কষ্ট পেয়েছি।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","\n" +
                "“মনে ছিলো কতো সপ্ন,\n" +
                "ছিলো কতো আসা..\n" +
                "সব কিছুই মিথ্যে ছিলো\n" +
                "তোমার ভালোবাসা।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“মন ভাঙ্গলে চোখের কোনে\n" +
                "আছড়ে পড়ে ঢেউ ,\n" +
                "বুকে কতটা কান্না চাপা থাকে,\n" +
                "জানতে পারেনা কেউ ।”");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“ছেড়ে দিল যদি ভালো থাকে\n" +
                "তাহলে ছেড়ে দাও,\n" +
                "কারন ভালো রাখার নাম\n" +
                "ভালোবাসা।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes", "“মন তাকেই পছন্দ করে\n" +
                "যে ভাগ্যে থাকে না !”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("cat","বাণী ও উক্তি");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“যে বৃষ্টির ফোঁটা তােমায় আজ\n" +
                "নতুন প্রেমের স্পর্শ মাখায়,\n" +
                "সেই বৃষ্টির ফোঁটায় পুরাতন প্রেম\n" +
                "দুচোখের জল লুকায়।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“তাকেই বেশি মনে পরে\n" +
                "যে সারাদিন একবার ও আমার\n" +
                "খোঁজ নেয় না !!”");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“পৃথিবীতে সবকিছু বুজতে\n" +
                "সময় লাগে,\n" +
                "কিন্তু ভুল বুঝতে একটা\n" +
                "মুহূর্তই যথেষ্ট !”");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“ছেড়ে গিয়েও স্মৃতির মাঝে\n" +
                "ডুবিয়ে রাখে যে।\n" +
                "অভিশাপ দিলাম স্মৃতি ছাড়াই\n" +
                "ভালো থাকুক সে।”");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","তারা বলে যে একটি ছবি 1000 শব্দ বলে, কিন্তু যখন আমি তোমাকে দেখি তখন আমি 3টি দেখি: আমি তোমাকে ভালোবাসি।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","আমার চোখে আসছে আকাশের বৃষ্টি, যা আমার হৃদয়কে আবি করে দিচ্ছে। ☔❤\uFE0F");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes", "জীবন একটি সুখের বাগান, কিন্তু কখনো কখনো তার ফুল কাটা যায়। \uD83C\uDF38\uD83D\uDE14");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","হৃদয় ভরা আছে অসীম ভালোবাসা, কিন্তু মনের এক কোণে ছুটে আছে নীরবতা। \uD83D\uDC94\uD83C\uDF0C");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","জীবন একটি রহস্যময় যাত্রা, আমি খোঁজছি উত্তর এবং আশা করছি সূর্যের আলো। \uD83C\uDF05\uD83D\uDD0D");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","জীবনের পথে হাঁটতে হলে, কখনো কখনো হাসতে হবে এবং কখনো কখনো কাঁদতে হয়। \uD83D\uDE0A\uD83D\uDEB6");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","চাঁদের আলো আমার চোখে রেখেছে একটি প্রিয় স্মৃতি, যা আমি সবসময় মনে রাখতে চাই। \uD83C\uDF15\uD83D\uDC96");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","জীবনের পাহাড়ে হাঁটতে হলে, কখনো কখনো পাথরের পথ দিয়ে হতে হবে। \uD83C\uDFD4\uFE0F\uD83D\uDC63");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","স্বপ্নের জগতে, আমি হারানো আছি মাত্র তোমার অসীম ভালোবাসায়। \uD83D\uDCA4❤\uFE0F");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","হৃদয় ভালোবাসায় ভরা, কিন্তু মন ভেঙ্গে গেছে বিপন্ন অপেক্ষায়। \uD83D\uDC94⏳");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes", "বিকেলের সূর্যাস্তে, আমি খোঁজছি আবির্ভাব এবং আশার রং। \uD83C\uDF07\uD83C\uDF08");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","হাসতে হাসতে মুক্ত, কিন্তু মনের আবেগে সংকোচিত। \uD83D\uDE04\uD83D\uDE1F");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","জীবনের চক্রবিদ্ধি, কখনো উচ্চ এবং কখনো নীচে, কিন্তু আমি চেষ্টা করছি সঠিক দিকে এগিয়ে যাওয়ার। \uD83D\uDD04\uD83C\uDFDE\uFE0F");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","মেঘের গভীর কালিন আকাশ, আমি বসবাস করছি আশার আলোয় এবং বৃষ্টির কোনও আগুন জ্বলতে দেখতে চাই। ☁\uFE0F\uD83C\uDF27\uFE0F\uD83D\uDD25");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","আমার হৃদয়ে বাস করুন এবং ভাড়া দেবেন না।");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","একজন আত্মার সঙ্গী হলেন এমন একজন যিনি আপনাকে অন্য কারো মতো বোঝেন না, চিরকাল আপনার জন্য থাকবেন।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes", "তুমি সন্দেহ করো তারাগুলো আগুন; সন্দেহ সূর্য নড়াচড়া না।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","হয়তো তুমি ও বাসবে ভালো!\n" +
                "কিন্ত আমি থাকবো না শান্ত হয়ে ঘুমিয়ে যাবো!\n" +
                "আর কোনদিন জাগবো না ভালোবাসার অজুহাতে!\n" +
                "তোমায় কাছে ডাকবো না আর কোনোদিন তোমার পথে, দাঁডিয়ে আমি থাকবো না।");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","বাঁধিনি হৃদয় পিঞ্জরে রেখেছি মুক্ত করে।যাবি যদি দূরেই পাখি, \n" +
                "যা রে উড়ে করবোনা মানা তোরে..।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","সৃষ্টি হবে অন্যরকম গল্প আজ, আলোর নিচে সাজাবো আমি, অন্ধকারের সাজ দেখে আবার আসেনা যেনো, \n" +
                "তোমার চোখের পানি। হটাৎ করে দেখবে তুমি, হারিয়ে গেছি আমি।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","সাঁঝের বেলায় বসে আছি বৃষ্টির অপেক্ষায়।\n" +
                "মেঘ করেছে বৃষ্টি হবে এটাই আশা,\n" +
                "আর এরই মধ্যে বেঁচে আছে কিছু ভালোবাসা।\n" +
                "তাই শুধুই বসে বসে অপেক্ষা করা।");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","কালের খেয়ায় স্বপ্ন দিচ্ছে পারি দুঃখের নীল অস্তরাগে।তোমায় ভালোবেসে কন্ঠস্বর বেদনার ঝড় হয়ে আসে।অবিশ্বাসের মেঘে মোর কান্নাভেজা মুখ খানি ভাসে।মোর জীবন যেন বিদায় নেয় তোমার মৃত্যুর আগে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","অতিরিক্ত মন খারাপ হলে মানুষ একেবারে নীরব নিথর হয়ে যায়… একা থাকতে ভালোবাসে। কারণ তখন তার সমস্যাকে নিজের মত করে কেউ দেখে না কিংবা মূল্যায়ন করে না। তাই মন খারাপের বেলায় একাকীত্বই হয় মানুষের একমাত্র সঙ্গ!!");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes", "অতীতটা স্মৃতি রোমন্থন করার জন্যে নিশ্চই খুব ভালো..কিন্তু অতীতের গর্ভে থাকার মানে বর্তমানকে অস্বীকার করা… বর্তমান কে অস্বীকার করে কেউ কোনদিন জীবনে সফল হতে পারে না!!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","অন্য আকাশে উড়ে দেখিস সুখটা কাকে বলে…. ক্লান্ত হলে ফিরে আসিস আমার চেনা ঘরে… কখনো যদি চোখের পাতা ভিজে যায় জলে… বুঝতে পারবি পাঁজর ভাঙ্গার কষ্ট কাকে বলে!!");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“সময় এবং উভয়ই যখন একসাথে আঘাত করে, তখন প্রতিটি মানুষ ভেতর থেকে পাথর হয়ে যায়।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“তুমি যা নও তার জন্যে ভালোবাসার থেকে তুমি যা আছো তার জন্যে ঘৃণিত হওয়া অনেক ভালো \uD83D\uDE0C”");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");

        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes", "“সত্য অল্প সময়ের জন্য কষ্ট দেয়, কিন্তু মিথ্যা আজীবন কষ্ট দেয়।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“সবসময় হাসতে থাকো করুন কেউ দেখতে পাবে না যে তুমি ভিতরে কতটা \uD83D\uDC94 ভেঙে পড়েছো \uD83D\uDE22।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“সবচেয়ে বেদনাদায়ক বিদায় সেইগুলি যা কখনও বলা হয় নি এবং কখনও ব্যাখ্যা করা যায় না!!”");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“সময় আসলে ❤\uFE0F হৃদয় নিরাময় করে না। এটি কেবল হৃদয়কে সমস্ত ব্যথা ভুলে যেতে সাহায্য করে \uD83D\uDE2D।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“কখনও কখনও তুমি যাকে সবচেয়ে বেশি বিশ্বাস করো সেই ব্যক্তি তোমায় হয়তো সবচেয়ে কম বিশ্বাস করে!”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“সুখ অল্প সময়ের জন্য ধৈর্য দেয়, কিন্তু ধৈর্য চিরন্তন সুখ নিয়ে আসে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“যখন অন্য কেউ কারও জীবনে তোমার জায়গা নিতে শুরু করে তখন এটি সত্যিই কষ্ট দেয়।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“গন্তব্য তো রাগ কোরতই কারণ হৃদয় যে অচেনা পথে বসে ছিল…”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“কিছু-কিছু অপূর্ণতাও দরকার, মানুষ হয়ে মানুষের মতো হওয়াও দরকার \uD83D\uDE09…”");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","“কখনও কখনও তোমাকে ছুটে পালাতে হয় দেখার জন্যে যে কে-কে তোমাকে অনুসরণ \uD83D\uDE36\u200D\uD83C\uDF2B\uFE0F করবে।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","ভালোবাসা তো সেটাই যাকে ভালোবাসার পরে অন্য কাউকে ভালোবাসার ইচ্ছাটা মরে যায়!!");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","\"জীবনের সবচেয়ে বড় গুরুত্বপূর্ণ বিষয় হলো ভালোবাসা এবং মানবতার সাথে যুক্তি করা।\"");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","\"আপনি কখনো জানতে পারবেন না যদি আপনি চেষ্টা না করেন।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","\"শব্দ একটি ক্ষতিকর ছুটির কারণ হতে পারে, তবে এটি একটি জীবন পরিবর্তনের কারণ হওয়ার জন্যও হতে পারে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","\"ভালোবাসার মূল্যবান প্রতি ক্ষণ একটি অমূল্য সৃষ্টি করে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","\"সফলতা সমৃদ্ধির মূল হলো প্রতি অসমৃদ্ধি এবং বিফলতা থেকে এগিয়ে চলা।\"\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","\"সবচেয়ে ভালো সময় হলো যখন আপনি অন্যদেরের জন্য কিছু করতে পারবেন।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","\"মনে রাখতে হবে যে, আপনি যদি একটি চূড়ান্ত লক্ষ্য প্রাপ্ত করতে চান, তবে সঠিক পথে চলতে হবেন, না যে সঠিকটি হয়েছে বলতে বসোনি।\"");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","\"বৃদ্ধির জন্য কখনোই হেমন্তবোর একটি বৃদ্ধির মাধ্যম হতে পারে।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","\"ভুল হতে একটি মানব অধিকার, তবে ভুল সহজে সহ্য করা হতে পারে না।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","\"জীবনের সবচেয়ে ভালো চিন্তা হলো যে, তা আপনি কি হতে চান, এবং তারপরে এটি কী করতে হবে।\"");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","\"সফলতা সমৃদ্ধির পথে একটি ধাপ হলো অপরের জন্য উৎসাহ এবং সমর্থন করা।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","মাঝে মাঝে নিজেকে আড়াল করে কাছের মানুষ গুলোকে টের পাইয়ে দিতে হয়। বুকের কতটা গভীরে কষ্ট জমে গেলে, মানুষ দূরে চলে যায়।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","কিছু হারিয়ে গেলে যা কষ্ট হয়। তার চেয়ে বহুগুণ বেশি কষ্ট হয় কেউ বদলে গেলে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","অভিমান থাকুক অভিমানের মতো। নাদুস-নুদুস খুব আদুরে, রাগ আর ক্রোধের মতো কিম্ভুতকিমাকার যেন তাকে গ্রাস না করে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","অভিমানে মুখ ফেরালে, সত্যিটা তো খুঁজলে না। প্রতারণাই দেখলে শুধু, ভালোবাসা বুঝলে না।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","হারিয়ে গেলেও ক্ষতি নেই কারন খুজার মতো কেউ নেই");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","ভালো আছি… এই মিথ্যে কথাটা বলতে বলতে কেউ কেউ, সত্যি একসময় ভালো থাকা শিখে ফেলে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","অভিমান খুব দামি একটা জিনিস! সবার উপর অভিমান করা যায় না, শুধু ভালোবাসার মানুষগুলোর উপরই অভিমান করা যায়।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","সবাই তোমাকে বুঝবে না, \n" +
                "এটাই বাস্তবতা এবং এটাই জীবন।\n" +
                "— সংগৃহীত");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","তোমার অভাবী ভালবাসায় \n" +
                "আমার সোনালী জীবন নষ্ট হয়ে গেছে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","একা থাকা এবং একা কাঁদা,একজন \n" +
                "মানুষকে অসীম শক্তিশালী \n" +
                "করে তোলে.!!");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","সময় সবার বাস্তবতা দেখিয়েছে, \n" +
                "নইলে সবাইকে অন্ধভাবে \n" +
                "বিশ্বাস করতাম..!");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","তুমি যে খুব স্পেশাল সেটা বুঝতে দিয়ে \n" +
                "আমি সাধারণ হয়ে গেলাম।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","আপনার অস্থায়ী আবেগের জন্য \n" +
                "স্থায়ী সিদ্ধান্ত নেবেন না।");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","\n" +
                "মাঝে মাঝে নিজেকে প্রশ্ন করি, \n" +
                "এমন কেউ কি আছে যে \n" +
                "আমাকে হারানোর ভয় পায়?");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","সুখ অল্প সময়ের জন্য ধৈর্য দেয়,\n" +
                "কিন্তু ধৈর্য চিরকালের জন্য সুখ দেয়।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","সম্পর্ক সবসময় হৃদয় থেকে হওয়া উচিত \n" +
                "কথায় নয়..!!");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," — সংগৃহীত");
        hashMap.put("texDes","যারা ভিতর থেকে মারা যায়,\n" +
                "তারাই অনেক সময় অন্যকে বাঁচতে শেখায়।");
        arrayList.add(hashMap);




       





    }
}