package com.example.juegofinal;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Collections;


public class Juego extends AppCompatActivity {

    ImageButton el0, el1, el2, el3, el4, el5, el6, el7,
            el8, el9, el10, el11, el12, el13, el14, el15;





    int imagenes[];

    ImageButton [] botonera = new ImageButton[16];


    int fondo;


    ArrayList<Integer> arrayBarajado;


    ImageButton primero;

    int numeroPrimero, numeroSegundo;


    boolean bloqueo = false;

    //para controlar las pausas del juego
    final Handler handler = new Handler();


    int aciertos=0;
    int puntuacion=0;
    TextView textoPuntuacion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        cargarImagenes();


        iniciar();
    }
    public void cargarImagenes(){
        imagenes = new int[]{
                R.drawable.la0,
                R.drawable.la1,
                R.drawable.la2,
                R.drawable.la3,
                R.drawable.la4,
                R.drawable.la5,
                R.drawable.la6,
                R.drawable.la7,
        };

        fondo = R.drawable.fondo;
    }

    public ArrayList<Integer> barajar(int longitud) {
        ArrayList resultadoA = new ArrayList<Integer>();
        for(int i=0; i<longitud; i++)
            resultadoA.add(i % longitud/2);
        Collections.shuffle(resultadoA);
        return  resultadoA;
    }


    public void cargarBotones(){
        el0 =findViewById(R.id.boton00);
        botonera[0] = el0;
        el1 =findViewById(R.id.boton01);
        botonera[1] = el1;
        el2 =findViewById(R.id.boton02);
        botonera[2] = el2;
        el3 =findViewById(R.id.boton03);
        botonera[3] = el3;
        el4 =findViewById(R.id.boton04);
        botonera[4] = el4;
        el5 =findViewById(R.id.boton05);
        botonera[5] = el5;
        el6 =findViewById(R.id.boton06);
        botonera[6] = el6;
        el7 =findViewById(R.id.boton07);
        botonera[7] = el7;
        el8 =findViewById(R.id.boton08);
        botonera[8] = el8;
        el9 =findViewById(R.id.boton09);
        botonera[9] = el9;
        el10 =findViewById(R.id.boton10);
        botonera[10] = el10;
        el11 =findViewById(R.id.boton11);
        botonera[11] = el11;
        el12 =findViewById(R.id.boton12);
        botonera[12] = el12;
        el13 =findViewById(R.id.boton13);
        botonera[13] = el13;
        el14 = findViewById(R.id.boton14);
        botonera[14] = el14;
        el15 = findViewById(R.id.boton15);
        botonera[15] = el15;

        textoPuntuacion =findViewById(R.id.textoPuntuacion);
        textoPuntuacion.setText(puntuacion+" ");

    }




    public void comprobar(int i, final ImageButton imgb){
        if(primero==null){

            primero = imgb;

            primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
            primero.setImageResource(imagenes[arrayBarajado.get(i)]);

            primero.setEnabled(false);

            numeroPrimero=arrayBarajado.get(i);
        }else{

            bloqueo=true;

            imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgb.setImageResource(imagenes[arrayBarajado.get(i)]);

            imgb.setEnabled(false);

            numeroSegundo=arrayBarajado.get(i);

            if(numeroPrimero==numeroSegundo){
                //reiniciamos
                primero=null;
                bloqueo=false;

                aciertos++;
                puntuacion++;
                textoPuntuacion.setText(puntuacion+" ");
                //al llegar a 8 aciertos se ha ganado el juego
                if(aciertos==8){
                    Toast toast = Toast.makeText(getApplicationContext(), "Has ganado!!", Toast.LENGTH_LONG);
                    toast.show();
                    Intent abrir=new Intent(Juego.this, MainActivity.class);
                    abrir.putExtra( "puntajeEnviado",textoPuntuacion.getText().toString());
                    startActivity(abrir);

                }
            }else{
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //les ponemos la imagen de fondo
                        primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        primero.setImageResource(R.drawable.fondo);
                        imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgb.setImageResource(R.drawable.fondo);
                        //los volvemos a habilitar
                        primero.setEnabled(true);
                        imgb.setEnabled(true);
                        //reiniciamos la variables auxiliaares
                        primero = null;
                        bloqueo = false;
                        //restamos uno a la puntuación
                        if (puntuacion > 0) {
                            puntuacion--;
                            textoPuntuacion.setText(puntuacion+" ");

                        }
                    }
                }, 1000);//al cabo de un segundo
            }
        }

    }

    public void iniciar(){
        arrayBarajado = barajar(imagenes.length*2);
        cargarBotones();


        for(int i=0; i<botonera.length; i++) {
            botonera[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            botonera[i].setImageResource(imagenes[arrayBarajado.get(i)]);
        }


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < botonera.length; i++) {
                    botonera[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
                    botonera[i].setImageResource(fondo);
                }
            }
        }, 1000);


        for(int i=0; i <arrayBarajado.size(); i++){
            final int j=i;
            botonera[i].setEnabled(true);
            botonera[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!bloqueo)
                        comprobar(j, botonera[j]);
                }
            });
        }
        aciertos=0;
        puntuacion=0;
        textoPuntuacion.setText(puntuacion+" ");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menujuego,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.icon_save1:{

                Intent abrir=new Intent(Juego.this, MainActivity.class);
                abrir.putExtra( "puntajeEnviado",textoPuntuacion.getText().toString());
                startActivity(abrir);



            }break;
            case R.id.icon_stop:{
                Intent intent=new Intent(this, Ingreso.class);
                startActivityForResult(intent,0);

            }break;
            case R.id.icon_reiniciar:{
                iniciar();

            }break;


        }
        return true;
    }






}
