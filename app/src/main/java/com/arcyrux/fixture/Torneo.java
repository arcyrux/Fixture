package com.arcyrux.fixture;

import android.os.Parcel;
import android.os.Parcelable;


public class Torneo implements Parcelable{

    int encuentros;
    int fechas;
    int xDia;
    boolean vuelta;
    String[] jugador;
    Partido[] partido;
    Equipo[] equipo;

    //@Deprecated
    public Torneo(){}

    public Torneo(Equipo[] team, Partido[] match, Boolean ida){
        equipo = team;
        partido = match;
        vuelta = ida;
        int num = team.length;
        xDia = ((num % 2) == 0) ? (num / 2) : ((num - 1) / 2);	//Si n equipos es par, los partidos por fecha es n/2. Sino, es (n-1)/2.
        fechas = ((num % 2) == 0) ? (num - 1) : num;	//Si n equipos es par, la cantidad de fechas es igual a n - 1. Si es impar, es igual a n.
        fechas = vuelta ? fechas * 2 : fechas;
        encuentros = xDia * fechas;	//El total de partidos, es fecha * partidosXfecha

        partido = new Partido[encuentros];
        for (int i = 0; i < encuentros; i++){
            Partido part = new Partido();
            partido[i] = part;
        }

        Armar(team);
    }

    public Torneo(Equipo[] team, Boolean ida){

        vuelta = ida;
        equipo = team;
        int num = team.length;
        xDia = ((num % 2) == 0) ? (num / 2) : ((num - 1) / 2);	//Si n equipos es par, los partidos por fecha es n/2. Sino, es (n-1)/2.
        fechas = ((num % 2) == 0) ? (num - 1) : num;	//Si n equipos es par, la cantidad de fechas es igual a n - 1. Si es impar, es igual a n.
        fechas = vuelta ? fechas * 2 : fechas;
        encuentros = xDia * fechas;	//El total de partidos, es fecha * partidosXfecha

        partido = new Partido[encuentros];
        for (int i = 0; i < encuentros; i++){
            Partido part = new Partido();
            partido[i] = part;
        }

        Armar(team);
    }

    void Posiciones(){

        //int tlen = equipo.length;
        for (Equipo anEquipo : equipo) {
            anEquipo.posicion = 1;
            anEquipo.ganados = 0;
            anEquipo.empatados = 0;
            anEquipo.jugados = 0;
            anEquipo.favor = 0;
            anEquipo.contra = 0;
        }

        for (int j = 0; j < encuentros; j++){
            if(partido[j].golLocal != -1 && partido[j].golVisita != -1){
                if(partido[j].golLocal > partido[j].golVisita){
                    equipo[indice(partido[j].local)].ganados++;
                }
                else if(partido[j].golLocal < partido[j].golVisita){
                    equipo[indice(partido[j].visita)].ganados++;
                }
                else if(partido[j].golLocal == partido[j].golVisita){
                    equipo[indice(partido[j].local)].empatados++;
                    equipo[indice(partido[j].visita)].empatados++;
                }
                equipo[indice(partido[j].local)].jugados++;
                equipo[indice(partido[j].visita)].jugados++;
                equipo[indice(partido[j].local)].favor += partido[j].golLocal;
                equipo[indice(partido[j].local)].contra += partido[j].golVisita;
                equipo[indice(partido[j].visita)].favor += partido[j].golVisita;
                equipo[indice(partido[j].visita)].contra += partido[j].golLocal;
            }
        }

        for (Equipo anEquipo : equipo) {
            anEquipo.puntos = anEquipo.ganados * 3 + anEquipo.empatados;
        }


        for (Equipo anEquipo : equipo) {
            for (Equipo anEquipo1 : equipo) {
                if (anEquipo.puntos < anEquipo1.puntos) {
                    anEquipo.posicion++;
                } else if (anEquipo.puntos == anEquipo1.puntos) {
                    if (anEquipo.favor - anEquipo.contra < anEquipo1.favor - anEquipo1.contra) {
                        anEquipo.posicion++;
                    } else if (anEquipo.favor - anEquipo.contra == anEquipo1.favor - anEquipo1.contra) {
                        if (anEquipo.favor < anEquipo1.favor) {
                            anEquipo.posicion++;
                        }
                    }
                }
            }
        }
    }

