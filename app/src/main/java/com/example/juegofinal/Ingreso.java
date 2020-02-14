package com.example.juegofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Ingreso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

    }
    public void BotonIngreso(View v){

        Intent intent=new Intent(v.getContext(), Juego.class);
        startActivityForResult(intent,0);

    }
    public void BotonPuntaje(View v){

        Intent intent=new Intent(v.getContext(), Puntajes.class);
        startActivityForResult(intent,0);

    }


    public void BotonSalir(View v){

    Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.icon_login:{

                Intent intent=new Intent(this,login.class);
                startActivityForResult(intent,0);

            }break;
        }
        return true;

    }
}
