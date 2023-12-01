package com.utn;

import jakarta.persistence.*;

@Entity
@Table(name = "equipos")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "titulares")
    private int titulares;

    @Column(name = "suplentes")
    private int suplentes;

    @Column(name = "nombre_dt")
    private String nombreDT;

    @Column(name = "puntos")
    private int puntos;

    // Constructores
    public Equipo() {
    }

    public Equipo(String nombre, int titulares, int suplentes, String nombreDT, int puntos) {
        this.nombre = nombre;
        this.titulares = titulares;
        this.suplentes = suplentes;
        this.nombreDT = nombreDT;
        this.puntos = puntos;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTitulares() {
        return titulares;
    }

    public void setTitulares(int titulares) {
        this.titulares = titulares;
    }

    public int getSuplentes() {
        return suplentes;
    }

    public void setSuplentes(int suplentes) {
        this.suplentes = suplentes;
    }

    public String getNombreDT() {
        return nombreDT;
    }

    public void setNombreDT(String nombreDT) {
        this.nombreDT = nombreDT;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    // Método para actualizar puntos según el resultado del partido
    public void actualizarPuntos(int puntosGanados, int puntosPerdidos) {
        this.puntos += puntosGanados - puntosPerdidos;
        // Aquí también podrías actualizar otros atributos si es necesario
    }

    // Sobrescribe el método toString para facilitar la impresión de información
    @Override
    public String toString() {
        return "com.utn.Equipo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", titulares=" + titulares +
                ", suplentes=" + suplentes +
                ", nombreDT='" + nombreDT + '\'' +
                ", puntos=" + puntos +
                '}';
    }
}
