package com.arcyrux.fixture;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;


public class IngresarJugadores extends Activity {

    int contador;
    int jugadores;
    int equipos;
    public final static String EXTRA_MESSAGE3 = "fixture_MENSAJE3";

    ArrayList<String> listaJugadores = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_jugadores);
        Intent myIntent = getIntent();

        jugadores = myIntent.getIntExtra(MainActivity.EXTRA_MESSAGE, 0);
        equipos = myIntent.getIntExtra(MainActivity.EXTRA_MESSAGE2, 0);
        int jugadores2 = myIntent.getIntExtra(mostrarJugadores.EXTRA_MESSAGE5, 0);


        if(jugadores2 != 0) {
            listaJugadores = myIntent.getStringArrayListExtra(mostrarJugadores.EXTRA_MESSAGE4);
            jugadores = jugadores2 - listaJugadores.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ingresar_jugadores, menu);
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

    public void siguiente(View view) {


        EditText nombreJugador = (EditText) findViewById(R.id.nombreJugador);
        String nombre = nombreJugador.getText().toString();
        listaJugadores.add(nombre);
        contador++;
        nombreJugador.setText("");

        if(contador >= jugadores){

            Intent intent = new Intent(this, mostrarJugadores.class);

            intent.putExtra(EXTRA_MESSAGE3, listaJugadores);

            startActivity(intent);
        }
    }
}
