package gangajal.app.project.uks.gangajal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import gangajal.app.project.uks.gangajal.Model.ImageModel;
import gangajal.app.project.uks.gangajal.R;
import gangajal.app.project.uks.gangajal.Utils.Tags;
import gangajal.app.project.uks.gangajal.Utils.UrlUtils;
import gangajal.app.project.uks.gangajal.adapter.SlideImageAdapter;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private WebView webView;
    private ViewPager mPager;
    private CirclePageIndicator indicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    private int[] myImageList ={R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3,R.drawable.banner_4,R.drawable.banner_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        findViews();
        initialization();
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        init();
    }


    private void initialization() {

        webView.loadUrl(UrlUtils.DASHBOARD);
        WebSettings settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(false);
        settings.setJavaScriptEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

    }

    private void findViews() {
        mPager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);
        webView = findViewById(R.id.web_view);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Tags.TITLE, getString(R.string.home));
            bundle.putString(Tags.WEB_URL, UrlUtils.DASHBOARD);
            intent.putExtras(bundle);
            MainActivity.this.startActivity(intent);
        } else if (id == R.id.nav_about_us) {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Tags.TITLE, getString(R.string.about_us));
            bundle.putString(Tags.WEB_URL, UrlUtils.ABOUT);
            intent.putExtras(bundle);
            MainActivity.this.startActivity(intent);
        } else if (id == R.id.nav_term) {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Tags.TITLE, getString(R.string.term_and_condition));
            bundle.putString(Tags.WEB_URL, UrlUtils.TERM);
            intent.putExtras(bundle);
            MainActivity.this.startActivity(intent);
        } else if (id == R.id.nav_charity) {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Tags.TITLE, getString(R.string.charity));
            bundle.putString(Tags.WEB_URL, UrlUtils.CHARITY);
            intent.putExtras(bundle);
            MainActivity.this.startActivity(intent);
        } else if (id == R.id.nav_order) {
            startActivity(new Intent(MainActivity.this, OrderActivity.class));
        } else if (id == R.id.nav_feedback) {
            startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Tags.TITLE, getString(R.string.contact_us));
            bundle.putString(Tags.WEB_URL, UrlUtils.CONTACT);
            intent.putExtras(bundle);
            MainActivity.this.startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private ArrayList<ImageModel> populateList() {

        ArrayList<ImageModel> list = new ArrayList<>();
        for (int i = 0; i <myImageList.length; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }
        return list;
    }


    private void init() {

        mPager.setAdapter(new SlideImageAdapter(MainActivity.this, imageModelArrayList));
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
