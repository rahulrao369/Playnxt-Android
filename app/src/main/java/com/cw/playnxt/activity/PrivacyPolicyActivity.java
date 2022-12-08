package com.cw.playnxt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityEditProfileBinding;
import com.cw.playnxt.databinding.ActivityPrivacyPolicyBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;

public class PrivacyPolicyActivity extends AppCompatActivity {
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
        context = PrivacyPolicyActivity.this;
        headerBinding.tvHeading.setText("Privacy Policy");
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
        binding.webView.loadUrl("https://playnxt.app/privacy-policy");
//        binding.webView.loadDataWithBaseURL(null, "https://playnxt.app/privacy-policy", "text/html", "UTF-8", null);
    }
}