package com.formacionbdi.microservicios.app.examenes.controllers;


import com.formacionbdi.microservicios.app.commons.controller.CommonController;
import com.formacionbdi.microservicios.app.examenes.services.ExamenService;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {


    //editar examen junto con sus preguntas
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id) {
        Optional<Examen> o = service.findById(id);

        if (result.hasErrors()){
            return this.validar(result);
        }

        if (!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        // asignamos el nombre al examen
        Examen examenDb = o.get();
        examenDb.setNombre(examen.getNombre());

        List<Pregunta> eliminadas = examenDb.getPreguntas()
                .stream()
                .filter(questions -> !examen.getPreguntas().contains(questions))
                .collect(Collectors.toList());

        eliminadas.forEach(examenDb::removePreguntas);

//        List<Pregunta> eliminadas = examenDb.getPreguntas();
//
//        eliminadas.stream().filter(pdb -> !examen.getPreguntas().contains(pdb)).collect(Collectors.toList());
//        eliminadas.forEach(examenDb::removePreguntas);
//
        examenDb.setPreguntas(examen.getPreguntas());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
    }


    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term){
        return ResponseEntity.ok(service.findByNombre(term));
    }

    @GetMapping("/asignaturas")
    public ResponseEntity<?> listarAsignaturas(){
        return ResponseEntity.ok(service.findAllAsignaturas());
    }
}
