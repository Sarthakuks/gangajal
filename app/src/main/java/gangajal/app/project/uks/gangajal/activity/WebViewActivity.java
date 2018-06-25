package gangajal.app.project.uks.gangajal.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import gangajal.app.project.uks.gangajal.R;
import gangajal.app.project.uks.gangajal.Utils.Tags;

public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivImg, ivBack;
    private TextView tvHeader;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        findViews();
        initialization();
        handleListeners();
    }

    private void handleListeners() {
        ivBack.setOnClickListener(this);
    }

    private void initialization() {

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString(Tags.TITLE);
        if (title.equals(getString(R.string.about_us))) {
            ivImg.setImageDrawable(getResources().getDrawable(R.drawable.about_banner));
        } else if (title.equals(getString(R.string.term_and_condition))) {
            ivImg.setImageDrawable(getResources().getDrawable(R.drawable.tc_banner));
        } else if (title.equals(getString(R.string.charity))) {
            ivImg.setImageDrawable(getResources().getDrawable(R.drawable.charity_banner));
        } else if (title.equals(getString(R.string.contact_us))) {
            ivImg.setImageDrawable(getResources().getDrawable(R.drawable.contact_banner));
        }
        tvHeader.setText(title);
        String url = bundle.getString(Tags.WEB_URL);
        webView.loadUrl(url);
        WebSettings settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(false);
        settings.setJavaScriptEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);


    }

    private void findViews() {
        webView = findViewById(R.id.web_view);
        ivImg = findViewById(R.id.iv_img);
        ivBack = findViewById(R.id.iv_back);
        tvHeader = findViewById(R.id.tv_header);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }


}
