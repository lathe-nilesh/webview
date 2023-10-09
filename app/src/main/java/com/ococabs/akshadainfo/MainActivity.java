package com.ococabs.akshadainfo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String Token = SharedPref.getInstance(MainActivity.this).getToken();
        if (Token.equals("")) {
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {

                if (!task.isSuccessful()) {
                    // Toast.makeText(getApplicationContext(), "fisSuccessful" , Toast.LENGTH_LONG).show();
                    return;
                }
                //get the token from task
                String token = task.getResult();
                Toast.makeText(getApplicationContext(),"Please enter valid OTP : "+token, Toast.LENGTH_LONG).show();
                SharedPref.getInstance(MainActivity.this).saveToken(token);
                // Toast.makeText(getApplicationContext(), "Token 11" + token , Toast.LENGTH_LONG).show();
            }).addOnFailureListener(e -> {
                // failure restart app
                // Toast.makeText(getApplicationContext(), "fcm-token token aobL" , Toast.LENGTH_LONG).show();
            });
        }
        WebView mywebView = findViewById(R.id.webview);
        mywebView.setWebViewClient(new WebViewClient());
        mywebView.loadUrl("https://www.akshadainfosystem.com");
        WebSettings webSettings= mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}