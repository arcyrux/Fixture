package com.arcyrux.fixture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;


public class mostrarJugadores extends Activity {

    int i;
    int contador;
    ArrayList<String> listaJugadores;
    public final static String EXTRA_MESSAGE4 = "fixture_MENSAJE4";
    public final static String EXTRA_MESSAGE5 = "fixture_MENSAJE5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent myIntent = getIntent();


        listaJugadores = myIntent.getStringArrayListExtra(IngresarJugadores.EXTRA_MESSAGE3);
        final int jugadores = listaJugadores.size();
        contador = jugadores;


        TextView textView[] = new TextView[jugadores];
        final Button boton[] = new Button[jugadores];

        ScrollView sView = new ScrollView(this);
        final LinearLayout myLayout = new LinearLayout(this);
        final LinearLayout inLayout[] = new LinearLayout[jugadores];


        myLayout.setOrientation(LinearLayout.VERTICAL);


        LinearLayout.LayoutParams textViewParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 6.0f);

        LinearLayout.LayoutParams lParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);

        sView.addView(myLayout);

        Button boton0 = new Button(this);
        boton0.setText("Listo");
        boton0.setTextSize(getResources().getDimension(R.dimen.z_text_size_fixture));
        boton0.setGravity(1);
        boton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(jugadores != contador){

                    Intent intent = new Intent(v.getContext(), IngresarJugadores.class);
                    intent.putExtra(EXTRA_MESSAGE4, listaJugadores);
                    intent.putExtra(EXTRA_MESSAGE5, jugadores);
                    startActivity(intent);

                }

            }
        });



        for (i = 0; i < jugadores; i++ ){

            TextView textView1 = new TextView(this);
            textView[i] = textView1;
            textView[i].setTextSize(getResources().getDimension(R.dimen.z_text_size_fixture));
            textView[i].setText(listaJugadores.get(i));
            textView[i].setGravity(0);
            textView[i].setId(i);


            Button boton1 = new Button(this);
            boton[i] = boton1;
            boton[i].setTextSize(getResources().getDimension(R.dimen.z_text_size_fixture));
            boton[i].setText("X");
            boton[i].setGravity(1);
            boton[i].setId(i);
            boton[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int indice = v.getId();
                    listaJugadores.remove(indice);
                    contador--;
                    myLayout.removeView(inLayout[indice]);
                }
            });


            LinearLayout linear1 = new LinearLayout(this);
            inLayout[i] = linear1;

            inLayout[i].addView(textView[i], textViewParams);
            inLayout[i].addView(boton[i]);

            myLayout.addView(inLayout[i], lParams);

        }

        myLayout.addView(boton0, lParams);

        setContentView(sView, lParams);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mostrar_jugadores, menu);
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
