package com.cw.playnxt.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.cw.playnxt.databinding.ActivityPrivacyPolicyBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;

public class TermsAndConditionActivity extends AppCompatActivity {
    Context context;
    private ActivityPrivacyPolicyBinding binding;
    private HeaderLayoutBinding headerBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
    }
    public void init() {
        context = TermsAndConditionActivity.this;
        headerBinding.tvHeading.setText("Terms & Conditons");
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);


        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setDomStorageEnabled(true);

        binding.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.e("MYT", "###url= " + url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {

            }
        });
        binding.webView.loadUrl("https://www.trblclef.com/terms-and-conditions");
//        binding.webView.loadDataWithBaseURL(null, "https://playnxt.app/privacy-policy", "text/html", "UTF-8", null);
    }
}