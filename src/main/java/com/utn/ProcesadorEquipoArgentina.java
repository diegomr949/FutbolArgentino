package com.utn;

// Subclase que implementa procesar un equipo
public class ProcesadorEquipoArgentina extends ProcesadorEquipoTemplate {

    @Override
    protected void mostrarInformacion(Equipo equipo) {
        System.out.println("Mostrando información del equipo:");
        System.out.println("Nombre: " + equipo.getNombre());
        System.out.println("Titulares: " + equipo.getTitulares());
        System.out.println("Suplentes: " + equipo.getSuplentes());
        System.out.println("Nombre del DT: " + equipo.getNombreDT());
        System.out.println("Puntos: " + equipo.getPuntos());
    }

    @Override
    protected void entrenarEquipo() {
        System.out.println("Entrenando al equipo.");
    }

    @Override
    protected void jugarPartido() {
        System.out.println("Jugando partido.");
    }
}
