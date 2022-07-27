package com.cw.playnxt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityChatBinding;
import com.cw.playnxt.server.Allurls;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Customprogress;
import com.squareup.picasso.Picasso;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{
    public static String CHAT_WEBVIEW_URL;
    Context context;
    private ActivityChatBinding binding;
    private MySharedPref mySharedPref;

    String userId ="";
    String receiverId ="";
    String receiverName ="";
    String receiverImage ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
        onclicks();

    }
    private void init() {
        context = ChatActivity.this;
        mySharedPref = new MySharedPref(context);
        Intent intent = getIntent();
        if (intent != null) {
            receiverId =intent.getStringExtra("receiverId");
            receiverName = intent.getStringExtra("receiverName");
            receiverImage =intent.getStringExtra("receiverImage");
            Log.d("TAG","receiverId ="+receiverId);
            Log.d("TAG","receiverName ="+receiverName);
            Log.d("TAG","receiverImage ="+receiverImage);
            userId = mySharedPref.getSavedUserid();
            Log.e("TAG","####userId"+userId);
            Log.e("TAG","####receiverId"+receiverId);

            setData();
            openChatWebview();
        }

    }

    public void onclicks(){
        binding.btnBack.setOnClickListener(this);
        binding.lytUserProfile.setOnClickListener(this);
        binding.lytName.setOnClickListener(this);
        binding.btnBack1.setOnClickListener(this);
    }

    public void setData(){
        binding.tvUserName.setText(receiverName);
        Picasso.get().load(Allurls.IMAGEURL+receiverImage).error(R.drawable.default_user).placeholder(R.drawable.default_user).into(binding.imgUserProfile);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                onBackPressed();
                break;
                case R.id.btnBack1:
                onBackPressed();
                break;
           /* case R.id.lytUserProfile:
                Intent intent = new Intent(context, ViewChatProfileImageActivity.class);
                intent.putExtra("receiverImage",receiverImage);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(binding.imgUserProfile,"profileTransition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ChatActivity.this,pairs);

                startActivity(intent, options.toBundle());
                break;
            case R.id.lyt_name:
                Intent intent1 = new Intent(context, UserProfileActivity.class);
                intent1.putExtra("userId",Long.valueOf(receiverId));
                intent1.putExtra("key","Chat");
                startActivity(intent1);
                break;*/
        }
    }

    public void openChatWebview() {
        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setDomStorageEnabled(true);
        CHAT_WEBVIEW_URL = Allurls.WEBVIEWURL + "chat?uuid=" + userId +"&receiver_id="+receiverId;
        Log.d("TAG","CHAT_WEBVIEW_URL>>>>>>>>>"+CHAT_WEBVIEW_URL);

        binding.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.e("MYT", "###url= " + url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {

            }
        });
        binding.webView.loadUrl(CHAT_WEBVIEW_URL);
    }

}