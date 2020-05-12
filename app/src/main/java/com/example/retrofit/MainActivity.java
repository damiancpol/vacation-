package com.example.retrofit;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.UserHandle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HTTP;

public class MainActivity extends AppCompatActivity {
TextView tx;
    JsonAPi jsonAPi;
Uusers up;
EditText log;
    EditText pass;

    Retrofit rf;
    ListView sb;
    CheckBox czek1;
    String username1;
    String password1;
    SharedPreferences sh;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        log=(EditText)findViewById(R.id.inputlogin);
        pass=(EditText)findViewById(R.id.haslo);
        czek1=(CheckBox)findViewById(R.id.czek1) ;
        sh = getSharedPreferences("my",
                Context.MODE_PRIVATE);
         editor = sh.edit();

        log.setText(sh.getString("login",""));
         pass.setText(sh.getString("haslo",""));
         czek1.setChecked(Boolean.valueOf(sh.getString("check","")));
         rf=new Retrofit.Builder()
        .baseUrl("http://192.168.0.4:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

       jsonAPi = rf.create(JsonAPi.class);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void checked(View view) {


    }


public void checklogin(View view) throws IOException {
    if(czek1.isChecked()){
        editor
                .putString("login", log.getText().toString())
                .putString("haslo", pass.getText().toString())
                .putString("check","True")
                .commit();
    }else{



        editor
                .putString("login", "")
                .putString("haslo", "")
                .putString("check","False")
                .commit();
    }


 up= new Uusers();
up.setLogin(log.getText().toString());
up.setHaslo(pass.getText().toString());
    Call<Uusers> checklogin = jsonAPi.checklogin(up);
checklogin.enqueue(new Callback<Uusers>() {
    @Override
    public void onResponse(Call<Uusers> call, Response<Uusers> response) {
        Uusers body = response.body();
        if(body==null) {
            Toast.makeText(getApplicationContext(),"Wprowadzono złe dane",Toast.LENGTH_LONG).show();
        }else {
            Intent in = new Intent(MainActivity.this, zalogowany.class);

            in.putExtra("up",body.getImie()+" "+body.getNazwisko());
            in.putExtra("stanowisko",body.getStanowisko());
            in.putExtra("id",String.valueOf(body.getId()));
            startActivity(in);
       }
        }
   @Override
    public void onFailure(Call<Uusers> call, Throwable t) {
        Toast.makeText(getApplicationContext(),"Wprowadzono złe dane",Toast.LENGTH_LONG).show();
    }
});

}







}
