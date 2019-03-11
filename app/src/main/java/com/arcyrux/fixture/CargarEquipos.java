package com.arcyrux.fixture;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import java.util.ArrayList;
import java.util.Random;


public class CargarEquipos extends Activity {

    int jugadores;
    int equipos;
    ArrayList<String> listaJugadores = new ArrayList<>();
    public final static String EXTRA_MESSAGE6 = "fixture_MENSAJE6";
    //public final static String EXTRA_MESSAGE7 = "fixture_MENSAJE7";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = getIntent();
        listaJugadores = myIntent.getStringArrayListExtra(CargarJugadores.EXTRA_MESSAGE4);
        equipos = myIntent.getIntExtra(CargarJugadores.EXTRA_MESSAGE5, 0);
        jugadores = listaJugadores.size();
        final EditText[] nombre = new EditText[equipos];


        //Layouts
        ScrollView sView = new ScrollView(this);
        LinearLayout myLayout = new LinearLayout(this);
        myLayout.setOrientation(LinearLayout.VERTICAL);

//        LinearLayout inLayout[] = new LinearLayout[jugadores];
//        LinearLayout.LayoutParams viewParams =
//                new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        6.0f);

        LinearLayout.LayoutParams layParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 2.0f);

        sView.addView(myLayout);


        for (int i = 0; i < equipos; i++){

            EditText nombre1 = new EditText(this);
            nombre[i] = nombre1;
            nombre[i].setTextSize(20);
            nombre[i].setSingleLine(true);
            nombre[i].setHint("Equipo " + (i+1));

            myLayout.addView(nombre[i]);
        }

        Button boton0 = new Button(this);
        boton0.setText("Listo");
        boton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Fixture.class);

                Equipo[] listaEquipos = new Equipo[equipos];
                for(int i = 0; i < equipos; i++) {
                    if(nombre[i].getText().toString().matches(".*\\w.*")){  //Verificar que no sean solo espacios en blanco.
                        Equipo team = new Equipo(nombre[i].getText().toString());
                        listaEquipos[i] = team;
                    }
                    else {  //Si es asi, completa con "Equipo n"
                        Equipo team = new Equipo("Equipo " + (i+1));
                        listaEquipos[i] = team;
                    }
                }

                SharedPreferences prefs = getSharedPreferences(MainActivity.EXTRA_MESSAGE3, MODE_PRIVATE);
                Boolean vuelta = prefs.getBoolean(MainActivity.EXTRA_MESSAGE3, true);

                Torneo torneo = new Torneo(listaEquipos, vuelta);

                torneo.jugador = listaJugadores.toArray(new String[listaJugadores.size()]);
                int longEquipo = listaEquipos.length;
                int jXequipo = roundUp(listaJugadores.size(), longEquipo);
                Random rnd = new Random();
                int p1;
                for(int i = 0; i < jXequipo; i++){
                    for (Equipo team : listaEquipos) {
                        if (listaJugadores.size() == 0) {break;}
                        p1 = rnd.nextInt(listaJugadores.size());
                        team.SumarJugador(listaJugadores.get(p1));
                        listaJugadores.remove(p1);
                    }
                }




                intent.putExtra(EXTRA_MESSAGE6, torneo);
                //intent.putExtra(EXTRA_MESSAGE7, true);
                startActivity(intent);
            }
        });

        myLayout.addView(boton0, layParams);

        setContentView(sView, layParams);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cargar_equipos, menu);
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

    public int roundUp(int dividendo, int divisor){
        return (dividendo + divisor - 1) / divisor;
    }
}
