package gangajal.app.project.uks.gangajal.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import gangajal.app.project.uks.gangajal.Model.ImageModel;
import gangajal.app.project.uks.gangajal.R;
import gangajal.app.project.uks.gangajal.Utils.SharedPreference;
import gangajal.app.project.uks.gangajal.Utils.Tags;
import gangajal.app.project.uks.gangajal.Utils.UrlUtils;
import gangajal.app.project.uks.gangajal.adapter.SlideImageAdapter;

public class PledgeActivity extends BaseActivity implements View.OnClickListener {
    private WebView webView;
    private ImageView ivBack;
    private TextView tvHeader;
    private ViewPager mPager;
    private CirclePageIndicator indicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    private int[] myImageList ={R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pledge);
        findViews();
        initialization();
        handleListeners();
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        init();
    }

    private void handleListeners() {
        ivBack.setOnClickListener(this);
    }

    private void findViews() {
        webView = findViewById(R.id.web_view);
        mPager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);
        ivBack = findViewById(R.id.iv_back);
        tvHeader = findViewById(R.id.tv_header);
    }

    private void initialization() {
        SharedPreference.setString(PledgeActivity.this, Tags.TRUE, Tags.IS_PLEDGE_OPEN);
        Bundle bundle = getIntent().getExtras();
        String title = null;
        if (bundle != null) {
            title = bundle.getString(Tags.TITLE);
        }
        tvHeader.setText(title);
        webView.loadUrl(UrlUtils.PLEDGE);
        WebSettings settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(false);
        settings.setJavaScriptEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private ArrayList<ImageModel> populateList() {
        ArrayList<ImageModel> list = new ArrayList<>();
        for (int i = 0; i < myImageList.length; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }
        return list;
    }


    private void init() {

        mPager.setAdapter(new SlideImageAdapter(PledgeActivity.this, imageModelArrayList));
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
//Set circle indicator radius
        indicator.setRadius(5 * density);
        NUM_PAGES = imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }


}
