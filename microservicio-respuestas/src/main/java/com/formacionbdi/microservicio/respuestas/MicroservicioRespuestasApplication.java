package com.formacionbdi.microservicio.respuestas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.formacionbdi.microservicio.respuestas.models.entity",
"com.formaciobdi.microservicios.app.commons.alumnos.models.entity",
"com.formacionbdi.microservicios.commons.examenes.models.entity"})
public class MicroservicioRespuestasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioRespuestasApplication.class, args);
    }

}
