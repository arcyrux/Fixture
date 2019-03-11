package com.arcyrux.fixture;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Equipo implements Parcelable{

    String nombre;
    int posicion;
    int puntos;
    int jugados;
    int ganados;
    int empatados;
    int perdidos;
    int favor;
    int contra;
    ArrayList<String> integrante;

    void SumarJugador(String name){
        integrante.add(name);
    }

    public Equipo(String name){
        posicion = 1;
        nombre = name;
        integrante = new ArrayList<>();
    }

    public Equipo(){integrante = new ArrayList<>();}




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(nombre); //String nombre;
        dest.writeInt(posicion); //int posicion;
        dest.writeInt(puntos); //int puntos;
        dest.writeInt(jugados); //int jugados;
        dest.writeInt(ganados); //int ganados;
        dest.writeInt(empatados); //int empatados;
        dest.writeInt(perdidos); //int perdidos;
        dest.writeInt(favor); //int favor;
        dest.writeInt(contra); //int contra;
        dest.writeStringList(integrante); //List<String> integrante = new ArrayList<>();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        @Override
        public Equipo createFromParcel(Parcel parcel)
        {
            return new Equipo(parcel);
        }
        @Override
        public Equipo[] newArray(int size)
        {
            return new Equipo[size];
        }
    };

    public Equipo(Parcel parcel){
        //seguir el mismo orden que el usado en el metodo writeToParcel
        nombre = parcel.readString();
        posicion = parcel.readInt();
        puntos = parcel.readInt();
        jugados = parcel.readInt();
        ganados = parcel.readInt();
        empatados = parcel.readInt();
        perdidos = parcel.readInt();
        favor = parcel.readInt();
        contra = parcel.readInt();
        integrante = parcel.createStringArrayList();
    }
}
