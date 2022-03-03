package com.formacionbdi.microservicio.app.cursos.controllers;


import com.formaciobdi.microservicios.app.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicio.app.cursos.models.entity.Curso;
import com.formacionbdi.microservicio.app.cursos.services.CursoService;
import com.formacionbdi.microservicios.app.commons.controller.CommonController;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {


    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return this.validar(result);
        }

        Optional<Curso> o = this.service.findById(id);

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso cursoDB = o.get();
        cursoDB.setNombre(curso.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDB));
    }


    //asigna un alumno a un curso
    @PutMapping("/{idCurso}/asignar-alumnos")
    public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long idCurso) {
        Optional<Curso> o = this.service.findById(idCurso);

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso cursoDB = o.get();

        alumnos.forEach(a -> {
            cursoDB.addAlumno(a);
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDB));

    }

    //elimina un alumno de un curso especifico
    @PutMapping("/{idCurso}/eliminar-alumno")
    public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long idCurso) {

        Optional<Curso> o = this.service.findById(idCurso);

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso cursoDB = o.get();

        cursoDB.removeAlumno(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDB));

    }


    //muestra los cursos que tiene el alumno
    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id) {
        Curso cursoBD = service.findCursoByAlumnoId(id);

        if (cursoBD != null) {
            List<Long> examenesIds = (List<Long>) service.ObtenerExamenesIdConRespuestasAlumno(id);
            List<Examen> examenes = cursoBD.getExamenes()
                    .stream()
                    .map(examen -> { if (examenesIds.contains(examen.getId())){
                        examen.setRespondido(true);
                    }
                    return examen;
                    }).collect(Collectors.toList());

            cursoBD.setExamenes(examenes);
        }
        return ResponseEntity.ok(cursoBD);
    }


    //asigna un examen al curso
    @PutMapping("/{idCurso}/asignar-examenes")
    public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long idCurso) {
        Optional<Curso> o = this.service.findById(idCurso);

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso cursoDB = o.get();

        examenes.forEach(e -> {
            cursoDB.addExamenes(e);
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDB));

    }


    //elimina un examen del curso especifico
    @PutMapping("/{idCurso}/eliminar-examen")
    public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long idCurso) {
        Optional<Curso> o = this.service.findById(idCurso);

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso cursoDB = o.get();

        cursoDB.removeExamen(examen);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDB));

    }




    @Value("${config.balanceador.test}")
    private String balanceadorTest;


   @GetMapping("/balanceador-test")
    public ResponseEntity<?> balanceadorTest() {

       Map<String, Object> response = new HashMap<String, Object>();
       response.put("balanceador", balanceadorTest);
       response.put("cursos", service.findAll());
        return ResponseEntity.ok(response);
    }
}
