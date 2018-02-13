package com.ali.zhihu;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ali.zhihu.R;
import com.ali.zhihu.ui.news.LastestNewsFragment;
import com.ali.zhihu.ui.theme.ThemeFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_mian);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_mian:
                        replaceFragment(new LastestNewsFragment());
                        break;
                    case R.id.nav_normal:
                        startFragment(13);
                        break;
                    case R.id.nav_user:
                        startFragment(12);
                        break;
                    case R.id.nav_movie:
                        startFragment(3);
                        break;
                    case R.id.nav_boring:
                        startFragment(11);
                        break;
                    case R.id.nav_design:
                        startFragment(4);
                        break;
                    case R.id.nav_company:
                        startFragment(5);
                        break;
                    case R.id.nav_money:
                        startFragment(6);
                        break;
                    case R.id.nav_intenet:
                        startFragment(10);
                        break;
                    case R.id.nav_startgame:
                        startFragment(2);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
        replaceFragment(new LastestNewsFragment());

    }
    public void startFragment(int themeId){
        ThemeFragment themeFragment = new ThemeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ThemeFragment.THEME_ID,themeId);
        themeFragment.setArguments(bundle);
        replaceFragment(themeFragment);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_layout,fragment);
        transaction.commit();
    }
}
