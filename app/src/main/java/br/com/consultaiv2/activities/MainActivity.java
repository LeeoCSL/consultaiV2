package br.com.consultaiv2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.consultaiv2.LoginActivity;
import br.com.consultaiv2.R;
import br.com.consultaiv2.adapter.ViewPagerAdapter;
import br.com.consultaiv2.application.CustomApplication;
import br.com.consultaiv2.fragments.ContaFragment;
import br.com.consultaiv2.fragments.HomeFragment;
import br.com.consultaiv2.util.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private BottomNavigationView navigation;
    private MenuItem prevMenuItem;

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

        CustomApplication CustomApplication = (CustomApplication)getApplication();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }else {
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
