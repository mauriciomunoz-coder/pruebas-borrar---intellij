package com.formacionbdi.microservicios.app.examenes.services;

import com.formacionbdi.microservicios.app.commons.service.CommonService;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;

import java.util.List;

public interface ExamenService extends CommonService<Examen> {


    public List<Examen> findByNombre(String term);

    public  List<Asignatura> findAllAsignaturas();

}
