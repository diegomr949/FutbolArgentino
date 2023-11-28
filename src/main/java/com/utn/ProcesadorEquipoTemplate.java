package com.utn;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Scanner;

public abstract class ProcesadorEquipoTemplate {

    private static final EntityManager ENTITY_MANAGER = EntityManagerFactoryUtil.getEntityManager();

    private Scanner scanner = new Scanner(System.in);

    public final void procesarEquipo(Equipo equipo) {
        configurarAtributos(equipo);
        mostrarInformacion(equipo);
        entrenarEquipo(equipo.getId());
        jugarPartido(equipo.getId());
        guardarEquipoEnBaseDeDatos(equipo);
    }

    protected void configurarAtributos(Equipo equipo) {
        System.out.print("Ingrese el nombre del equipo: ");
        equipo.setNombre(scanner.nextLine());

        System.out.print("Ingrese la cantidad de puntos del equipo: ");
        equipo.setPuntos(Integer.parseInt(scanner.nextLine()));

        System.out.print("Ingrese la cantidad de jugadores titulares: ");
        equipo.setTitulares(Integer.parseInt(scanner.nextLine()));

        System.out.print("Ingrese la cantidad de jugadores suplentes: ");
        equipo.setSuplentes(Integer.parseInt(scanner.nextLine()));

        System.out.print("Ingrese el nombre del director técnico: ");
        equipo.setNombreDT(scanner.nextLine());
    }

    protected abstract void mostrarInformacion(Equipo equipo);

    protected void entrenarEquipo(long equipoId) {
        EntityTransaction transaction = ENTITY_MANAGER.getTransaction();

        try {
            transaction.begin();
            Equipo equipo = ENTITY_MANAGER.find(Equipo.class, equipoId);

            if (equipo != null) {
                System.out.println("Entrenando al equipo " + equipo.getNombre());
                transaction.commit();
                System.out.println("Equipo entrenado con éxito.");
            } else {
                System.out.println("No se encontró un equipo con el ID proporcionado.");
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    protected void jugarPartido(long equipoId) {
        EntityTransaction transaction = ENTITY_MANAGER.getTransaction();

        try {
            transaction.begin();
            Equipo equipo = ENTITY_MANAGER.find(Equipo.class, equipoId);

            if (equipo != null) {
                System.out.println("Jugando partido con " + equipo.getNombre());
                transaction.commit();
                System.out.println("Partido jugado con éxito.");
            } else {
                System.out.println("No se encontró un equipo con el ID proporcionado.");
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    protected void guardarEquipoEnBaseDeDatos(Equipo equipo) {
        EntityTransaction transaction = ENTITY_MANAGER.getTransaction();
        transaction.begin();

        try {
            ENTITY_MANAGER.persist(equipo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    protected abstract void entrenarEquipo();

    protected abstract void jugarPartido();
}
