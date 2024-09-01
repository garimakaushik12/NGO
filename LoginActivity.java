package com.garima.sukhmayfoundation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText t1,t2;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        t1=findViewById(R.id.text4);
        t2=findViewById(R.id.text5);
        btn=findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=t1.getText().toString();
                String pass=t2.getText().toString();
                if ((!name.isEmpty()) && (!pass.isEmpty()))
                {
                    Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(i);
                }
                else
                {
                    t1.setError("Enter Name");
                    t2.setError("Enter Password");
                }

            }
        });

    }
}