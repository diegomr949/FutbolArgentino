package com.utn;

import javax.persistence.EntityTransaction;
import javax.persistence.EntityManager;

public class ProcesadorEquipoArgentina extends ProcesadorEquipoTemplate {

    @Override
    protected void configurarAtributos(Equipo equipo) {
        System.out.println("Configurando atributos en Argentina para el equipo " + equipo.getNombre());
    }

    @Override
    protected void mostrarInformacion(Equipo equipo) {
        System.out.println("Mostrando información del equipo en Argentina:");
        System.out.println("ID: " + equipo.getId());
        System.out.println("Nombre: " + equipo.getNombre());
        System.out.println("Titulares: " + equipo.getTitulares());
        System.out.println("Suplentes: " + equipo.getSuplentes());
        System.out.println("Nombre del DT: " + equipo.getNombreDT());
        System.out.println("Puntos: " + equipo.getPuntos());
    }

    @Override
    protected void entrenarEquipo() {
        EntityManager entityManager = (EntityManager) EntityManagerFactoryUtil.getEntityManager();
        EntityTransaction transaction = (EntityTransaction) entityManager.getTransaction();

        try {
            transaction.begin();
            System.out.print("Ingrese el ID del equipo que desea entrenar: ");
            long equipoId = scanner.nextLong();

            Equipo equipo = entityManager.find(Equipo.class, equipoId);

            if (equipo != null) {
                System.out.println("Entrenando al equipo " + equipo.getNombre() + " en Argentina");
                transaction.commit();
                System.out.println("Equipo entrenado con éxito en Argentina.");
            } else {
                System.out.println("No se encontró un equipo con el ID proporcionado.");
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    protected void jugarPartido() {
        EntityManager entityManager = (EntityManager) EntityManagerFactoryUtil.getEntityManager();
        EntityTransaction transaction = (EntityTransaction) entityManager.getTransaction();

        try {
            transaction.begin();
            System.out.print("Ingrese el ID del equipo local: ");
            long equipoLocalId = scanner.nextLong();

            System.out.print("Ingrese el ID del equipo visitante: ");
            long equipoVisitanteId = scanner.nextLong();

            Equipo equipoLocal = entityManager.find(Equipo.class, equipoLocalId);
            Equipo equipoVisitante = entityManager.find(Equipo.class, equipoVisitanteId);

            if (equipoLocal != null && equipoVisitante != null) {
                System.out.println("Jugando partido en Argentina entre " + equipoLocal.getNombre() + " y " + equipoVisitante.getNombre());
                transaction.commit();
                System.out.println("Partido jugado con éxito en Argentina.");
            } else {
                System.out.println("No se encontró uno o ambos equipos con los IDs proporcionados.");
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    protected void jugarPartido(Equipo equipoLocal, Equipo equipoVisitante) {
        System.out.println("Jugando partido en Argentina entre " + equipoLocal.getNombre() + " y " + equipoVisitante.getNombre());
        jugarPartido(equipoLocal, equipoVisitante);
    }

    @Override
    protected void registrarResultado(Equipo equipoLocal, Equipo equipoVisitante, int golesLocal, int golesVisitante) {
        System.out.println("Resultado registrado en Argentina: " + equipoLocal.getNombre() + " " + golesLocal + " - " + golesVisitante + " " + equipoVisitante.getNombre());

        int puntosGanadosLocal = calcularPuntosGanados(golesLocal, golesVisitante);
        int puntosPerdidosLocal = calcularPuntosPerdidos(golesLocal, golesVisitante);

        int puntosGanadosVisitante = calcularPuntosGanados(golesVisitante, golesLocal);
        int puntosPerdidosVisitante = calcularPuntosPerdidos(golesVisitante, golesLocal);

        EntityManager entityManager = (EntityManager) EntityManagerFactoryUtil.getEntityManager();
        EntityTransaction transaction = (EntityTransaction) entityManager.getTransaction();
        transaction.begin();

        try {
            equipoLocal.actualizarPuntos(puntosGanadosLocal, puntosPerdidosLocal);
            equipoVisitante.actualizarPuntos(puntosGanadosVisitante, puntosPerdidosVisitante);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    private int calcularPuntosGanados(int golesEquipo, int golesContrario) {
        return golesEquipo > golesContrario ? 3 : (golesEquipo == golesContrario ? 1 : 0);
    }

    private int calcularPuntosPerdidos(int golesEquipo, int golesContrario) {
        return golesEquipo < golesContrario ? 3 : (golesEquipo == golesContrario ? 1 : 0);
    }
}
