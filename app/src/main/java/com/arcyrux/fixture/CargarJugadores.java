package com.arcyrux.fixture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import java.util.ArrayList;



public class CargarJugadores extends Activity {

    int jugadores;
    int equipos;
    ArrayList<String> listaJugadores = new ArrayList<>();
    public final static String EXTRA_MESSAGE4 = "fixture_MENSAJE4";
    public final static String EXTRA_MESSAGE5 = "fixture_MENSAJE5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_jugadores);

        Intent myIntent = getIntent();
        jugadores = myIntent.getIntExtra(MainActivity.EXTRA_MESSAGE, 0);
        equipos = myIntent.getIntExtra(MainActivity.EXTRA_MESSAGE2, 0);

//        TextView textView[] = new TextView[jugadores];
//        final Spinner spin[] = new Spinner[jugadores];
        final EditText[] nombre = new EditText[jugadores];


        ScrollView sView = new ScrollView(this);
        final LinearLayout myLayout = new LinearLayout(this);
        final LinearLayout inLayout[] = new LinearLayout[jugadores];


        myLayout.setOrientation(LinearLayout.VERTICAL);


        LinearLayout.LayoutParams textViewParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        6.0f);

        LinearLayout.LayoutParams lParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 2.0f);



        sView.addView(myLayout);


        for (int i = 0; i < jugadores; i++ ){

//            Spinner spin1 = new Spinner(getApplicationContext());
//            List<String> list = new ArrayList<>();
//            list.add("");
//            list.add("Tincho");
//            list.add("Javi");
//            list.add("Fede");
//            list.add("Facu");
//            ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, list);
//            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spin1.setAdapter(dataAdapter);
//            //spin1.setBackgroundColor(320);
//            spin[i] = spin1;
//            String selec = spin[i].getSelectedItem().toString();

            EditText edit1 = new EditText(this);
            nombre[i] = edit1;
            nombre[i].setTextSize(20);
            nombre[i].setSingleLine(true);
            nombre[i].setHint("Jugador " + (i + 1));

//            if(selec != ""){
//               nombre[i].setText(selec, TextView.BufferType.EDITABLE);
//            }

            LinearLayout linear1 = new LinearLayout(this);
            inLayout[i] = linear1;
            inLayout[i].addView(nombre[i], textViewParams);
//            inLayout[i].addView(spin[i]);
            myLayout.addView(inLayout[i], lParams);

        }

        Button boton0 = new Button(this);
        boton0.setText("Listo");
        boton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CargarEquipos.class);
                for(int i = 0; i < jugadores; i++) {
                    if(nombre[i].getText().toString().matches(".*\\w.*")){  //Verificar que no sean solo espacios en blanco.
                        listaJugadores.add(nombre[i].getText().toString());
                    }
                    else {  //Si es asi, completa con "Equipo n"
                        listaJugadores.add("Jugador " + (i+1));
                    }

                }
                intent.putExtra(EXTRA_MESSAGE4, listaJugadores);
                intent.putExtra(EXTRA_MESSAGE5, equipos);
                startActivity(intent);
            }
        });

        myLayout.addView(boton0, lParams);

        setContentView(sView, lParams);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cargar_jugadores, menu);
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

}

