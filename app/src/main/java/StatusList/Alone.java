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

public class Alone extends AppCompatActivity {

    RecyclerView alonRecyclerView;
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alone);
        alonRecyclerView = findViewById(R.id.alonRecyclerView);
        toolbar = findViewById(R.id.toolbar);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Alone.this, MainActivity.class);
                startActivity(intent);
            }
        });

        alonTable();
        finalArrayListTable();


        AlonAdapter alonAdapter = new AlonAdapter();
        alonRecyclerView.setAdapter(alonAdapter);
        alonRecyclerView.setLayoutManager(new LinearLayoutManager(Alone.this));


    }

    private class AlonAdapter extends RecyclerView.Adapter{

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
                View view1 = layoutInflater.inflate(R.layout.itemlayout, parent, false);
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
                            Toast.makeText(Alone.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(Alone.this, "ca-app-pub-3940256099942544/2247696110")
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
        hashMap.put("texTitle","- ফ্রেডেরিক নিয়চে!");
        hashMap.put("texDes","আমি একক, তাই আমি সকল লোকের মধ্যে একজন বা তার থেকে ভালো হতে পারি।");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- অলোন্সো ডিবার্ট!");
        hashMap.put("texDes","\"একক থাকতে ভীষণ দুর্বলতা প্রয়োজন করে না, তা হচ্ছে নিজের সঙ্গে সঙ্গে একক হওয়ার জন্য বড় মুদ্রা।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- বেটি ডেভিস");
        hashMap.put("texDes","\"এককিত্বে মোকাবিলা করতে সক্ষম হতে হলে আপনি নিজেকে ভালোভাবে জানতে হবেন।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","একাকীত্ব তখনই মানুষ অনুভব করে যখন সে নিজের সাথে কথা বলে কারণ তখন কেউ তার কথা শোনার মত থাকে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","একাকীত্ব অপরের দ্বারা সৃষ্টি হয় না। এটি তখনই তৈরি হয় যখন নিজের অন্তঃসত্ত্বা বলে যে ,”তোমার জন্য ভাবার এ জগতে কেউ নেই”।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","ঘুম নেই আঁখিপাতে\n" +
                "আমি যে একেলা,তুমিও একাকী\n" +
                "আজি এ বাদল-রাতে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","আমি আজি বসে আছি একা\n" +
                "দূরে নদী চলে যায় আঁকাবাঁকা\n" +
                "আমার মনের যত আশা\n" +
                "এ নদীর কলতানে ফিরিয়া পেয়েছি তার ভাষা।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","তুমি বিনা কাটে না বিরহের এদিন\n" +
                "তুমি ছাড়া পৃথিবী অর্থহীন\n" +
                "শূন্যতায় ঘেরা সারাটা দিন\n" +
                "তুমি এসে করে দাও মোর জীবন\n" +
                "নতুন করে রঙিন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","আমি একা থাকা অপছন্দ করি না কারণ আমি ভিড়ের মধ্যে অন্যতম একজন হতে চাইনা।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","কায়িক পরিশ্রম যেমন মানুষকে করে তোলে শারীরিকভাবে ক্লান্ত ,তেমনি দীর্ঘদিন একাকীত্বের মধ্য জীবনযাপন করলে সে হয়ে পড়ে মানসিকভাবে ক্লান্ত।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","মনের কথা বোঝাতে গেলে একলা বলতে হয়।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- - মায়া এ্যাঞ্জেলোউ");
        hashMap.put("texDes","\"এককিত্ব আপনার ভালোবাসা আপনার আত্মবিশ্বাস এবং স্বাধীনতা তৈরি করতে সাহায্য করে।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- ড্যালাই লামা");
        hashMap.put("texDes","\"একা থাকা একটি কৌশল, একজন আত্ম-বিকাশ যাত্রী হতে একটি অদভুত সুযোগ।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- পাওলো কোয়েল্হো!");
        hashMap.put("texDes","\"এককিত্ব একটি প্রাকৃতিক অবস্থা, তা মানতে হয় না নিজেকে একক ভাবে অনুভূত করার ক্ষমতা হতে হবে।\" ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","একা আমি একাই রব\n" +
                "এভাবেই একদিন চলে যাব!!");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","এ অন্ধকারে লাগে বড় একা\n" +
                "কবে তুমি আসবে আবার\n" +
                "দেবে আমায় দেখা?");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","এই বিশ্বব্রহ্মাণ্ডে একটি বিন্দুর মতো আমরা সবাই একা।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","সবথেকে কঠিনতম একাকীত্ব হল নিজেকে নিজের ভালো না লাগা ।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","নিজেকে ভালো করে জানার জন্যও নিজেকে পর্যালোচনা করার জন্য একাকীত্বের প্রয়োজনীয়তা আছে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes", "মানুষের কখনও কখনও একা থাকা ভালো কারণ সেই সময়ে কেউ আপনাকে সেভাবে আঘাত করতে পারে না।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","আমরা এই পৃথিবীতে সবাই একা এসেছি এবং একাই মৃত্যুবরণ করি ।অতএব নিঃসঙ্গতা অবশ্যই আমাদের জীবন যাত্রার একটি অংশ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","একাকীত্ব, মানুষকে নতুন করে নিজেকে আবিষ্কার করতে সুযোগ করে দেয়।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","একা থাকা প্রতিটা সময় মানুষকে শক্ত ও সাহসী করে তোলে ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","বন্ধুবিহীন একাকীত্ব অসহনীয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","মনকে সর্বদা শক্তিশালী করে রাখা যায় না , মাঝে মাঝে নিভৃতে একাকী থাকারও প্রয়োজন ;নিজের কান্না গুলির বহিঃপ্রকাশের জন্য।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","একাকীত্ব একটি অভ্যাসে দাঁড়িয়ে গেলে সেখান থেকে বেরিয়ে আসা খুবই কষ্টসাধ্য।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","অসৎ মানুষের সঙ্গ লাভ করার থেকে নিঃসঙ্গতা ও একাকীত্ব অধিকতর শ্রেয় ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","অবসর সময়ে নিজের সঙ্গকে উপভোগ করার জন্য একাকীত্ব অপরিহার্য।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknown");
        hashMap.put("texDes","\"আমি একক, কিন্তু নতুন পথে এগিয়ে চলছি।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Ralph Waldo Emerson!");
        hashMap.put("texDes","\"আমি নিজেকে খোজার মাধ্যমে পৃথিবীর মধ্যে একক।\" ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-  Lao Tzu");
        hashMap.put("texDes","\"একাকিত্ব আত্মবিশ্বাসের সূত্র, যা এককতা এবং সম্পূর্ণতা একে অপরকে দেখতে সহায় করে।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-  Dr. Seuss");
        hashMap.put("texDes","\"একক হওয়া মানে অত্যন্ত শক্তিশালী হওয়া, একক থাকতে পারা মানে একক হওয়া।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknown");
        hashMap.put("texDes","\"আমি যে বুকটি পড়ি, তা হলো আমার প্রতি দিনের অদ্ভুত কিছু সময় যা আমি নিজের সঙ্গে কাটাতে পারি।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Havelock Ellis!");
        hashMap.put("texDes","\"একক হওয়া মানে সম্পূর্ণভাবে নতুন পথে হাঁটতে সাহায্য করা।\" ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Orson Welles");
        hashMap.put("texDes","\"একক থাকা মানে তোমার সাথে তোমার সবচেয়ে ভালো বন্ধু থাকবে।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","বিচ্ছিন্নতার অনুভূতি সৃষ্টি করে একাকীত্ব , যা পরিশেষে মানুষের জীবনকে চরম কষ্ট প্রদান করে ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","সেই জিনিসটি মানুষকে স্বতন্ত্র করে যেটি অবধারিতভাবে তাকে একাকীত্বে নিমজ্জিত করেছিল।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","কখনো কখনো রুটিন মাফিক জীবনের ব্যস্ততার থেকে রেহাই পাবার জন্য একা থাকার প্রয়োজনীয়তা পড়ে ।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","ঠিকানাহীন চলেছি আমি\n" +
                "জানি না কোন পথে\n" +
                "একাকিত্বের যন্ত্রণা নিয়ে\n" +
                "বেঁচে আছি কোনোমতে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","একাকীত্বের সঙ্গী তুমি\n" +
                "আমার নীরবতার ভাষা\n" +
                "হে পরমপ্রিয় প্রাণনাথ\n" +
                "তুমি আমার মনের জাগাও সকল ভালোবাসা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes", "প্রিয়জনের বিদায় মানুষের মনকে দেয় সর্বাধিক একাকীত্ব ।\n");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","একাকী নিঃসঙ্গ জীবনের শ্রেষ্ঠ সঙ্গী একটি ভালো পুস্তক ।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","কিছু মানুষ একাকীত্বেই বেশি স্বচ্ছন্দ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","সবার মধ্যে থেকেও একলা অনুভব করাই হল সবথেকে কষ্টকর ও কঠিনতম একাকীত্ব।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","শুধুমাত্র বন্ধুত্বের অভাব ই একাকীত্ব এনে দেয় না; একাকীত্ব প্রকৃতপক্ষে লক্ষ্যের অভাব থেকে জন্ম নেয়।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes", "একাকীত্ব জীবনের সৌন্দর্যকে বাড়াতে সাহায্য করে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","মানবজাতি সত্যিই বড় বিচিত্র ।যখন তারা একা থাকে তখন তারা সবার সঙ্গ চায় ,আবার যখন তারা সবার মধ্যে থাকে তখন তারা একাকীত্বকেই কামনা করে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","কিছু সময়ের একাকীত্ব ভাল কিন্তু সারা জীবনের জন্য নয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","পৃথিবীর প্রত্যেকটা মানুষ কোনো না কোনো সময়ে একাকীত্বের যন্ত্রণা ভোগ করেছে ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","কারো স্মৃতি আঁকড়ে বেঁচে থাকার সব থেকে খারাপ দিক টি কেবলমাত্র কষ্ট নয় ; তা হল একাকীত্ব ।কারণ একাকীত্ব কারো সাথে ভাগ করে নেওয়া যায় না।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","একাকীত্ব মানে নিঃসঙ্গতা নয় এটি একটি ধারণা যে তোমাকে কেউ গুরুত্ব দেয় না, তোমার ব্যাপারে কেউ ভাবে না ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","“আমি একাকিত্ব অনুভব না করে একা থাকার চেষ্টা করছি।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","“আমি একা নই, আমার কল্পনায় অনেক বন্ধুরা আছে যার আমাকে আগে বাড়তে সাহস দায়।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","আমি একা নই, কিন্তু আমি একা – তোমাকে ছাড়া ।”");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","“এই সময় শক্তিশালী হওয়ার, একা হাঁটা শুরু করুন”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","“আপনার আশা হারাবেন না, যতই একাকিত্ব হোক না কেন!”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","“আমার একাকিত্ব খুব ভালো লাগছে, আমি তখনই তোমার সাথে যাবো যদি তুমি আমার নীরবতার চেয়েও মধুর হও।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","“আপনার বিকাশের শ্রেষ্ট মুহূর্তগুলি হলো যখন আপনি একাকিত্বের মধ্যে থেকে কিছু #deep thinking করেন এবং সেই চিন্তাধারা দিয়ে কিছু #creative করে দেখান দেখান।”");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","“সূর্যও একা। এবং সে এখনও জ্বলজ্বল করছে।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","“আমাদের সমস্ত দুঃখ আমাদের একা থাকার অক্ষমতা থেকেই আসে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","“বয়স বাড়ার সাথে সাথে আমি একাকিত্বে আরও স্বাচ্ছন্দ্যবোধ করি।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Charlie Chaplin!");
        hashMap.put("texDes","\"একক যাও, যাকে তোমার প্রতি আগ্রহ নেই।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-  Oscar Wilde");
        hashMap.put("texDes","\"একাকিত্ব একজন মুক্তির আভাস।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Rabindranath Tagore");
        hashMap.put("texDes","\"একলা চলো, একলা চলো, একলা চলো, যে পথ তুই চলে, সে পথের দরজা তোলো।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Marilyn Monroe");
        hashMap.put("texDes","\"একা থাকা আপনি বড় হতে পারেন, তারপরেও একা থাকা আপনি সম্পূর্ণ পূর্ণ হতে পারেন না।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Swami Vivekananda!");
        hashMap.put("texDes","\"একাকিত্ব আমাদের আত্মা এবং চৈতন্যের প্রাণী।\" ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Jean-Paul Sartre");
        hashMap.put("texDes","\"আমি একলা একলা হয়ে থাকি, একা হয়ে থাকি, একটু একটু হয়ে থাকি, তবুও একাকিত্ব তৈরি করতে হয়।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","একাকীত্ব একটি অভ্যাসে দাঁড়িয়ে গেলে সেখান থেকে বেরিয়ে আসা খুবই কষ্টসাধ্য।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","অসৎ মানুষের সঙ্গ লাভ করার থেকে নিঃসঙ্গতা ও একাকীত্ব অধিকতর শ্রেয় ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","অবসর সময়ে নিজের সঙ্গকে উপভোগ করার জন্য একাকীত্ব অপরিহার্য।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","একাকীত্ব তখনই মানুষ অনুভব করে যখন সে নিজের সাথে কথা বলে কারণ তখন কেউ তার কথা শোনার মত থাকে না।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","একাকীত্ব অপরের দ্বারা সৃষ্টি হয় না। এটি তখনই তৈরি হয় যখন নিজের অন্তঃসত্ত্বা বলে যে ,”তোমার জন্য ভাবার এ জগতে কেউ নেই”।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Whitney Houston");
        hashMap.put("texDes","\"একাকিত্ব একটি শক্তিশালী অবস্থান, যা তোমাকে তোমার নিজেকে জানতে দেয়.\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-  Oscar Wilde!");
        hashMap.put("texDes","একলা হওয়া মানে ভালোবাসা অপরকে শেখা।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Dr. Seuss");
        hashMap.put("texDes","\"আমি ভালোবাসি একাকিত্ব, একক অবস্থায় বৃদ্ধি করার জন্য যত্ন নেয়া।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- George Carlin");
        hashMap.put("texDes","\"আমি একা আছি কারণ আমি একাকিত্বকে একটি মিষ্টি প্রস্তুতি মনে করি।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-  Jodi Picoult");
        hashMap.put("texDes","\"আমি একলা হয়ে থাকি, কিন্তু আমি একা নয়।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-  Oprah Winfrey!");
        hashMap.put("texDes","\"আমি একলা হয়ে থাকি না, এটি আমি একলা পথে হোয়ারিয়া পড়ার সময় আমার সাথে থাকতে বলে।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Paulo Coelho");
        hashMap.put("texDes","\"একক হতে মানা এবং এককতা হতে মানা, এই দুটি আলাদা কিছু।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","“একা থাকার অর্থ হল নিজের সম্পর্কে আরও জানার জন্য নিজেকে সময় দেওয়া।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","“আপনি কখনোই একাকিত্ব অনুভব করবেন না যদি আপনি যার সঙ্গে আছেন তাকে ভালোবাসেন।”\n");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","“যে সময়টা আপনি একাকী বোধ করেন সেই সময়টাই আপনার নিজের আসল স্বভাবে ফিরে এসে কোনো চিন্তা ছাড়া থাকা সবচেয়ে বেশি প্রয়োজন।”");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","মনকে সর্বদা শক্তিশালী করে রাখা যায় না , মাঝে মাঝে নিভৃতে একাকী থাকারও প্রয়োজন ;নিজের কান্না গুলির বহিঃপ্রকাশের জন্য।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","মনে রেখো তুমি জগতে একা নয়।তোমার মধ্যে ভগবান আর তোমার নিজস্ব বুদ্ধিমত্তা ও বাস করে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","আমি একা থাকা অপছন্দ করি না কারণ আমি ভিড়ের মধ্যে অন্যতম একজন হতে চাইনা।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","কায়িক পরিশ্রম যেমন মানুষকে করে তোলে শারীরিকভাবে ক্লান্ত ,তেমনি দীর্ঘদিন একাকীত্বের মধ্য জীবনযাপন করলে সে হয়ে পড়ে মানসিকভাবে ক্লান্ত।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","বিচ্ছিন্নতার অনুভূতি সৃষ্টি করে একাকীত্ব , যা পরিশেষে মানুষের জীবনকে চরম কষ্ট প্রদান করে ।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","শুধুমাত্র বন্ধুত্বের অভাব ই একাকীত্ব এনে দেয় না; একাকীত্ব প্রকৃতপক্ষে লক্ষ্যের অভাব থেকে জন্ম নেয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","একাকীত্ব জীবনের সৌন্দর্যকে বাড়াতে সাহায্য করে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","মানবজাতি সত্যিই বড় বিচিত্র ।যখন তারা একা থাকে তখন তারা সবার সঙ্গ চায় ,আবার যখন তারা সবার মধ্যে থাকে তখন তারা একাকীত্বকেই কামনা করে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- আর্থার স্কপেনহার");
        hashMap.put("texDes","তখন পর্যন্ত একজন মানুষ নিজেকে আবিষ্কার করতে পারেনা যতক্ষণ সে একদম একা হয়ে যায়।আর যদি সে একাকিত্ব পছন্দই না করে তবে সে কখনো আবিষ্কার করতে পারবেনা।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","-  গিলারমো ম্যালডোরাডো");
        hashMap.put("texDes","একাকিত্ব সঙ্গের অভাব নয় বরং এটি অভিপ্রায়ের অভাব।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- এডা জে লিসান");
        hashMap.put("texDes","আমরা যখন একা থাকতে পারিনা তখন আমরা তার মূল্য দিতে ব্যর্থ হই যে আমাদের সাথে জন্ম থেকে মৃত্যু পর্যন্তই থাকে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- লিওনার্দো দা ভিঞ্চি!");
        hashMap.put("texDes"," তুমি যখন একা থাক শুধুমাত্র তখনই তুমি একান্ত তোমার হয়েই থাকতে পার।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes"," কখনো কখনো তোমার একাকী দাড়াতে হয়, এটা বোঝার জন্য যে তুমি এখনো পার।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- র\u200D্যাল্ফ ওয়াল্ডো এমারসন");
        hashMap.put("texDes","তুমি ছাড়া অন্য কোন কিছুই তোমাকে নিজের মত করে সুখী করতে সক্ষম নয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","নিজেকে ভালো করে জানার জন্যও নিজেকে পর্যালোচনা করার জন্য একাকীত্বের প্রয়োজনীয়তা আছে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত!");
        hashMap.put("texDes","মানুষের কখনও কখনও একা থাকা ভালো কারণ সেই সময়ে কেউ আপনাকে সেভাবে আঘাত করতে পারে না।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","আমরা এই পৃথিবীতে সবাই একা এসেছি এবং একাই মৃত্যুবরণ করি ।অতএব নিঃসঙ্গতা অবশ্যই আমাদের জীবন যাত্রার একটি অংশ।\n");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","একাকীত্ব, মানুষকে নতুন করে নিজেকে আবিষ্কার করতে সুযোগ করে দেয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- সংগৃহীত");
        hashMap.put("texDes","একা থাকা প্রতিটা সময় মানুষকে শক্ত ও সাহসী করে তোলে ।");
        arrayList.add(hashMap);

    }
}