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

public class Lost extends AppCompatActivity {

    RecyclerView LostRecyclerView;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    ArrayList<HashMap<String,String>> finalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lost);
        LostRecyclerView = findViewById(R.id.LostRecyclerView);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });



        ListTable();
        finalArrayListTable();


        LostAdapter lostAdapter = new LostAdapter();
        LostRecyclerView.setAdapter(lostAdapter);
        LostRecyclerView.setLayoutManager(new LinearLayoutManager(Lost.this));

    }

    private class LostAdapter extends RecyclerView.Adapter{

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
                            Toast.makeText(Lost.this, "কপি", Toast.LENGTH_SHORT).show();
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

                AdLoader adLoader = new AdLoader.Builder(Lost.this, "ca-app-pub-3940256099942544/2247696110")
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
    private void ListTable(){

        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Socrates");
        hashMap.put("texDes","The unexamined life is not worth lilving\n" +
                "- অপরিক্ষীত জীবনের কোনো মুল্য নেই।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Francis Bacon");
        hashMap.put("texDes","Opportunity makes a thief\n" +
                "- সুযোগেই মানুষকে চোর বানায়।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- William Sexpear");
        hashMap.put("texDes","When sorrows come, they come not single spices but in battalations. \n" +
                "– দুঃখ যখন আসে তখন দলবেধে আসে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- William Sexpear");
        hashMap.put("texDes","Cowards die many times before their death \n" +
                "– ভীরুরা মরার আগেই অনেকবার মারা যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," – Nelson Mandela");
        hashMap.put("texDes","“The greatest glory in living lies not in never falling, but in rising every time we fall.”\n" +
                "“জীবনযাপনের সর্বাধিক গৌরব কখনই পড়ে যাওয়াতে নয়, বরং প্রতিবারই আমরা পড়ে যাওয়ার পরে উঠে দাঁড়ানো তে রয়েছে।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Redwan Masood");
        hashMap.put("texDes","“One mistake may make a person cry a lot, but remember there are some mistakes in life that can save a thousand mistakes in the future.”\n" +
                "“একটা ভুল মানুষকে হয়তো অনেক কাঁদায়, কিন্তু মনে রাখতে হবে জীবনে এমন কিছু ভুল আছে যা ভবিষ্যতে হাজারটা ভুল থেকে বাচায়”।”");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Sadness flies away on the wings of time.\n" +
                "দুঃখ সময়ের ডানায় উড়ে যায়।\n");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes", "You think you're lost but you're not lost on your own.\n" +
                "আপনি মনে করেন আপনি হারিয়ে গেছেন কিন্তু আপনি নিজে হারিয়ে যাননি। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Grief does not change you, it reveals you.\n" +
                "দুঃখ আপনাকে পরিবর্তন করে না, এটি আপনাকে প্রকাশ করে। ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Henry Ford");
        hashMap.put("texDes","“When everything seems to be going against you, remember that the airplane takes off against the wind, not with it.” \n" +
                "\"যখন সবকিছু আপনার বিরুদ্ধে যাচ্ছে বলে মনে হয়, মনে রাখবেন যে বিমানটি বাতাসের বিপরীতে উড্ডয়ন করে, এটি দিয়ে নয়।\" ");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Henry David Thoreau");
        hashMap.put("texDes","“Not until we are lost do we begin to understand ourselves.” \n" +
                "\"আমরা হারিয়ে না যাওয়া পর্যন্ত আমরা নিজেদের বুঝতে শুরু করি।\"");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Joseph Campbell");
        hashMap.put("texDes","“We must be willing to let go of the life we have planned, so as to have the life that is waiting for us.”\n" +
                "\"আমাদের অবশ্যই আমাদের পরিকল্পনা করা জীবন ছেড়ে দিতে ইচ্ছুক হতে হবে, যাতে সেই জীবন আমাদের জন্য অপেক্ষা করছে।");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Michel de Montaigne");
        hashMap.put("texDes","“The soul which has no fixed purpose in life is lost; to be everywhere, is to be nowhere.” \n" +
                "“যে আত্মার জীবনের কোন নির্দিষ্ট উদ্দেশ্য নেই সে হারিয়ে গেছে; সর্বত্র থাকা, কোথাও না থাকা।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Dalai Lama");
        hashMap.put("texDes","“It is worth remembering that the time of greatest gain in terms of wisdom and inner strength is often that of greatest difficulty.” \n" +
                "\"এটি মনে রাখা উচিত যে জ্ঞান এবং অভ্যন্তরীণ শক্তির দিক থেকে সর্বাধিক লাভের সময়টি প্রায়শই সবচেয়ে বড় অসুবিধা।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","– Maya Angelou");
        hashMap.put("texDes","“We may encounter many defeats but we must not be defeated.” \n" +
                "\"আমরা অনেক পরাজয়ের সম্মুখীন হতে পারি কিন্তু আমাদের পরাজিত হওয়া উচিত নয়।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle"," – Nils Kjaer");
        hashMap.put("texDes","“It is those who get lost, who find the new ways.”\n" +
                "\"যারা হারিয়ে যায়, তারাই নতুন পথ খুঁজে পায়।\" ");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Marion Cotillard");
        hashMap.put("texDes","“When people don’t know exactly what depression is, they can be judgemental.” \n" +
                "\"লোকেরা যখন বিষণ্নতা ঠিক কী তা জানে না, তখন তারা বিচারযোগ্য হতে পারে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Plato");
        hashMap.put("texDes","“We can easily forget a child who is afraid of the dark. The real tragedy of life is when men are afraid of the light.” \n" +
                "“আমরা সহজেই এমন একটি শিশুকে ভুলে যেতে পারি যে অন্ধকারকে ভয় পায়। জীবনের আসল ট্র্যাজেডি হল যখন মানুষ আলোকে ভয় পায়।\"");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","~Terri Guillemets");
        hashMap.put("texDes","Those we love and lose are always connected by heartstrings into infinity. \n" +
                "আমরা যাদের ভালবাসি এবং হারালাম তারা সর্বদা হার্টস্ট্রিং দ্বারা অনন্তের সাথে সংযুক্ত থাকে। ");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","“If you want to lose a fight, talk about it first.”\n" +
                "\"আপনি যদি লড়াইয়ে হারতে চান তবে প্রথমে এটি সম্পর্কে কথা বলুন।\"");
        hashMap.put("texDes","");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","“Fight not to win, but to avoid losing. A surefire losing strategy.”\n" +
                "“জেতার জন্য নয়, হার এড়াতে লড়াই করো। নিশ্চিত হারানোর কৌশল।”");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","What lies behind you and what lies in front of you, pales in comparison to what lies inside of you.\n" +
                "আপনার পিছনে কি আছে এবং আপনার সামনে যা আছে, আপনার ভিতরে যা আছে তার তুলনায় ফ্যাকাশে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","― Mary E. Pearson");
        hashMap.put("texDes","“It can take years to mold a dream. It takes only a fraction of a second for it to be shattered.”\n" +
                "“একটি স্বপ্ন ঢালাই করতে কয়েক বছর সময় লাগতে পারে। এটি ভেঙ্গে যেতে এক সেকেন্ডের একটি ভগ্নাংশ সময় লাগে।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Betty Friedan");
        hashMap.put("texDes","Aging is not \"lost youth\" but a new stage of opportunity and strength.\n" +
                "বার্ধক্য \"হারানো যৌবন\" নয় বরং সুযোগ এবং শক্তির একটি নতুন পর্যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Confucius");
        hashMap.put("texDes","He who learns but does not think, is lost. He who thinks but does not learn is in great danger.\n" +
                "যে শেখে কিন্তু চিন্তা করে না সে হারিয়ে যায়। যে চিন্তা করে কিন্তু শেখে না সে মহা বিপদে পড়ে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Henry David Thoreau");
        hashMap.put("texDes","Not until we are lost do we begin to understand ourselves.\n" +
                "আমরা হারিয়ে না যাওয়া পর্যন্ত আমরা নিজেদের বুঝতে শুরু করি না।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Billy Graham");
        hashMap.put("texDes","When wealth is lost, nothing is lost; when health is lost, something is lost; when character is lost, all is lost.\n" +
                "সম্পদ হারিয়ে গেলে কিছুই হারায় না; যখন স্বাস্থ্য হারিয়ে যায়, কিছু হারিয়ে যায়; চরিত্র হারিয়ে গেলে সব হারিয়ে যায়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Rita Rudner");
        hashMap.put("texDes","I never panic when I get lost. I just change where it is I want to go.\n" +
                "আমি হারিয়ে গেলে আমি কখনই আতঙ্কিত হই না। আমি যেখানে যেতে চাই তা পরিবর্তন করি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Lisa Hammond");
        hashMap.put("texDes","Sometimes on the way to your dream, you get lost and find a better one.\n" +
                "কখনও কখনও আপনার স্বপ্নের পথে, আপনি হারিয়ে যান এবং আরও ভাল একটি খুঁজে পান।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Washington Irving");
        hashMap.put("texDes","Love is never lost. If not reciprocated, it will flow back and soften and purify the heart.\n" +
                "ভালোবাসা কখনো হারায় না। যদি প্রতিদান না দেওয়া হয়, তবে এটি ফিরে প্রবাহিত হবে এবং হৃদয়কে নরম ও শুদ্ধ করবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Sometimes the bad things that happen in our lives put us directly on the path to the best things that will ever happen to us.\n" +
                "কখনও কখনও আমাদের জীবনে ঘটে যাওয়া খারাপ জিনিসগুলি আমাদের সরাসরি সেরা জিনিসগুলির পথে নিয়ে যায় যা আমাদের সাথে ঘটবে।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","You can’t start the next chapter if you keep rereading the last.\n" +
                "আপনি যদি শেষটি পুনরায় পড়তে থাকেন তবে আপনি পরবর্তী অধ্যায়টি শুরু করতে পারবেন না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","― J.R.R. Tolkien,");
        hashMap.put("texDes","“Not all those who wander are lost.”\n" +
                "\"যারা ঘুরে বেড়ায় তারা সবাই হারিয়ে যায় না।\"");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Just because I am smiling, doesn’t mean my life is perfect.\n" +
                "কারণ আমি হাসছি, তার মানে এই নয় যে আমার জীবন নিখুঁত।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","If you leave without a reason don’t come back with an excuse.\n" +
                "কারণ ছাড়া চলে গেলে অজুহাত দিয়ে ফিরে আসবেন না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","The sad moment when you find an old conversation between YOU and Someone you don’t talk to anymore.\n" +
                "দুঃখের মুহূর্ত যখন আপনি আপনার এবং এমন কারো মধ্যে একটি পুরানো কথোপকথন খুঁজে পান যার সাথে আপনি আর কথা বলেন না।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","The word happiness would lose its meaning if it were not balanced by sadness.\n" +
                "সুখ শব্দটি তার অর্থ হারাবে যদি এটি দুঃখের সাথে ভারসাম্যপূর্ণ না হয়।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","I can’t trust someone who is friends with everyone.\n" +
                "আমি এমন কাউকে বিশ্বাস করতে পারি না যে সবার সাথে বন্ধু।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Trust is like paper. Once it’s crumpled it can’t be perfect again.\n" +
                "বিশ্বাস হল কাগজের মত। একবার চূর্ণবিচূর্ণ হয়ে গেলে তা আর নিখুঁত হতে পারে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","My weakness is that I care too much.\n" +
                "আমার দুর্বলতা হল আমি খুব বেশি যত্ন করি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Sorry heart, but I am listening to my brain this time. I know better.\n" +
                "দুঃখিত হৃদয়, কিন্তু আমি এই সময় আমার মস্তিষ্ক শুনছি. আমি ভাল জানি.");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes", "My heartache won’t define me.\n" +
                "আমার হৃদয়ের ব্যথা আমাকে সংজ্ঞায়িত করবে না।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","- Unknow");
        hashMap.put("texDes","Farewell to a chapter I never wanted to end.\n" +
                "একটি অধ্যায়ের বিদায় যা আমি কখনই শেষ করতে চাইনি।");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("itemType","read");
        hashMap.put("texTitle","Charlie Chaplin");
        hashMap.put("texDes","“I always like walking in the rain, so no one can see me crying.” \n" +
                "\"আমি সবসময় বৃষ্টিতে হাঁটতে পছন্দ করি, যাতে কেউ আমাকে কাঁদতে না দেখতে পারে।\"");
        arrayList.add(hashMap);

    }
}