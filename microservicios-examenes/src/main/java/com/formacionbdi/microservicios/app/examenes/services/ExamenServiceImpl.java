package com.formacionbdi.microservicios.app.examenes.services;

import com.formacionbdi.microservicios.app.commons.service.CommonServiceImpl;
import com.formacionbdi.microservicios.app.examenes.models.repository.AsignaturaRepository;
import com.formacionbdi.microservicios.app.examenes.models.repository.ExamenRepository;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements ExamenService{


    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Examen> findByNombre(String term) {
        return repository.findByNombre(term);
    }



    @Transactional(readOnly = true)
    @Override
    public List<Asignatura> findAllAsignaturas() {
        return (List<Asignatura>) asignaturaRepository.findAll();
    }
}
