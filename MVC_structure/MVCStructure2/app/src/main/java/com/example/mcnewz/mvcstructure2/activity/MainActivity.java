package com.example.mcnewz.mvcstructure2.activity;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mcnewz.mvcstructure2.R;
import com.example.mcnewz.mvcstructure2.fragment.MainFragment;
import com.example.mcnewz.mvcstructure2.fragment.SecondFragment;
import com.example.mcnewz.mvcstructure2.util.ScreenUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getScreen Size from Model ScreenUtils
        int screenWidth = ScreenUtils.getInstance().getScreenWidth();
        int screenHeight= ScreenUtils.getInstance().getScreenHeight();

        // Toast screenWidth & screenHeight
        Toast.makeText(MainActivity.this,
                "Width = " + screenWidth + "Height = " + screenHeight,
                Toast.LENGTH_SHORT).show();

        // Android Version
        if (Build.VERSION.SDK_INT >= 21){
            // Run on Android 21+
        } else {
            // Run on Android 1-20
        }

        // Fragment here !
        if (savedInstanceState == null) {
            // First Create
            // connect Fragment here
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer,
                            MainFragment.newInstance(123),
                            "MainFragment")
                    .commit();

            // Detach and attach
            // hind from activity because detach on //2.38 h
            SecondFragment secondFragment = SecondFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer,
                            secondFragment,
                            "SecondFragment")
                    .detach(secondFragment)
                    .commit();
        }
    }

    // run after onCreate and then setData
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // setText to fragment
        if(savedInstanceState == null){
            MainFragment fragment = (MainFragment)
                    getSupportFragmentManager().findFragmentByTag("MainFragment");
            fragment.setHelloText("Woo Hooooo\nWoo Hooooo\nWoo Hooooo\nWoo Hooooo\nWoo Hooooo\nWoo Hooooo\nWoo Hooooo\nWoo Hooooo\nWoo Hooooo\nWoo Hooooo\nWoo Hooooo\nWoo Hooooo\nWoo Hooooo\nWoo Hooooo\nWoo Hooooo\n");

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_first_tab :{
                // detach , attach
                //Show MainFragment // Close SecondFragment
                MainFragment mainFragment = (MainFragment)
                        getSupportFragmentManager().findFragmentByTag("MainFragment");

                SecondFragment secondFragment = (SecondFragment)
                        getSupportFragmentManager().findFragmentByTag("SecondFragment");

                getSupportFragmentManager().beginTransaction()
                        .detach(secondFragment)
                        .attach(mainFragment)
                        .commit();
                return true;
            }

            case R.id.action_second_tab :{
                // detach , attach
                //close MainFragment // Show SecondFragment
                MainFragment mainFragment = (MainFragment)
                        getSupportFragmentManager().findFragmentByTag("MainFragment");

                SecondFragment secondFragment = (SecondFragment)
                        getSupportFragmentManager().findFragmentByTag("SecondFragment");

                getSupportFragmentManager().beginTransaction()
                        .detach(mainFragment)
                        .attach(secondFragment)
                        .commit();
                return true;
            }

            case R.id.action_second_fragment:
                // Fragment open Here
                Fragment fragment = getSupportFragmentManager()
                        .findFragmentById(R.id.contentContainer);

                if (fragment instanceof SecondFragment == false) {
                    getSupportFragmentManager().beginTransaction()
//                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .setCustomAnimations(
                                    R.anim.from_right, R.anim.to_left,
                                    R.anim.from_left, R.anim.to_right
                            )
                            .replace(R.id.contentContainer,
                                    SecondFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                }

                Toast.makeText(this,
                        "Second Fragment Here",
                        Toast.LENGTH_SHORT).show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
