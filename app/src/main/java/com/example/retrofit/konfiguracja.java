package com.example.retrofit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class konfiguracja extends AppCompatActivity {
EditText login;
EditText haslo;
EditText stanowisko;
EditText nazwisko;
EditText email;
EditText imie;
String id;
    Uusers body;
    JsonAPi jsonAPi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfiguracja);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       login= (EditText)findViewById(R.id.log);
      haslo=  (EditText)findViewById(R.id.has);
       stanowisko= (EditText)findViewById(R.id.stanowisko);
       nazwisko= (EditText)findViewById(R.id.nazwisko);
       email= (EditText)findViewById(R.id.email);
       imie=  (EditText)findViewById(R.id.imie);
        Bundle idl = getIntent().getExtras();
       id= idl.getString("id");
        Retrofit rf=new Retrofit.Builder()
                .baseUrl("http://192.168.0.4:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         jsonAPi = rf.create(JsonAPi.class);
        konfiguracja();
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

    public void konfiguracja(){
        Uusers uu=new Uusers();
        uu.setId(Integer.valueOf(id));
        Call<Uusers> konfiguracja = jsonAPi.konfiguracja(uu);
        konfiguracja.enqueue(new Callback<Uusers>() {
            @Override
            public void onResponse(Call<Uusers> call, Response<Uusers> response) {
             body = response.body();

                login.setText(body.getLogin());
                haslo.setText(body.getHaslo());
                stanowisko.setText(body.getStanowisko());
                email.setText(body.getEmail());
                imie.setText(body.getImie());
                nazwisko.setText(body.getNazwisko());
            }

            @Override
            public void onFailure(Call<Uusers> call, Throwable t) {

            }
        });


    }

    public void updateuser(View view) {
Uusers uo=new Uusers();
uo.setId(Integer.valueOf(id));
uo.setImie(imie.getText().toString());
uo.setNazwisko(nazwisko.getText().toString());
uo.setLogin(login.getText().toString());
uo.setHaslo(haslo.getText().toString());
uo.setEmail(email.getText().toString());
uo.setStanowisko(stanowisko.getText().toString());
        Toast.makeText(getApplicationContext(),"Dane zostały zmienione",Toast.LENGTH_LONG).show();
        Call<Void> uusersCall = jsonAPi.updateUser(uo);
        uusersCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }
}
