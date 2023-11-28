package com.utn;

import jakarta.persistence.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("EquiposPersistenceUnit");

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int opcion;

            do {
                System.out.println("Selecciona una opción:");
                System.out.println("1. Crear equipo");
                System.out.println("2. Leer equipo por ID");
                System.out.println("3. Leer todos los equipos");
                System.out.println("4. Actualizar equipo por ID");
                System.out.println("5. Eliminar equipo por ID");
                System.out.println("6. Entrenar Equipo");
                System.out.println("7. Jugar Partido");
                System.out.println("8. Salir");
                System.out.print("Opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        crearEquipo();
                        break;
                    case 2:
                        leerEquipoPorId();
                        break;
                    case 3:
                        leerTodosLosEquipos();
                        break;
                    case 4:
                        actualizarEquipo();
                        break;
                    case 5:
                        eliminarEquipo();
                        break;
                    case 6:
                        entrenarEquipo();
                        break;
                    case 7:
                        jugarPartido();
                        break;
                    case 8:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intenta de nuevo.");
                }
            } while (opcion != 8);
        }
    }

    private static void crearEquipo() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Equipo equipo = new Equipo();
            configurarAtributos(entityManager, equipo);

            entityManager.persist(equipo);
            transaction.commit();

            System.out.println("Equipo creado con éxito. ID: " + equipo.getId());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    private static void leerEquipoPorId() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Ingrese el ID del equipo: ");
            long equipoId = scanner.nextLong();

            Equipo equipo = entityManager.find(Equipo.class, equipoId);

            if (equipo != null) {
                System.out.println("Información del equipo con ID " + equipoId + ":");
                mostrarInformacionEquipo(equipo);
            } else {
                System.out.println("No se encontró un equipo con el ID proporcionado.");
            }
        } finally {
            entityManager.close();
        }
    }

    private static void leerTodosLosEquipos() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            TypedQuery<Equipo> query = entityManager.createQuery("SELECT e FROM Equipo e", Equipo.class);
            List<Equipo> equipos = query.getResultList();

            if (!equipos.isEmpty()) {
                System.out.println("Lista de equipos:");
                for (Equipo equipo : equipos) {
                    mostrarInformacionEquipo(equipo);
                }
            } else {
                System.out.println("No hay equipos registrados.");
            }
        } finally {
            entityManager.close();
        }
    }

    private static void actualizarEquipo() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Scanner scanner = new Scanner(System.in);

        try {
            transaction.begin();

            System.out.print("Ingrese el ID del equipo que desea actualizar: ");
            long equipoId = scanner.nextLong();

            Equipo equipo = entityManager.find(Equipo.class, equipoId);

            if (equipo != null) {
                System.out.println("Información actual del equipo:");
                mostrarInformacionEquipo(equipo);

                configurarAtributos(entityManager, equipo);

                transaction.commit();
                System.out.println("Equipo actualizado con éxito.");
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

    private static void eliminarEquipo() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Scanner scanner = new Scanner(System.in);

        try {
            transaction.begin();

            System.out.print("Ingrese el ID del equipo que desea eliminar: ");
            long equipoId = scanner.nextLong();

            Equipo equipo = entityManager.find(Equipo.class, equipoId);

            if (equipo != null) {
                entityManager.remove(equipo);
                transaction.commit();
                System.out.println("Equipo eliminado con éxito.");
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

    private static void mostrarInformacionEquipo(Equipo equipo) {
        System.out.println("ID: " + equipo.getId());
        System.out.println("Nombre: " + equipo.getNombre());
        System.out.println("Titulares: " + equipo.getTitulares());
        System.out.println("Suplentes: " + equipo.getSuplentes());
        System.out.println("Nombre del DT: " + equipo.getNombreDT());
        System.out.println("Puntos: " + equipo.getPuntos());
        System.out.println("-----------------------");
    }

    private static void configurarAtributos(EntityManager entityManager, Equipo equipo) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el nombre del equipo: ");
        equipo.setNombre(scanner.nextLine());

        System.out.print("Ingrese la cantidad de jugadores titulares: ");
        equipo.setTitulares(scanner.nextInt());

        System.out.print("Ingrese la cantidad de jugadores suplentes: ");
        equipo.setSuplentes(scanner.nextInt());

        scanner.nextLine();

        System.out.print("Ingrese el nombre del director técnico: ");
        equipo.setNombreDT(scanner.nextLine());

        System.out.print("Ingrese la cantidad de puntos del equipo: ");
        equipo.setPuntos(scanner.nextInt());
    }

    private static void entrenarEquipo() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Ingrese el ID del equipo que desea entrenar: ");
            long equipoId = scanner.nextLong();

            transaction.begin();

            Equipo equipo = entityManager.find(Equipo.class, equipoId);

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
        } finally {
            entityManager.close();
        }
    }

    private static void jugarPartido() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Ingrese el ID del equipo que desea jugar el partido: ");
            long equipoId = scanner.nextLong();

            transaction.begin();

            Equipo equipo = entityManager.find(Equipo.class, equipoId);

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
        } finally {
            entityManager.close();
        }
    }
}
