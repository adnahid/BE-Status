package com.delower.bestatus;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.statusapplication.R;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class DetailsActivity extends AppCompatActivity {



    public static String StatusTitle = "";
    public static String StatusText = "";
    public static Bitmap StatusBitmap = null;
    FloatingActionButton fabShare,fabCopy;
    ImageView topImage;
    TextView textLayout;

    Button buttonLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        fabShare = findViewById(R.id.fabShare);
        fabCopy = findViewById(R.id.fabCopy);
        topImage = findViewById(R.id.topImage);
        textLayout = findViewById(R.id.textLayout);
        buttonLayout = findViewById(R.id.buttonLayout);


        MobileAds.initialize(DetailsActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        String share = "https://cdn-icons-png.flaticon.com/128/2099/2099122.png";
        String copy = "https://cdn-icons-png.flaticon.com/128/1828/1828249.png";


        Picasso.get().load(share).placeholder(R.drawable.baseline_add_home_24).into(fabShare);
        Picasso.get().load(copy).placeholder(R.drawable.baseline_add_home_24).into(fabCopy);


        buttonLayout.setText(StatusTitle);
        textLayout.setText(StatusText);

        Random rnd = new Random();
        int color = Color.argb(220, rnd.nextInt(220), rnd.nextInt(220), rnd.nextInt(220));
       buttonLayout.setBackgroundColor(color);

        if (StatusBitmap!=null){
            topImage.setImageBitmap(StatusBitmap);
        }

        fabCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textLayout!=null){

                    ClipboardManager clipboard = (ClipboardManager) getSystemService(DetailsActivity.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("copy",textLayout.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(DetailsActivity.this, "কপি", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,textLayout.getText().toString());
                intent.setType("text/plain");
                intent = Intent.createChooser(intent,"share via");
                startActivity(intent);


            }
        });














    }
}