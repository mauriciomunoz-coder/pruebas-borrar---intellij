package com.formacionbdi.microservicios.app.commons.controller;

import com.formacionbdi.microservicios.app.commons.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommonController<E, S extends CommonService<E>> {

    @Autowired
    protected S service;


    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok().body(service.findAll());
    }


    // retorna una lista de paginables
    @GetMapping("/paginable")
    public ResponseEntity<?> listar(Pageable pageable) {
        return ResponseEntity.ok().body(service.findAll(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<E> o = service.findById(id);

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(o.get());
    }


    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody E entity, BindingResult result) {

        if (result.hasErrors()){
            return this.validar(result);
        }

        E entityDB = service.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(entityDB);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    // metodo que arroja los mensaje de errores de validacion de cada metodo
    protected ResponseEntity<?> validar(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo: " + err.getField() + ' ' + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