    Equipo[] OrdenarTabla(Equipo[] team){

        for(int i = 0; i < team.length; i++){
            for(int j = 0; j < i; j++){
                if(team[i].posicion < team[j].posicion){
                    Equipo aux = team[j];
                    team[j] = team[i];
                    team[i] = aux;
                }
            }
        }
        return team;
    }


    private void Armar (Equipo[] clubes) {
        int auxT = clubes.length;
        Boolean impar = (auxT % 2 != 0);
        if (impar) {
            auxT++;
        }
        int totalP = (auxT * (auxT - 1)) / 2;//total de partidos
        Equipo[] locales = new Equipo[fechas * fechas];
        Equipo[] visitas = new Equipo[fechas * fechas];
        int modIF = (auxT / 2);//para hacer mod cada inicio de fecha
        int indiceInverso = auxT - 2;
        for (int io = 0; io < totalP; io++) {
            if (io % modIF == 0) {//seria el partido inicial de cada fecha
                //si es impar el numero de clubes la primera fecha se borra poniendo null
                if (impar) {
                    locales[io] = null;
                    visitas[io] = null;
                } else {
                    //se pone uno local otro  visita al ultimo equipo
                    if (io % 2 == 0) {
                        locales[io] = clubes[io % (auxT - 1)];
                        visitas[io] = clubes[auxT - 1];
                    } else {
                        locales[io] = clubes[auxT - 1];
                        visitas[io] = clubes[io % (auxT - 1)];
                    }
                }
            } else {
                locales[io] = clubes[io % (auxT - 1)];
                visitas[io] = clubes[indiceInverso];
                --indiceInverso;
                if (indiceInverso < 0) {
                    indiceInverso = auxT - 2;
                }
            }
        }

        int num = 0;
        for(int iu = 0; iu < totalP; iu++){
            if(locales[iu]!= null){
                partido[num].local = locales[iu];
                partido[num].visita = visitas[iu];
                num++;
            }
        }

        if(vuelta){
            for(int iu = 0; iu < totalP; iu++){
                if(locales[iu]!= null){
                    partido[num].local = visitas[iu];
                    partido[num].visita = locales[iu];
                    num++;
                }
            }
        }
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(encuentros); // int encuentros;
        dest.writeInt(fechas); //int fechas;
        dest.writeInt(xDia); //int xDia;
        dest.writeInt(vuelta ? 1 : 0);
        dest.writeStringArray(jugador); //String[] jugador;
        dest.writeInt(equipo.length);
        dest.writeTypedArray(equipo, flags);
        dest.writeInt(partido.length);
        dest.writeTypedArray(partido, flags);
    }

    public static final Parcelable.Creator<Torneo> CREATOR =
            new Parcelable.Creator<Torneo>()
            {
                @Override
                public Torneo createFromParcel(Parcel parcel)
                {
                    return new Torneo(parcel);
                }
                @Override
                public Torneo[] newArray(int size)
                {
                    return new Torneo[size];
                }
            };

    public Torneo(Parcel parcel){
        //seguir el mismo orden que el usado en el metodo writeToParcel
        encuentros = parcel.readInt();
        fechas = parcel.readInt();
        xDia = parcel.readInt();
        vuelta = parcel.readInt() == 1 ;
        jugador = parcel.createStringArray();
        equipo = new Equipo[parcel.readInt()];
        parcel.readTypedArray(equipo, Equipo.CREATOR);
        partido = new Partido[parcel.readInt()];
        parcel.readTypedArray(partido, Partido.CREATOR);
    }

    private int indice(Equipo team){
        int index = 0;

        for (int i = 0; i < equipo.length; i++){
            if(equipo[i].nombre.equals(team.nombre)){
                index = i;
                break;
            }
        }
        return index;
    }
}
