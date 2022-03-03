package com.formacionbdi.microservicio.app.cursos.models.entity;


import com.formaciobdi.microservicios.app.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;


    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;


    @PrePersist
    private void prePersist() {
        this.createAt = new Date();
    }

    // relacion de curso con alumno
    @OneToMany(fetch = FetchType.LAZY)
    private List<Alumno> alumnos;


    @ManyToMany(fetch = FetchType.LAZY)
    private List<Examen> examenes;


    //con este metodo inicializamos la lista alumnos
    public Curso() {
        this.alumnos = new ArrayList<>();
        this.examenes = new ArrayList<>();
    }

    public void addExamenes(Examen examen) {
        this.examenes.add(examen);
    }


    public void removeExamen(Examen examen) {
        this.examenes.remove(examen);
    }


    //metodo para agregar de a un alumno a la lista alumnos
    public void addAlumno(Alumno alumno) {
        this.alumnos.add(alumno);
    }


    //metodo para eliminar un alumno de la lista
    public void removeAlumno(Alumno alumno) {
        this.alumnos.remove(alumno);
    }


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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }



    public List<Examen> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<Examen> examenes) {
        this.examenes = examenes;
    }
}
