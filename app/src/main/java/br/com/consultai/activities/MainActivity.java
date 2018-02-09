package br.com.consultai.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.consultai.LoginActivity;
import br.com.consultai.R;
import br.com.consultai.adapter.ViewPagerAdapter;
import br.com.consultai.application.CustomApplication;
import br.com.consultai.fragments.ContaFragment;
import br.com.consultai.fragments.HomeFragment;
import br.com.consultai.util.BottomNavigationViewHelper;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private BottomNavigationView navigation;
    private MenuItem prevMenuItem;
    public static String nomeOperadoraa;
    public static String SChip;
    public static String versao = "7";
    // FRAGMENTS
    private HomeFragment mHomeFragment;
    private ContaFragment mContaFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mHomeFragment.refreshUI();
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.navigation_dashboard:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.navigation_exit:
                    Paper.book().destroy();

                    CustomApplication customApplication = (CustomApplication) getApplicationContext();
                    customApplication.destroySession();

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomApplication CustomApplication = (CustomApplication) getApplication();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);



        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }

                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(mViewPager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE}, 10);
                return;
            }
        } else {

        }

        TelephonyManager manager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        nomeOperadoraa = manager.getNetworkOperatorName();
        SChip = manager.getSimSerialNumber();


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        mHomeFragment = new HomeFragment();
        mContaFragment = new ContaFragment();

        adapter.addFragment(mHomeFragment);
        adapter.addFragment(mContaFragment);
        /*
        adapter.addFragment(myOrdersFragment);
        adapter.addFragment(myAccountFragment);*/

        viewPager.setAdapter(adapter);
    }
}
