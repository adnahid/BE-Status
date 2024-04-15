package EnglishStatusActivity;

import static android.app.PendingIntent.getActivity;

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

public class InspiredEng extends AppCompatActivity {

    RecyclerView inspriedRecyclerView;
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inpired_eng);
        inspriedRecyclerView = findViewById(R.id.inspriedRecyclerView);
        //toolbar = findViewById(R.id.toolbar);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        


        

        inspriedTable();
        finalArrayListTable();


        InspriedAdapter inspriedAdapter = new InspriedAdapter();
        inspriedRecyclerView.setAdapter(inspriedAdapter);
        inspriedRecyclerView.setLayoutManager(new LinearLayoutManager(InspiredEng.this));



    }

    private class InspriedAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(InspiredEng.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(InspiredEng.this, "ca-app-pub-3940256099942544/2247696110")
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
    private void inspriedTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Estee Lauder");
        hashMap.put("texDes","“I never dreamed about success, I worked for it.” \n" +
                "“আমি কখনও সাফল্যের স্বপ্ন দেখিনি, আমি এর জন্য কাজ করেছি।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Theodore Roosevelt");
        hashMap.put("texDes","“Believe you can and you’re halfway there.” \n" +
                "“বিশ্বাস করুন আপনি পারবেন এবং আপনি সেখানে অর্ধেক।” ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– George Bernard Shaw");
        hashMap.put("texDes","“Life isn’t about finding yourself. Life is about creating yourself.” \n" +
                "“জীবন নিজেকে সন্ধান করার ব্যাপারে নয়। জীবন নিজেকে সৃষ্টির ব্যাপারে।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Alexander Graham Bell");
        hashMap.put("texDes","“Before anything else, preparation is the key to success.” \n" +
                "“অন্য যে কোনও কিছুর আগে প্রস্তুতিই সাফল্যের মূল চাবিকাঠি।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," - Steve Jobs");
        hashMap.put("texDes","The only way to do great work is to love what you do." +
                "মহান কাজ করার একমাত্র উপায় হলো তা প্রিয় কাজে ভালোবাসা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes", "Best friend: one million memories, ten thousand inside jokes, one hundred shared secrets.\n" +
                "\"শ্রেষ্ঠ বন্ধু: এক মিলিয়ন স্মৃতি, দশ হাজার ভিতরের জোকস, একশ ভাগ অংশিত গোপন রহস্য।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Friends knock on the door. Best friend walks into your house and starts eating.\n" +
                "বন্ধুরা দরজা খাটছে। সেরা বন্ধু আপনার বাড়িতে ঢুকে নিজেকে খাওয়া শুরু করে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Never let your best friend feel lonely… disturb him her all the time.\n" +
                "\"কখনও আপনার সেরা বন্ধুকে একা অনুভব করতে দিবেন না... সব সময় তাকে ব্যতিক্রমণ করুন।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Good friends are like Stars. You don’t always see them but you know they will be with you forever.\n" +
                "ভালো বন্ধুরা তারা মতো। আপনি সব সময় তাদের দেখতে পান না কিন্তু আপনি জানেন যে তারা আপনার সাথে চিরকাল থাকবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","True friends are like burning stars; they shine brightest on the darkest nights.\n" +
                "সত্যিকারের বন্ধুরা যেন পৃথিবীর সবচেয়ে অন্ধকারে তারা সবচেয়ে উজ্জ্বল প্রকাশ করে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Abraham Lincoln");
        hashMap.put("texDes","In the end, it's not the years in your life that count. It's the life in your years.\" \n" +
                "\"শেষে, আপনার জীবনের বছরগুলি গণনা করে না। আপনার জীবনের বছরে কী বিচিত্রতা রয়েছে, সেটি গণনা হয়।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," - Theodore Roosevelt");
        hashMap.put("texDes", "\"Believe you can and you're halfway there.\"\n" +
                "\"মনে করুন আপনি পারেন, আর আপনি অধম পথে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Sam Levenson");
        hashMap.put("texDes","\"Don't watch the clock; do what it does. Keep going.\" \n" +
                "\"ঘড়িটি দেখবেন না; সেটি যেভাবে কাজ করে তাই করুন। চলা যাওয়ার পথে থাকুন।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","When your actions inspire the people around you to dream more, learn more, and become more, you’re a leader.\n" +
                "যখন আপনার ক্রিয়াবলী আপনার চারপাশের মানুষদেরকে আরো স্বপ্ন দেখাতে, আরও অনেক শেখাতে, আরও উন্নত হতে উৎসাহিত করে, তখন আপনি একটি নেতা।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","To be successful, you must know something everyone else doesn’t know.\n" +
                "সফল হতে হলে, আপনাকে যে কিছু জানা দরকার যা সবার অজানা।\n");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Don’t wish it was easier, wish you have been better.\n" +
                "\"সহজ হতো কামনা করো না, এটা করা উচিত ছিল যে আপনি ভালো হতেন।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Successful people will always do what unsuccessful people ain’t willing to do.\n" +
                "সফল ব্যক্তিগুলো সর্বদা সেই কাজগুলো করবে যা অসফল ব্যক্তিগুলো করতে সামর্থ্য অভিমানী নয়।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes", "Everyone first has a will to win, but in the end, only a few have a will to prepare.\n" +
                "সবাইকে প্রথমে জয়ের ইচ্ছা থাকে, কিন্তু শেষে কেবল কয়েক জনেরই প্রস্তুতির ইচ্ছা থাকে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","If you desire your life to be meaningful, you’ve got to get up, go out there and do something about it.\n" +
                "\"আপনি যদি আপনার জীবনকে অর্থপূর্ণ করতে চান, তবে আপনাকে উঠে দাঁড়ান, বাইরে যান এবং এটি সম্পর্কে কিছু করতে হবে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Herbert");
        hashMap.put("texDes", "Help thyself and God will help thee\n" +
                "-  পরিশ্রমীদের আল্লাহ সাহায্য করেন");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Walt Disney");
        hashMap.put("texDes","“The way to get started is to quit talking and begin doing.” \n" +
                "“শুরু করার উপায় হলো কথা বন্দ করা এবং কাজ শুরু করা।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","_Aristotle");
        hashMap.put("texDes","“It is during our darkest moments that we must focus to see the light.” \n" +
                "“আমাদের সব চেয়ে অন্ধকার সময়ে আমাদের আলো দেখার জন্য মনোনিবেশ করা উচিত।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Ralph Waldo Emerson");
        hashMap.put("texDes","“Do not go where the path may lead, go instead where there is no path and leave a trail.” \n" +
                "“সেখানে যেও না যেইখানে পথ নিয়ে যায়, পরিবর্তে সেখানে যাও যেখানে কোনো পথ নেই এবং একটি পথচিহ্ন ছেড়ে দাও।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Maya Angelou");
        hashMap.put("texDes","“You will face many defeats in life, but never let yourself be defeated.”\n" +
                "“আপনি জীবনে অনেক পরাজয়ের মুখোমুখি হবেন, কিন্তু নিজেকে কখনও পরাজিত হতে দেবেন না।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Napoleon Hill");
        hashMap.put("texDes","“Whatever the mind of man can conceive and believe, it can achieve.” \n" +
                "“মানুষের মন যা ধারণা ও বিশ্বাস করতে পারে তা অর্জন করতে পারে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– William Shakespeare");
        hashMap.put("texDes","“We know what we are, but know not what we may be.” \n" +
                "“আমরা কী তা আমরা জানি তবে আমরা কী হতে পারি তা জানি না।” ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Humayun Ahmed");
        hashMap.put("texDes","“The human child who can conquer small desires can also conquer big desires.”\n" +
                "“যে মানব সন্তান ক্ষুদ্র কামনা জয় করতে পারে সে বৃহৎ কামনাও জয় করতে পারে।” ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Vanlun");
        hashMap.put("texDes","“Life means success and success means misery.” \n" +
                "“জীবন মানেই সাফল্য এবং সাফল্য মানেই দু্র্ভোগ।”");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Light is appreciated because there is dark.\n" +
                "অন্ধকার আছে বলেই আলোর কদর।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Friendship isn’t about whom you have known the longest. It’s about who came, and never left your side.\n" +
                "\"বন্ধুত্ব সবচেয়ে বেশি কাল পালা কে জানতে নয়, এটা তা নিয়ে যারা এসেছে এবং কখনও আপনার পাশে থেকে চলে যায় নি।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Dear best friend, I don’t know how to thank you but I’m lucky to have you in my life.\n" +
                "প্রিয় সখা, আমি তোমার কাছে কিভাবে ধন্যবাদ জানাব, তা জানি না, কিন্তু আমি আমার জীবনে তোমাকে দেখতে ভাগ্যবান।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Best friends are the people you can do anything and nothing with and still have the best time.\n" +
                "\"সেরা বন্ধুরা তারা যারা তোমার সঙ্গে যা কিছু করতে পারে বা কিছুই না করতে পারে, তবুও সেরা সময় কাটাতে পারে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","True friends don’t judge each other. They judge other people together.\n" +
                "সত্যিকারের বন্ধুরা একে অপরকে মূল্যায়ন করেন না। তারা একসাথে অন্য মানুষকে মূল্যায়ন করে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Best Friend makes the good times better and bad times easier.\n" +
                "\"সেরা বন্ধু ভালো সময়গুলি ভালো করে এবং খারাপ সময়গুলি সহজ করে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Finding friends with the same mental disorder: priceless!\n" +
                "\"একই মানসিক ব্যাধির সাথে বন্ধু খুঁজে পাওয়া: অমূল্য়!\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes", "Friendship isn’t a big thing. It’s millions of small things.\n" +
                "\"বন্ধুত্ব একটা বড় জিনিস নয়। এটা অনেক ছোট ছোট জিনিস।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","It’s not how many friends you can count, it’s how many of those you can count on.");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Having those weird conversations with your best friend and thinking, if anyone heard us, we’d be in a mental hospital.");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Don't wait for opportunities, create them.\n" +
                "সুযোগের জন্য অপেক্ষা করবেন না, সেগুলি তৈরি করুন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Life is a journey, enjoy the ride.\n" +
                "জীবন একটি ভ্রমণ, যাত্রা উপভোগ করুন।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Be the reason someone smiles today.\n" +
                "আজকে কারো হাসার উপলক্ষ হও.");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Mahatma Gandhi");
        hashMap.put("texDes","“Learn as if you will live forever, live like you will die tomorrow.” \n" +
                "\"শিখুন যেন আপনি চিরকাল বেঁচে থাকবেন, এমনভাবে বাঁচুন যেন আপনি আগামীকাল মারা যাবেন।\" ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","— Henry David Thoreau");
        hashMap.put("texDes","“Success usually comes to those who are too busy to be looking for it.” \n" +
                "\"সাফল্য সাধারণত তাদের কাছে আসে যারা এটি খুঁজতে খুব ব্যস্ত।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","“You learn more from failure than from success. Don’t let it stop you. Failure builds character.”\n" +
                "\"আপনি সাফল্যের চেয়ে ব্যর্থতা থেকে বেশি শিখেন। এটি আপনাকে থামাতে দেবেন না। ব্যর্থতা চরিত্র গঠন করে।\"");
        arrayList.add(hashMap);

    }

}