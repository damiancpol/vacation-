package com.example.retrofit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class konfAdmin extends AppCompatActivity {
    EditText login;
    EditText haslo;
    EditText stanowisko;
    EditText nazwisko;
    EditText email;
    EditText imie;
    String id;
    Uusers body;
    JsonAPi jsonAPi;
    List<String> bodyy;
    List<String>bodyy1=new ArrayList<>();
    static ArrayAdapter adp1;
    Spinner sp5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konf_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        login= (EditText)findViewById(R.id.log1);
        haslo=  (EditText)findViewById(R.id.has1);
        stanowisko= (EditText)findViewById(R.id.stanowisko1);
        nazwisko= (EditText)findViewById(R.id.nazwisko1);
        email= (EditText)findViewById(R.id.email1);
        imie=  (EditText)findViewById(R.id.imie1);
        sp5=findViewById(R.id.sp5);
        Bundle idl = getIntent().getExtras();
        id= idl.getString("id");
        Retrofit rf=new Retrofit.Builder()
                .baseUrl("http://192.168.0.4:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonAPi = rf.create(JsonAPi.class);
        shsp3();
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
        uo.setZastepstwo(sp5.getSelectedItem().toString());
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

    public void shsp3() {

        Call<List<Uusers>> alll = jsonAPi.getAlll();
        alll.enqueue(new Callback<List<Uusers>>() {
            @Override
            public void onResponse(Call<List<Uusers>> call, Response<List<Uusers>> response) {
                List<Uusers> body = response.body();

                for(Uusers pl:body){
                    if(pl.getStanowisko().equals("Kierownik")){

                        bodyy1.add(pl.getLogin());

                    }

                    adp1=new ArrayAdapter<>(konfAdmin.this,android.R.layout.simple_spinner_item,bodyy1);
                    sp5.setAdapter(adp1);
                }


            }

            @Override
            public void onFailure(Call<List<Uusers>> call, Throwable t) {

            }
        });
    }
}

