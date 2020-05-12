package com.example.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class zalogowany extends AppCompatActivity {
    JsonAPi in;
EditText start;
EditText end;
TextView zal;
MainActivity ma;
String stanowisko;
String up;
String id;
JsonAPi jsonAPi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zalogowany);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        zal=(TextView)findViewById(R.id.textView);


        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://192.168.0.4:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         jsonAPi = build.create(JsonAPi.class);
        Bundle extras = getIntent().getExtras();
       up=extras.getString("up");
       stanowisko=extras.getString("stanowisko");
      zal.setText("Witaj "+up);
      id=extras.getString("id");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void przejdz(View view) {

        Intent in = new Intent(zalogowany.this,dodajwniosek.class);
        in.putExtra("up",up);
        in.putExtra("stanowisko",stanowisko);
        in.putExtra("id",id);
        startActivity(in);
    }

    public void listawnioskow(View view) {

        Intent in=new Intent(zalogowany.this,Allurlop.class);
        in.putExtra("id",id);
        startActivity(in);

    }


    public void konfi(View view) {
 if(stanowisko.equals("Kierownik")){
     Intent ii=new Intent(zalogowany.this,konfAdmin.class);
     ii.putExtra("id",id);
     startActivity(ii);



 }else {
     Intent ii = new Intent(zalogowany.this, konfiguracja.class);
     ii.putExtra("id", id);
     startActivity(ii);
 }
    }
}
