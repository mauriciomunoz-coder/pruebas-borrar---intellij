package com.formacionbdi.microservicios.commons.examenes.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "examenes")
public class Examen {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;


    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;


    @NotNull  //debido a que es un objeto se valida con NotNull
    @OneToMany(mappedBy = "examen", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"examen"}, allowSetters = true)
    private List<Pregunta> preguntas;


    @ManyToOne(fetch = FetchType.LAZY)
    private Asignatura asignatura;

    @Transient
    private boolean respondido;


    //inicializamos la lista preguntas
    public Examen() {
        this.preguntas = new ArrayList<>();
    }

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }


    public void addPreguntas(Pregunta pregunta) {
        this.preguntas.add(pregunta); // agregamos una pregunta a la tabla preguntas de la BD
        pregunta.setExamen(this);  //egregamos el id del examen "para manejar la relacion bidireccional"
    }

    public void removePreguntas(Pregunta pregunta) {
        this.preguntas.remove(pregunta); // eliminamos la pregunta de la tabla preguntas de la BD
        pregunta.setExamen(null);  //el campo del id del examen lo dejamos en null
    }

    // metodo que agrega el listado de preguntas
    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas.clear();
        preguntas.forEach(this::addPreguntas);
    }


    //valida si el elemento a eliminar existe o no en la BD
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Examen)) {
            return false;
        }

        Examen a = (Examen) obj;

        return this.id != null && this.id.equals(a.getId());
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

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }


    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public boolean isRespondido() {
        return respondido;
    }

    public void setRespondido(boolean respondido) {
        this.respondido = respondido;
    }
}
