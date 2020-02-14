package com.example.juegofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;
import com.example.juegofinal.model.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.juegofinal.model.Persona;

public class usuariocontrol extends AppCompatActivity {

    FirebaseAuth mAuth;

    EditText nomC;
    ListView listC_personas;

    FirebaseDatabase firebaseDatabaseC;
    DatabaseReference VARIABLEC;
    Persona personaSelectedC;

    EditText puntajeC,edadC;


    private List<Persona> listPersonC = new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapterPersonaC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuariocontrol);

        mAuth=FirebaseAuth.getInstance();



        nomC = findViewById(R.id.EdNombre);
        edadC=findViewById(R.id.edEdad);
        puntajeC=findViewById(R.id.edPuntaje);

        //listar
        listC_personas = findViewById(R.id.lv_datosPersonas1);
        inicializarFirebaseC();
        listarDatosC();

        listC_personas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personaSelectedC = (Persona) parent.getItemAtPosition(position);
                nomC.setText(personaSelectedC.getNombre());
                edadC.setText(personaSelectedC.getEdad());
                puntajeC.setText(personaSelectedC.getPuntaje());

            }
        });

    }




    private void listarDatosC() {
        VARIABLEC.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPersonC.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Persona p = objSnaptshot.getValue(Persona.class);
                    listPersonC.add(p);

                    arrayAdapterPersonaC = new ArrayAdapter<Persona>(usuariocontrol.this, android.R.layout.simple_list_item_1, listPersonC);
                    listC_personas.setAdapter(arrayAdapterPersonaC);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebaseC() {
        FirebaseApp.initializeApp(this);
        firebaseDatabaseC = FirebaseDatabase.getInstance();
        VARIABLEC = firebaseDatabaseC.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuariocontrol,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        String nombre = nomC.getText().toString();

        String puntaje = puntajeC.getText().toString();
        String edad = edadC.getText().toString();

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
                    VARIABLEC.child("Persona").child(p.getUid()).setValue(p);
                    Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
                    limpiarCajas();
                }
                break;
            }
            case R.id.icon_save:{
                Persona p = new Persona();
                p.setUid(personaSelectedC.getUid());
                p.setNombre(nomC.getText().toString().trim());
                p.setEdad(edadC.getText().toString().trim());
                p.setPuntaje(puntajeC.getText().toString().trim());

                VARIABLEC.child("Persona").child(p.getUid()).setValue(p);
                Toast.makeText(this,"Actualizado", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_delete:{

                Persona p = new Persona();
                p.setUid(personaSelectedC.getUid());
                VARIABLEC.child("Persona").child(p.getUid()).removeValue();
                Toast.makeText(this,"Eliminado", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }

            case R.id.icon_cerrars:{

                FirebaseAuth.getInstance().signOut();

                Intent intent=new Intent(this,Ingreso.class);
                startActivityForResult(intent,0);

            }break;

        }
        return true;

    }
    private void validacion() {
        String nombre = nomC.getText().toString();
        String puntaje = puntajeC.getText().toString();
        String edad = edadC.getText().toString();
        if (nombre.equals("")){
            nomC.setError("Required");
            Toast.makeText(this,"Ingresa tu nombre para guardar ", Toast.LENGTH_LONG).show();
        }
        else if (edad.equals("")){
            edadC.setError("Required");
            Toast.makeText(this,"Ingresa tu edad para guardar ", Toast.LENGTH_LONG).show();
        }

        else if (puntaje.equals("")){
            puntajeC.setError("Required");
            Toast.makeText(this,"Juega otra partida para mejorar tu puntaje ", Toast.LENGTH_LONG).show();
        }
    }
    private void limpiarCajas() {
        nomC.setText("");
        puntajeC.setText("");
        edadC.setText("");

    }
}
