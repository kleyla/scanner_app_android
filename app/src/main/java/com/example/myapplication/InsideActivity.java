package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class InsideActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    //private NavigationView mNavView;

    private Button mBtnMl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(mToolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawLay);

        NavigationView mNavView = (NavigationView) findViewById(R.id.navView);
        mNavView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new InsideFragment()).commit();

            mNavView.setCheckedItem(R.id.op1);
        }

        //Boton Camera
        mBtnMl = (Button) findViewById(R.id.btnMl);
        mBtnMl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsideActivity.this, MlActivity.class));
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.op1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InsideFragment()).commit();
                break;
            case R.id.op2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProFragment()).commit();
                break;
            case R.id.op3:
                Toast.makeText(this, "hey",Toast.LENGTH_LONG).show();
                break;
            case R.id.op4:
                Toast.makeText(this, "hey 4",Toast.LENGTH_LONG).show();
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if( mDrawer.isDrawerOpen(GravityCompat.START)){
            mDrawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

}
