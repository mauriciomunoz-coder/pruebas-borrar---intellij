package com.formacionbdi.microservicio.app.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.formaciobdi.microservicios.app.commons.alumnos.models.entity",
        "com.formacionbdi.microservicio.app.cursos.models.entity",
        "com.formacionbdi.microservicios.commons.examenes.models.entity"})
public class MicroservicioCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioCursosApplication.class, args);
    }

}
