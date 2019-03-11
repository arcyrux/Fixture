package com.arcyrux.fixture;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class Archivo {


    void escribir(Torneo torneo){

        File folder = new File(Environment.getExternalStorageDirectory() + "/arcyrux/Fixture");
        try {
            folder.mkdirs();
        }
        catch(Exception e){
            Log.e("Ficheros","Error al escribir fichero a tarjeta SD");
            e.printStackTrace();
        }

        ArrayList<String> columna = new ArrayList<>();

        try {
            String dato;
            File ruta_sd = Environment.getExternalStorageDirectory();
            File f = new File(folder.getAbsolutePath(), "fixture.dat");
            OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(f));

//Guardar cantidad de equipos, partidos, jugadores e ida/vuelta
            dato = torneo.equipo.length + "," + torneo.partido.length + "," + torneo.jugador.length+ "," + torneo.vuelta ;
            columna.add(dato);

//Guardar cada equipo. En la primera linea los datos y en la segunda los jugadores
            for(Equipo equipo : torneo.equipo) {
                dato = equipo.nombre + "," +
                        equipo.posicion + "," +
                        equipo.puntos + "," +
                        equipo.jugados + "," +
                        equipo.ganados + "," +
                        equipo.empatados + "," +
                        equipo.perdidos;
                columna.add(dato);

//Pasar de array a string los jugadores.
                String aux= "";
                for(String nombre : equipo.integrante){
                    aux += nombre + ",";
                }
                //Cortar el ultimo caracter que queda como coma.
                aux = aux.substring(0, aux.length() - 1);
                columna.add(aux);
            }

//Guardar cada partido
            for(Partido partido : torneo.partido){
                dato =
                        partido.golLocal + "," +
                                partido.golVisita;
                columna.add(dato);
            }

//Guardar los jugadores en la ultima linea
            dato = "";
            for(String jugador : torneo.jugador){
                dato += jugador + ",";
            }
            dato = dato.substring(0, dato.length() - 1);
            columna.add(dato);

//Escribir linea por linea en el archivo
            for (String linea : columna){
                fout.write(linea + "\r\n");
            }
            fout.close();
        }

        catch (Exception ex) {
            Log.e("Ficheros","Error al escribir fichero a tarjeta SD");
            ex.printStackTrace();
        }
    }

    public Torneo leer() {
        Torneo torneo;
        File archivo;
        FileReader fr = null;
        BufferedReader br;
        ArrayList<String> linea = new ArrayList<>();
        File ruta_sd = Environment.getExternalStorageDirectory();
        File folder = new File(Environment.getExternalStorageDirectory() + "/arcyrux/Fixture");
        Equipo[] equipo;
        String[] jugadores;
        String[] renglon;
        int fila = 0;

        try {
// Apertura del fichero y creacion de BufferedReader para poder
// hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File (folder, "fixture.dat");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

// Lectura del fichero
            String line;
            while((line=br.readLine())!=null) {
                linea.add(line);
            }
//Leer los datos fijos del torneo
            renglon = linea.toArray(new String[linea.size()]);
            ArrayList<String> dato = listar(renglon[fila]);
            int largoEquipo = Integer.parseInt(dato.get(0));
            int largoPartido = Integer.parseInt(dato.get(1));
            int largoJugador = Integer.parseInt(dato.get(2));
            boolean vuelta = dato.get(3).equals("true");
            equipo = new Equipo[largoEquipo];
            fila++;
//Crear cada uno de los equipos
            for(int i = 0; i < largoEquipo; i++){
                dato = listar(renglon[fila]);
                Equipo team = new Equipo();
                team.nombre = dato.get(0);
                team.posicion = Integer.parseInt(dato.get(1));
                team.puntos = Integer.parseInt(dato.get(2));
                team.jugados = Integer.parseInt(dato.get(3));
                team.ganados = Integer.parseInt(dato.get(4));
                team.empatados = Integer.parseInt(dato.get(5));
                team.perdidos = Integer.parseInt(dato.get(6));
                fila++;
                team.integrante = listar(renglon[fila]);
                equipo[i] = team;
                fila++;
            }
//Crear el torneo
            torneo = new Torneo(equipo, vuelta);

            for(int i = 0; i < largoPartido; i++){
                dato = listar(renglon[fila]);
                torneo.partido[i].golLocal = Integer.parseInt(dato.get(0));
                torneo.partido[i].golVisita = Integer.parseInt(dato.get(1));
                fila++;
            }
//Cargar los jugadores
            dato = listar(renglon[fila]);
            jugadores = new String[largoJugador];
            for (int i = 0; i < largoJugador; i++){
                jugadores[i] = dato.get(i);
            }
            torneo.jugador = jugadores;
//Finalizar y devolver el torneo
            return torneo;
        }
        catch(Exception e){
            e.printStackTrace();
            torneo = new Torneo();
            return torneo;
        }
        finally{
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todos va bien como si salta
            // una excepcion.
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }


//    public int roundUp(int dividendo, int divisor){
//        return (dividendo + divisor - 1) / divisor;
//    }
//
//    public Equipo comparar(String nombre, Equipo[] equipos){
//        Equipo team = new Equipo();
//
//        for (Equipo equipo : equipos){
//            if(equipo.nombre.equals(nombre)){
//                return team;
//            }
//        }
//        return team;
//    }

    public ArrayList<String> listar(String dato){
        ArrayList<String> lista = new ArrayList<>();
        String letra;
        String palabra = "";
        int largo = dato.length();

        for(int i = 0; i < largo; i++){
            letra = dato.substring(i , i+1);
            if(!letra.equals(",")){
                palabra += letra;
            }
            else{
                lista.add(palabra);
                palabra = "";
            }
        }
        lista.add(palabra);
        return lista;
    }
}


