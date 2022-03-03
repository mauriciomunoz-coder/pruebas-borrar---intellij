package com.formacionbdi.microservicio.respuestas.services;

import com.formacionbdi.microservicio.respuestas.models.entity.Respuesta;

public interface RespuestaService {

    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);

    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);

    public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);


}
