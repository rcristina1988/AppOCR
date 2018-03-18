package com.evora.rita.handwrittingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button resetButton;
    private TextView textView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetElements();

        ConfigureWebView();
        ConfigureWebViewSettings();
        AddJavascriptInterface();

        webView.loadUrl("file:android_asset/index.html");
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Reseted");
                webView.reload();
            }
        });
    }

    private void GetElements() {
        resetButton = findViewById(R.id.btnReset);
        textView = findViewById(R.id.insertedText);
        webView = findViewById(R.id.webview);
    }

    private void ConfigureWebView() {
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebContentsDebuggingEnabled(true);
    }

    private void AddJavascriptInterface() {
        webView.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public void TextRecognized(String text) {
                if(text != null && text.length()>0)
                {
                    OnTextRecognized(text);
                }
            }
        }, "TextRecognizer");
    }

    private void ConfigureWebViewSettings() {
        WebSettings settings =  webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
    }

    private void OnTextRecognized(String text){
        try {
            this.textView.setText(text);
        }
        catch(Exception ex){}
    }
}

