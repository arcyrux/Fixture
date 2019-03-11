package com.arcyrux.fixture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Fixture extends Activity {

    Torneo torneo;
    Equipo[] team;
    public final static String EXTRA_MESSAGE7 = "fixture_MENSAJE7";
    public final static String EXTRA_MESSAGE8 = "fixture_MENSAJE9";
    private int mRequestCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent myIntent = getIntent();
        //ArrayList<String> listaJugadores = myIntent.getStringArrayListExtra(CargarEquipos.EXTRA_MESSAGE6);
        torneo = myIntent.getParcelableExtra(MainActivity.EXTRA_MESSAGE);
        if(torneo == null) {
            torneo = myIntent.getParcelableExtra(CargarEquipos.EXTRA_MESSAGE6);
        }
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            torneo = savedInstanceState.getParcelable("tourn");
        }
        else{torneo.Posiciones();}
    }

    @Override
    protected void onResume(){
        super.onResume();

        team = torneo.OrdenarTabla(torneo.equipo);
        int largo = team.length;
        setContentView(R.layout.activity_mostrar_fixture);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout parent = (LinearLayout)findViewById(R.id.parent);
        LinearLayout linear = (LinearLayout)findViewById(R.id.linear);

        for (int i = 0; i < largo; i++) {
            View linea = inflater.inflate(R.layout.linea_fixture, null);
            parent.addView(linea);


            Button boton = (Button) linear.findViewById(R.id.button);
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MostrarPartidos.class);
                    intent.putExtra(EXTRA_MESSAGE7, torneo);
                    intent.putExtra(EXTRA_MESSAGE8, mRequestCode);
                    startActivityForResult(intent, 1);
                }
            });
            int col = i % 2 == 0 ? Color.LTGRAY : Color.GRAY;
            linea.setBackgroundColor(col);

            boton = (Button) linea.findViewById(R.id.button);
            boton.setText(team[i].nombre);
            boton.setId(i);
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MostrarEquipos.class);
                    int num = v.getId();
                    intent.putExtra(EXTRA_MESSAGE7, torneo.equipo[num].integrante);
                    startActivity(intent);
                }
            });


            TextView tv = (TextView) linea.findViewById(R.id.textPOS);
            tv.setText(Integer.toString(team[i].posicion));

            tv = (TextView) linea.findViewById(R.id.textPTS);
            tv.setText(Integer.toString(team[i].puntos));

            tv = (TextView) linea.findViewById(R.id.textPJ);
            tv.setText(Integer.toString(team[i].jugados));

            tv = (TextView) linea.findViewById(R.id.textPG);
            tv.setText(Integer.toString(team[i].ganados));

            tv = (TextView) linea.findViewById(R.id.textPE);
            tv.setText(Integer.toString(team[i].empatados));

            tv = (TextView) linea.findViewById(R.id.textPP);
            tv.setText(Integer.toString(team[i].perdidos));

            tv = (TextView) linea.findViewById(R.id.textGF);
            tv.setText(Integer.toString(team[i].favor));

            tv = (TextView) linea.findViewById(R.id.textGC);
            tv.setText(Integer.toString(team[i].contra));

            tv = (TextView) linea.findViewById(R.id.textDIF);
            tv.setText(Integer.toString(team[i].favor - team[i].contra));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fixture, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if(requestCode == mRequestCode && resultCode == RESULT_OK){
        torneo = data.getParcelableExtra(MostrarPartidos.EXTRA_MESSAGE9);
        //}
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelable("tourn", torneo);
    }
}
