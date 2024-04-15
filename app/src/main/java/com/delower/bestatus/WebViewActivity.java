package com.delower.bestatus;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//import com.example.statusapplication.R;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    public static String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web_view);
        webView = findViewById(R.id.webView);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(WebViewActivity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();



        if (networkInfo!=null && networkInfo.isConnected()){
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(url);
        }else {
            //Lottie.setVisibility(View.GONE);
            new AlertDialog.Builder(WebViewActivity.this)
                    .setTitle("No Internet")
                    .setMessage("Please Connect Your Internet")
                    .setIcon(R.drawable.no_internet)
                    .show();


        }

    }
}