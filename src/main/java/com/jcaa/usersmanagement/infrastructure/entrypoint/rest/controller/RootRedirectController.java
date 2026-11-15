package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import io.swagger.v3.oas.annotations.Hidden;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * La raíz de la API no tiene contenido propio: redirige a la documentación Swagger para que quien
 * abra la URL del servicio vea los endpoints disponibles en lugar de un 401.
 */
@Hidden
@RestController
public class RootRedirectController {

  private static final String ROOT_PATH = "/";
  private static final String SWAGGER_UI_PATH = "/swagger-ui.html";

  @GetMapping(ROOT_PATH)
  public ResponseEntity<Void> redirectToSwagger() {
    return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(SWAGGER_UI_PATH)).build();
  }
}
