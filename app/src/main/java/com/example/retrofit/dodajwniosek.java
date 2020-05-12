package com.example.retrofit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class dodajwniosek extends AppCompatActivity {
  List<String> bodyy;
List<String>bodyy1=new ArrayList<>();
Spinner spiner;
String stanowisko;
String up;
TextView imieNazwisko;
TextView st;
TextView tx;
TextView tx1;
    TextView tx2;
  static  ArrayAdapter<String> adp;

DatePickerDialog dp;
    DatePickerDialog dp1;
    int year;
    int day;
    int month;
    JsonAPi jsonAPi;
    Retrofit rf;
    Spinner sp4;
    EditText et;
    TextView tgg;
    String id;
    EditText eod;
private static final String[] paths={"Urlop wypoczynkowy","Urlop na żądanie","Urlop okolicznościowy","Opieka nad dzieckiem","Urlop bezpłatny","Opieka nad dzieckiem","Urlop macierzyński","Urlop ojcowski","Urlop rodzicielski","Urlop na poszukiwanie pracy","Odbiór za święto","Praca z domu","Wolne za nadgodziny","Delegacja","Nieobecności nieusprawiedliwiona"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodajwniosek);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sp4=(Spinner) findViewById(R.id.spinner2);
      spiner=(Spinner) findViewById(R.id.spinner);
ArrayAdapter<String> pp=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,paths);

spiner.setAdapter(pp);

       Bundle bu= getIntent().getExtras();
       up= bu.getString("up");
       id=bu.getString("id");
     stanowisko= bu.getString("stanowisko");
       imieNazwisko= (TextView)findViewById(R.id.textView6);
       st= (TextView)findViewById(R.id.textView5);
       imieNazwisko.setText("Imię i nazwisko: "+up);
       st.setText("Stanowisko/dział: "+stanowisko);
        tx=(TextView) findViewById(R.id.textView8);
        tx1=(TextView) findViewById(R.id.textView9);
        tx2=(TextView) findViewById(R.id.textView10);
       et= (EditText) findViewById(R.id.editText);
 eod=(EditText)findViewById(R.id.eod);
   final Calendar c=Calendar.getInstance();
       year= c.get(Calendar.YEAR);
       day= c.get(Calendar.DAY_OF_MONTH);
       month=c.get(Calendar.MONTH);

        rf=new Retrofit.Builder()
                .baseUrl("http://192.168.0.4:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonAPi = rf.create(JsonAPi.class);

         shsp2();

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

    public void tx8(View view) {

        dp=new DatePickerDialog(dodajwniosek.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                String time=year+"-"+(month+1)+"-"+dayOfMonth;
                SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
                Date parse = null;
                try {
                    parse = sf.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tx.setText(sf.format(parse));
            }
        },year,month,day);
        dp.show();

    }

    public void tx9(View view) {
        dp1=new DatePickerDialog(dodajwniosek.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String time=year+"-"+(month+1)+"-"+dayOfMonth;
                SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
                Date parse = null;
                try {
                     parse = sf.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                
                tx1.setText(sf.format(parse));
            }
        },year,month,day);
        dp1.show();

    }

    public void shsp2() {
        Call<List<String>> all = jsonAPi.getAll();
        all.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {

               bodyy = response.body();
                bodyy1.addAll(bodyy);
                adp=new ArrayAdapter<>(dodajwniosek.this,android.R.layout.simple_spinner_item,bodyy);
                sp4.setAdapter(adp);
       }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }


    public void save(View view) throws ParseException {
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        Urlop uo=new Urlop();
        uo.setTitle(up);
        uo.setRodzaj(spiner.getSelectedItem().toString());
        uo.setStart(tx.getText().toString());
        uo.setEnd(tx1.getText().toString());
        uo.setZastepstwo(sp4.getSelectedItem().toString());
        uo.setDescription(et.getText().toString());
        uo.setId(Integer.valueOf(id));
        uo.setLiczbadniurlopu(eod.getText().toString());
         Toast.makeText(getApplicationContext(),"Wniosek został wysłany",Toast.LENGTH_LONG).show();

        Call<Void> saveusers = jsonAPi.saveurlop(uo);
        saveusers.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });



    }
}

