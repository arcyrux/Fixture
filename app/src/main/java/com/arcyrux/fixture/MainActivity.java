package com.arcyrux.fixture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "fixture_MENSAJE1";
    public final static String EXTRA_MESSAGE2 = "fixture_MENSAJE2";
    public final static String EXTRA_MESSAGE3 = "fixture_MENSAJE3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText jugadores = (EditText) findViewById(R.id.jugadores);
        final EditText equipos = (EditText) findViewById(R.id.equipos);


        equipos.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                String cant = jugadores.getText().toString();
                if (hasFocus && cant.matches(".*\\w.*")) {
                    int num = (Integer.parseInt(cant) + 1) / 2;
                    equipos.setText("" + num);
                }
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
            Toast.makeText(this, "     FIXTURE    " + "\n" + "  Martin Meyra  " + "\n" + " Version: alpha ", Toast.LENGTH_LONG).show();
            //return true;
        }
        if (id == R.id.action_sol) {
            Toast.makeText(this, "Te amo, Solchis!", Toast.LENGTH_LONG).show();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void continuar(View view) {

        try {
            Archivo archivo = new Archivo();
            Torneo torneo = archivo.leer();
            Intent intent = new Intent(this, Fixture.class);
            intent.putExtra(EXTRA_MESSAGE, torneo);
            startActivity(intent);
        }
        catch(Exception e){
            Toast.makeText(this, getResources().getString(R.string.error_falta_guardado), Toast.LENGTH_LONG).show();
        }
    }

    public void seguir(View view) {

        try {
            Intent intent = new Intent(this, CargarJugadores.class);

            EditText editText = findViewById(R.id.jugadores);
            String message = editText.getText().toString();
            int jugadores = Integer.parseInt(message);
            intent.putExtra(EXTRA_MESSAGE, jugadores);

            editText = findViewById(R.id.equipos);
            message = editText.getText().toString();
            int equipos = Integer.parseInt(message);
            intent.putExtra(EXTRA_MESSAGE2, equipos);

            CheckBox box = findViewById(R.id.vuelta);
            Boolean vuelta = box.isChecked();

            if(jugadores < equipos){
                Toast.makeText(this, getResources().getString(R.string.error_menos_jugadores), Toast.LENGTH_LONG).show();
                return;
            }
            if(equipos < 2){
                Toast.makeText(this, getResources().getString(R.string.error_menos_equipos), Toast.LENGTH_LONG).show();
                return;
            }

            SharedPreferences.Editor editor = getSharedPreferences(EXTRA_MESSAGE3, MODE_PRIVATE).edit();
            editor.putBoolean(EXTRA_MESSAGE3, vuelta);
            editor.apply();

            startActivity(intent);
        }
        catch (Exception e){
            Toast.makeText(this, getResources().getString(R.string.error_faltan_datos), Toast.LENGTH_LONG).show();
//            e.printStackTrace();
        }
    }
}
