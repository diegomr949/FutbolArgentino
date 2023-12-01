package com.utn;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String PERSISTENCE_UNIT_NAME = "EquiposPersistenceUnit";
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    public static void main(String[] args) {
        ProcesadorEquipoTemplate procesadorEquipoTemplate = new ProcesadorEquipoArgentina();

        try (Scanner scanner = new Scanner(System.in)) {
            int opcion;

            do {
                mostrarMenu();
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
                        procesadorEquipoTemplate.procesarEquipo(new Equipo());
                        break;
                    case 7:
                        procesadorEquipoTemplate.jugarPartido();
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

    private static void mostrarMenu() {
        System.out.println("Selecciona una opción:");
        System.out.println("1. Crear equipo");
        System.out.println("2. Leer equipo por ID");
        System.out.println("3. Leer todos los equipos");
        System.out.println("4. Actualizar equipo por ID");
        System.out.println("5. Eliminar equipo por ID");
        System.out.println("6. Entrenar Equipo");
        System.out.println("7. Jugar Partido");
        System.out.println("8. Salir");
    }

    private static void crearEquipo() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        try {
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
                System.err.println("Error al crear el equipo: " + e.getMessage());
            }
        } finally {
            if (entityManager.isOpen()) {
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
            if (entityManager.isOpen()) {
                entityManager.close();
            }
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    private static void actualizarEquipo() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            try {
                System.out.print("Ingrese el ID del equipo que desea actualizar: ");
                long equipoId = scanner.nextLong();
                scanner.nextLine(); // Consumir la nueva línea después del nextLong()

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
                System.err.println("Error al actualizar el equipo: " + e.getMessage());
            }
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    private static void eliminarEquipo() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            try {
                System.out.print("Ingrese el ID del equipo que desea eliminar: ");
                long equipoId = scanner.nextLong();
                scanner.nextLine(); // Consumir la nueva línea después del nextLong()

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
                System.err.println("Error al eliminar el equipo: " + e.getMessage());
            }
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
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
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Ingrese el nombre del equipo: ");
            equipo.setNombre(scanner.nextLine());

            System.out.print("Ingrese la cantidad de jugadores titulares: ");
            equipo.setTitulares(scanner.nextInt());

            System.out.print("Ingrese la cantidad de jugadores suplentes: ");
            equipo.setSuplentes(scanner.nextInt());

            scanner.nextLine(); // Consumir la nueva línea después del nextInt()

            System.out.print("Ingrese el nombre del director técnico: ");
            equipo.setNombreDT(scanner.nextLine());

            System.out.print("Ingrese la cantidad de puntos del equipo: ");
            equipo.setPuntos(scanner.nextInt());
        }
    }

    private static class EquiposPersistenceUnit {
    }
}
