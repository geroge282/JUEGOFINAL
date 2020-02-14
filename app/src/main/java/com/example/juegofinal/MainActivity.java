package com.example.juegofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.juegofinal.model.Persona;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText nomP;
    ListView listV_personas;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference VARIABLE;

    String datorecibido;
    EditText puntajeP,edadP;
    Bundle datos;



    //listar

    private List<Persona> listPerson = new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapterPersona;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nomP = findViewById(R.id.EdNombre);
        edadP=findViewById(R.id.edEdad);

        datos = getIntent().getExtras();
       datorecibido = datos.getString("puntajeEnviado");
         puntajeP = findViewById(R.id.edPuntaje);
        puntajeP.setText(datorecibido);

        //listar
        listV_personas = findViewById(R.id.lv_datosPersonas1);
        inicializarFirebase();
        listarDatos();



    }


    private void listarDatos() {
        VARIABLE.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPerson.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Persona p = objSnaptshot.getValue(Persona.class);
                    listPerson.add(p);

                    arrayAdapterPersona = new ArrayAdapter<Persona>(MainActivity.this, android.R.layout.simple_list_item_1, listPerson);
                    listV_personas.setAdapter(arrayAdapterPersona);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();

        VARIABLE = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        String nombre = nomP.getText().toString();

        String puntaje = puntajeP.getText().toString();
        String edad = edadP.getText().toString();


        switch (item.getItemId()){
            case R.id.icon_add:{

                if (nombre.equals("")||puntaje.equals("")||edad.equals("")){
                    validacion();
                }
                else {
                    Persona p = new Persona();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setEdad(edad);
                    p.setPuntaje(puntaje);
                    VARIABLE.child("Persona").child(p.getUid()).setValue(p);
                    Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
                    limpiarCajas();
                }
                break;
            }
            case R.id.icon_return:{
                Intent intent=new Intent(this, Juego.class);
                startActivityForResult(intent,0);

            }

            default:break;
        }
        return true;
    }

    private void validacion() {
        String nombre = nomP.getText().toString();
        String puntaje = puntajeP.getText().toString();
        String edad = edadP.getText().toString();
        if (nombre.equals("")){
            nomP.setError("Required");
            Toast.makeText(this,"Ingresa tu nombre para guardar ", Toast.LENGTH_LONG).show();
        }
        else if (edad.equals("")){
            edadP.setError("Required");
            Toast.makeText(this,"Ingresa tu edad para guardar ", Toast.LENGTH_LONG).show();
        }

        else if (puntaje.equals("")){
            puntajeP.setError("Required");
            Toast.makeText(this,"Juega otra partida para mejorar tu puntaje ", Toast.LENGTH_LONG).show();
        }
    }
    private void limpiarCajas() {
        nomP.setText("");
        puntajeP.setText("");
        edadP.setText("");

    }


}
