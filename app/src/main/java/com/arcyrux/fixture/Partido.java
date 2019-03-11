package com.arcyrux.fixture;

import android.os.Parcel;
import android.os.Parcelable;

public class Partido implements Parcelable{

    int golLocal;
    int golVisita;
    Equipo local;
    Equipo visita;

    /**
     * @ constructor standard
     */
    public Partido(){
        golLocal = -1;
        golVisita = -1;
    }

    /**
     * @param team1  equipo local
     * @param team2  equipo visitante
     */
    public Partido(Equipo team1, Equipo team2){
        golLocal = -1;
        golVisita = -1;
        local = team1;
        visita = team2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(golLocal); //int golLocal;
        dest.writeInt(golVisita); //int golVisita;
        dest.writeParcelable(local, 0); //Equipo local;
        dest.writeParcelable(visita, 0); //Equipo visita;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Partido createFromParcel(Parcel parcel){
            return new Partido(parcel);
        }
        @Override
        public Partido[] newArray(int size){
            return new Partido[size];
        }
    };

    public Partido(Parcel parcel){
        //seguir el mismo orden que el usado en el metodo writeToParcel
        golLocal = parcel.readInt();
        golVisita = parcel.readInt();
        local = parcel.readParcelable(Equipo.class.getClassLoader());
        visita = parcel.readParcelable(Equipo.class.getClassLoader());
    }
}
