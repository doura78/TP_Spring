package fr.diginamic.hello.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GestionnaireException {

    @ExceptionHandler(VilleException.class)
    public ResponseEntity<String> handlerVilleException(VilleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
