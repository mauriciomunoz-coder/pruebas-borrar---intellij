package com.formacionbdi.microservicio.respuestas.services;

import com.formacionbdi.microservicio.respuestas.models.entity.Respuesta;
import com.formacionbdi.microservicio.respuestas.models.repository.RespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RespuestaServiceImpl implements RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Override
    @Transactional
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        respuestaRepository.saveAll(respuestas);
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
        return respuestaRepository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
        return respuestaRepository.findExamenesIdsConRespuestasByAlumno(alumnoId);
    }


}
