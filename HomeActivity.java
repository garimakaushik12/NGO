package com.garima.sukhmayfoundation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    /*SharedPreferences pref;
    SharedPreferences.Editor editor;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        bottomNavigationView=findViewById(R.id.nav);
        /*pref=getSharedPreferences("mypref",MODE_PRIVATE);
        editor=pref.edit();
*/
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().add(R.id.container2,new HomeFragment()).addToBackStack("").commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {

                if (item.getItemId()==R.id.home)
                {
                    FragmentManager fm=getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.container2,new HomeFragment()).addToBackStack("").commit();

                } else if (item.getItemId()==R.id.about)
                {
                    FragmentManager fm=getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.container2,new SOSFragment()).addToBackStack("").commit();
                }
                else if (item.getItemId()==R.id.refer)
                {
                    FragmentManager fm=getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.container2,new ProfileFragment()).addToBackStack("").commit();
                }



                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}