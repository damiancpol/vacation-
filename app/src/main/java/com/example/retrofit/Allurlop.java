package com.example.retrofit;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Allurlop extends AppCompatActivity {
    List<String>bodyy1=new ArrayList<>();
String id;
    JsonAPi jsonAPi;
    TextView ed;
    TextView ts;
    ListView ll;
    ArrayAdapter aa;
    List<String>data=new ArrayList<>() ;
    List<String> description=new ArrayList<>() ;;
    List<Integer> image=new ArrayList<>();
    TextView tt;
    TextView tt1;
    ImageView im1;
    String content;
    ArrayAdapter ap;
    CustomView customView;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allurlop);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://192.168.0.4:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonAPi = build.create(JsonAPi.class);
         ll=(ListView)findViewById(R.id.rc);
         List<String> list = new ArrayList<>();
         list.add("a");
         list.add("b");
         list.add("c");
         lista();
         CustomView customView=new CustomView(this,image,description,data);
ll.setAdapter(customView);
         tt=(TextView)findViewById(R.id.tt1);
         tt1=(TextView)findViewById(R.id.tt2);
         im1=(ImageView)findViewById(R.id.im1);









        Bundle extras = getIntent().getExtras();
     id=  extras.getString("id");

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


    public void lista(){

        Call<List<Urlop>> listCall = jsonAPi.geTallUsers();
        listCall.enqueue(new Callback<List<Urlop>>() {
            @Override
            public void onResponse(Call<List<Urlop>> call, Response<List<Urlop>> response) {
                List<Urlop> body = response.body();

                String daty="";
                String descs="";
                int im=0;
             for(Urlop bb:body){
                 if(id.equals(String.valueOf(bb.getId()))) {

                     daty = "Od: " + bb.getStart() + " - do: " + bb.getEnd();
                     descs = "";
                     data.add(daty + "\n" + bb.getDescription()+"\n"+" Zastępstwo: "+bb.getZastepstwo());

                     if (bb.getStatus() == 0) {
                         im = R.drawable.czeka;
                         descs = "Czeka na akceptację przełożonego"+"\n";
                     } else if (bb.getStatus() == 2) {
                         im = R.drawable.odrzucony;
                         descs = "Odrzucone przez przełożonego"+"\n";
                     } else {
                         im = R.drawable.zakceptowany;
                         descs = "Zatwierdzony przez przełożonego"+"\n";
                     }

                     description.add(descs);
                     image.add(im);

                     customView=new CustomView(Allurlop.this,image,description,data);
                     ll.setAdapter(customView);
                 }
                }

            }
            @Override
            public void onFailure(Call<List<Urlop>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }

}
