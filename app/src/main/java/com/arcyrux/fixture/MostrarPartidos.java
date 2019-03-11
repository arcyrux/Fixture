package com.arcyrux.fixture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class MostrarPartidos extends Activity {

    Torneo torneo;
    public final static String EXTRA_MESSAGE9 = "fixture_MENSAJE8";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_partidos);
        Intent myIntent = getIntent();
        torneo = myIntent.getParcelableExtra(Fixture.EXTRA_MESSAGE7);

        int largo = torneo.partido.length;

        ScrollView sView = new ScrollView(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.activity_mostrar_partidos, null);

        int xFecha = torneo.xDia;
        for (int i = 0; i < largo; i++) {
            if (xFecha == torneo.xDia){
                TextView dia = new TextView(this);
                dia.setText("FECHA " + roundUp(i+1, torneo.xDia));
                parent.addView(dia);
                xFecha = 0;
            }
            xFecha++;

            View custom = inflater.inflate(R.layout.linea_partido, null);

            TextView nombreLocal = (TextView) custom.findViewById(R.id.nomLocal);
            TextView nombreVisita = (TextView) custom.findViewById(R.id.nomVisita);
            EditText golLocal = (EditText) custom.findViewById(R.id.golLocal);
            EditText golVisita = (EditText) custom.findViewById(R.id.golVisita);

            nombreLocal.setText(torneo.partido[i].local.nombre);
            nombreVisita.setText(torneo.partido[i].visita.nombre);
            golLocal.setId(i * 2 + 1);
            golVisita.setId(i * 2 + 2);
            golLocal.setNextFocusDownId(i * 2 + 2);
            golVisita.setNextFocusDownId(i * 2 + 3);
            if(torneo.partido[i].golLocal != -1){
                golLocal.setText(Integer.toString(torneo.partido[i].golLocal));
            }
            if(torneo.partido[i].golVisita != -1){
                golVisita.setText(Integer.toString(torneo.partido[i].golVisita));
            }

            parent.addView(custom);
        }

        Button volver = new Button(this);
        volver.setText(getResources().getString(R.string.volver));
        volver.setTextSize(getResources().getDimension(R.dimen.z_text_size_equipo));
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onBackPressed();}
        });
        parent.addView(volver);


        sView.addView(parent);
        setContentView(sView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mostrar_partidos, menu);
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
    public void onBackPressed() {

        int resLocal;
        int resVisita;
        EditText local;
        EditText visita;

        for(int i = 0; i < torneo.partido.length; i++ ){
            local = (EditText) findViewById(i * 2 + 1);
            visita = (EditText) findViewById(i * 2 + 2);

            if(local.getText().toString().matches(".*\\w.*")){
                resLocal = Integer.parseInt(local.getText().toString());
            }
            else{resLocal = -1;}
            if(visita.getText().toString().matches(".*\\w.*")) {
                resVisita = Integer.parseInt(visita.getText().toString());
            }
            else{resVisita = -1;}

            if(resLocal > -1 && resVisita > -1){
                torneo.partido[i].golLocal = resLocal;
                torneo.partido[i].golVisita = resVisita;
            }
        }

        torneo.Posiciones();
        Archivo archivo = new Archivo();
        archivo.escribir(torneo);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_MESSAGE9, torneo);
        setResult(RESULT_OK, intent);
        finish();
    }

    public int roundUp(int dividendo, int divisor){
        return (dividendo + divisor - 1) / divisor;
    }

    public void volver(View view){
        onBackPressed();
    }
}
